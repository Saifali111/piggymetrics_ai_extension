package com.piggymetrics.statistic_service.controller;

import com.piggymetrics.statistic_service.domain.Account;
import com.piggymetrics.statistic_service.domain.timeseries.DataPoint;
import com.piggymetrics.statistic_service.service.StatisticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/current/{name}")
    public List<DataPoint> getCurrentAccountStatistics(@PathVariable String name) {
        return statisticsService.findByAccountName(name);
    }

    @GetMapping("/{accountName}")
    public List<DataPoint> getStatisticsByAccountName(@PathVariable String accountName) {
        return statisticsService.findByAccountName(accountName);
    }

    @PutMapping("/{accountName}")
    public void saveAccountStatistics(@PathVariable String accountName,
                                      @Valid @RequestBody Account account) {
        statisticsService.save(accountName, account);
    }
}
