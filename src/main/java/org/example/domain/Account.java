package org.example.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Account {
    private String id;
    private String userId;
    private long moneyAmount;

    Account(String id, String userId, long moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public String getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public long getMoneyAmount() {
        return moneyAmount;
    }
}
