package site.kuril.domain.trade.service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.kuril.domain.trade.adapter.repository.ITradeRepository;
import site.kuril.domain.trade.model.entity.TradeLockRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import site.kuril.domain.trade.service.factory.TradeLockRuleFilterFactory;
import cn.bugstack.wrench.design.framework.link.model2.handler.ILogicHandler;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;

/**
 * 用户参与次数限制规则过滤器
 * <p>
 * 检查用户在指定活动中的参与次数是否已达上限
 * </p>
 */
@Slf4j
@Component
public class UserTakeLimitRuleFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository tradeRepository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-用户参与次数限制校验，用户ID: {} 活动ID: {}", requestParameter.getUserId(), requestParameter.getActivityId());

        String userId = requestParameter.getUserId();
        Long activityId = requestParameter.getActivityId();

        Integer userTakeOrderCount = tradeRepository.queryOrderCountByActivityId(activityId, userId);

        if (null == userTakeOrderCount) {
            userTakeOrderCount = 0;
        }

        log.info("用户 {} 在活动 {} 中已参与 {} 次", userId, activityId, userTakeOrderCount);

        // 检查参与次数限制（假设限制为3次）
        if (userTakeOrderCount >= 3) {
            log.warn("用户 {} 参与活动 {} 次数已达上限", userId, activityId);
            throw new AppException(ResponseCode.E0103);
        }

        // 将用户参与次数设置到动态上下文中
        dynamicContext.setUserTakeOrderCount(userTakeOrderCount + 1);

        // 走到下一个责任链节点
        return next(requestParameter, dynamicContext);
    }

}
