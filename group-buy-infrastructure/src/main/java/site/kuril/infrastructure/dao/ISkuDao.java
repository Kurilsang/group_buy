package site.kuril.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.kuril.infrastructure.dao.po.Sku;

@Mapper
public interface ISkuDao {

    Sku querySkuByGoodsId(String goodsId);

}
