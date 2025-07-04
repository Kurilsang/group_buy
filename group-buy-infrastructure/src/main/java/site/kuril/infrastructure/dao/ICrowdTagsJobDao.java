package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.CrowdTagsJob;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ICrowdTagsJobDao {

    CrowdTagsJob queryCrowdTagsJob(CrowdTagsJob crowdTagsJobReq);

}
