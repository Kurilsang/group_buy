package site.kuril.domain.trade.service.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kuril.domain.trade.model.entity.GroupBuyActivityEntity;
import site.kuril.domain.trade.model.entity.TradeLockRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import site.kuril.domain.trade.service.filter.ActivityUsabilityRuleFilter;
import site.kuril.domain.trade.service.filter.UserTakeLimitRuleFilter;
import site.kuril.types.design.framework.link.model2.LinkArmory;
import site.kuril.types.design.framework.link.model2.chain.BusinessLinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 交易锁单规则过滤器工厂
 * <p>
 * 负责组装锁单阶段的规则过滤责任链
 * </p>
 */
@Slf4j
@Service
public class TradeRuleFilterFactory {

    @Bean("tradeLockRuleFilter")
    public BusinessLinkedList<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> tradeRuleFilter(ActivityUsabilityRuleFilter activityUsabilityRuleFilter, UserTakeLimitRuleFilter userTakeLimitRuleFilter) {
        // 组装链
        LinkArmory<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易锁单规则过滤链", activityUsabilityRuleFilter, userTakeLimitRuleFilter);
        // 链对象
        return linkArmory.getLogicLink();
    }

    /**
     * 动态上下文
     * <p>
     * 用于在锁单规则责任链中传递数据
     * </p>
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        
        /** 拼团活动实体 */
        private GroupBuyActivityEntity groupBuyActivity;
        
    }

}
