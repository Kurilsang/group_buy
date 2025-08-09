package site.kuril.domain.activity.service.trial;

import site.kuril.domain.activity.adapter.repository.IActivityRepository;
import site.kuril.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity,DynamicContext,TrailBlanceEntity> extends AbstractMultiThreadStrategyRouter<MarketProductEntity,DynamicContext,TrailBlanceEntity> {
    @Resource
    protected IActivityRepository repository;
    protected long timeout = 500;
    @Override
    protected void multiThread(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 缺省的方法
    }

}
