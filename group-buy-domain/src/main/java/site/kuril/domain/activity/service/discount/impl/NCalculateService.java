package site.kuril.domain.activity.service.discount.impl;

import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * N元购折扣计算服务实现
 * <p>
 * 实现固定金额购买的折扣计算逻辑，即N元购活动
 * </p>
 */
@Slf4j
@Service("N")
public class NCalculateService extends AbstractDiscountCalculateService {

    /**
     * 执行N元购折扣计算
     * <p>
     * 直接返回配置的固定金额，不考虑原价
     * </p>
     *
     * @param originalAmount 商品原始价格（此处不使用）
     * @param discountConfig 折扣配置信息，包含固定购买金额
     * @return 固定的N元购金额
     */
    @Override
    protected BigDecimal executeDiscountCalculation(BigDecimal originalAmount, GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig) {
        final String fixedPriceExpression = discountConfig.getMarketExpr();
        final Integer discountTypeCode = discountConfig.getDiscountType().getCode();
        
        log.info("执行N元购折扣计算 - 折扣类型: {}, 原价: {}, 固定价格: {}", 
            discountTypeCode, originalAmount, fixedPriceExpression);

        // N元购直接返回配置的固定金额
        BigDecimal fixedPrice = new BigDecimal(fixedPriceExpression);
        
        log.info("N元购折扣计算完成 - 原价: {}, N元购价格: {}", originalAmount, fixedPrice);
        
        return fixedPrice;
    }
}
