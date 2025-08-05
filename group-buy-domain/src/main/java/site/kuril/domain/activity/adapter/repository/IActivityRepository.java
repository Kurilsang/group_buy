package site.kuril.domain.activity.adapter.repository;


import site.kuril.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.model.valobj.SCSkuActivityVO;
import site.kuril.domain.activity.model.valobj.SkuVO;
import site.kuril.domain.activity.model.valobj.TeamStatisticVO;

import java.util.List;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    SkuVO querySkuByGoodsId(String goodsId);

    String test();

    SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId);

    boolean isTagCrowdRange(String tagId, String userId);

    boolean downgradeSwitch();

    boolean cutRange(String userId);

    /**
     * 查询用户自己的拼团订单详情列表
     *
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param ownerCount 查询数量
     * @return 用户拼团订单详情列表
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailListByOwner(Long activityId, String userId, Integer ownerCount);

    /**
     * 查询随机的拼团订单详情列表（排除指定用户）
     *
     * @param activityId 活动ID
     * @param userId 排除的用户ID
     * @param randomCount 查询数量
     * @return 随机拼团订单详情列表
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailListByRandom(Long activityId, String userId, Integer randomCount);

    /**
     * 根据活动ID查询团队统计信息
     *
     * @param activityId 活动ID
     * @return 团队统计信息
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);
}
