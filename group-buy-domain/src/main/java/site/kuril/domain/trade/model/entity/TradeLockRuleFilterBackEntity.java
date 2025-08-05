package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易锁单规则过滤返回实体
 * <p>
 * 用于交易锁定阶段的规则过滤结果返回
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeLockRuleFilterBackEntity {

    // 用户参与活动的订单量
    private Integer userTakeOrderCount;

}
