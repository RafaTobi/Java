package be.kdg.sa.land.config;

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
    public Queue topicQueuePdt() {
        return new Queue(TOPIC_QUEUE_PDT, false);
    }

    @Bean
    public Binding topicPdtBinding(TopicExchange topicExchange, Queue topicQueueWbt) {
        return BindingBuilder.bind(topicQueueWbt).to(topicExchange).with("pdt.*");
    }


}
