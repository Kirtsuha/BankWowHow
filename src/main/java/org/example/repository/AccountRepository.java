package org.example.repository;

import org.example.configuration.AccountProperties;
import org.example.domain.Account;
import org.example.exceptions.AccountWithIdNotFoundException;
import org.example.exceptions.AmountShouldBePositiveException;
import org.example.exceptions.InsufficientFundsException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AccountRepository {
    //private final HashMap<String, Account> accounts = new HashMap<>();
    private final double transfer_commission;

    public AccountRepository(TransactionHelper transactionHelper, AccountProperties accountProperties) {
        this.transfer_commission = accountProperties.getTransfer_commission();
    }

    public void create(Session session, Account account) {
        var accountExists = false; // i expect that 2 same UUID values cant be generated
        //session.find(Account.class, account.getId()) != null;
        session.persist(account);
    }

    public void update(Session session, Account account) {
        read(session, account.getId());
        session.persist(account);
    }

    public Account read(Session session, Long id) {
        var account = session.find(Account.class, id);
        if (account == null) throw new AccountWithIdNotFoundException(id.toString());
        return account;
    }

    public void delete(Session session, Long id) {
        var account = read(session, id);
        session.remove(account);
    }

    public List<Account> getAllAccounts(Session session) {
        var result = session.createQuery("FROM Account", Account.class).stream().toList();
        return result;
    }

    public void deposit(Session session, Long id, long amount) {
        if (amount < 0) {
            throw new AmountShouldBePositiveException(amount);
        }
        Account account = read(session, id);
        account.setMoneyAmount(account.getMoneyAmount() + amount);
        update(session, account);
    }

    public void withdraw(Session session, Long id, long amount) {
        if (amount < 0) {
            throw new AmountShouldBePositiveException(amount);
        }
        Account account = read(session, id);
        if (account.getMoneyAmount() < amount) {
            throw new InsufficientFundsException(id.toString());
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
        update(session, account);
    }

    public void transfer(Session session, Long idSender, Long idReceiver, long amount) {
        boolean addCommission = !Objects.equals(read(session, idSender).getUser(), read(session, idReceiver).getUser());
        withdraw(session, idSender, amount);
        long new_amount = amount;
        if (addCommission) {
            new_amount = (long) (amount * (1 - transfer_commission));
        }
        deposit(session, idReceiver, new_amount);
    }
}
