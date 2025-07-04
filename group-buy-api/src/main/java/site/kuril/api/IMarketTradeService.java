package site.kuril.api;

import site.kuril.api.dto.LockMarketPayOrderRequestDTO;
import site.kuril.api.dto.LockMarketPayOrderResponseDTO;
import site.kuril.api.response.Response;

public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}
