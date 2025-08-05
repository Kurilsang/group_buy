package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 交易结算规则命令实体
 * <p>
 * 用于交易结算阶段的规则过滤处理
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeSettlementRuleCommandEntity {

    /** 渠道来源 */
    private String source;
    /** 渠道编号 */
    private String channel;
    /** 用户ID */
    private String userId;
    /** 外部交易单号 */
    private String outTradeNo;
    /** 外部交易时间 */
    private Date outTradeTime;

} 