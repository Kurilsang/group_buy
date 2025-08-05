package site.kuril.domain.activity.service.discount.impl;

import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 直减折扣计算服务实现 - ZJ(直减)类型
 * <p>
 * 实现直接减免固定金额的折扣计算逻辑，支持：
 * - 无条件直接减免指定金额
 * - 最低支付金额保护
 * </p>
 * 
 * @author system
 * @version 1.0
 * @since 2024-01-01
 */
@Slf4j
@Service("ZJ")
public class ZJCalculateService extends AbstractDiscountCalculateService {

    /** 最低支付金额（分） */
    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = new BigDecimal("0.01");

    /**
     * 执行直减折扣计算
     * <p>
     * 从原价中直接减去配置的减免金额
     * </p>
     *
     * @param originalAmount 商品原始价格
     * @param discountConfig 折扣配置信息，包含减免金额
     * @return 计算后的折扣价格
     */
    @Override
    protected BigDecimal executeDiscountCalculation(BigDecimal originalAmount, GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig) {
        final String deductionExpression = discountConfig.getMarketExpr();
        final Integer discountTypeCode = discountConfig.getDiscountType().getCode();
        
        log.info("执行直减折扣计算 - 折扣类型: {}, 原价: {}, 减免金额: {}", 
            discountTypeCode, originalAmount, deductionExpression);

        // 计算直减后价格
        BigDecimal deductionAmount = new BigDecimal(deductionExpression);
        BigDecimal discountedAmount = calculateDirectDeduction(originalAmount, deductionAmount);
        
        // 应用最低支付保护
        BigDecimal finalAmount = applyMinimumPaymentProtection(discountedAmount);
        
        log.info("直减折扣计算完成 - 原价: {}, 减免: {}, 实际支付: {}", 
            originalAmount, deductionAmount, finalAmount);

        return finalAmount;
    }

    /**
     * 计算直减后的价格
     * 
     * @param originalAmount 原始金额
     * @param deductionAmount 减免金额
     * @return 直减后金额
     */
    private BigDecimal calculateDirectDeduction(BigDecimal originalAmount, BigDecimal deductionAmount) {
        return originalAmount.subtract(deductionAmount);
    }

    /**
     * 应用最低支付金额保护
     * <p>
     * 确保直减后的金额不会低于系统设定的最低支付金额
     * </p>
     * 
     * @param discountedAmount 折扣后金额
     * @return 受保护的最终金额
     */
    private BigDecimal applyMinimumPaymentProtection(BigDecimal discountedAmount) {
        if (discountedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("直减后金额过低，应用最低支付保护 - 折扣后: {}, 保护金额: {}", 
                discountedAmount, MINIMUM_PAYMENT_AMOUNT);
            return MINIMUM_PAYMENT_AMOUNT;
        }
        return discountedAmount;
    }
}
