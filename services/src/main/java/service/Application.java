package service;

import dataaccess.TransactionFacade;
import datafeeds.CsvTranscationFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        TransactionFacade.instance().loadIntoDatabase(CsvTranscationFactory.newCsvTransactionFactory().load());
    }
}