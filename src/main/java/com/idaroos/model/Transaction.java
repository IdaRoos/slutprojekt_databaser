package com.idaroos.model;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int toaccount_id;
    private int fromaccount_id;
    private int amount;
    private Timestamp created;

    public Transaction(int id, int toaccount_id, int fromaccount_id, int amount, Timestamp created) {
        this.id = id;
        this.toaccount_id = toaccount_id;
        this.fromaccount_id = fromaccount_id;
        this.amount = amount;
        this.created = created;
    }

    public Transaction(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToaccount_id() {
        return toaccount_id;
    }

    public void setToaccount_id(int toaccount_id) {
        this.toaccount_id = toaccount_id;
    }

    public int getFromaccount_id() {
        return fromaccount_id;
    }

    public void setFromaccount_id(int fromaccount_id) {
        this.fromaccount_id = fromaccount_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
