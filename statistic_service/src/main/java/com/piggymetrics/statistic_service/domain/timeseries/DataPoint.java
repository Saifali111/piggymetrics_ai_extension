package com.piggymetrics.statistic_service.domain.timeseries;

import com.piggymetrics.statistic_service.domain.Currency;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Document(collection = "datapoints")
public class DataPoint {

    @Id
    private String id;  // format: "accountName:yyyy-MM-dd"

    @Indexed
    private String account;

    private Date date;

    private Set<ItemMetric> incomes;
    private Set<ItemMetric> expenses;
    private Map<StatisticMetric, BigDecimal> statistics;
    private Map<Currency, BigDecimal> rates;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Set<ItemMetric> getIncomes() { return incomes; }
    public void setIncomes(Set<ItemMetric> incomes) { this.incomes = incomes; }

    public Set<ItemMetric> getExpenses() { return expenses; }
    public void setExpenses(Set<ItemMetric> expenses) { this.expenses = expenses; }

    public Map<StatisticMetric, BigDecimal> getStatistics() { return statistics; }
    public void setStatistics(Map<StatisticMetric, BigDecimal> statistics) { this.statistics = statistics; }

    public Map<Currency, BigDecimal> getRates() { return rates; }
    public void setRates(Map<Currency, BigDecimal> rates) { this.rates = rates; }
}