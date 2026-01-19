package org.example.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class User {
    private final String id;
    private final String login;
    private final List<String> accountIdList;

    public User(String id, String login, List<String> accountIdList) {
        this.id = id;
        this.login = login;
        this.accountIdList = accountIdList;
    }

    public String getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public List<String> getAccountIdList() {
        return accountIdList;
    }
}
