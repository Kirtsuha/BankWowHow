package org.example.domain;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "login")
    private String login;

    @OneToMany(mappedBy = "account_id")
    private List<Account> accountIdList;

    public User(Long id, String login, List<Account> accountIdList) {
        this.id = id;
        this.login = login;
        this.accountIdList = accountIdList;
    }

    public User() {}

    public Long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public List<Account> getAccountIdList() {
        return accountIdList;
    }
}
