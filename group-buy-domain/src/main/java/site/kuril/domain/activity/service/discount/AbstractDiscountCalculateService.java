package site.kuril.domain.activity.service.discount;

import lombok.extern.slf4j.Slf4j;
import site.kuril.domain.activity.adapter.repository.IActivityRepository;
import site.kuril.domain.activity.model.valobj.DiscountTypeEnum;
import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 抽象折扣计算服务
 * <p>
 * 提供折扣计算的通用框架，包含：
 * - 人群标签过滤逻辑
 * - 折扣计算模板方法
 * - 子类需实现具体的折扣计算算法
 * </p>
 */
@Slf4j
public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    /** 活动数据仓储 */
    @Resource
    protected IActivityRepository activityDataRepository;

    /**
     * 计算折扣价格
     * <p>
     * 执行折扣计算的主要流程：
     * 1. 人群标签过滤检查
     * 2. 执行具体的折扣计算逻辑
     * </p>
     *
     * @param customerId     客户用户ID
     * @param originalAmount 商品原始价格
     * @param discountConfig 拼团折扣配置信息
     * @return 计算后的折扣价格
     */
    @Override
    public BigDecimal calculate(String customerId, BigDecimal originalAmount, GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig) {
        log.debug("开始执行折扣计算 - 客户ID: {}, 原价: {}, 折扣类型: {}", 
            customerId, originalAmount, discountConfig.getDiscountType());

        // 第一阶段：执行人群标签过滤
        if (requiresTagValidation(discountConfig)) {
            boolean isEligibleCustomer = validateCustomerTag(customerId, discountConfig.getTagId());
            if (!isEligibleCustomer) {
                log.info("折扣计算中止 - 客户不在目标人群范围内, 客户ID: {}, 标签ID: {}", 
                    customerId, discountConfig.getTagId());
                return originalAmount;
            }
        }

        // 第二阶段：执行具体折扣计算
        BigDecimal calculatedPrice = executeDiscountCalculation(originalAmount, discountConfig);
        
        log.debug("折扣计算完成 - 客户ID: {}, 原价: {}, 折后价: {}", 
            customerId, originalAmount, calculatedPrice);
            
        return calculatedPrice;
    }

    /**
     * 检查是否需要进行标签验证
     * 
     * @param discountConfig 折扣配置
     * @return 是否需要标签验证
     */
    private boolean requiresTagValidation(GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig) {
        return DiscountTypeEnum.TAG.equals(discountConfig.getDiscountType());
    }

    /**
     * 验证客户是否符合人群标签要求
     * 
     * @param customerId 客户ID
     * @param targetTagId 目标标签ID
     * @return 是否在人群范围内
     */
    private boolean validateCustomerTag(String customerId, String targetTagId) {
        log.debug("执行人群标签验证 - 客户ID: {}, 目标标签: {}", customerId, targetTagId);
        return activityDataRepository.isTagCrowdRange(targetTagId, customerId);
    }

    /**
     * 执行具体的折扣计算逻辑
     * <p>
     * 由子类实现具体的折扣计算算法，如满减、直减、折扣等
     * </p>
     *
     * @param originalAmount 原始金额
     * @param discountConfig 折扣配置
     * @return 计算后的金额
     */
    protected abstract BigDecimal executeDiscountCalculation(BigDecimal originalAmount, GroupBuyActivityDiscountVO.GroupBuyDiscount discountConfig);
}
