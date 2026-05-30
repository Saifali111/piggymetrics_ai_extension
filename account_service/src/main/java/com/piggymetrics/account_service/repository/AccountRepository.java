package com.piggymetrics.account_service.repository;

import com.piggymetrics.account_service.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    com.piggymetrics.account_service.domain.Account findByName(String name);
}