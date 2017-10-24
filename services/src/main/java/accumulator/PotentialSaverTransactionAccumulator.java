package accumulator;

import model.ClassificationEnum;
import model.Transaction;

import java.util.Map;

public class PotentialSaverTransactionAccumulator extends TransactionAccumulatorProxy {

    private double deposit = 0.0;
    private double expense = 0.0;

    public PotentialSaverTransactionAccumulator(TransactionAccumulator delegate) {
        super(delegate);
    }

    @Override
    public void forEach(Transaction transaction) {
        if(transaction.getAmount() > 0) {
            deposit = deposit + transaction.getAmount();
        } else if (transaction.getAmount() < 0) {
            expense = expense + Math.abs(transaction.getAmount());
        }
        super.forEach(transaction);
    }

    @Override
    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        if(((expense * 100) / deposit) < 25) {
            classificationMap.put(ClassificationEnum.POTENTIAL_SAVER, true);
        } else {
            classificationMap.put(ClassificationEnum.POTENTIAL_SAVER, false);
        }
        super.accumulate(classificationMap);
    }
}
