package com.piggymetrics.statistic_service.service;

import com.piggymetrics.statistic_service.domain.Account;
import com.piggymetrics.statistic_service.domain.timeseries.DataPoint;

import java.util.List;

public interface StatisticsService {

    List<DataPoint> findByAccountName(String accountName);
    DataPoint save(String accountName, Account account);
}
