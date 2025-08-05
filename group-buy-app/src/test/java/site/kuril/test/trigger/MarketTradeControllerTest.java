package site.kuril.test.trigger;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.kuril.api.IMarketTradeService;
import site.kuril.api.dto.LockMarketPayOrderRequestDTO;
import site.kuril.api.dto.LockMarketPayOrderResponseDTO;
import site.kuril.api.response.Response;

import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketTradeControllerTest {

    @Resource
    private IMarketTradeService marketTradeService;

    @Test
    public void test_lockMarketPayOrder() {
        LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO = new LockMarketPayOrderRequestDTO();
        lockMarketPayOrderRequestDTO.setUserId("test_user");
        lockMarketPayOrderRequestDTO.setTeamId(null);
        lockMarketPayOrderRequestDTO.setActivityId(100123L);
        lockMarketPayOrderRequestDTO.setGoodsId("9890001");

        lockMarketPayOrderRequestDTO.setSource("s01");
        lockMarketPayOrderRequestDTO.setChannel("c01");
        lockMarketPayOrderRequestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        
        Response<LockMarketPayOrderResponseDTO> lockMarketPayOrderResponseDTOResponse = marketTradeService.lockMarketPayOrder(lockMarketPayOrderRequestDTO);
        log.info("测试结果 req:{} res:{}", JSON.toJSONString(lockMarketPayOrderRequestDTO), JSON.toJSONString(lockMarketPayOrderResponseDTOResponse));
    }

}
