package accumulator;

import model.ClassificationEnum;
import model.Transaction;

import java.util.Map;

/**
 * A simple interface to process a list of {@link model.Transaction}s.
 */
public interface TransactionAccumulator {

    void forEach(Transaction transaction);

    void accumulate(Map<ClassificationEnum,Boolean> classificationMap);
}
