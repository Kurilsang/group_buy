package site.kuril.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SettlementMarketPayOrderRequestDTO {

    // 用户ID
    private String userId;
    // 渠道
    private String source;
    // 来源
    private String channel;
    // 外部交易单号
    private String outTradeNo;
    // 外部交易时间
    private Date outTradeTime;

} 