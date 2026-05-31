package com.piggymetrics.account_service.service;

import com.piggymetrics.account_service.config.RabbitConfig;
import com.piggymetrics.account_service.domain.Account;
import com.piggymetrics.account_service.domain.Currency;
import com.piggymetrics.account_service.domain.Saving;
import com.piggymetrics.account_service.domain.User;
import com.piggymetrics.account_service.messaging.AccountUpdateMessage;
import com.piggymetrics.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor

public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Account findByName(String accountName) {
        Assert.hasLength(accountName, "account name must not be empty");
        return repository.findByName(accountName);
    }

    @Override
    public Account create(User user) {
        Account existing = repository.findByName(user.getUsername());
        Assert.isNull(existing, "account already exists: " + user.getUsername());

        // default saving with zero values
        Saving saving = new Saving();
        saving.setAmount(BigDecimal.ZERO);
        saving.setCurrency(Currency.getDefault());
        saving.setInterest(BigDecimal.ZERO);
        saving.setDeposit(false);
        saving.setCapitalization(false);

        Account account = new Account();
        account.setName(user.getUsername());
        account.setLastSeen(new Date());
        account.setSaving(saving);

        repository.save(account);
        log.info("new account has been created: {}", account.getName());

        return account;
    }

    @Override
    public void saveChanges(String name, Account update) {
        Account account = repository.findByName(name);
        Assert.notNull(account, "can't find account with name: " + name);

        account.setIncomes(update.getIncomes());
        account.setExpenses(update.getExpenses());
        account.setSaving(update.getSaving());
        account.setNote(update.getNote());
        account.setLastSeen(new Date());

        repository.save(account);
        log.debug("account {} changes have been saved", name);

        // statisticsClient.updateStatistics(name, account);
        AccountUpdateMessage message = new AccountUpdateMessage(name, account);
        rabbitTemplate.convertAndSend(
                RabbitConfig.STATISTICS_EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                message
        );
        log.info("account update event published for: {}", name);
    }
}
