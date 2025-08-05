package site.kuril.domain.trade.model.entity;

import site.kuril.domain.trade.model.valobj.TradeOrderStatusEnumVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 营销支付订单实体
 * <p>
 * 表示拼团营销活动中的支付订单信息
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketPayOrderEntity {

    /** 预购订单ID */
    private String orderId;
    /** 拼团队伍ID */
    private String teamId;
    /** 折扣金额 */
    private BigDecimal deductionPrice;
    /** 交易订单状态枚举 */
    private TradeOrderStatusEnumVO tradeOrderStatusEnumVO;

}
