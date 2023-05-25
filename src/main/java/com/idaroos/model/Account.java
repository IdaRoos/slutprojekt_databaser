package com.idaroos;

import java.sql.Time;
import java.sql.Timestamp;

public class Account {

    private int id;
    private int customer_id;
    private int account_number;
    private String account_name;
    private int balance;
    private Timestamp created;


    public Account(int id, int customer_id, int account_number, String account_name, int balance, Timestamp created) {
        this.id = id;
        this.customer_id = customer_id;
        this.account_number = account_number;
        this.account_name = account_name;
        this.balance = balance;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
