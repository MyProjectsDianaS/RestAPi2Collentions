package com.didi.model;

import java.util.ArrayList;
import java.util.Date;

public class IndividualBuyer extends Buyers {

    private Date dateRegistered;
    private String buyerPersonalId;
    private ArrayList<ObjectId> transactions;

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getBuyerPersonalId() {
        return buyerPersonalId;
    }

    public void setBuyerPersonalId(String buyerPersonalId) {
        this.buyerPersonalId = buyerPersonalId;
    }

    public ArrayList<ObjectId> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<ObjectId> transactions) {
        this.transactions = transactions;
    }
}
