package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.domain.Account;
import org.example.exceptions.AccountWithIdNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AccountRepository {
    //private final HashMap<String, Account> accounts = new HashMap<>();
    private SessionFactory sessionFactory;
    private TransactionHelper transactionHelper;

    public AccountRepository(SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public void create(Account account) {
        var accountExists = sessionFactory.getCurrentSession().find(Account.class, account.getId()) != null;
        if (accountExists) {
            throw new AccountWithIdNotFoundException(account.getId().toString());
        }
        sessionFactory.getCurrentSession().persist(account);
    }

    public void update(Account account) {
        read(account.getId());
        sessionFactory.getCurrentSession().persist(account);
    }

    public Account read(Long id) {
        var account = sessionFactory.getCurrentSession().find(Account.class, id);
        if (account == null) throw new AccountWithIdNotFoundException(id.toString());
        return account;
    }

    public void delete(Long id) {
        var account = read(id);
        sessionFactory.getCurrentSession().remove(account);
    }

    public List<Account> getAllAccounts() {
        var result = sessionFactory.getCurrentSession().createQuery("FROM Account", Account.class).stream().toList();
        return result;
    }
}
