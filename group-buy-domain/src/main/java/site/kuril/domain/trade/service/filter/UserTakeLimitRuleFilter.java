package site.kuril.domain.trade.service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.adapter.repository.ITradeRepository;
import site.kuril.domain.trade.model.entity.TradeLockRuleCommandEntity;
import site.kuril.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import site.kuril.domain.trade.service.factory.TradeRuleFilterFactory;
import site.kuril.types.design.framework.link.model2.handler.ILogicHandler;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;

/**
 * 用户参与次数限制规则过滤器
 * <p>
 * 检查用户参与活动的次数是否超过限制
 * </p>
 */
@Slf4j
@Service
public class UserTakeLimitRuleFilter implements ILogicHandler<TradeLockRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private ITradeRepository tradeRepository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
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

        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(userTakeOrderCount + 1)
                .build();
    }

}
