package site.kuril.test.trigger;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.kuril.api.IDCCService;
import site.kuril.api.response.Response;
import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.service.IIndexGroupBuyMarketService;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DCCControllerTest {

    @Resource
    private IDCCService idccService;

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Test
    public void test_updateConfig() {
        Response<Boolean> response = idccService.updateConfig("downgradeSwitch", "1");
        log.info("请求参数：{}", JSON.toJSONString("downgradeSwitch:1"));
        log.info("测试结果：{}", JSON.toJSONString(response));
    }

    /**
     * 拼团集市
     */
    @Test
    public void test_indexMarketTrial() throws Exception {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setUserId("test_user");
        marketProductEntity.setChannel("c01");
        marketProductEntity.setSource("s01");
        marketProductEntity.setGoodsId("9890001");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);
        log.info("请求参数：{}", JSON.toJSONString(marketProductEntity));
        log.info("测试结果：{}", JSON.toJSONString(trialBalanceEntity));
    }

}
