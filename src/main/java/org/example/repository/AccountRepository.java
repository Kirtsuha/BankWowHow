package org.example.repository;

import org.example.domain.Account;
import org.example.exceptions.AccountWithIdNotFoundException;
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
        if (!accounts.containsKey(account.getId())) {
            throw new AccountWithIdNotFoundException(account.getId());
        }
        accounts.put(account.getId(), account);
        return account;
    }

    public Account read(String id) {
        if (!accounts.containsKey(id)) {
            throw new AccountWithIdNotFoundException(id);
        }
        return accounts.get(id);
    }

    public Account delete(String id) {
        if (!accounts.containsKey(id)) {
            throw new AccountWithIdNotFoundException(id);
        }
        return accounts.remove(id);
    }

    public List<Account> getAllAccounts() {
        return accounts.values().stream().toList();
    }
}
