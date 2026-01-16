package org.example.repository;

import org.example.domain.Account;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AccountRepository {
    private final HashMap<String, Account> accounts = new HashMap<>();

    public AccountRepository() {}

    public Account create(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    public Account update(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    public Account read(String id) {
        return accounts.get(id);
    }

    public Account delete(String id) {
        return accounts.remove(id);
    }

    public List<Account> getAllAccounts() {
        return accounts.values().stream().toList();
    }
}
