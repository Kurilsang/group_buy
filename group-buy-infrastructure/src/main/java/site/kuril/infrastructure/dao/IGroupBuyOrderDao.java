package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;


@Mapper
public interface IGroupBuyOrderDao {

    void insert(GroupBuyOrder groupBuyOrder);

    int updateAddLockCount(String teamId);

    int updateSubtractionLockCount(String teamId);

    GroupBuyOrder queryGroupBuyProgress(String teamId);

    /**
     * 更新拼团完成数量
     * 
     * @param teamId 团队ID
     * @return 更新行数
     */
    int updateAddCompleteCount(String teamId);

    /**
     * 更新拼团订单状态为完成
     * 
     * @param teamId 团队ID
     * @return 更新行数
     */
    int updateOrderStatus2COMPLETE(String teamId);

    /**
     * 根据团队ID集合查询进行中的拼团信息
     * 
     * @param teamIds 团队ID集合
     * @return 拼团信息列表
     */
    List<GroupBuyOrder> queryGroupBuyProgressByTeamIds(Set<String> teamIds);

    /**
     * 根据团队ID集合查询总团队数量
     * 
     * @param teamIds 团队ID集合
     * @return 总团队数量
     */
    Integer queryAllTeamCount(Set<String> teamIds);

    /**
     * 根据团队ID集合查询成功团队数量
     * 
     * @param teamIds 团队ID集合
     * @return 成功团队数量
     */
    Integer queryAllTeamCompleteCount(Set<String> teamIds);

    /**
     * 根据团队ID集合查询总用户数量
     * 
     * @param teamIds 团队ID集合
     * @return 总用户数量
     */
    Integer queryAllUserCount(Set<String> teamIds);

}
