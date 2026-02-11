package org.example.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="money")
    private long moneyAmount;

    Account(Long id, User user, long moneyAmount) {
        this.id = id;
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public Account() {}

    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public long getMoneyAmount() {
        return moneyAmount;
    }
}
