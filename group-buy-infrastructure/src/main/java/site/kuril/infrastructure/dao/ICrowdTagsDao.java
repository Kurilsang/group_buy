package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.CrowdTags;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ICrowdTagsDao {

    void updateCrowdTagsStatistics(CrowdTags crowdTagsReq);

}
