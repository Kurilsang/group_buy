package site.kuril.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementMarketPayOrderResponseDTO {

    // 用户ID
    private String userId;
    // 拼团队伍ID
    private String teamId;
    // 活动ID
    private Long activityId;
    // 外部交易单号
    private String outTradeNo;

} 