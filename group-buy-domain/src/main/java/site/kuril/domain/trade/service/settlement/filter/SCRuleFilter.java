package site.kuril.domain.trade.service.settlement.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.adapter.repository.ITradeRepository;
import site.kuril.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import site.kuril.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import site.kuril.types.design.framework.link.model2.handler.ILogicHandler;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;

/**
 * SC渠道黑名单规则过滤器
 * <p>
 * 检查渠道是否在黑名单中，如果在黑名单则拦截
 * </p>
 */
@Slf4j
@Service
public class SCRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-渠道黑名单校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // sc 渠道黑名单拦截
        boolean intercept = repository.isSCBlackIntercept(requestParameter.getSource(), requestParameter.getChannel());
        if (intercept) {
            log.error("{}{} 渠道黑名单拦截", requestParameter.getSource(), requestParameter.getChannel());
            throw new AppException(ResponseCode.E0105);
        }

        return next(requestParameter, dynamicContext);
    }

} 