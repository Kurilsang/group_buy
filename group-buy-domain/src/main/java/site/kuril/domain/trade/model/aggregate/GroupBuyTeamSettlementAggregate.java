package site.kuril.domain.trade.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kuril.domain.trade.model.entity.GroupBuyTeamEntity;
import site.kuril.domain.trade.model.entity.TradePaySuccessEntity;
import site.kuril.domain.trade.model.entity.UserEntity;

/**
 * 拼团队伍结算聚合
 * <p>
 * 封装拼团结算业务所需的完整信息
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamSettlementAggregate {

    /** 用户实体 */
    private UserEntity userEntity;
    /** 拼团队伍实体 */
    private GroupBuyTeamEntity groupBuyTeamEntity;
    /** 交易支付成功实体 */
    private TradePaySuccessEntity tradePaySuccessEntity;

} 