package org.example.service;

import org.example.AccountProperties;
import org.example.domain.Account;
import org.example.domain.User;
import org.example.exceptions.AmountShouldBePositiveException;
import org.example.exceptions.InsufficientFundsException;
import org.example.exceptions.OnlyAccountException;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
//@ConfigurationProperties(prefix = "account")
public class AccountService {

    private final long default_amount;
    private final double transfer_commission;

    private final AccountRepository accountRepository;
    private final ObjectProvider<Account> objectProvider;
    private final UserService userService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          ObjectProvider<Account> objectProvider,
                          AccountProperties accountProperties,
                          UserService userService) {
        this.accountRepository = accountRepository;
        this.objectProvider = objectProvider;
        this.default_amount = accountProperties.getDefault_amount();
        this.transfer_commission = accountProperties.getTransfer_commission();
        this.userService = userService;
    }

    public Account getAccount(String id) {
        return accountRepository.read(id);
    }

    public Account createAccount(String userId) {
        //no exception needed!
        User user = userService.getUser(userId);

        Account account = objectProvider.getObject(
                UUID.randomUUID().toString(),
                userId,
                default_amount
        );

        return accountRepository.create(account);
    }

    public Account deleteAccount(String id) {
        Account tempAccount = accountRepository.read(id);
        Optional<Account> result = accountRepository.getAllAccounts().stream()
                .filter(account -> !account.equals(tempAccount))
                .findFirst();
        if (result.isEmpty()) {
            throw new OnlyAccountException(id);
        }
        Account otherAccount = result.get();
        transfer(id, otherAccount.getId(), tempAccount.getMoneyAmount());

        accountRepository.delete(id);
        return otherAccount;
    }

    public void deposit(String id, long amount) {
        if (amount < 0) {
            throw new AmountShouldBePositiveException(amount);
        }
        Account account = accountRepository.read(id);
        Account newAccount = objectProvider.getObject(
                id,
                account.getUserId(),
                account.getMoneyAmount() + amount
        );
        accountRepository.update(newAccount);
    }

    public void withdraw(String id, long amount) {
        if (amount < 0) {
            throw new AmountShouldBePositiveException(amount);
        }
        Account account = accountRepository.read(id);
        if (account.getMoneyAmount() < amount) {
            throw new InsufficientFundsException(id);
        }
        Account newAccount = objectProvider.getObject(
                id,
                account.getUserId(),
                account.getMoneyAmount() - amount
        );
        accountRepository.update(newAccount);
    }

    public void transfer(String idSender, String idReceiver, long amount) {
        boolean addCommission = !Objects.equals(accountRepository.read(idSender).getUserId(), accountRepository.read(idReceiver).getUserId());
        withdraw(idSender, amount);
        long new_amount = amount;
        if (addCommission) {
            new_amount = (long) (amount * (1 - transfer_commission));
        }
        //System.out.println("COMMISION: " + addCommission + new_amount);
        deposit(idReceiver, new_amount);
    }
}
