package site.kuril.infrastructure.dao;


import org.apache.ibatis.annotations.Mapper;
import site.kuril.infrastructure.dao.po.GroupBuyActivity;

import java.util.List;

/**
 * 拼团活动Mapper接口
 */
@Mapper
public interface GroupBuyActivityDao {

    /**
     * 查询所有拼团活动列表
     * @return 拼团活动实体列表
     */
    List<GroupBuyActivity> queryGroupBuyActivityList();
    GroupBuyActivity queryValidGroupBuyActivity(GroupBuyActivity groupBuyActivityReq);

    GroupBuyActivity queryValidGroupBuyActivityId(Long activityId);

    GroupBuyActivity queryGroupBuyActivityByActivityId(Long activityId);
}