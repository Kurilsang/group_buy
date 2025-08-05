package site.kuril.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知任务HTTP枚举
 */
@Getter
@AllArgsConstructor
public enum NotifyTaskHTTPEnumVO {

    SUCCESS("success", "成功"),
    ERROR("error", "失败"),
    ;

    private final String code;
    private final String desc;

} 