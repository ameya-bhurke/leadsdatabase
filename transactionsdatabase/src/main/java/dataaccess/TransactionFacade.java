package dataaccess;

import model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A simple facade for loading and reading @{@link model.Transaction}s.
 */
public class TransactionFacade {

    private static final TransactionFacade TRANSACTION_FACADE = new TransactionFacade();

    @Autowired
    private SessionFactory sessionFactory;

    private TransactionFacade() {}

    public static final TransactionFacade instance() {
        return TRANSACTION_FACADE;
    }

    public void loadIntoDatabase(List<Transaction> transactionList) {
        final Session currentSession = sessionFactory.getCurrentSession();
        for(final Transaction transaction : transactionList) {
            currentSession.persist(transaction);
        }
    }

    public List<Transaction> find(final String customerId, final int month, final int year) {
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

        return sessionFactory.getCurrentSession()
                .createQuery("FROM Transaction WHERE date >= :stDate AND date <= :edDate ", Transaction.class)
                .setParameter("stDate", start.toDate())
                .setParameter("edDate", end.toDate())
                .list();
    }

    public long transactionCount() {
        return (Long) sessionFactory.getCurrentSession().createQuery("select transactionCount(*) from Transaction").uniqueResult();
    }
}
