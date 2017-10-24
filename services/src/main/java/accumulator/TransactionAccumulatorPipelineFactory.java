package accumulator;

/**
 * A factory to build a transaction accumulator pipeline to classify customer transaction pattern.
 */
public class TransactionAccumulatorPipelineFactory {

    public static TransactionAccumulator buildTransactionAccumulatorPipeline() {
        TransactionAccumulator transactionAccumulatorPipeline = new PotentialLoanTransactionAccumulator();
        transactionAccumulatorPipeline = new PotentialSaverTransactionAccumulator(transactionAccumulatorPipeline);
        transactionAccumulatorPipeline = new MorningPersonTransactionAccumulator(transactionAccumulatorPipeline);
        transactionAccumulatorPipeline = new FastSpenderTransactionAccumulator(transactionAccumulatorPipeline);
        transactionAccumulatorPipeline = new BigTicketSpenderTransactionAccumulator(transactionAccumulatorPipeline);
        transactionAccumulatorPipeline = new BigSpenderTransactionAccumulator(transactionAccumulatorPipeline);
        transactionAccumulatorPipeline = new AfternoonPersonTransactionAccumulator(transactionAccumulatorPipeline);
        return transactionAccumulatorPipeline;
    }
}
