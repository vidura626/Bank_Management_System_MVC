package lk.ijse.sanasa.to.table;

public class TableDetailsViewInterAccountTransaction {
    String interAccountTransactionID,transactionID,account01ID,account02ID;
    double amount;
    String date;
    String time;

    public String getInterAccountTransactionID() {
        return interAccountTransactionID;
    }

    public void setInterAccountTransactionID(String interAccountTransactionID) {
        this.interAccountTransactionID = interAccountTransactionID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getAccount01ID() {
        return account01ID;
    }

    public void setAccount01ID(String account01ID) {
        this.account01ID = account01ID;
    }

    public String getAccount02ID() {
        return account02ID;
    }

    public void setAccount02ID(String account02ID) {
        this.account02ID = account02ID;
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

    public TableDetailsViewInterAccountTransaction(String interAccountTransactionID, String transactionID, String account01ID, String account02ID, double amount, String date, String time) {
        this.interAccountTransactionID = interAccountTransactionID;
        this.transactionID = transactionID;
        this.account01ID = account01ID;
        this.account02ID = account02ID;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public TableDetailsViewInterAccountTransaction() {
    }
}
