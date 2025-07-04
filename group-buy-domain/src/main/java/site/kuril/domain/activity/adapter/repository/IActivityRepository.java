package site.kuril.domain.activity.adapter.repository;


import site.kuril.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.kuril.domain.activity.model.valobj.SCSkuActivityVO;
import site.kuril.domain.activity.model.valobj.SkuVO;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    SkuVO querySkuByGoodsId(String goodsId);

    String test();

    SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId);

    boolean isTagCrowdRange(String tagId, String userId);
}
