package datafeeds;

import java.util.Date;

/**
 * A bean class for Transactions. Each instance hold properties for a single transaction.
 */
public final class Transaction {

    private String CustomerId;
    private Date Date;
    private double Amount;
    private String Description;

    public Transaction() {
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public Date getDate() {
        return Date;
    }

    public double getAmount() {
        return Amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setCustomerId(String customerId) {
        this.CustomerId = customerId;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public void setAmount(double amount) {
        this.Amount = amount;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (Double.compare(that.Amount, Amount) != 0) return false;
        if (!CustomerId.equals(that.CustomerId)) return false;
        if (!Date.equals(that.Date)) return false;
        return Description.equals(that.Description);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = CustomerId.hashCode();
        result = 31 * result + Date.hashCode();
        temp = Double.doubleToLongBits(Amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "CustomerId='" + CustomerId + '\'' +
                ", Date=" + Date +
                ", Amount=" + Amount +
                ", Description='" + Description + '\'' +
                '}';
    }
}
