package com.piggymetrics.account_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String STATISTICS_EXCHANGE = "statistics.exchange";
    public static final String ROUTING_KEY = "statistics.update";

    // tells RabbitMQ to send/receive messages as JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // the exchange — just a router, doesn't store messages
    @Bean
    public TopicExchange statisticsExchange() {
        return new TopicExchange(STATISTICS_EXCHANGE);
    }

    // wires the JSON converter into the RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
