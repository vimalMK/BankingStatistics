package com.vimalmoorthy.bankingstatistics.model;

import java.time.Instant;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Vimal on 04/17/2018.
 * Transaction class with amount and timestamp representation
 */
@Data
@NoArgsConstructor
public class Transaction {
    private double amount;
    private Date timestamp;

    public Transaction(double amount, Date timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }
    public Date getTimestamp() {
        return new Date(this.timestamp.getTime());
    }

    @Override
    public String toString() {
        return "Transaction {" + timestamp + " " + amount + "}";
    }
}

