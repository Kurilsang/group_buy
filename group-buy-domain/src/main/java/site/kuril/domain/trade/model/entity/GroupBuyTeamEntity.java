package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 拼团队伍实体
 * <p>
 * 表示一个拼团的基本信息，包括人数、状态和有效时间等
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamEntity {

    /** 拼团队伍ID */
    private String teamId;
    /** 活动ID */
    private Long activityId;
    /** 目标人数 */
    private Integer targetCount;
    /** 完成人数 */
    private Integer completeCount;
    /** 锁定人数 */
    private Integer lockCount;
    /** 拼团状态 */
    private Integer status;
    /** 有效开始时间 */
    private Date validStartTime;
    /** 有效结束时间 */
    private Date validEndTime;
    /** 回调通知地址 */
    private String notifyUrl;

} 