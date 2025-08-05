package site.kuril.domain.trade.service.settlement;

import site.kuril.domain.trade.model.entity.TradePaySettlementEntity;
import site.kuril.domain.trade.model.entity.TradePaySuccessEntity;

import java.util.Map;

/**
 * 交易结算订单服务接口
 * <p>
 * 提供拼团交易结算的核心业务能力
 * </p>
 */
public interface ITradeSettlementOrderService {

    /**
     * 结算营销支付订单
     * <p>
     * 处理支付成功后的结算流程，包括规则验证和状态更新
     * </p>
     *
     * @param tradePaySuccessEntity 交易支付成功实体
     * @return 交易支付结算实体
     * @throws Exception 当结算过程出现异常时抛出
     */
    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception;

    /**
     * 执行结算回调通知任务
     * <p>
     * 查询所有未执行的回调任务并执行通知
     * </p>
     *
     * @return 执行结果统计 waitCount:等待处理数量, successCount:成功数量, errorCount:失败数量, retryCount:重试数量
     * @throws Exception 当执行过程出现异常时抛出
     */
    Map<String, Integer> execSettlementNotifyJob() throws Exception;

    /**
     * 执行指定团队的结算回调通知任务
     * <p>
     * 查询指定团队的未执行回调任务并执行通知
     * </p>
     *
     * @param teamId 团队ID
     * @return 执行结果统计 waitCount:等待处理数量, successCount:成功数量, errorCount:失败数量, retryCount:重试数量
     * @throws Exception 当执行过程出现异常时抛出
     */
    Map<String, Integer> execSettlementNotifyJob(String teamId) throws Exception;

} 