package service;

import model.Transaction;

import java.util.List;

public class TransactionResponse {

    private String classification;
    private List<Transaction> transactionList;

    public TransactionResponse() {
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
