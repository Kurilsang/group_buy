package site.kuril.domain.trade.service.settlement;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.adapter.port.ITradePort;
import site.kuril.domain.trade.adapter.repository.ITradeRepository;
import site.kuril.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import site.kuril.domain.trade.model.entity.*;
import site.kuril.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;
import cn.bugstack.wrench.design.framework.link.model2.chain.BusinessLinkedList;
import site.kuril.types.enums.NotifyTaskHTTPEnumVO;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 交易结算订单服务实现
 * <p>
 * 处理拼团交易的结算业务逻辑
 * </p>
 */
@Slf4j
@Service
public class TradeSettlementOrderService implements ITradeSettlementOrderService {

    @Resource
    private ITradeRepository repository;
    @Resource
    @Qualifier("tradePort")
    private ITradePort port;
    @Resource
    private BusinessLinkedList<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> tradeSettlementRuleFilter;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception {
        log.info("拼团交易-支付订单结算:{} outTradeNo:{}", tradePaySuccessEntity.getUserId(), tradePaySuccessEntity.getOutTradeNo());
        
        // 1. 结算规则过滤
        TradeSettlementRuleFilterBackEntity tradeSettlementRuleFilterBackEntity = tradeSettlementRuleFilter.apply(
                TradeSettlementRuleCommandEntity.builder()
                        .source(tradePaySuccessEntity.getSource())
                        .channel(tradePaySuccessEntity.getChannel())
                        .userId(tradePaySuccessEntity.getUserId())
                        .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                        .outTradeTime(tradePaySuccessEntity.getOutTradeTime())
                        .build(),
                new TradeSettlementRuleFilterFactory.DynamicContext());

        String teamId = tradeSettlementRuleFilterBackEntity.getTeamId();

        // 2. 查询组团信息
        GroupBuyTeamEntity groupBuyTeamEntity = GroupBuyTeamEntity.builder()
                .teamId(tradeSettlementRuleFilterBackEntity.getTeamId())
                .activityId(tradeSettlementRuleFilterBackEntity.getActivityId())
                .targetCount(tradeSettlementRuleFilterBackEntity.getTargetCount())
                .completeCount(tradeSettlementRuleFilterBackEntity.getCompleteCount())
                .lockCount(tradeSettlementRuleFilterBackEntity.getLockCount())
                .status(tradeSettlementRuleFilterBackEntity.getStatus())
                .validStartTime(tradeSettlementRuleFilterBackEntity.getValidStartTime())
                .validEndTime(tradeSettlementRuleFilterBackEntity.getValidEndTime())
                .notifyUrl(tradeSettlementRuleFilterBackEntity.getNotifyUrl())
                .notifyConfigVO(tradeSettlementRuleFilterBackEntity.getNotifyConfigVO())
                .build();

        // 3. 构建聚合对象
        GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate = GroupBuyTeamSettlementAggregate.builder()
                .userEntity(UserEntity.builder().userId(tradePaySuccessEntity.getUserId()).build())
                .groupBuyTeamEntity(groupBuyTeamEntity)
                .tradePaySuccessEntity(tradePaySuccessEntity)
                .build();

        // 4. 拼团交易结算
        NotifyTaskEntity notifyTaskEntity = repository.settlementMarketPayOrder(groupBuyTeamSettlementAggregate);

        // 5. 组队回调处理 - 处理失败也会有定时任务补偿，通过这样的方式，可以减轻任务调度，提高时效性
        if (null != notifyTaskEntity) {
            threadPoolExecutor.execute(() -> {
                Map<String, Integer> notifyResultMap = null;
                try {
                    log.info("拼团交易-执行结算通知回调，指定 teamId:{} notifyTaskEntity:{}", teamId, JSON.toJSONString(notifyTaskEntity));
                    notifyResultMap = execSettlementNotifyJob(notifyTaskEntity);
                    log.info("回调通知拼团完结 result:{}", JSON.toJSONString(notifyResultMap));
                } catch (Exception e) {
                    log.error("回调通知拼团完结失败 result:{}", JSON.toJSONString(notifyResultMap), e);
                }
            });
        }

        // 6. 返回结算信息 - 公司中开发这样的流程时候，会根据外部需要进行值的设置
        return TradePaySettlementEntity.builder()
                .source(tradePaySuccessEntity.getSource())
                .channel(tradePaySuccessEntity.getChannel())
                .userId(tradePaySuccessEntity.getUserId())
                .teamId(teamId)
                .activityId(groupBuyTeamEntity.getActivityId())
                .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                .build();
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob() throws Exception {
        log.info("拼团交易-执行结算通知任务");

        // 查询未执行任务
        List<NotifyTaskEntity> notifyTaskEntityList = repository.queryUnExecutedNotifyTaskList();

        return execSettlementNotifyJob(notifyTaskEntityList);
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob(String teamId) throws Exception {
        log.info("拼团交易-执行结算通知回调，指定 teamId:{}", teamId);
        List<NotifyTaskEntity> notifyTaskEntityList = repository.queryUnExecutedNotifyTaskList(teamId);
        return execSettlementNotifyJob(notifyTaskEntityList);
    }
    
    /**
     * 执行单个回调任务
     */
    public Map<String, Integer> execSettlementNotifyJob(NotifyTaskEntity notifyTaskEntity) throws Exception {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("waitCount", 1);
        resultMap.put("successCount", 0);
        resultMap.put("errorCount", 0);
        resultMap.put("retryCount", 0);
        
        try {
            String result = port.groupBuyNotify(notifyTaskEntity);
            if (NotifyTaskHTTPEnumVO.SUCCESS.getCode().equals(result)) {
                repository.updateNotifyTaskStatusSuccess(notifyTaskEntity.getTeamId());
                resultMap.put("successCount", 1);
                resultMap.put("waitCount", 0);
            } else {
                repository.updateNotifyTaskStatusError(notifyTaskEntity.getTeamId());
                resultMap.put("errorCount", 1);
                resultMap.put("waitCount", 0);
            }
        } catch (Exception e) {
            log.error("执行回调通知失败 teamId:{}", notifyTaskEntity.getTeamId(), e);
            repository.updateNotifyTaskStatusRetry(notifyTaskEntity.getTeamId());
            resultMap.put("retryCount", 1);
            resultMap.put("waitCount", 0);
        }
        
        return resultMap;
    }

    private Map<String, Integer> execSettlementNotifyJob(List<NotifyTaskEntity> notifyTaskEntityList) throws Exception {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("waitCount", notifyTaskEntityList.size());
        resultMap.put("successCount", 0);
        resultMap.put("errorCount", 0);
        resultMap.put("retryCount", 0);

        for (NotifyTaskEntity notifyTaskEntity : notifyTaskEntityList) {
            try {
                String result = port.groupBuyNotify(notifyTaskEntity);
                if (NotifyTaskHTTPEnumVO.SUCCESS.getCode().equals(result)) {
                    repository.updateNotifyTaskStatusSuccess(notifyTaskEntity.getTeamId());
                    resultMap.put("successCount", resultMap.get("successCount") + 1);
                } else {
                    repository.updateNotifyTaskStatusError(notifyTaskEntity.getTeamId());
                    resultMap.put("errorCount", resultMap.get("errorCount") + 1);
                }
            } catch (Exception e) {
                log.error("执行回调通知失败 teamId:{}", notifyTaskEntity.getTeamId(), e);
                repository.updateNotifyTaskStatusRetry(notifyTaskEntity.getTeamId());
                resultMap.put("retryCount", resultMap.get("retryCount") + 1);
            }
        }

        resultMap.put("waitCount", resultMap.get("waitCount") - resultMap.get("successCount") - resultMap.get("errorCount") - resultMap.get("retryCount"));

        return resultMap;
    }

} 