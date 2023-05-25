package com.idaroos;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int tocustomer_id;
    private int fromcustomer_id;
    private int amount;
    private Timestamp created;

    public Transaction(int id, int tocustomer_id, int fromcustomer_id, int amount, Timestamp created) {
        this.id = id;
        this.tocustomer_id = tocustomer_id;
        this.fromcustomer_id = fromcustomer_id;
        this.amount = amount;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTocustomer_id() {
        return tocustomer_id;
    }

    public void setTocustomer_id(int tocustomer_id) {
        this.tocustomer_id = tocustomer_id;
    }

    public int getFromcustomer_id() {
        return fromcustomer_id;
    }

    public void setFromcustomer_id(int fromcustomer_id) {
        this.fromcustomer_id = fromcustomer_id;
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
