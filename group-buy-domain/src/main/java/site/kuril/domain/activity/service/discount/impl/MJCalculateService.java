package site.kuril.domain.activity.service.discount.impl;

import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.service.discount.AbstractDiscountCalculateService;
import site.kuril.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 满减折扣计算服务实现 - MJ(满减)类型
 * <p>
 * 实现满X减Y的折扣计算逻辑，支持：
 * - 满足条件时进行减免
 * - 不满足条件时返回原价
 * - 最低支付金额保护
 * </p>
 * 
 * @author system
 * @version 1.0
 * @since 2024-01-01
 */
@Slf4j
@Service("MJ")
public class MJCalculateService extends AbstractDiscountCalculateService {

    /** 最低支付金额（分） */
    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = new BigDecimal("0.01");

    /**
     * 执行满减折扣计算
     * <p>
     * 解析满减表达式，格式为：满足金额,减免金额（如：100,10表示满100减10）
     * </p>
     *
     * @param originalAmount 商品原始价格
     * @param discountConfig 折扣配置信息
     * @return 计算后的折扣价格
     */
    @Override
    protected BigDecimal executeDiscountCalculation(BigDecimal originalAmount, GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig) {
        final String thresholdExpression = discountConfig.getMarketExpr();
        final Integer discountTypeCode = discountConfig.getDiscountType().getCode();
        
        log.info("执行满减折扣计算 - 折扣类型: {}, 原价: {}, 满减规则: {}", 
            discountTypeCode, originalAmount, thresholdExpression);

        // 解析满减规则
        ThresholdRule thresholdRule = parseThresholdExpression(thresholdExpression);
        
        // 检查是否满足最低消费门槛
        if (!meetsMinimumThreshold(originalAmount, thresholdRule.getThresholdAmount())) {
            log.info("未达到满减门槛 - 原价: {}, 门槛: {}, 返回原价", 
                originalAmount, thresholdRule.getThresholdAmount());
            return originalAmount;
        }

        // 计算满减后价格
        BigDecimal discountedAmount = originalAmount.subtract(thresholdRule.getDeductionAmount());
        
        // 应用最低支付保护
        BigDecimal finalAmount = applyMinimumPaymentProtection(discountedAmount);
        
        log.info("满减折扣计算完成 - 原价: {}, 减免: {}, 实际支付: {}", 
            originalAmount, thresholdRule.getDeductionAmount(), finalAmount);

        return finalAmount;
    }

    /**
     * 解析满减表达式
     * 
     * @param expression 满减表达式，格式：满足金额,减免金额
     * @return 解析后的门槛规则对象
     */
    private ThresholdRule parseThresholdExpression(String expression) {
        String[] ruleParts = expression.split(Constants.SPLIT);
        BigDecimal thresholdAmount = new BigDecimal(ruleParts[0].trim());
        BigDecimal deductionAmount = new BigDecimal(ruleParts[1].trim());
        return new ThresholdRule(thresholdAmount, deductionAmount);
    }

    /**
     * 检查是否满足最低消费门槛
     * 
     * @param originalAmount  原始金额
     * @param thresholdAmount 门槛金额
     * @return 是否满足门槛
     */
    private boolean meetsMinimumThreshold(BigDecimal originalAmount, BigDecimal thresholdAmount) {
        return originalAmount.compareTo(thresholdAmount) >= 0;
    }

    /**
     * 应用最低支付金额保护
     * 
     * @param discountedAmount 折扣后金额
     * @return 受保护的最终金额
     */
    private BigDecimal applyMinimumPaymentProtection(BigDecimal discountedAmount) {
        if (discountedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("满减后金额过低，应用最低支付保护 - 折扣后: {}, 保护金额: {}", 
                discountedAmount, MINIMUM_PAYMENT_AMOUNT);
            return MINIMUM_PAYMENT_AMOUNT;
        }
        return discountedAmount;
    }

    /**
     * 门槛规则内部类
     */
    private static class ThresholdRule {
        private final BigDecimal thresholdAmount;
        private final BigDecimal deductionAmount;

        public ThresholdRule(BigDecimal thresholdAmount, BigDecimal deductionAmount) {
            this.thresholdAmount = thresholdAmount;
            this.deductionAmount = deductionAmount;
        }

        public BigDecimal getThresholdAmount() {
            return thresholdAmount;
        }

        public BigDecimal getDeductionAmount() {
            return deductionAmount;
        }
    }
}
