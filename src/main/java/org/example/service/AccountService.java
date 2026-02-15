package org.example.service;

import org.example.configuration.AccountProperties;
import org.example.domain.Account;
import org.example.domain.User;
import org.example.exceptions.OnlyAccountException;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountService {

    private final long default_amount;

    private final AccountRepository accountRepository;

    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          AccountProperties accountProperties,
                          TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.accountRepository = accountRepository;
        this.default_amount = accountProperties.getDefault_amount();
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Account getAccount(Long id) {
        return transactionHelper.makeTransactional(sessionFactory.openSession(),
                session -> accountRepository.read(session, id)
        );
    }

    public Account createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        account.setMoneyAmount(default_amount);

        return transactionHelper.makeTransactional(sessionFactory.openSession(), session -> {
            accountRepository.create(session, account);
            return account;
        });
    }

    public Account deleteAccount(Long id) {
        return transactionHelper.makeTransactional(sessionFactory.openSession(), session -> {
            Account accountToDelete = accountRepository.read(session, id);
            Optional<Account> result = accountRepository.getAllAccounts(session).stream()
                    .filter(account -> !account.equals(accountToDelete) &&
                            account.getUser().equals(accountToDelete.getUser()))
                    .findFirst();
            if (result.isEmpty()) {
                throw new OnlyAccountException(id.toString());
            }
            Account otherAccount = result.get();
            accountRepository.transfer(session, id, otherAccount.getId(), accountToDelete.getMoneyAmount());

            accountRepository.delete(session, id);
            return otherAccount;
        });
    }



    public void deposit(Long id, long amount) {
        transactionHelper.makeTransactional(sessionFactory.openSession(), session -> {
            accountRepository.deposit(session, id, amount);
            return null;
        });
    }

    public void withdraw(Long id, long amount) {
        transactionHelper.makeTransactional(sessionFactory.openSession(), session -> {
            accountRepository.withdraw(session, id, amount);
            return null;
        });
    }

    public void transfer(Long idSender, Long idReceiver, long amount) {
        transactionHelper.makeTransactional(sessionFactory.openSession(), session -> {
            accountRepository.transfer(session, idSender, idReceiver, amount);
            return null;
        });
    }
}
