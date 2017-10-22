package datafeeds;

import java.util.List;

/**
 * A simple factory interface to load transactions.
 */
public interface TransactionFactory {

    List<Transaction> load();
}
