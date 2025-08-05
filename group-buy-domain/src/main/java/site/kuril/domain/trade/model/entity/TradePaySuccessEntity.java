package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 交易支付成功实体
 * <p>
 * 表示外部支付系统通知的支付成功信息
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradePaySuccessEntity {

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