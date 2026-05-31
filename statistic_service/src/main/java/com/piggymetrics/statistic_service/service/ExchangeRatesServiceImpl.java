package com.piggymetrics.statistic_service.service;

import com.piggymetrics.statistic_service.domain.Currency;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    // Hardcoded rates for now — wire a real API (e.g. exchangerate.host) later
    private static final Map<Currency, BigDecimal> RATES = Map.of(
            Currency.USD, BigDecimal.ONE,
            Currency.EUR, new BigDecimal("1.08"),
            Currency.RUB, new BigDecimal("0.011")
    );

    @Override
    public Map<Currency, BigDecimal> getCurrentRates() {
        return RATES;
    }

    @Override
    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        Assert.notNull(amount, "amount must not be null");
        Map<Currency, BigDecimal> rates = getCurrentRates();
        BigDecimal ratio = rates.get(to).divide(rates.get(from), 4, RoundingMode.HALF_UP);
        return amount.multiply(ratio);
    }
}
