package dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Transaction;

public class TransactionTestUtil {

    private TransactionTestUtil()
    {}

    public static List<Transaction> transactions() {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transactions.add(new Transaction("Test" + i, new Date(), 1000.0, "Test" + i ));
        }
        return transactions;
    }
}