package lk.ijse.sanasa.to;

public class Loans {
    String loanID;
    double amount;
    String loanTypeID;
    String accountID;
    int installment;
    double installmentAmount;
    String issuedDate;
    int remainingInstallments;
    double remainingLoanAmount;
    int monthlyInstallmentDate;
    String settledOrNot;
    double interestAmount;

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
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

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public int getRemainingInstallments() {
        return remainingInstallments;
    }

    public void setRemainingInstallments(int remainingInstallments) {
        this.remainingInstallments = remainingInstallments;
    }

    public double getRemainingLoanAmount() {
        return remainingLoanAmount;
    }

    public void setRemainingLoanAmount(double remainingLoanAmount) {
        this.remainingLoanAmount = remainingLoanAmount;
    }

    public int getMonthlyInstallmentDate() {
        return monthlyInstallmentDate;
    }

    public void setMonthlyInstallmentDate(int monthlyInstallmentDate) {
        this.monthlyInstallmentDate = monthlyInstallmentDate;
    }

    public String getSettledOrNot() {
        return settledOrNot;
    }

    public void setSettledOrNot(String settledOrNot) {
        this.settledOrNot = settledOrNot;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Loans(String loanID, double amount, String loanTypeID, String accountID, int installment, double installmentAmount, String issuedDate, int remainingInstallments, double remainingLoanAmount, int monthlyInstallmentDate, String settledOrNot, double interestAmount) {
        this.loanID = loanID;
        this.amount = amount;
        this.loanTypeID = loanTypeID;
        this.accountID = accountID;
        this.installment = installment;
        this.installmentAmount = installmentAmount;
        this.issuedDate = issuedDate;
        this.remainingInstallments = remainingInstallments;
        this.remainingLoanAmount = remainingLoanAmount;
        this.monthlyInstallmentDate = monthlyInstallmentDate;
        this.settledOrNot = settledOrNot;
        this.interestAmount = interestAmount;
    }

    public Loans() {
    }
}
