package service;

import dataaccess.TransactionFacade;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionFacade transactionFacade;

    @RequestMapping("/transaction")
    public TransactionResponse transactions( @RequestParam(value="customer_id") String customerId,
                                            @RequestParam(value = "month") int month,
                                            @RequestParam(value = "year") int year ) {
        TransactionResponse transactionResponse = new TransactionResponse();
        List<Transaction> transactionList = transactionFacade.find(customerId,month,year);
        transactionResponse.setTransactionList(transactionList);
        transactionResponse.setClassifications(transactionFacade.classifyCustomerTransactions(transactionList));
        transactionResponse.setBalance(transactionFacade.balance(customerId));
        return transactionResponse;
    }

}
