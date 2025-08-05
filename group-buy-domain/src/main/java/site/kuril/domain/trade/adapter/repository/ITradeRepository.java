package site.kuril.domain.trade.adapter.repository;

import site.kuril.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import site.kuril.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import site.kuril.domain.trade.model.entity.GroupBuyActivityEntity;
import site.kuril.domain.trade.model.entity.GroupBuyTeamEntity;
import site.kuril.domain.trade.model.entity.MarketPayOrderEntity;
import site.kuril.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * 交易数据仓储接口
 * <p>
 * 提供交易相关的数据访问能力
 * </p>
 */
public interface ITradeRepository {

    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);
    
    /**
     * 检查SC渠道是否在黑名单中
     * 
     * @param source 渠道来源
     * @param channel 渠道编号
     * @return 是否被拦截
     */
    boolean isSCBlackIntercept(String source, String channel);
    
    /**
     * 根据团队ID查询拼团队伍信息
     * 
     * @param teamId 团队ID
     * @return 拼团队伍实体
     */
    GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId);
    
    /**
     * 结算营销支付订单
     * 
     * @param groupBuyTeamSettlementAggregate 拼团队伍结算聚合
     */
    void settlementMarketPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);

}
