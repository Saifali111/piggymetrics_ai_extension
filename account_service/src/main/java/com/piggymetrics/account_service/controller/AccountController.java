package com.piggymetrics.account_service.controller;

import com.piggymetrics.account_service.domain.Account;
import com.piggymetrics.account_service.domain.User;
import com.piggymetrics.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{name}")
    public Account getAccountByName(@PathVariable String name) {
        return accountService.findByName(name);
    }

    @GetMapping("/current/{username}")
    public Account getCurrentAccount(@PathVariable String username) {
        return accountService.findByName(username);
    }

    @PutMapping("/current/{username}")
    public void saveCurrentAccount(@PathVariable String username,
                                   @Valid @RequestBody Account account) {
        accountService.saveChanges(username, account);
    }

    @PostMapping("/")
    public Account createNewAccount(@Valid @RequestBody User user) {
        return accountService.create(user);
    }
}
