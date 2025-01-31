package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Registers a new account if username does not already exist
     * @param account Account
     * @return New registered account or null if username already exists
     */
    public Account registerAccount(Account account) {
        Account newAccount = accountRepository.findByUsername(account.getUsername())
                                              .orElse(null);
        if (newAccount != null)
            return null;
        else return accountRepository.save(account);
    }

    /**
     * Attempts to login
     * @param account Account with username and password
     * @return Account if successful, else null
     */
    public Account login(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
                                .orElse(null);
    }
}
