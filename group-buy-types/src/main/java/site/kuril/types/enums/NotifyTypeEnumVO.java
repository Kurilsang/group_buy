package site.kuril.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotifyTypeEnumVO {

    HTTP("HTTP", "HTTP回调"),
    MQ("MQ", "MQ消息");

    private final String code;
    private final String desc;

    public static NotifyTypeEnumVO fromCode(String code) {
        for (NotifyTypeEnumVO notifyTypeEnumVO : NotifyTypeEnumVO.values()) {
            if (notifyTypeEnumVO.code.equals(code)) {
                return notifyTypeEnumVO;
            }
        }
        return HTTP;
    }

} 