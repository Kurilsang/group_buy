package site.kuril.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import site.kuril.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.kuril.types.design.framework.tree.StrategyHandler;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;

@Slf4j
@Service
public class RootNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private SwitchNode switchNode;

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-RootNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));
        if(StringUtils.isBlank(requestParameter.getUserId())
        ||StringUtils.isBlank(requestParameter.getChannel())
        ||StringUtils.isBlank(requestParameter.getSource())
        ||StringUtils.isBlank(requestParameter.getGoodsId())
     )
        {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());

        }
        log.info("根节点经过");
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return switchNode;
    }

}