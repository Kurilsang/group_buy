package site.kuril.domain.trade.adapter.repository;

import site.kuril.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import site.kuril.domain.trade.model.aggregate.GroupBuyTeamSettlementAggregate;
import site.kuril.domain.trade.model.entity.GroupBuyActivityEntity;
import site.kuril.domain.trade.model.entity.GroupBuyTeamEntity;
import site.kuril.domain.trade.model.entity.MarketPayOrderEntity;
import site.kuril.domain.trade.model.entity.NotifyTaskEntity;
import site.kuril.domain.trade.model.valobj.GroupBuyProgressVO;

import java.util.List;

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
     * @return 创建的回调任务实体，如果拼团未完成则返回null
     */
    NotifyTaskEntity settlementMarketPayOrder(GroupBuyTeamSettlementAggregate groupBuyTeamSettlementAggregate);

    /**
     * 查询未执行的回调任务列表
     * 
     * @return 未执行的回调任务列表
     */
    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList();

    /**
     * 查询指定团队的未执行回调任务列表
     * 
     * @param teamId 团队ID
     * @return 未执行的回调任务列表
     */
    List<NotifyTaskEntity> queryUnExecutedNotifyTaskList(String teamId);

    /**
     * 更新回调任务状态为成功
     * 
     * @param teamId 团队ID
     * @return 更新行数
     */
    int updateNotifyTaskStatusSuccess(String teamId);

    /**
     * 更新回调任务状态为失败（增加重试次数）
     * 
     * @param teamId 团队ID
     * @return 更新行数
     */
    int updateNotifyTaskStatusError(String teamId);

    /**
     * 更新回调任务状态为重试
     * 
     * @param teamId 团队ID
     * @return 更新行数
     */
    int updateNotifyTaskStatusRetry(String teamId);

    /**
     * 抢占团队库存（无锁化设计）
     * <p>
     * 通过Redis缓存进行库存抢占，降低数据库行锁压力
     * </p>
     * 
     * @param teamStockKey 团队库存key
     * @param recoveryTeamStockKey 恢复库存key
     * @param target 目标数量
     * @param validTime 有效时间（分钟）
     * @return 是否抢占成功
     */
    boolean occupyTeamStock(String teamStockKey, String recoveryTeamStockKey, Integer target, Integer validTime);

    /**
     * 恢复团队库存
     * <p>
     * 当发生异常时，记录库存恢复量
     * </p>
     * 
     * @param recoveryTeamStockKey 恢复库存key
     * @param validTime 有效时间（分钟）
     */
    void recoveryTeamStock(String recoveryTeamStockKey, Integer validTime);

}
