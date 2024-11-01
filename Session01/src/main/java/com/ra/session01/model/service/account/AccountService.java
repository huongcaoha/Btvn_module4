package com.ra.session01.model.service.account;

import com.ra.session01.model.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account saveAccount(Account account);
    void deleteAccount(Account account);
    Account findById(long id);
}
