package site.kuril.domain.trade.service.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kuril.domain.trade.model.entity.GroupBuyActivityEntity;
import site.kuril.domain.trade.model.entity.TradeLockRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import site.kuril.domain.trade.service.filter.ActivityUsabilityRuleFilter;
import site.kuril.domain.trade.service.filter.TeamStockOccupyRuleFilter;
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
public class TradeLockRuleFilterFactory {

    @Bean("tradeRuleFilter")
    public BusinessLinkedList<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> tradeRuleFilter(
            ActivityUsabilityRuleFilter activityUsabilityRuleFilter,
            UserTakeLimitRuleFilter userTakeLimitRuleFilter,
            TeamStockOccupyRuleFilter teamStockOccupyRuleFilter) {

        // 组装链
        LinkArmory<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易规则过滤链",
                        activityUsabilityRuleFilter,
                        userTakeLimitRuleFilter,
                        teamStockOccupyRuleFilter);

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
        
        /** 用户参与次数 */
        private Integer userTakeOrderCount;
        
        /**
         * 生成团队库存Key
         * @param teamId 团队ID
         * @return 团队库存Key
         */
        public String generateTeamStockKey(String teamId) {
            return "team_stock_" + teamId;
        }
        
        /**
         * 生成恢复团队库存Key
         * @param teamId 团队ID  
         * @return 恢复团队库存Key
         */
        public String generateRecoveryTeamStockKey(String teamId) {
            return "recovery_team_stock_" + teamId;
        }
        
    }

}
