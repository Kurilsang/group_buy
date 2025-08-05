package site.kuril.domain.activity.service;

import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.kuril.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拼团集市首页服务实现
 * <p>
 * 提供拼团商品的试算和展示功能，通过策略模式支持不同类型的活动处理
 * </p>
 * 
 * @author system
 * @version 1.0
 * @since 2024-01-01
 */
@Slf4j
@Service
public class IndexGroupBuyMarketServiceImpl implements IIndexGroupBuyMarketService {

    /** 默认活动策略工厂 */
    @Resource
    private DefaultActivityStrategyFactory activityStrategyFactory;

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
}
