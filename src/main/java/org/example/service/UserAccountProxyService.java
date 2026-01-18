package org.example.service;

import org.example.domain.Account;
import org.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAccountProxyService {
    private AccountService accountService;
    private UserService userService;

    @Autowired
    public UserAccountProxyService(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    public void account_transfer(String sourceId, String targetId, long amount) {
        Account sourceAccount = accountService.getAccount(sourceId);
        Account targetAccount = accountService.getAccount(targetId);
        accountService.transfer(sourceId, targetId, amount);

        userService.removeAccountFromUser(sourceAccount.getUserId(), sourceAccount);
        userService.removeAccountFromUser(targetAccount.getUserId(), targetAccount);

        Account newSourceAccount = accountService.getAccount(sourceId);
        Account newTargetAccount = accountService.getAccount(targetId);

        userService.addAccountToUser(newSourceAccount.getUserId(), newSourceAccount);
        userService.addAccountToUser(newTargetAccount.getUserId(), newTargetAccount);
    }

    public void account_deposit(String id, long amount) {
        Account account = accountService.getAccount(id);
        accountService.deposit(id, amount);
        userService.removeAccountFromUser(account.getUserId(), account);
        Account newAccount = accountService.getAccount(id);
        userService.addAccountToUser(newAccount.getUserId(), newAccount);
    }

    public void account_withdraw(String id, long amount) {
        Account account = accountService.getAccount(id);
        accountService.withdraw(id, amount);
        userService.removeAccountFromUser(account.getUserId(), account);
        Account newAccount = accountService.getAccount(id);
        userService.addAccountToUser(newAccount.getUserId(), newAccount);
    }

    public User user_create(String login) {
        return userService.createUser(login);
    }

    public List<User> show_all_users() {
        return userService.getAllUsers();
    }

    public Account account_create(String id) {
        Account createdAccount = accountService.createAccount(id);
        userService.addAccountToUser(id, createdAccount);
        return createdAccount;
    }

    public Account account_close(String id) {
        Account account = accountService.getAccount(id);
        long amount = account.getMoneyAmount();
        Account otherAccount = accountService.deleteAccount(id);
        //account_deposit(otherAccount.getId(), account.getMoneyAmount());
        userService.removeAccountFromUser(account.getUserId(), account);
        account_deposit(otherAccount.getId(), amount);
        return otherAccount;
    }
}
