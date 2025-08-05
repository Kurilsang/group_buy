package site.kuril.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupBuyOrderDetailEntity {

    // 用户ID
    private String userId;
    // 拼单组队ID
    private String teamId;
    // 活动ID
    private Long activityId;
    // 目标数量
    private Integer targetCount;
    // 完成数量
    private Integer completeCount;
    // 锁单数量
    private Integer lockCount;
    // 拼团开始时间
    private Date validStartTime;
    // 拼团结束时间
    private Date validEndTime;
    // 外部交易单号
    private String outTradeNo;

} 