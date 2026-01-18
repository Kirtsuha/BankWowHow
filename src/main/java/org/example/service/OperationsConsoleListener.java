package org.example.service;

import org.example.domain.User;
import org.example.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class OperationsConsoleListener {
    private Scanner scanner = new Scanner(System.in);
    private UserAccountProxyService proxyService;

    @Autowired
    public OperationsConsoleListener(UserAccountProxyService proxyService) {
        this.proxyService = proxyService;
    }

    public void default_message() {
        System.out.println("Please enter one of operation type:\n" +
                "-ACCOUNT_CREATE\n" +
                "-SHOW_ALL_USERS\n" +
                "-ACCOUNT_CLOSE\n" +
                "-ACCOUNT_WITHDRAW\n" +
                "-ACCOUNT_DEPOSIT\n" +
                "-ACCOUNT_TRANSFER\n" +
                "-USER_CREATE\n");
    }

    public void logic_switcher() {
        default_message();
        String command = scanner.nextLine();
        switch (command) {
            case "USER_CREATE":
                user_create();
                break;
            case "ACCOUNT_CREATE":
                account_create();
                break;
            case "SHOW_ALL_USERS":
                show_all_users();
                break;
            case "ACCOUNT_CLOSE":
                account_close();
                break;
            case "ACCOUNT_WITHDRAW":
                account_withdraw();
                break;
            case "ACCOUNT_DEPOSIT":
                account_deposit();
                break;
            case "ACCOUNT_TRANSFER":
                account_transfer();
                break;
            default:
                break;
        }
        logic_switcher();
    }

    private void account_transfer() {
        try {
            System.out.println("Enter source account ID:");
            String sourceId = scanner.nextLine();
            System.out.println("Enter target account ID");
            String targetId = scanner.nextLine();
            System.out.println("Enter amount to transfer:");
            long amount = scanner.nextLong();
            proxyService.account_transfer(sourceId, targetId, amount);
            System.out.println("Amount " + amount + "transferred from account ID " + sourceId + " to account " + targetId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void account_deposit() {
        try {
            System.out.println("Enter account ID:");
            String id = scanner.nextLine();
            System.out.println("Enter amount to deposit:");
            long amount = scanner.nextLong();
            proxyService.account_deposit(id, amount);
            System.out.println("Amount " + amount + " deposited to account ID: " + id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void account_withdraw() {
        try {
            System.out.println("Enter account ID to withdraw from");
            String id = scanner.nextLine();
            System.out.println("Enter amount to withdraw:");
            long amount = scanner.nextLong();
            proxyService.account_withdraw(id, amount);
            System.out.println("Amount " + amount + " withdrew from account ID: " + id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void user_create() {
        try {
            System.out.println("Enter login for new user:");
            String login = scanner.nextLine();
            User createdUser = proxyService.user_create(login);
            System.out.print("User created: ");
            System.out.println(printUser(createdUser));

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void show_all_users() {
        try {
            List<User> users = proxyService.show_all_users();
            System.out.println("List of all users:");
            for (User user : users) {
                System.out.println(printUser(user));
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void account_create() {
        try {
            System.out.println("Enter the user id for which to create an account: ");
            String id = scanner.nextLine();
            Account createdAccount = proxyService.account_create(id);
            System.out.print("Account created: ");
            System.out.println(printAccount(createdAccount));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void account_close() {
        try {
            System.out.println("Enter account ID to close:");
            String id = scanner.nextLine();
            Account otherAccount = proxyService.account_close(id);
            System.out.println("Account with ID " + id + " has been closed.\n");
            System.out.println("Remaining funds transfered to account " + otherAccount.getId());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private String printAccount(Account account) {
        return "Account{id=" + account.getId() +
                ", user_id=" + account.getUserId() +
                ", amount=" + account.getMoneyAmount() + "}";
    }

    private String printUser(User user) {
        String result = "User{id=" + user.getId() +
                ", login=" + user.getLogin() +
                ", /accountList=[";
        for (Account account : user.getAccountList()) {
            result += printAccount(account);
        }
        result += "]}";
        return result;
    }

}
