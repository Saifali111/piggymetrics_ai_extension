package com.piggymetrics.account_service.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Saving {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    @NotNull
    private BigDecimal interest;

    @NotNull
    private Boolean deposit;

    @NotNull
    private Boolean capitalization;
}
