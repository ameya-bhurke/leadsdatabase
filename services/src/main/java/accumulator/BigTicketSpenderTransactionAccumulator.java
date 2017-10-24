package accumulator;

import model.ClassificationEnum;
import model.Transaction;

import java.util.Map;

public class BigTicketSpenderTransactionAccumulator extends TransactionAccumulatorProxy {

    private boolean bigTicketSpender = false;

    public BigTicketSpenderTransactionAccumulator(TransactionAccumulator delegate) {
        super(delegate);
    }

    @Override
    public void forEach(Transaction transaction) {
        if(!bigTicketSpender && Math.abs(transaction.getAmount()) > 1000) {
            bigTicketSpender = true;
        }
        super.forEach(transaction);
    }

    @Override
    public void accumulate(Map<ClassificationEnum, Boolean> classificationMap) {
        classificationMap.put(ClassificationEnum.BIG_TICKET_SPENDER,bigTicketSpender);
        super.accumulate(classificationMap);
    }
}
