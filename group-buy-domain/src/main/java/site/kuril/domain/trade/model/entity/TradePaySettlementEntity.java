package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易支付结算实体
 * <p>
 * 表示结算处理后返回的结果信息
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradePaySettlementEntity {

    /** 渠道来源 */
    private String source;
    /** 渠道编号 */
    private String channel;
    /** 用户ID */
    private String userId;
    /** 拼团队伍ID */
    private String teamId;
    /** 活动ID */
    private Long activityId;
    /** 外部交易单号 */
    private String outTradeNo;

} 