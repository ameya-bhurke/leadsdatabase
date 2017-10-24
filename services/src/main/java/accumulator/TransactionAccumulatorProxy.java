package accumulator;

import model.ClassificationEnum;
import model.Transaction;

import java.util.Map;

/**
 * A simple proxy implementation of the {@link TransactionAccumulator} interface.
 */
public class TransactionAccumulatorProxy implements TransactionAccumulator {

    private final TransactionAccumulator delegate;

    public TransactionAccumulatorProxy(final TransactionAccumulator delegate) {
        this.delegate = delegate;
    }

    public void forEach(Transaction transaction) {
        if(delegate != null) {
            delegate.forEach(transaction);
        }
    }

    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        if(delegate != null) {
            delegate.accumulate(classificationMap);
        }
    }
}
