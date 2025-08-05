package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface INotifyTaskDao {

    void insert(NotifyTask notifyTask);

}
