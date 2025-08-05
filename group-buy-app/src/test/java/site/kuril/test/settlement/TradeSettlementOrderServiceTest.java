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

import javax.annotation.Resource;
import java.util.Date;

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
        tradePaySuccessEntity.setUserId("test_user");
        tradePaySuccessEntity.setOutTradeNo("782858645114");
        tradePaySuccessEntity.setOutTradeTime(new Date());
        
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
        
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
        log.info("测试结果:{}", JSON.toJSONString(tradePaySettlementEntity));
    }

} 