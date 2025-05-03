package com.bank.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType; // Deposit, Withdrawal, Transfer
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {
        this.timestamp = new Date(); // по умолчанию текущая дата
    }

    public Long getId() {
        return id;
    }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}
