package service;

import dataaccess.SpringJdbcConfig;
import dataaccess.TransactionFacade;
import datafeeds.CsvTranscationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.text.MessageFormat;
import java.util.logging.Logger;

@SpringBootApplication(scanBasePackages = {"model", "dataaccess","service"})
@Import({SpringJdbcConfig.class})
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    @Autowired
    private TransactionFacade transactionFacade;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    public void run(String... strings) throws Exception {
        transactionFacade.initializeTransactionDatabase();
        transactionFacade.loadIntoDatabase(CsvTranscationFactory.newCsvTransactionFactory().load());
        LOGGER.info(MessageFormat.format("Loaded {0} transactions into transactions database.",
                transactionFacade.transactionCount()));
    }
}