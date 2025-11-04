package be.kdg.sa.warehouse.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitTopology {
    public static final String PDT_EXCHANGE = "pdt-exchange";
    public static final String TOPIC_QUEUE_PDT = "pdt-queue";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(PDT_EXCHANGE);
    }

    @Bean
    public Queue topicQueueWbt() {
        return new Queue(TOPIC_QUEUE_PDT, false);
    }

    @Bean
    public Binding topicWbtBinding(TopicExchange topicExchange, Queue topicWbtQueue) {
        return BindingBuilder.bind(topicWbtQueue).to(topicExchange).with("pdt.*");

    }
}
