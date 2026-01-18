package org.example.service;

import org.example.domain.Account;
import org.example.exceptions.AmountShouldBePositiveException;
import org.example.exceptions.InsufficientFundsException;
import org.example.exceptions.OnlyAccountException;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
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
        //no exception needed!
        Account account = objectProvider.getObject(
                UUID.randomUUID().toString(),
                userId,
                default_amount
        );
        return accountRepository.create(account);
    }

    public Account deleteAccount(String id) {
        try {
            Account tempAccount = accountRepository.read(id);
            Optional<Account> result = accountRepository.getAllAccounts().stream()
                    .filter(account -> !account.equals(tempAccount))
                    .findFirst();
            if (!result.isPresent()) {
                throw new OnlyAccountException(id);
            }
            Account otherAccount = result.get();
            transfer(id, otherAccount.getId(), tempAccount.getMoneyAmount());

            accountRepository.delete(id);

            return otherAccount;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Account deposit(String id, long amount) {
        try {
            if (amount < 0) {
                throw new AmountShouldBePositiveException(amount);
            }
            Account account = accountRepository.read(id);
            Account newAccount = objectProvider.getObject(
                    id,
                    account.getId(),
                    account.getMoneyAmount() + amount
            );
            accountRepository.update(newAccount);
            return newAccount;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Account withdraw(String id, long amount) {
        try {
            if (amount < 0) {
                throw new AmountShouldBePositiveException(amount);
            }
            Account account = accountRepository.read(id);
            if (account.getMoneyAmount() < amount) {
                throw new InsufficientFundsException(id);
            }
            Account newAccount = objectProvider.getObject(
                    id,
                    account.getId(),
                    account.getMoneyAmount() - amount
            );
            accountRepository.update(newAccount);
            return newAccount;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void transfer(String idSender, String idReceiver, long amount) {
        boolean addCommission = accountRepository.read(idSender).getUserId() == accountRepository.read(idReceiver).getUserId();
        withdraw(idSender, amount);
        long new_amount = amount;
        if (addCommission) {
            new_amount = (long) (amount * (1 - transfer_commission));
        }
        deposit(idReceiver, new_amount);
    }
}
