package site.kuril.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.kuril.infrastructure.dao.po.GroupBuyDiscount;

import java.util.List;

/**
 * 拼团折扣Mapper接口
 */
@Mapper
public interface GroupBuyDiscountDao {

    /**
     * 查询所有拼团折扣列表
     * @return 拼团折扣实体列表
     */
    List<GroupBuyDiscount> queryGroupBuyDiscountList();
}