package site.kuril.infrastructure.event;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.kuril.types.event.BaseEvent;

import javax.annotation.Resource;

@Slf4j
@Component
public class EventPublisher {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.config.producer.exchange}")
    private String exchangeName;

    public void publish(String routingKey, String message) {
        try {
            // 持久化消息配置
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message, m -> {
                // 设置消息配置
                m.getMessageProperties().setDeliveryMode(org.springframework.amqp.core.MessageDeliveryMode.PERSISTENT);
                return m;
            });
            log.info("发送MQ消息成功 message:{}", message);
        } catch (Exception e) {
            log.error("发送MQ消息失败 team_success message:{}", message, e);
            throw e;
        }
    }

    public void publish(String routingKey, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String message = JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message, m -> {
                // 设置消息配置
                m.getMessageProperties().setDeliveryMode(org.springframework.amqp.core.MessageDeliveryMode.PERSISTENT);
                return m;
            });
            log.info("发送MQ事件消息成功 message:{}", message);
        } catch (Exception e) {
            log.error("发送MQ事件消息失败 message:{}", JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }
} 