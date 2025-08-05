package site.kuril.domain.trade.service.settlement;

import site.kuril.domain.trade.model.entity.TradePaySettlementEntity;
import site.kuril.domain.trade.model.entity.TradePaySuccessEntity;

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

} 