package site.kuril.api;

import site.kuril.api.dto.LockMarketPayOrderRequestDTO;
import site.kuril.api.dto.LockMarketPayOrderResponseDTO;
import site.kuril.api.dto.SettlementMarketPayOrderRequestDTO;
import site.kuril.api.dto.SettlementMarketPayOrderResponseDTO;
import site.kuril.api.response.Response;

public interface IMarketTradeService {

    /**
     * 营销锁单
     *
     * @param requestDTO 锁单商品信息
     * @return 锁单结果信息
     */
    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO requestDTO);

    /**
     * 营销结算
     *
     * @param requestDTO 结算商品信息
     * @return 结算结果信息
     */
    Response<SettlementMarketPayOrderResponseDTO> settlementMarketPayOrder(SettlementMarketPayOrderRequestDTO requestDTO);

}
