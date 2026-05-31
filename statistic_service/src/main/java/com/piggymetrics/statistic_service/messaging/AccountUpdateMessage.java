package com.piggymetrics.statistic_service.messaging;

import com.piggymetrics.statistic_service.domain.Account;

public class AccountUpdateMessage {

    private String accountName;
    private Account account;

    public AccountUpdateMessage() {}

    public AccountUpdateMessage(String accountName, Account account) {
        this.accountName = accountName;
        this.account = account;
    }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}
