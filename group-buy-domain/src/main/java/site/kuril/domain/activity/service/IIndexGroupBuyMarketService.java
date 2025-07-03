package site.kuril.domain.activity.service;

import site.kuril.domain.activity.model.entity.MarketProductEntity;
import site.kuril.domain.activity.model.entity.TrialBalanceEntity;


public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

}
