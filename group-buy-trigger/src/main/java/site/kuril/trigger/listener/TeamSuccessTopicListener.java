package site.kuril.trigger.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TeamSuccessTopicListener {

    /**
     * 直接监听配置类中定义的队列
     */
    @RabbitListener(queues = "${spring.rabbitmq.config.producer.topic_team_success.queue}")
    public void listener(String message) {
        try {
            log.info("接收消息：{}", message);
            // 这里可以添加具体的业务处理逻辑
            // 例如：拼团成功后的业务处理
        } catch (Exception e) {
            log.error("消息处理异常，消息：{}", message, e);
            // 根据业务需要，可以选择重新抛出异常让消息重新入队
            // 或者记录错误日志进行人工处理
        }
    }
} 