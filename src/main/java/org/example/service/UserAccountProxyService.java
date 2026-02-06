package org.example.service;

import org.example.domain.Account;
import org.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAccountProxyService {
    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public UserAccountProxyService(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    public void account_transfer(String sourceId, String targetId, long amount) {
        accountService.transfer(sourceId, targetId, amount);
    }

    public void account_deposit(String id, long amount) {
        accountService.deposit(id, amount);
    }

    public void account_withdraw(String id, long amount) {
        accountService.withdraw(id, amount);
    }

    public User user_create(String login) {
        return userService.createUser(login);
    }

    public List<User> show_all_users() {
        return userService.getAllUsers();
    }

    public Account get_account(String id) {
        return accountService.getAccount(id);
    }

    public Account account_create(String userId) {
        Account createdAccount = accountService.createAccount(userId);
        userService.addAccountToUser(userId, createdAccount.getId());
        return createdAccount;
    }

    public Account account_close(String accountId) {
        Account account = accountService.getAccount(accountId);
        Account otherAccount = accountService.deleteAccount(accountId);
        userService.removeAccountFromUser(account.getUser(), accountId);
        return otherAccount;
    }
}
