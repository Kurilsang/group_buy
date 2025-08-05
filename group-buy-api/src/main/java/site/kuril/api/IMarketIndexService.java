package site.kuril.api;

import site.kuril.api.dto.GoodsMarketRequestDTO;
import site.kuril.api.dto.GoodsMarketResponseDTO;
import site.kuril.api.response.Response;

public interface IMarketIndexService {

    /**
     * 查询拼团营销配置
     *
     * @param goodsMarketRequestDTO 营销商品信息
     * @return 营销配置信息
     */
    Response<GoodsMarketResponseDTO> queryGroupBuyMarketConfig(GoodsMarketRequestDTO goodsMarketRequestDTO);

} 