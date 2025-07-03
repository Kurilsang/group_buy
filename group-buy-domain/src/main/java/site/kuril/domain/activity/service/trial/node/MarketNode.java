package site.kuril.domain.activity.service.trial.node;

import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.model.valobj.SkuVO;
import site.kuril.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import site.kuril.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.kuril.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import site.kuril.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import site.kuril.domain.activity.service.trial.thread.test;
import site.kuril.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;


@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    EndNode endNode;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext context) throws ExecutionException, InterruptedException, TimeoutException {
        log.info("拼团商品查询试算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", requestParameter.getUserId());

        // 异步查询活动配置
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(requestParameter.getSource(), requestParameter.getChannel(), repository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        // 异步查询商品信息 - 在实际生产中，商品有同步库或者调用接口查询。这里暂时使用DB方式查询。
        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParameter.getGoodsId(), repository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);
      // 写入上下文 - 对于一些复杂场景，获取数据的操作，有时候会在下N个节点获取，这样前置查询数据，可以提高接口响应效率
        context.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        context.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));

        test testTask = new test(repository);
        FutureTask<SkuVO> testTask1 = new FutureTask<>(testTask);
        threadPoolExecutor.execute(testTask1);
    }

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
