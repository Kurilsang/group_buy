package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.SCSkuActivity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ISCSkuActivityDao {

    SCSkuActivity querySCSkuActivityBySCGoodsId(SCSkuActivity scSkuActivity);

}
