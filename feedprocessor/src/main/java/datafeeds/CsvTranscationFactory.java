package datafeeds;

import model.Transaction;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvTranscationFactory implements TransactionFactory {

    private static final Logger LOGGER = Logger.getLogger(CsvTranscationFactory.class.getName());

    private final String transactionFile;

    public CsvTranscationFactory(String transactionFile) {
        this.transactionFile = transactionFile;
    }

    public static final CsvTranscationFactory newCsvTransactionFactory() {
        return new CsvTranscationFactory("classpath:data.txt");
    }

    public List<Transaction> load() {
        try {
            CsvSchema bootstrapSchema = CsvSchema.builder()
                    .addColumn("customerId")
                    .addColumn("date")
                    .addColumn("amount", CsvSchema.ColumnType.NUMBER)
                    .addColumn("description")
                    .setUseHeader(false)
                    .setSkipFirstDataRow(true)
                    .build();
            CsvMapper mapper = new CsvMapper();
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:MM:ss a");
            mapper.setDateFormat(dateFormat);
            MappingIterator<Transaction> mappingIterator =
                    mapper.reader(Transaction.class).with(bootstrapSchema).readValues(
                            new ClassPathResource(transactionFile).getInputStream());
            return mappingIterator.readAll();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading transactions from file", e);
            return Collections.emptyList();
        }
    }
}
