package site.kuril.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.kuril.api.IMarketTradeService;
import site.kuril.api.dto.LockMarketPayOrderRequestDTO;
import site.kuril.api.response.Response;
import site.kuril.api.dto.LockMarketPayOrderResponseDTO;

import javax.annotation.Resource;

/**
 * 交易库存锁定测试
 * <p>
 * 测试独占锁和无锁化设计的库存抢占处理
 * </p>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeStockLockTest {

    @Resource
    private IMarketTradeService marketTradeService;

    /**
     * 测试锁单第1笔 - 首次开团
     */
    @Test
    public void test_lockMarketPayOrder() {
        LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO = new LockMarketPayOrderRequestDTO();
        lockMarketPayOrderRequestDTO.setUserId("test001");
        lockMarketPayOrderRequestDTO.setTeamId(null); // 首次开团，无teamId
        lockMarketPayOrderRequestDTO.setActivityId(100123L);
        lockMarketPayOrderRequestDTO.setGoodsId("9890001");
        lockMarketPayOrderRequestDTO.setSource("s01");
        lockMarketPayOrderRequestDTO.setChannel("c01");
        lockMarketPayOrderRequestDTO.setNotifyUrl("http://127.0.0.1:8091/api/v1/test/group_buy_notify");
        lockMarketPayOrderRequestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        
        Response<LockMarketPayOrderResponseDTO> lockMarketPayOrderResponseDTOResponse = 
            marketTradeService.lockMarketPayOrder(lockMarketPayOrderRequestDTO);
        
        log.info("测试结果 req:{} res:{}", JSON.toJSONString(lockMarketPayOrderRequestDTO), 
            JSON.toJSONString(lockMarketPayOrderResponseDTOResponse));
    }

    /**
     * 测试完整的拼团流程 - 首次开团 + 参与拼团
     * 测试组队库存占用规则过滤器的无锁化设计
     */
    @Test
    public void test_complete_group_buy_flow() {
        // 第一步：首次开团
        LockMarketPayOrderRequestDTO firstRequest = new LockMarketPayOrderRequestDTO();
        firstRequest.setUserId("test001");
        firstRequest.setTeamId(null); // 首次开团，无teamId
        firstRequest.setActivityId(100123L);
        firstRequest.setGoodsId("9890001");
        firstRequest.setSource("s01");
        firstRequest.setChannel("c01");
        firstRequest.setNotifyUrl("http://127.0.0.1:8091/api/v1/test/group_buy_notify");
        firstRequest.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        
        Response<LockMarketPayOrderResponseDTO> firstResponse = 
            marketTradeService.lockMarketPayOrder(firstRequest);
        
        log.info("首次开团测试结果 req:{} res:{}", JSON.toJSONString(firstRequest), 
            JSON.toJSONString(firstResponse));
        
        // 检查首次开团是否成功
        if (!"0000".equals(firstResponse.getCode()) || firstResponse.getData() == null) {
            log.error("首次开团失败，无法继续测试");
            return;
        }
        
        String teamId = firstResponse.getData().getTeamId();
        log.info("获取到teamId: {}", teamId);
        
        // 第二步：参与已有拼团
        LockMarketPayOrderRequestDTO secondRequest = new LockMarketPayOrderRequestDTO();
        secondRequest.setUserId("test002");
        secondRequest.setTeamId(teamId); // 使用第一步返回的teamId
        secondRequest.setActivityId(100123L);
        secondRequest.setGoodsId("9890001");
        secondRequest.setSource("s01");
        secondRequest.setChannel("c01");
        secondRequest.setNotifyUrl("http://127.0.0.1:8091/api/v1/test/group_buy_notify");
        secondRequest.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        
        Response<LockMarketPayOrderResponseDTO> secondResponse = 
            marketTradeService.lockMarketPayOrder(secondRequest);
        
        log.info("参与拼团测试结果 req:{} res:{}", JSON.toJSONString(secondRequest), 
            JSON.toJSONString(secondResponse));
    }

    /**
     * 测试并发库存抢占
     * 模拟多个用户同时参与拼团，验证无锁化库存抢占机制
     */
    @Test
    public void test_concurrent_stock_occupy() {
        // 第一步：创建一个拼团
        LockMarketPayOrderRequestDTO createTeamRequest = new LockMarketPayOrderRequestDTO();
        createTeamRequest.setUserId("creator001");
        createTeamRequest.setTeamId(null); // 首次开团
        createTeamRequest.setActivityId(100123L);
        createTeamRequest.setGoodsId("9890001");
        createTeamRequest.setSource("s01");
        createTeamRequest.setChannel("c01");
        createTeamRequest.setNotifyUrl("http://127.0.0.1:8091/api/v1/test/group_buy_notify");
        createTeamRequest.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        
        Response<LockMarketPayOrderResponseDTO> createTeamResponse = 
            marketTradeService.lockMarketPayOrder(createTeamRequest);
        
        log.info("创建拼团结果: {}", JSON.toJSONString(createTeamResponse));
        
        if (!"0000".equals(createTeamResponse.getCode()) || createTeamResponse.getData() == null) {
            log.error("创建拼团失败，无法继续并发测试");
            return;
        }
        
        String teamId = createTeamResponse.getData().getTeamId();
        log.info("并发测试使用teamId: {}", teamId);
        
        // 第二步：模拟多个用户并发抢占（目标量为3，已有1个，还能抢占2个）
        for (int i = 1; i <= 5; i++) {
            LockMarketPayOrderRequestDTO requestDTO = new LockMarketPayOrderRequestDTO();
            requestDTO.setUserId("user_" + String.format("%03d", i));
            requestDTO.setTeamId(teamId);
            requestDTO.setActivityId(100123L);
            requestDTO.setGoodsId("9890001");
            requestDTO.setSource("s01");
            requestDTO.setChannel("c01");
            requestDTO.setNotifyUrl("http://127.0.0.1:8091/api/v1/test/group_buy_notify");
            requestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));
            
            Response<LockMarketPayOrderResponseDTO> response = 
                marketTradeService.lockMarketPayOrder(requestDTO);
            
            log.info("并发测试 - 用户{} 结果:{}", i, JSON.toJSONString(response));
            
            // 如果拼团已满，后续请求应该失败
            if ("E0006".equals(response.getCode()) || "E0008".equals(response.getCode())) {
                log.info("拼团已满或库存抢占失败，符合预期");
            }
        }
    }

} 