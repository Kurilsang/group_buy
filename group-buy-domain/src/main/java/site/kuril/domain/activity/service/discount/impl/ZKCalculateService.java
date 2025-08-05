package site.kuril.domain.activity.service.discount.impl;

import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 折扣计算服务实现 - ZK(折扣)类型
 * <p>
 * 实现基于百分比的折扣计算逻辑，支持：
 * - 按百分比折扣计算
 * - 最低支付金额保护
 * - 精确的小数处理
 * </p>
 */
@Slf4j
@Service("ZK")
public class ZKCalculateService extends AbstractDiscountCalculateService {

    /** 最低支付金额（分） */
    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = new BigDecimal("0.01");

    /**
     * 执行折扣价格计算
     * <p>
     * 根据折扣配置中的折扣表达式计算最终的折扣价格
     * </p>
     *
     * @param originalAmount 商品原始价格
     * @param discountConfig 折扣配置信息
     * @return 计算后的折扣价格
     */
    @Override
    protected BigDecimal executeDiscountCalculation(BigDecimal originalAmount, GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig) {
        final String discountExpression = discountConfig.getMarketExpr();
        final Integer discountTypeCode = discountConfig.getDiscountType().getCode();
        
        log.info("执行折扣价格计算 - 折扣类型: {}, 原价: {}, 折扣表达式: {}", 
            discountTypeCode, originalAmount, discountExpression);

        // 计算折扣后价格
        BigDecimal discountedAmount = calculateDiscountedPrice(originalAmount, discountExpression);
        
        // 应用最低支付保护
        BigDecimal finalAmount = applyMinimumPaymentProtection(discountedAmount);
        
        log.info("折扣价格计算完成 - 原价: {}, 折后价: {}, 实际支付: {}", 
            originalAmount, discountedAmount, finalAmount);

        return finalAmount;
    }

    /**
     * 计算折扣后价格
     * 
     * @param originalAmount 原始金额
     * @param discountExpression 折扣表达式（百分比）
     * @return 折扣后金额
     */
    private BigDecimal calculateDiscountedPrice(BigDecimal originalAmount, String discountExpression) {
        BigDecimal discountRate = new BigDecimal(discountExpression);
        return originalAmount.multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 应用最低支付金额保护
     * <p>
     * 确保折扣后的金额不会低于系统设定的最低支付金额
     * </p>
     * 
     * @param discountedAmount 折扣后金额
     * @return 受保护的最终金额
     */
    private BigDecimal applyMinimumPaymentProtection(BigDecimal discountedAmount) {
        if (discountedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("折扣后金额过低，应用最低支付保护 - 折扣后: {}, 保护金额: {}", 
                discountedAmount, MINIMUM_PAYMENT_AMOUNT);
            return MINIMUM_PAYMENT_AMOUNT;
        }
        return discountedAmount;
    }
}
