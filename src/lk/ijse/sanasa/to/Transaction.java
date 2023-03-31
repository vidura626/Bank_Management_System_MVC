package lk.ijse.sanasa.to;

public class Transaction {
    String transactionID;
    String accountID;
    double amount;
    String date;
    String time;
    String type;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transaction(String transactionID, String accountID, double amount, String date, String time, String type) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public Transaction() {
    }
}
