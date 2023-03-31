package lk.ijse.sanasa.to;

public class DepositAccount {
    String depositTypeAccountID,depositTypeID,accountID,createdDate;
    double balance;

    public String getDepositTypeAccountID() {
        return depositTypeAccountID;
    }

    public void setDepositTypeAccountID(String depositTypeAccountID) {
        this.depositTypeAccountID = depositTypeAccountID;
    }

    public String getDepositTypeID() {
        return depositTypeID;
    }

    public void setDepositTypeID(String depositTypeID) {
        this.depositTypeID = depositTypeID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public DepositAccount(String depositTypeAccountID, String depositTypeID, String accountID, String createdDate, double balance) {
        this.depositTypeAccountID = depositTypeAccountID;
        this.depositTypeID = depositTypeID;
        this.accountID = accountID;
        this.createdDate = createdDate;
        this.balance = balance;
    }

    public DepositAccount() {
    }
}
