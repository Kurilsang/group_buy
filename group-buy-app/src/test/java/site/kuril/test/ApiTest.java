package site.kuril.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.kuril.infrastructure.event.EventPublisher;
import site.kuril.types.event.BaseEvent;
import site.kuril.types.event.TeamSuccessEvent;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private EventPublisher publisher;

    @Value("${spring.rabbitmq.config.producer.topic_team_success.routing_key}")
    private String routingKey;

    @Test
    public void test_rabbitmq() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        publisher.publish(routingKey, "订单结算：ORD-20231234");
        publisher.publish(routingKey, "订单结算：ORD-20231235");
        publisher.publish(routingKey, "订单结算：ORD-20231236");
        publisher.publish(routingKey, "订单结算：ORD-20231237");
        publisher.publish(routingKey, "订单结算：ORD-20231238");

        log.info("消息发送完成，等待消息消费...");
        
        // 等待，消息消费。测试后，可主动关闭。
        countDownLatch.await();
    }

    @Test
    public void test_rabbitmq_event() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 创建团队成功事件
        TeamSuccessEvent teamSuccessEvent = TeamSuccessEvent.builder()
                .teamId("TEAM001")
                .activityId("ACT100123")
                .successTime(new Date())
                .build();

        // 构建事件数据
        TeamSuccessEvent.TeamSuccessData eventData = TeamSuccessEvent.TeamSuccessData.builder()
                .teamId("TEAM001")
                .activityId("ACT100123")
                .targetCount(3)
                .completeCount(3)
                .successTime(new Date())
                .build();

        // 构建事件消息
        BaseEvent.EventMessage<TeamSuccessEvent.TeamSuccessData> eventMessage = 
                teamSuccessEvent.buildEventMessage(eventData);

        // 发送事件消息
        publisher.publish(routingKey, eventMessage);

        log.info("事件消息发送完成，等待消息消费...");
        
        // 等待，消息消费。测试后，可主动关闭。
        countDownLatch.await();
    }
} 