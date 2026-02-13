package org.example.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "login")
    private String login;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    public User(Long id, String login, List<Account> accounts) {
        this.id = id;
        this.login = login;
        this.accounts = accounts;
    }

    public User() {}

    public Long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAccounts(List<Account> accountIdList) {
        this.accounts = accountIdList;
    }
}
