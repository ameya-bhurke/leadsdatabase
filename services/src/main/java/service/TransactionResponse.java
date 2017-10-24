package service;

import model.ClassificationEnum;
import model.Transaction;

import java.util.List;

public class TransactionResponse {

    private double balance;
    private List<ClassificationEnum> classifications;
    private List<Transaction> transactionList;

    public TransactionResponse() {
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<ClassificationEnum> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<ClassificationEnum> classifications) {
        this.classifications = classifications;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
