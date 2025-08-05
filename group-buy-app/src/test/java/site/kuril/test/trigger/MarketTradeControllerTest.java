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

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketTradeControllerTest {

    @Resource
    private IMarketTradeService marketTradeService;

    @Test
    public void test_lockMarketPayOrder() throws InterruptedException {
        LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO = new LockMarketPayOrderRequestDTO();
        lockMarketPayOrderRequestDTO.setUserId("test_user");
        lockMarketPayOrderRequestDTO.setActivityId(100123L);
        lockMarketPayOrderRequestDTO.setGoodsId("9890001");
        lockMarketPayOrderRequestDTO.setSource("s01");
        lockMarketPayOrderRequestDTO.setChannel("c01");
        lockMarketPayOrderRequestDTO.setOutTradeNo("190684113109");
        lockMarketPayOrderRequestDTO.setTeamId("96876129");

        Response<LockMarketPayOrderResponseDTO> response = marketTradeService.lockMarketPayOrder(lockMarketPayOrderRequestDTO);
        log.info("请求参数：{}", JSON.toJSONString(lockMarketPayOrderRequestDTO));
        log.info("测试结果：{}", JSON.toJSONString(response));

        LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO1 = new LockMarketPayOrderRequestDTO();
        lockMarketPayOrderRequestDTO1.setUserId("test_user");
        lockMarketPayOrderRequestDTO1.setActivityId(100123L);
        lockMarketPayOrderRequestDTO1.setGoodsId("9890001");
        lockMarketPayOrderRequestDTO1.setSource("s01");
        lockMarketPayOrderRequestDTO1.setChannel("c01");
        lockMarketPayOrderRequestDTO1.setOutTradeNo("190684113110");
        lockMarketPayOrderRequestDTO1.setTeamId("96876129");

        Response<LockMarketPayOrderResponseDTO> response1 = marketTradeService.lockMarketPayOrder(lockMarketPayOrderRequestDTO1);
        log.info("请求参数：{}", JSON.toJSONString(lockMarketPayOrderRequestDTO1));
        log.info("测试结果：{}", JSON.toJSONString(response1));

        Thread.sleep(Integer.MAX_VALUE);
    }

}
