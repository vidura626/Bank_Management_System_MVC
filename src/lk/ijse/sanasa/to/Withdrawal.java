package lk.ijse.sanasa.to;

public class Withdrawal {
    String withdrawalID,transactionID,depositTypeAccountID;
    double amount;

    public String getWithdrawalID() {
        return withdrawalID;
    }

    public void setWithdrawalID(String withdrawalID) {
        this.withdrawalID = withdrawalID;
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

    public Withdrawal(String withdrawalID, String transactionID, String depositTypeAccountID, double amount) {
        this.withdrawalID = withdrawalID;
        this.transactionID = transactionID;
        this.depositTypeAccountID = depositTypeAccountID;
        this.amount = amount;
    }

    public Withdrawal() {
    }
}
