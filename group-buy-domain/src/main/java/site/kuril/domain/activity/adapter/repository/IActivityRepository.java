package site.kuril.domain.activity.adapter.repository;


import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.model.valobj.SkuVO;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);

    String test();
}
