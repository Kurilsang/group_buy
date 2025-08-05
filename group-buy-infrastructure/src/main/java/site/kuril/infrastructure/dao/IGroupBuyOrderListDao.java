package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IGroupBuyOrderListDao {

    void insert(GroupBuyOrderList groupBuyOrderListReq);

    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    Integer queryOrderCountByActivityId(GroupBuyOrderList groupBuyOrderListReq);
    
    /**
     * 更新订单状态为完成
     * 
     * @param groupBuyOrderList 包含userId, outTradeNo, status, outTradeTime的订单信息
     */
    void updateOrderStatus2COMPLETE(GroupBuyOrderList groupBuyOrderList);

}
