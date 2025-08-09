package site.kuril.domain.activity.service;

import site.kuril.domain.activity.adapter.repository.IActivityRepository;
import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import site.kuril.domain.activity.model.valobj.TeamStatisticVO;
import site.kuril.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 拼团集市首页服务实现
 * <p>
 * 提供拼团商品的试算和展示功能，通过策略模式支持不同类型的活动处理
 * </p>
 */
@Slf4j
@Service
public class IndexGroupBuyMarketServiceImpl implements IIndexGroupBuyMarketService {

    /** 默认活动策略工厂 */
    @Resource
    private DefaultActivityStrategyFactory activityStrategyFactory;

    /** 活动仓储 */
    @Resource
    private IActivityRepository activityRepository;

    /**
     * 执行拼团集市商品试算
     * <p>
     * 根据输入的商品信息，通过策略处理器计算拼团价格、折扣等信息
     * </p>
     *
     * @param productInfo 市场商品信息实体
     * @return 试算结果实体，包含价格、折扣等信息
     * @throws Exception 当策略处理失败时抛出异常
     */
    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity productInfo) throws Exception {
        final String customerId = productInfo.getUserId();
        final String goodsId = productInfo.getGoodsId();
        final Long activityId = productInfo.getActivityId();
        
        log.info("开始执行拼团集市商品试算 - 客户ID: {}, 商品ID: {}, 活动ID: {}", 
            customerId, goodsId, activityId);

        try {
            // 获取活动策略处理器
            StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyProcessor = 
                activityStrategyFactory.strategyHandler();

            // 创建动态上下文
            DefaultActivityStrategyFactory.DynamicContext processingContext = 
                new DefaultActivityStrategyFactory.DynamicContext();

            // 执行策略计算
            TrialBalanceEntity calculationResult = strategyProcessor.apply(productInfo, processingContext);

            log.info("拼团集市商品试算完成 - 客户ID: {}, 商品ID: {}, 原价: {}, 支付价: {}, 折扣价: {}", 
                customerId, goodsId, 
                calculationResult.getOriginalPrice(), 
                calculationResult.getPayPrice(),
                calculationResult.getDeductionPrice());

            return calculationResult;
            
        } catch (Exception exception) {
            log.error("拼团集市商品试算失败 - 客户ID: {}, 商品ID: {}, 异常信息: {}", 
                customerId, goodsId, exception.getMessage(), exception);
            throw new Exception("商品试算处理失败: " + exception.getMessage(), exception);
    }
    }

    @Override
    public List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, Integer ownerCount, Integer randomCount) {
        log.info("查询进行中的用户拼团订单详情列表 - 活动ID: {}, 用户ID: {}, 个人数据: {}, 随机数据: {}", 
            activityId, userId, ownerCount, randomCount);

        List<UserGroupBuyOrderDetailEntity> resultList = new ArrayList<>();

        try {
            // 1. 先查询用户自己的拼团记录
            if (ownerCount > 0) {
                List<UserGroupBuyOrderDetailEntity> ownerList = activityRepository.queryInProgressUserGroupBuyOrderDetailListByOwner(activityId, userId, ownerCount);
                if (ownerList != null && !ownerList.isEmpty()) {
                    resultList.addAll(ownerList);
                    log.info("查询到用户{}自己的拼团记录数量: {}", userId, ownerList.size());
                }
            }

            // 2. 再查询其他用户的随机拼团记录
            if (randomCount > 0) {
                List<UserGroupBuyOrderDetailEntity> randomList = activityRepository.queryInProgressUserGroupBuyOrderDetailListByRandom(activityId, userId, randomCount);
                if (randomList != null && !randomList.isEmpty()) {
                    resultList.addAll(randomList);
                    log.info("查询到随机拼团记录数量: {}", randomList.size());
                }
            }

        } catch (Exception e) {
            log.error("查询拼团订单详情失败 - 活动ID: {}, 用户ID: {}", activityId, userId, e);
            // 发生异常时返回空列表，避免影响主流程
        }

        log.info("查询到拼团订单详情总数量: {}", resultList.size());
        return resultList;
    }

    @Override
    public TeamStatisticVO queryTeamStatisticByActivityId(Long activityId) {
        log.info("根据活动ID查询团队统计信息 - 活动ID: {}", activityId);

        try {
            // 查询团队统计信息
            TeamStatisticVO statistic = activityRepository.queryTeamStatisticByActivityId(activityId);
            
            if (statistic != null) {
                log.info("团队统计信息 - 总团队: {}, 成功团队: {}, 总人数: {}", 
                    statistic.getAllTeamCount(), statistic.getAllTeamCompleteCount(), statistic.getAllTeamUserCount());
                return statistic;
            } else {
                log.warn("未查询到活动{}的团队统计信息，返回默认值", activityId);
                // 返回默认统计信息
                return TeamStatisticVO.builder()
                        .allTeamCount(0)
                        .allTeamCompleteCount(0)
                        .allTeamUserCount(0)
                        .build();
            }

        } catch (Exception e) {
            log.error("查询团队统计信息失败 - 活动ID: {}", activityId, e);
            // 发生异常时返回默认统计信息
            return TeamStatisticVO.builder()
                    .allTeamCount(0)
                    .allTeamCompleteCount(0)
                    .allTeamUserCount(0)
                    .build();
    }
    }
}
