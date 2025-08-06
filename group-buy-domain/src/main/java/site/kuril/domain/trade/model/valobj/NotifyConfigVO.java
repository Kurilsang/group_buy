package site.kuril.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kuril.types.enums.NotifyTypeEnumVO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyConfigVO {

    private NotifyTypeEnumVO notifyType;
    private String notifyMQ;
    private String notifyUrl;

} 