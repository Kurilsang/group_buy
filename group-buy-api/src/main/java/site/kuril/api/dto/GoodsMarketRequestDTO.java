package site.kuril.api.dto;

import lombok.Data;

@Data
public class GoodsMarketRequestDTO {

    // 用户ID
    private String userId;
    // 商品ID
    private String goodsId;
    // 渠道
    private String source;
    // 来源
    private String channel;

} 