package com.piggymetrics.statistic_service.config;

import com.piggymetrics.statistic_service.repository.converter.DataPointIdReaderConverter;
import com.piggymetrics.statistic_service.repository.converter.DataPointIdWriterConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new DataPointIdWriterConverter(),
                new DataPointIdReaderConverter()
        ));
    }
}