package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 交易规则过滤返回实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeRuleFilterBackEntity {

    // 用户参与活动的订单量
    private Integer userTakeOrderCount;

}
