package com.piggymetrics.statistic_service.messaging;

import com.piggymetrics.statistic_service.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMessageListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatisticsService statisticsService;

    @RabbitListener(queues = "statistics.queue")
    public void handleAccountUpdate(AccountUpdateMessage message) {
        log.info("received account update for: {}", message.getAccountName());
        statisticsService.save(message.getAccountName(), message.getAccount());
    }
}
