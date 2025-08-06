package site.kuril.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamSuccessEvent extends BaseEvent<TeamSuccessEvent.TeamSuccessData> {

    private String teamId;
    private String activityId;
    private Date successTime;

    @Override
    public EventMessage<TeamSuccessData> buildEventMessage(TeamSuccessData data) {
        return EventMessage.<TeamSuccessData>builder()
                .id(UUID.randomUUID().toString())
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return "topic.team_success";
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamSuccessData {
        private String teamId;
        private String activityId;
        private Integer targetCount;
        private Integer completeCount;
        private Date successTime;
    }
} 