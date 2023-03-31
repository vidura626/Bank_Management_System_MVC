package lk.ijse.sanasa.to;

public class RejectedLoans {
    String rejLoanID;
    double amount;
    String loanTypeID,accountID,reason;

    @Override
    public String toString() {
        return "RejectedLoans{" +
                "rejLoanID='" + rejLoanID + '\'' +
                ", amount=" + amount +
                ", loanTypeID='" + loanTypeID + '\'' +
                ", accountID='" + accountID + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    public String getRejLoanID() {
        return rejLoanID;
    }

    public void setRejLoanID(String rejLoanID) {
        this.rejLoanID = rejLoanID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getLoanTypeID() {
        return loanTypeID;
    }

    public void setLoanTypeID(String loanTypeID) {
        this.loanTypeID = loanTypeID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RejectedLoans(String rejLoanID, double amount, String loanTypeID, String accountID, String reason) {
        this.rejLoanID = rejLoanID;
        this.amount = amount;
        this.loanTypeID = loanTypeID;
        this.accountID = accountID;
        this.reason = reason;
    }

    public RejectedLoans() {
    }
}
