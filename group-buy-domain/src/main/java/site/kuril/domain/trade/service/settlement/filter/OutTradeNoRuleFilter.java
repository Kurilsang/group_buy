package site.kuril.domain.trade.service.settlement.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.kuril.domain.trade.adapter.repository.ITradeRepository;
import site.kuril.domain.trade.model.entity.MarketPayOrderEntity;
import site.kuril.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import site.kuril.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import cn.bugstack.wrench.design.framework.link.model2.handler.ILogicHandler;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;

/**
 * 外部交易单号有效性规则过滤器
 * <p>
 * 检查外部交易单号是否存在且有效
 * </p>
 */
@Slf4j
@Component
public class OutTradeNoRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-外部单号校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // 查询拼团信息
        MarketPayOrderEntity marketPayOrderEntity = repository.queryMarketPayOrderEntityByOutTradeNo(requestParameter.getUserId(), requestParameter.getOutTradeNo());

        if (null == marketPayOrderEntity) {
            log.error("不存在的外部交易单号或用户已退单，不需要做支付订单结算:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
            throw new AppException(ResponseCode.E0104);
        }

        dynamicContext.setMarketPayOrderEntity(marketPayOrderEntity);

        return next(requestParameter, dynamicContext);
    }

} 