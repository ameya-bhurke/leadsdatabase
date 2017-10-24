package accumulator;

import model.ClassificationEnum;
import model.Transaction;
import org.joda.time.DateTime;

import java.util.Map;

public class AfternoonPersonTransactionAccumulator extends TransactionAccumulatorProxy {

    private int afternoonTransactionCount = 0;
    private int totalCount = 0;

    public AfternoonPersonTransactionAccumulator(TransactionAccumulator delegate) {
        super(delegate);
    }

    @Override
    public void forEach(Transaction transaction) {
        final DateTime transactionDateTime = new DateTime(transaction.getDate());
        final DateTime transactionDateWithNoonTime = transactionDateTime.withTime(12,0,0,0);
        totalCount++;
        if(transactionDateTime.isAfter(transactionDateWithNoonTime)) {
            afternoonTransactionCount++;
        }
        super.forEach(transaction);
    }

    @Override
    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        if(((afternoonTransactionCount * 100) / totalCount) > 50) {
            classificationMap.put(ClassificationEnum.AFTERNOON_PERSON, true);
        } else {
            classificationMap.put(ClassificationEnum.AFTERNOON_PERSON, false);
        }
        super.accumulate(classificationMap);
    }
}
