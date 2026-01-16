package org.example.service;

import org.example.domain.Account;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountService {
    private final long default_amount = 500;
    private final double transfer_commission = 0.15;

    private final AccountRepository accountRepository;
    private final ObjectProvider<Account> objectProvider;

    public AccountService(AccountRepository accountRepository, ObjectProvider<Account> objectProvider) {
        this.accountRepository = accountRepository;
        this.objectProvider = objectProvider;
    }

    public Account createAccount(String userId) {
        Account account = objectProvider.getObject(
                UUID.randomUUID().toString(),
                userId,
                default_amount
        );
        return accountRepository.create(account);
    }

    public boolean deleteAccount(String id) {
        if (accountRepository.read(id) == null) return false;
        accountRepository.delete(id);
        return true;
    }

    public Account deposit(String id, long amount) {
        Account account = accountRepository.read(id);
        Account newAccount = objectProvider.getObject(
                id,
                account.getId(),
                account.getId() + amount
        );
        accountRepository.update(newAccount);
        return newAccount;
    }

    public Account withdraw(String id, long amount) {
        return deposit(id, -amount);
    }

    public void transfer(String idSender, String idReceiver, long amount, boolean addCommission) {
        withdraw(idSender, amount);
        long new_amount = amount;
        if (addCommission) {
            new_amount = (long) (amount * (1 - transfer_commission));
        }
        deposit(idReceiver, new_amount);
    }
}
