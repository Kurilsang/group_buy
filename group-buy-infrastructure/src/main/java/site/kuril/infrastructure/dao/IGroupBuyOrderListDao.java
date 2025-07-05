package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IGroupBuyOrderListDao {

    void insert(GroupBuyOrderList groupBuyOrderListReq);

    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    Integer queryOrderCountByActivityId(GroupBuyOrderList groupBuyOrderListReq);
}
