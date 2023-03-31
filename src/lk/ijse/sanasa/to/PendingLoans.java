package lk.ijse.sanasa.to;

public class PendingLoans {
    String pendingLoansID;
    double amount;
    String loanTypeID;
    String accountID;
    int installment;
    double installmentAmount;

    public String getPendingLoansID() {
        return pendingLoansID;
    }

    public void setPendingLoansID(String pendingLoansID) {
        this.pendingLoansID = pendingLoansID;
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

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public PendingLoans(String pendingLoansID, double amount, String loanTypeID, String accountID, int installment, double installmentAmount) {
        this.pendingLoansID = pendingLoansID;
        this.amount = amount;
        this.loanTypeID = loanTypeID;
        this.accountID = accountID;
        this.installment = installment;
        this.installmentAmount = installmentAmount;
    }

    public PendingLoans() {
    }
}
