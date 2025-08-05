package site.kuril.domain.trade.service;

import site.kuril.domain.trade.adapter.repository.ITradeRepository;
import site.kuril.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import site.kuril.domain.trade.model.entity.*;
import site.kuril.domain.trade.model.valobj.GroupBuyProgressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.service.factory.TradeRuleFilterFactory;
import site.kuril.types.design.framework.link.model2.chain.BusinessLinkedList;

import javax.annotation.Resource;

/**
 * 交易订单服务实现
 * <p>
 * 负责处理团购交易订单的核心业务逻辑，包括：
 * - 未支付订单查询
 * - 拼团进度追踪
 * - 营销订单锁定与风控过滤
 * </p>
 */
@Slf4j
@Service
public class TradeOrderService implements ITradeOrderService {

    /** 交易数据仓储 */
    @Resource
    private ITradeRepository tradeDataRepository;

    /** 交易规则过滤链 */
    @Resource
    private BusinessLinkedList<TradeLockRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> businessRuleChain;

    /**
     * 根据外部交易号查询未支付的营销订单
     * <p>
     * 用于检查用户是否已经有相同外部交易号的未支付订单，避免重复下单
     * </p>
     *
     * @param customerId      客户用户ID
     * @param externalTradeNo 外部交易流水号
     * @return 未支付的营销支付订单实体，如果不存在则返回null
     */
    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String customerId, String externalTradeNo) {
        log.info("开始查询未支付营销订单 - 客户ID: {}, 外部交易号: {}", customerId, externalTradeNo);
        
        MarketPayOrderEntity unpaidOrder = tradeDataRepository.queryMarketPayOrderEntityByOutTradeNo(customerId, externalTradeNo);
        
        if (unpaidOrder != null) {
            log.info("发现未支付营销订单 - 客户ID: {}, 订单ID: {}", customerId, unpaidOrder.getOrderId());
        } else {
            log.debug("未发现重复的未支付订单 - 客户ID: {}, 外部交易号: {}", customerId, externalTradeNo);
    }

        return unpaidOrder;
    }

    /**
     * 查询指定团队的拼团进度
     * <p>
     * 获取拼团活动的当前进度信息，包括已参与人数、目标人数等
     * </p>
     *
     * @param groupTeamId 拼团队伍标识
     * @return 拼团进度值对象
     */
    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String groupTeamId) {
        log.info("查询拼团进度 - 团队ID: {}", groupTeamId);
        
        GroupBuyProgressVO progressInfo = tradeDataRepository.queryGroupBuyProgress(groupTeamId);
        
        if (progressInfo != null) {
            log.info("拼团进度查询成功 - 团队ID: {}, 当前进度: {}/{}, 锁单数量: {}", 
                groupTeamId, progressInfo.getCompleteCount(), progressInfo.getTargetCount(), progressInfo.getLockCount());
        }
        
        return progressInfo;
    }

    /**
     * 锁定营销优惠支付订单
     * <p>
     * 执行订单锁定的核心流程：
     * 1. 通过规则过滤链进行风控检查
     * 2. 获取用户参与次数信息
     * 3. 构建订单聚合对象
     * 4. 执行订单锁定操作
     * </p>
     *
     * @param customerInfo    客户信息实体
     * @param activityInfo    支付活动实体
     * @param discountInfo    优惠折扣实体
     * @return 锁定成功的营销支付订单
     * @throws Exception 当规则过滤失败或订单锁定失败时抛出异常
     */
    @Override
    public MarketPayOrderEntity lockMarketPayOrder(UserEntity customerInfo, PayActivityEntity activityInfo, PayDiscountEntity discountInfo) throws Exception {
        final String customerId = customerInfo.getUserId();
        final Long activityId = activityInfo.getActivityId();
        final String goodsId = discountInfo.getGoodsId();
        
        log.info("开始锁定营销优惠支付订单 - 客户ID: {}, 活动ID: {}, 商品ID: {}", customerId, activityId, goodsId);
        
        try {
            // 第一步：执行交易规则过滤检查
            TradeLockRuleFilterBackEntity riskControlResult = executeTradeRuleValidation(customerId, activityId);

            // 第二步：获取用户参与次数信息（用于数据库唯一索引约束）
            Integer participationCount = extractUserParticipationCount(riskControlResult);

            // 第三步：构建业务聚合对象
            GroupBuyOrderAggregate orderAggregate = buildOrderAggregate(customerInfo, activityInfo, discountInfo, participationCount);
            
            // 第四步：执行订单锁定操作
            MarketPayOrderEntity lockedOrder = tradeDataRepository.lockMarketPayOrder(orderAggregate);
            
            log.info("营销优惠支付订单锁定成功 - 客户ID: {}, 订单ID: {}", customerId, lockedOrder.getOrderId());
            return lockedOrder;
            
        } catch (Exception exception) {
            log.error("营销优惠支付订单锁定失败 - 客户ID: {}, 活动ID: {}, 异常信息: {}", 
                customerId, activityId, exception.getMessage(), exception);
            throw exception;
        }
    }

    /**
     * 执行交易规则验证
     * 
     * @param customerId 客户ID
     * @param activityId 活动ID
     * @return 规则过滤结果
     * @throws Exception 当规则验证失败时抛出异常
     */
    private TradeLockRuleFilterBackEntity executeTradeRuleValidation(String customerId, Long activityId) throws Exception {
        TradeLockRuleCommandEntity ruleCommand = TradeLockRuleCommandEntity.builder()
                .activityId(activityId)
                .userId(customerId)
                .build();

        TradeRuleFilterFactory.DynamicContext filterContext = new TradeRuleFilterFactory.DynamicContext();
        
        return businessRuleChain.apply(ruleCommand, filterContext);
    }

    /**
     * 提取用户参与次数
     * 
     * @param filterResult 规则过滤结果
     * @return 用户参与次数
     */
    private Integer extractUserParticipationCount(TradeLockRuleFilterBackEntity filterResult) {
        return filterResult.getUserTakeOrderCount();
    }

    /**
     * 构建订单聚合对象
     * 
     * @param customerInfo       客户信息
     * @param activityInfo       活动信息
     * @param discountInfo       折扣信息
     * @param participationCount 参与次数
     * @return 订单聚合对象
     */
    private GroupBuyOrderAggregate buildOrderAggregate(UserEntity customerInfo, PayActivityEntity activityInfo, 
                                                      PayDiscountEntity discountInfo, Integer participationCount) {
        return GroupBuyOrderAggregate.builder()
                .userEntity(customerInfo)
                .payActivityEntity(activityInfo)
                .payDiscountEntity(discountInfo)
                .userTakeOrderCount(participationCount)
                .build();
    }
}
