package site.kuril.test.activity;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.service.IIndexGroupBuyMarketService;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IIndexGroupBuyMarketServiceTest {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

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

    @Test
    public void test_indexMarketTrial_withActivity() throws Exception {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setUserId("test_user");
        marketProductEntity.setChannel("c01");
        marketProductEntity.setSource("s01");
        marketProductEntity.setGoodsId("9890001");
        marketProductEntity.setActivityId(100123L);

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);
        log.info("请求参数：{}", JSON.toJSONString(marketProductEntity));
        log.info("测试结果：{}", JSON.toJSONString(trialBalanceEntity));
    }

    /**
     * 测试缓存和降级功能
     * 验证缓存处理和降级服务控制
     */
    @Test
    public void test_indexMarketTrial_cache_and_downgrade() throws Exception {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setUserId("dacihua");
        marketProductEntity.setSource("s01");
        marketProductEntity.setChannel("c01");
        marketProductEntity.setGoodsId("9890001");
        
        log.info("=== 开始测试缓存和降级功能 ===");
        
        // 第一次调用，应该从数据库获取并写入缓存
        log.info("第一次调用（从数据库获取并写入缓存）");
        TrialBalanceEntity trialBalanceEntity1 = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);
        log.info("请求参数:{}", JSON.toJSONString(marketProductEntity));
        log.info("第一次调用结果:{}", JSON.toJSONString(trialBalanceEntity1));
        
        // 第二次调用，应该从缓存获取
        log.info("第二次调用（应该从缓存获取）");
        TrialBalanceEntity trialBalanceEntity2 = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);
        log.info("第二次调用结果:{}", JSON.toJSONString(trialBalanceEntity2));
        
        log.info("=== 缓存和降级功能测试完成 ===");
    }

}
