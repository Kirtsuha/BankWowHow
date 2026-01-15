package org.example.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class User {
    private final String id;
    private final String login;
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
