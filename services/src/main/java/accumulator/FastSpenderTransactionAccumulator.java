package accumulator;

import model.ClassificationEnum;
import model.Transaction;
import org.joda.time.DateTime;

import java.util.Map;

public class FastSpenderTransactionAccumulator extends TransactionAccumulatorProxy {

    private double depositedAmount = 0.0;
    private DateTime depositedAmountDateTime = null;
    private double totalExpenseSinceLastDeposit = 0.0;
    private boolean fastSpender = false;

    public FastSpenderTransactionAccumulator(TransactionAccumulator delegate) {
        super(delegate);
    }

    @Override
    public void forEach(Transaction transaction) {
        if(transaction.getAmount() > 0) {
           depositedAmount = transaction.getAmount();
           depositedAmountDateTime = new DateTime(transaction.getDate());
           totalExpenseSinceLastDeposit = 0.0;
        } else if(transaction.getAmount() < 0) {
            DateTime transactionDateTime = new DateTime(transaction.getDate());
            if(depositedAmountDateTime != null && transactionDateTime.isBefore(depositedAmountDateTime.plusDays(7))) {
                totalExpenseSinceLastDeposit = totalExpenseSinceLastDeposit + Math.abs(transaction.getAmount());
                if(((totalExpenseSinceLastDeposit * 100) / depositedAmount) > 75) {
                    fastSpender = true;
                }
            }
        }
        super.forEach(transaction);
    }

    @Override
    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        classificationMap.put(ClassificationEnum.FAST_SPENDER, fastSpender);
        super.accumulate(classificationMap);
    }
}
