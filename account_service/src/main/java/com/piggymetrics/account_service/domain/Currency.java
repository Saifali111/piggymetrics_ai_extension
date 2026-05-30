package com.piggymetrics.account_service.domain;

public enum Currency {
    USD, EUR, RUB;

    public static Currency getDefault() {
        return USD;
    }
}
