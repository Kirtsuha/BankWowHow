package org.example.domain;

import java.util.List;

public class User {
    private String id;
    private String login;
    private List<Account> accountList;

    public User(String id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    public String getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public List<Account> getAccountList() {
        return accountList;
    }
}
