package accumulator;

import model.ClassificationEnum;
import model.Transaction;

import java.util.Map;

public class PotentialLoanTransactionAccumulator implements TransactionAccumulator {

    public void forEach(Transaction transaction) {

    }

    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        final boolean bigSpender = classificationMap.get(ClassificationEnum.BIG_SPENDER);
        final boolean fastSpender = classificationMap.get(ClassificationEnum.FAST_SPENDER);
        if(bigSpender && fastSpender) {
            classificationMap.put(ClassificationEnum.POTENTIAL_LOAN, true);
            classificationMap.put(ClassificationEnum.BIG_SPENDER, false);
            classificationMap.put(ClassificationEnum.FAST_SPENDER, false);
        }
    }
}
