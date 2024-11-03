package be.kdg.SA.Land.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {
    public static final String WBT_EXCHANGE = "wbt-exchange";
    public static final String TOPIC_QUEUE_WBT = "wbt-queue";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(WBT_EXCHANGE);
    }

    @Bean
    public Queue topicQueueWbt() {
        return new Queue(TOPIC_QUEUE_WBT, false);
    }

    @Bean
    public Binding topicWbtBinding(TopicExchange topicExchange, Queue topicQueueWbt) {
        return BindingBuilder.bind(topicQueueWbt).to(topicExchange).with("wbt.*");
    }


}
