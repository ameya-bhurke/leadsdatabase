package service;

import dataaccess.TransactionFacade;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeadsController {

    @Autowired
    private TransactionFacade transactionFacade;

    @RequestMapping("/leads")
    public LeadsResponse transactions(@RequestParam(value="customer_id") String customerId,
                                      @RequestParam(value = "month") int month,
                                      @RequestParam(value = "year") int year ) {
        LeadsResponse leadsResponse = new LeadsResponse();
        List<Transaction> transactionList = transactionFacade.find(customerId,month,year);
        leadsResponse.setTransactionList(transactionList);
        leadsResponse.setClassifications(transactionFacade.classifyCustomerTransactions(transactionList));
        leadsResponse.setBalance(transactionFacade.balance(customerId));
        return leadsResponse;
    }

}
