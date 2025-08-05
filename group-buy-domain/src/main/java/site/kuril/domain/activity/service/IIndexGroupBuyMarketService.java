package site.kuril.domain.activity.service;

import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;
import site.kuril.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import site.kuril.domain.activity.model.valobj.TeamStatisticVO;

import java.util.List;


public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

    /**
     * 查询进行中的用户拼团订单详情列表
     *
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param ownerCount 个人数据条数
     * @param randomCount 随机数据条数
     * @return 拼团订单详情列表
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, Integer ownerCount, Integer randomCount);

    /**
     * 根据活动ID查询团队统计信息
     *
     * @param activityId 活动ID
     * @return 团队统计信息
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);

}
