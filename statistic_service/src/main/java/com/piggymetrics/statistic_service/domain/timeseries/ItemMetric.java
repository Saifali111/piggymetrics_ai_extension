package com.piggymetrics.statistic_service.domain.timeseries;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemMetric {

    private String title;
    private BigDecimal amount;

    public ItemMetric(String title, BigDecimal amount) {
        this.title = title;
        this.amount = amount;
    }

    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemMetric that = (ItemMetric) o;
        return title.equalsIgnoreCase(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase());
    }
}
