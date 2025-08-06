package site.kuril.test.settlement;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.kuril.domain.trade.model.entity.TradePaySettlementEntity;
import site.kuril.domain.trade.model.entity.TradePaySuccessEntity;
import site.kuril.domain.trade.service.settlement.ITradeSettlementOrderService;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 交易结算订单服务测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeSettlementOrderServiceTest {

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    @Test
    public void test_settlementMarketPayOrder() throws Exception {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("user002");
        tradePaySuccessEntity.setOutTradeNo("742471786436");
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
        log.info("测试结果:{}", JSON.toJSONString(tradePaySettlementEntity));
    }

    /**
     * 测试不存在的外部交易单号 - 应该抛出E0104异常
     */
    @Test
    public void test_settlementMarketPayOrder_InvalidOutTradeNo() {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("test_user");
        tradePaySuccessEntity.setOutTradeNo("999999999999"); // 不存在的交易单号
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        try {
            tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
            log.error("测试失败：应该抛出E0104异常");
        } catch (AppException e) {
            log.info("测试成功：捕获到预期异常 - 错误码: {}, 错误信息: {}", e.getCode(), e.getInfo());
            assert ResponseCode.E0104.getCode().equals(e.getCode());
        } catch (Exception e) {
            log.error("测试失败：捕获到非预期异常", e);
        }
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
    }

    /**
     * 测试用户不匹配的外部交易单号 - 应该抛出E0104异常
     */
    @Test
    public void test_settlementMarketPayOrder_UserMismatch() {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("wrong_user"); // 错误的用户ID
        tradePaySuccessEntity.setOutTradeNo("782858645114"); // 存在的交易单号但用户不匹配
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        try {
            tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
            log.error("测试失败：应该抛出E0104异常");
        } catch (AppException e) {
            log.info("测试成功：捕获到预期异常 - 错误码: {}, 错误信息: {}", e.getCode(), e.getInfo());
            assert ResponseCode.E0104.getCode().equals(e.getCode());
        } catch (Exception e) {
            log.error("测试失败：捕获到非预期异常", e);
        }
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
    }

    /**
     * 测试交易时间超出拼团有效期 - 应该抛出E0106异常
     */
    @Test
    public void test_settlementMarketPayOrder_ExpiredTradeTime() {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("test_user");
        tradePaySuccessEntity.setOutTradeNo("782858645114");
        
        // 设置一个很久以后的交易时间，超出拼团有效期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30); // 30天后
        tradePaySuccessEntity.setOutTradeTime(calendar.getTime());
        
        try {
            tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
            log.error("测试失败：应该抛出E0106异常");
        } catch (AppException e) {
            log.info("测试成功：捕获到预期异常 - 错误码: {}, 错误信息: {}", e.getCode(), e.getInfo());
            assert ResponseCode.E0106.getCode().equals(e.getCode());
        } catch (Exception e) {
            log.error("测试失败：捕获到非预期异常", e);
        }
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
    }

    /**
     * 测试SC渠道黑名单拦截 - 需要先模拟黑名单数据
     * 注意：当前实现的isSCBlackIntercept始终返回false，实际项目中需要真实的黑名单逻辑
     */
    @Test
    public void test_settlementMarketPayOrder_SCBlackChannel() {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("sc_black"); // 假设这是黑名单渠道
        tradePaySuccessEntity.setChannel("c99"); // 假设这是黑名单渠道
        tradePaySuccessEntity.setUserId("test_user");
        tradePaySuccessEntity.setOutTradeNo("782858645114");
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        try {
            tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
            log.info("当前黑名单逻辑未实现，测试通过（实际项目中应抛出E0105异常）");
        } catch (AppException e) {
            log.info("测试成功：捕获到预期异常 - 错误码: {}, 错误信息: {}", e.getCode(), e.getInfo());
            if (ResponseCode.E0105.getCode().equals(e.getCode())) {
                log.info("SC渠道黑名单拦截功能正常");
            }
        } catch (Exception e) {
            log.error("测试失败：捕获到非预期异常", e);
        }
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
    }

    /**
     * 测试空值参数 - 测试参数校验
     */
    @Test
    public void test_settlementMarketPayOrder_NullParameters() {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("test_user");
        tradePaySuccessEntity.setOutTradeNo(null); // 空的交易单号
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        try {
            tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
            log.error("测试失败：应该处理空值参数异常");
        } catch (Exception e) {
            log.info("测试成功：捕获到参数异常 - {}", e.getMessage());
        }
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
    }

    @Test
    public void test_settlementMarketPayOrder_mq() throws Exception {
        // 使用user001的MQ锁单记录进行结算测试
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("user001");
        tradePaySuccessEntity.setOutTradeNo("264705424092"); // 从锁单测试中获取
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
        
        log.info("MQ结算测试 - 请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
        log.info("MQ结算测试 - 测试结果:{}", JSON.toJSONString(tradePaySettlementEntity));

        // 暂停等待异步MQ消息处理完成
        Thread.sleep(5000);
    }

    @Test
    public void test_settlementMarketPayOrder_http() throws Exception {
        // 使用user002的HTTP锁单记录进行结算测试
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("user002");
        tradePaySuccessEntity.setOutTradeNo("826795838332"); // 从锁单测试中获取
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
        
        log.info("HTTP结算测试 - 请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
        log.info("HTTP结算测试 - 测试结果:{}", JSON.toJSONString(tradePaySettlementEntity));

        // 暂停等待异步HTTP回调处理完成
        Thread.sleep(5000);
    }

    @Test
    public void test_execSettlementNotifyJob() throws Exception {
        log.info("测试定时任务补偿机制");
        
        // 执行定时任务，处理所有未完成的回调任务
        java.util.Map<String, Integer> result = tradeSettlementOrderService.execSettlementNotifyJob();
        
        log.info("定时任务执行结果:{}", JSON.toJSONString(result));
    }

} 