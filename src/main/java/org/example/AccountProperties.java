package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final long default_amount;
    private final double transfer_commission;

    public long getDefault_amount() {
        return default_amount;
    }

    public double getTransfer_commission() {
        return transfer_commission;
    }

    public AccountProperties(@Value("${account.default_amount:333}") long default_amount,
                             @Value("${account.transfer_commission}") double transfer_commission) {
        this.default_amount = default_amount;
        this.transfer_commission = transfer_commission;
    }
}
