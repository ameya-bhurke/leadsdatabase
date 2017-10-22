package datafeeds;

import java.util.List;
import model.Transaction;

/**
 * A simple factory interface to load transactions.
 */
public interface TransactionFactory {

    List<Transaction> load();
}
