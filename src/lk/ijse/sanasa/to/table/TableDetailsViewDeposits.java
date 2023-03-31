package lk.ijse.sanasa.to.table;

public class TableDetailsViewDeposits {
    String depositID,transactionID,depositTypeAccountID;
    double amount;
    String accountID;
    String date;
    String time;
    String type;

    public String getDepositID() {
        return depositID;
    }

    public void setDepositID(String depositID) {
        this.depositID = depositID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getDepositTypeAccountID() {
        return depositTypeAccountID;
    }

    public void setDepositTypeAccountID(String depositTypeAccountID) {
        this.depositTypeAccountID = depositTypeAccountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
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

    public TableDetailsViewDeposits(String depositID, String transactionID, String depositTypeAccountID, double amount, String accountID, String date, String time, String type) {
        this.depositID = depositID;
        this.transactionID = transactionID;
        this.depositTypeAccountID = depositTypeAccountID;
        this.amount = amount;
        this.accountID = accountID;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public TableDetailsViewDeposits() {
    }
}
