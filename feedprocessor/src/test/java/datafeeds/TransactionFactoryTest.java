package datafeeds;

import model.Transaction;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionFactoryTest {

    private static final Logger logger = Logger.getLogger(TransactionFactoryTest.class.getName());

    @Test
    public void testLoadTransactions() {
        try {
            TransactionFactory transactionFactory = new CsvTranscationFactory("data.txt");
            List<Transaction> transactionList = transactionFactory.load();
            Assert.assertTrue("Transaction list empty", transactionList.size() == 2995);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading transactions", e);
        }
    }

    @Test
    public void testTransactionDataValidity() {
        try {
            TransactionFactory transactionFactory = new CsvTranscationFactory("data.txt");
            List<Transaction> transactionList = transactionFactory.load();
            Transaction transaction = transactionList.get(0);
            Assert.assertTrue("CustomerId is not as expected", transaction.getCustomerId().equals("3"));
            Assert.assertTrue("Amount is not as expected", transaction.getAmount() == -13.94);
            Assert.assertTrue("Description is not as expected", transaction.getDescription().equals("SIT MAURIS IPSUM SIT"));
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:MM:ss a");
            Date expectedDate = dateFormat.parse("9/05/2016 1:26:54 PM");
            Assert.assertTrue("Date is not as expected", transaction.getDate().equals(expectedDate));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading transactions", e);
        }
    }
}
