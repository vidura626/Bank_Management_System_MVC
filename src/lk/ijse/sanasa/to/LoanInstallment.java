package lk.ijse.sanasa.to;

public class LoanInstallment {
    String loanInstallmentID,transactionID,loanID;

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

    public LoanInstallment(String loanInstallmentID, String transactionID, String loanID) {
        this.loanInstallmentID = loanInstallmentID;
        this.transactionID = transactionID;
        this.loanID = loanID;
    }

    public LoanInstallment() {
    }
}
