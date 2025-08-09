package site.kuril.domain.trade.service.settlement.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.model.entity.MarketPayOrderEntity;
import site.kuril.domain.trade.model.entity.GroupBuyTeamEntity;
import site.kuril.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import site.kuril.domain.trade.service.settlement.filter.SCRuleFilter;
import site.kuril.domain.trade.service.settlement.filter.OutTradeNoRuleFilter;
import site.kuril.domain.trade.service.settlement.filter.SettableRuleFilter;
import site.kuril.domain.trade.service.settlement.filter.EndRuleFilter;
import cn.bugstack.wrench.design.framework.link.model2.LinkArmory;
import cn.bugstack.wrench.design.framework.link.model2.chain.BusinessLinkedList;

/**
 * 交易结算规则过滤器工厂
 * <p>
 * 负责组装结算阶段的规则过滤责任链
 * </p>
 */
@Slf4j
@Service
public class TradeSettlementRuleFilterFactory {

    @Bean("tradeSettlementRuleFilter")
    public BusinessLinkedList<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> tradeSettlementRuleFilter(
            SCRuleFilter scRuleFilter,
            OutTradeNoRuleFilter outTradeNoRuleFilter,
            SettableRuleFilter settableRuleFilter,
            EndRuleFilter endRuleFilter) {

        // 组装链
        LinkArmory<TradeSettlementRuleCommandEntity, DynamicContext, TradeSettlementRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易结算规则过滤链", scRuleFilter, outTradeNoRuleFilter, settableRuleFilter, endRuleFilter);

        // 链对象
        return linkArmory.getLogicLink();
    }

    /**
     * 动态上下文
     * <p>
     * 用于在责任链中传递数据
     * </p>
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        
        /** 营销支付订单实体 */
        private MarketPayOrderEntity marketPayOrderEntity;
        /** 拼团队伍实体 */
        private GroupBuyTeamEntity groupBuyTeamEntity;
        
    }

} 