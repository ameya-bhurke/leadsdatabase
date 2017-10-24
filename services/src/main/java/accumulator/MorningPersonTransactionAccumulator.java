package accumulator;

import model.ClassificationEnum;
import model.Transaction;
import org.joda.time.DateTime;

import java.util.Map;

public class MorningPersonTransactionAccumulator extends TransactionAccumulatorProxy {

    private int morningTransactionCount = 0;
    private int totalCount = 0;

    public MorningPersonTransactionAccumulator(TransactionAccumulator delegate) {
        super(delegate);
    }

    @Override
    public void forEach(Transaction transaction) {
        final DateTime transactionDateTime = new DateTime(transaction.getDate());
        final DateTime transactionDateWithNoonTime = transactionDateTime.withTime(12,0,0,0);
        totalCount++;
        if(transactionDateTime.isBefore(transactionDateWithNoonTime)) {
            morningTransactionCount++;
        }
        super.forEach(transaction);
    }

    @Override
    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        if(((morningTransactionCount * 100) / totalCount) > 50) {
            classificationMap.put(ClassificationEnum.MORNING_PERSON, true);
        }
        super.accumulate(classificationMap);
    }
}
