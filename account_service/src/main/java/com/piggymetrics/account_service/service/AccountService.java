package com.piggymetrics.account_service.service;

import com.piggymetrics.account_service.domain.Account;
import com.piggymetrics.account_service.domain.User;

public interface AccountService {

    Account findByName(String accountName);

    Account create(User user);

    void saveChanges(String name, Account update);
}
