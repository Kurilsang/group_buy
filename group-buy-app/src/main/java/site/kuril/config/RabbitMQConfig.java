package site.kuril.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    
    @Value("${spring.rabbitmq.config.producer.exchange}")
    private String exchangeName;
    
    @Value("${spring.rabbitmq.config.producer.topic_team_success.queue}")
    private String queueName;
    
    @Value("${spring.rabbitmq.config.producer.topic_team_success.routing_key}")
    private String routingKey;
    
    /**
     * 1. 创建交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName, true, false);
    }
    
    /**
     * 2. 创建队列
     */
    @Bean
    public Queue teamSuccessQueue() {
        return new Queue(queueName, true); // 持久化队列
    }
    
    /**
     * 3. 绑定队列到交换机
     */
    @Bean
    public Binding teamSuccessBinding() {
        return BindingBuilder
                .bind(teamSuccessQueue())        // 绑定队列
                .to(topicExchange())             // 到交换机
                .with(routingKey);               // 使用路由键
    }
} 