package dataaccess;

import accumulator.TransactionAccumulator;
import accumulator.TransactionAccumulatorPipelineFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import model.ClassificationEnum;
import model.Transaction;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A simple facade for loading and reading @{@link model.Transaction}s.
 */
@Component
public class TransactionFacade {

    private static final Logger LOGGER = Logger.getLogger(TransactionFacade.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TransactionFacade() {}

    public void loadIntoDatabase(final List<Transaction> transactionList) {
        final String insert = "INSERT INTO transactions(customer_id, date, amount, description) VALUES(?,?,?,?)";
        jdbcTemplate.batchUpdate(insert, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, transactionList.get(i).getCustomerId());
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(transactionList.get(i).getDate().getTime()));
                preparedStatement.setDouble(3,transactionList.get(i).getAmount());
                preparedStatement.setString(4,transactionList.get(i).getDescription());
            }

            public int getBatchSize() {
                return transactionList.size();
            }
        });

    }

    public void initializeTransactionDatabase() {
        LOGGER.info("Creating transactions table");
        jdbcTemplate.execute("DROP TABLE transactions IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE transactions(" +
                "customer_id VARCHAR2(255), date TIMESTAMP, amount DOUBLE, description VARCHAR2(255) );" );
        LOGGER.info("Created transactions table successfully");
    }

    public List<Transaction> find(final String customerId, final int month, final int year) {
        LOGGER.info(MessageFormat.format("Find transactions for customer: {0}, for months:{1} and year: {2}",
                customerId, month, year));
        DateTime start = new DateTime();
        start = start.withYear(year);
        start = start.withMonthOfYear(month);
        start = start.dayOfMonth().withMinimumValue();
        start = start.withTimeAtStartOfDay();

        DateTime end = new DateTime();
        end = end.withYear(year);
        end = end.withMonthOfYear(month);
        end = end.dayOfMonth().withMaximumValue();
        end = end.withTime(23, 59, 59, 999);

        TransactionResultSetExtractor transactionResultSetExtractor = new TransactionResultSetExtractor();
        List<Transaction> transactionQueryResponseList =
                jdbcTemplate.query("SELECT * FROM transactions WHERE customer_id=? AND date >= ? AND date <= ? ORDER BY date ASC",
                new Object[] {customerId,start.toDate(),end.toDate()}, transactionResultSetExtractor);
        LOGGER.info(MessageFormat.format("Found {0} transactions.", transactionQueryResponseList.size()));
        return transactionQueryResponseList;
    }

    public double balance(final String customerId) {
        LOGGER.info(MessageFormat.format("Find balance for customer: {0}", customerId));
        BalanceResultSetExctractor balanceResultSetExctractor = new BalanceResultSetExctractor();
        final double balance = jdbcTemplate.query("SELECT SUM(amount) FROM transactions WHERE customer_id=?", new Object[] {customerId},
                balanceResultSetExctractor);
        LOGGER.info(MessageFormat.format("Balance for customer: {0} is {1}", customerId, balance));
        BigDecimal bigDecimal = new BigDecimal(balance);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public List<ClassificationEnum> classifyCustomerTransactions(List<Transaction> transactionList) {
        final TransactionAccumulator transactionAccumulator =
                TransactionAccumulatorPipelineFactory.buildTransactionAccumulatorPipeline();
        final List<ClassificationEnum> classifications = new ArrayList<ClassificationEnum>();
        Map<ClassificationEnum, Boolean> classificationMap = new HashMap<ClassificationEnum, Boolean>();
        for (final Transaction transaction : transactionList) {
            transactionAccumulator.forEach(transaction);
        }
        transactionAccumulator.accumulate(classificationMap);
        for (Map.Entry<ClassificationEnum, Boolean> classificationEntry: classificationMap.entrySet()){
            if(classificationEntry.getValue()) {
                classifications.add(classificationEntry.getKey());
            }
        }
        return classifications;
    }

    public long transactionCount() {
        LOGGER.info("Query total transaction count");
        RowCountCallbackHandler rowCountCallbackHandler = new RowCountCallbackHandler();
        jdbcTemplate.query("SELECT * FROM transactions", rowCountCallbackHandler);
        LOGGER.info(MessageFormat.format("Total count of transactions: {0}",
                rowCountCallbackHandler.getRowCount()));
        return rowCountCallbackHandler.getRowCount();
    }

    private class TransactionResultSetExtractor implements ResultSetExtractor<List<Transaction>> {

        public List<Transaction> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Transaction> transactionList = new ArrayList<Transaction>();
            while(resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setCustomerId(resultSet.getString(1));
                transaction.setDate(resultSet.getTimestamp(2));
                transaction.setAmount(resultSet.getDouble(3));
                transaction.setDescription(resultSet.getString(4));
                transactionList.add(transaction);
            }
            return transactionList;
        }
    }

    private class BalanceResultSetExctractor implements ResultSetExtractor<Double> {
        public Double extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            resultSet.next();
            return resultSet.getDouble(1);
        }
    }
}
