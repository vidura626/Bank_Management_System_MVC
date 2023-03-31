package lk.ijse.sanasa.to;

public class Deposit {
    String depositID,transactionID,depositTypeAccountID;
    double amount;

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

    public Deposit(String depositID, String transactionID, String depositTypeAccountID, double amount) {
        this.depositID = depositID;
        this.transactionID = transactionID;
        this.depositTypeAccountID = depositTypeAccountID;
        this.amount = amount;
    }

    public Deposit() {
    }
}
