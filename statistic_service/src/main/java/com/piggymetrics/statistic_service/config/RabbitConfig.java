package com.piggymetrics.statistic_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String STATISTICS_QUEUE = "statistics.queue";
    public static final String STATISTICS_EXCHANGE = "statistics.exchange";
    public static final String ROUTING_KEY = "statistics.update";

    // the actual mailbox where messages wait
    @Bean
    public Queue statisticsQueue() {
        return new Queue(STATISTICS_QUEUE, true); // true = durable, survives restart
    }

    // the router
    @Bean
    public TopicExchange statisticsExchange() {
        return new TopicExchange(STATISTICS_EXCHANGE);
    }

    // the rule: send messages with routing key "statistics.update" to "statistics.queue"
    @Bean
    public Binding binding(Queue statisticsQueue, TopicExchange statisticsExchange) {
        return BindingBuilder
                .bind(statisticsQueue)
                .to(statisticsExchange)
                .with(ROUTING_KEY);
    }

    // tells the listener to deserialize messages as JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}
