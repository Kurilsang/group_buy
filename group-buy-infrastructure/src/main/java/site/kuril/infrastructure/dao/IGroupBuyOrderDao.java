package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;


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

}
