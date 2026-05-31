package com.piggymetrics.statistic_service.service;

import com.piggymetrics.statistic_service.domain.Currency;

import java.math.BigDecimal;
import java.util.Map;


public interface ExchangeRatesService {
    Map<Currency, BigDecimal> getCurrentRates();
    BigDecimal convert(Currency from, Currency to, BigDecimal amount);
}
