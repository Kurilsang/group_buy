package site.kuril.domain.activity.service.trial.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.model.valobj.SkuVO;
import site.kuril.domain.activity.service.trial.node.RootNode;
import cn.bugstack.wrench.design.framework.tree.StrategyHandler;

import java.math.BigDecimal;

@Service
public class DefaultActivityStrategyFactory {
    private RootNode rootNode;

    public DefaultActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }
    public StrategyHandler<MarketProductEntity,DynamicContext, TrialBalanceEntity> strategyHandler()
    {
        return rootNode;
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext{
        // 拼团活动营销配置值对象
        private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;
        // 商品信息
        private SkuVO skuVO;
        private BigDecimal deductionPrice;
        // 支付金额
        private BigDecimal payPrice;
        // 活动可见性限制
        private boolean visible;
        // 活动
        private boolean enable;
    }
}
