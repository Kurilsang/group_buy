package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 回调通知任务实体
 * <p>
 * 表示拼团完成后的回调通知任务信息
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyTaskEntity {

    /** 活动ID */
    private Long activityId;
    /** 拼单组队ID */
    private String teamId;
    /** 回调类型（HTTP、MQ） */
    private String notifyType;
    /** 回调消息 */
    private String notifyMQ;
    /** 回调接口 */
    private String notifyUrl;
    /** 回调次数 */
    private Integer notifyCount;
    /** 回调状态【0初始、1完成、2重试、3失败】 */
    private Integer notifyStatus;
    /** 参数对象 */
    private String parameterJson;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
    
    /**
     * 获取分布式锁的key
     * @return 锁key
     */
    public String lockKey() {
        return "notify_task_lock_" + teamId;
    }

} 