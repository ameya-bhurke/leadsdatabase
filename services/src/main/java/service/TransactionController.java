package service;

import dataaccess.TransactionFacade;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    public TransactionResponse transactions( @RequestParam(value="customer_id") String customerId,
                                            @RequestParam(value = "month") int month,
                                            @RequestParam(value = "year") int year ) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionList(TransactionFacade.instance().find(customerId,month,year));
        return transactionResponse;
    }

}
