package lk.ijse.sanasa.to.table;

public class TableDetailsViewLoanInstallment {
    String loanInstallmentID,transactionID,loanID;
    String accountID;
    double amount;
    String date;
    String time;

    public String getLoanInstallmentID() {
        return loanInstallmentID;
    }

    public void setLoanInstallmentID(String loanInstallmentID) {
        this.loanInstallmentID = loanInstallmentID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
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

    public TableDetailsViewLoanInstallment(String loanInstallmentID, String transactionID, String loanID, String accountID, double amount, String date, String time) {
        this.loanInstallmentID = loanInstallmentID;
        this.transactionID = transactionID;
        this.loanID = loanID;
        this.accountID = accountID;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public TableDetailsViewLoanInstallment() {
    }
}
