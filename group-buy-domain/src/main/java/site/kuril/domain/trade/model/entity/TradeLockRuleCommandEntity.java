package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易锁单规则命令实体
 * <p>
 * 用于交易锁定阶段的规则过滤处理
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeLockRuleCommandEntity {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;

}
