package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface IGroupBuyOrderListDao {

    void insert(GroupBuyOrderList groupBuyOrderListReq);

    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    Integer queryOrderCountByActivityId(GroupBuyOrderList groupBuyOrderListReq);
    
    /**
     * 更新订单状态为完成
     * 
     * @param groupBuyOrderList 包含userId, outTradeNo, status, outTradeTime的订单信息
     * @return 更新行数
     */
    int updateOrderStatus2COMPLETE(GroupBuyOrderList groupBuyOrderList);

    /**
     * 查询拼团完成订单外部交易单号列表
     * 
     * @param teamId 团队ID
     * @return 外部交易单号列表
     */
    List<String> queryGroupBuyCompleteOrderOutTradeNoListByTeamId(String teamId);

    /**
     * 查询用户在指定活动下的进行中的拼团订单列表（个人数据）
     * 
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param count 查询数量
     * @return 用户拼团订单列表
     */
    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByUserId(@Param("activityId") Long activityId, @Param("userId") String userId, @Param("count") Integer count);

    /**
     * 查询指定活动下的随机拼团订单列表（排除指定用户）
     * 
     * @param activityId 活动ID
     * @param userId 排除的用户ID
     * @param count 查询数量
     * @return 随机拼团订单列表
     */
    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByRandom(@Param("activityId") Long activityId, @Param("userId") String userId, @Param("count") Integer count);

    /**
     * 查询指定活动下所有进行中的拼团订单列表
     * 
     * @param activityId 活动ID
     * @return 拼团订单列表
     */
    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByActivityId(@Param("activityId") Long activityId);

}
