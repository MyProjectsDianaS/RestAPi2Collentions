package com.didi.model;

import java.util.ArrayList;

public class CorporateBuyer extends Buyers{

    private String address;
    private String companyId;
    private ArrayList<ObjectId> transactions;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public ArrayList<ObjectId> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<ObjectId> transactions) {
        this.transactions = transactions;
    }
}
