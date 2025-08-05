package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

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

}
