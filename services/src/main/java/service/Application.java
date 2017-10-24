package service;

import dataaccess.Config;
import dataaccess.TransactionFacade;
import datafeeds.CsvTranscationFactory;
import model.ClassificationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import util.CustomClassificationEnumSerializer;
import util.CustomDateSerializer;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Logger;

@SpringBootApplication(scanBasePackages = {"model", "dataaccess","service"})
@SpringBootConfiguration
@Import({Config.class})
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

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addCustomDateDeserialization() {
        return new Jackson2ObjectMapperBuilderCustomizer() {

            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializerByType(Date.class, new CustomDateSerializer());
                jacksonObjectMapperBuilder.serializerByType(ClassificationEnum.class, new CustomClassificationEnumSerializer());
            }

        };
    }
}