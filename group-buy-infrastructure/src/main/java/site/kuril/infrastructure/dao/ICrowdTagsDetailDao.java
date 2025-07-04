package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.CrowdTagsDetail;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ICrowdTagsDetailDao {

    void addCrowdTagsUserId(CrowdTagsDetail crowdTagsDetailReq);

}
