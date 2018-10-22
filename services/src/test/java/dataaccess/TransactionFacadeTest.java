package dataaccess;

import java.util.List;

import org.junit.Test;

import model.ClassificationEnum;

public class TransactionFacadeTest {

    @Test
    public void testClassifyCustomerTransactions() {
        TransactionFacade transactionFacade = new TransactionFacade();
        List<ClassificationEnum> classifications = 
            transactionFacade.classifyCustomerTransactions(TransactionTestUtil.transactions());
        System.out.println("Classifications: " + classifications);
    }
}