package site.kuril.infrastructure.dao;

import site.kuril.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface INotifyTaskDao {

    /**
     * 插入回调任务记录
     */
    void insert(NotifyTask notifyTask);

    /**
     * 查询未执行的回调任务列表
     */
    List<NotifyTask> queryUnExecutedNotifyTaskList();

    /**
     * 查询指定团队的未执行回调任务列表
     */
    List<NotifyTask> queryUnExecutedNotifyTaskListByTeamId(String teamId);

    /**
     * 更新回调任务状态为成功
     */
    int updateNotifyTaskStatusSuccess(String teamId);

    /**
     * 更新回调任务状态为失败（增加重试次数）
     */
    int updateNotifyTaskStatusError(String teamId);

    /**
     * 更新回调任务状态为重试
     */
    int updateNotifyTaskStatusRetry(String teamId);

}
