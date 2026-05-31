package com.piggymetrics.statistic_service.service;

import com.piggymetrics.statistic_service.domain.*;
import com.piggymetrics.statistic_service.domain.timeseries.*;
import com.piggymetrics.statistic_service.repository.DataPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPointRepository repository;

    @Autowired
    private ExchangeRatesService ratesService;

    @Override
    public List<DataPoint> findByAccountName(String accountName) {
        Assert.hasLength(accountName, "account name must not be empty");
        return repository.findByAccount(accountName);
    }

    @Override
    public DataPoint save(String accountName, Account account) {

        Instant instant = LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Date date = Date.from(instant);

        // ID format: "john:2026-05-30" — unique per account per day
        String pointId = accountName + ":" + LocalDate.now().toString();

        Set<ItemMetric> incomes = account.getIncomes().stream()
                .map(this::createItemMetric)
                .collect(Collectors.toSet());

        Set<ItemMetric> expenses = account.getExpenses().stream()
                .map(this::createItemMetric)
                .collect(Collectors.toSet());

        Map<StatisticMetric, BigDecimal> statistics = createStatisticMetrics(incomes, expenses, account.getSaving());

        DataPoint dataPoint = new DataPoint();
        dataPoint.setId(pointId);
        dataPoint.setAccount(accountName);
        dataPoint.setDate(date);
        dataPoint.setIncomes(incomes);
        dataPoint.setExpenses(expenses);
        dataPoint.setStatistics(statistics);
        dataPoint.setRates(ratesService.getCurrentRates());

        log.debug("new datapoint has been created: {}", pointId);

        return repository.save(dataPoint);
    }

    private Map<StatisticMetric, BigDecimal> createStatisticMetrics(
            Set<ItemMetric> incomes, Set<ItemMetric> expenses, Saving saving) {

        BigDecimal savingAmount = ratesService.convert(
                saving.getCurrency(), Currency.getBase(), saving.getAmount());

        BigDecimal expensesAmount = expenses.stream()
                .map(ItemMetric::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal incomesAmount = incomes.stream()
                .map(ItemMetric::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                StatisticMetric.EXPENSES_AMOUNT, expensesAmount,
                StatisticMetric.INCOMES_AMOUNT, incomesAmount,
                StatisticMetric.SAVING_AMOUNT, savingAmount
        );
    }

    private ItemMetric createItemMetric(Item item) {
        BigDecimal amount = ratesService
                .convert(item.getCurrency(), Currency.getBase(), item.getAmount())
                .divide(item.getPeriod().getBaseRatio(), 4, RoundingMode.HALF_UP);
        return new ItemMetric(item.getTitle(), amount);
    }
}
