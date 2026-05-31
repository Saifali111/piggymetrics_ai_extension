package com.piggymetrics.statistic_service.domain;

public enum Currency {

    USD, EUR, RUB;

    public static Currency getBase() {
        return USD;
    }
}