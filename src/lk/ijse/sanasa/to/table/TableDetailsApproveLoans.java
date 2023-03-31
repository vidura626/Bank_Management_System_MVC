package lk.ijse.sanasa.to.table;

import javafx.scene.control.Button;

public class TableDetailsApproveLoans {
    String loanID;
    double amount;
    int installment;
    int monthlyInstallmentDate;
    Button button;
    String pendingLoanID;

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

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public int getMonthlyInstallmentDate() {
        return monthlyInstallmentDate;
    }

    public void setMonthlyInstallmentDate(int monthlyInstallmentDate) {
        this.monthlyInstallmentDate = monthlyInstallmentDate;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getPendingLoanID() {
        return pendingLoanID;
    }

    public void setPendingLoanID(String pendingLoanID) {
        this.pendingLoanID = pendingLoanID;
    }

    public TableDetailsApproveLoans(String loanID, double amount, int installment, int monthlyInstallmentDate, Button button, String pendingLoan) {
        this.loanID = loanID;
        this.amount = amount;
        this.installment = installment;
        this.monthlyInstallmentDate = monthlyInstallmentDate;
        this.button = button;
        this.pendingLoanID = pendingLoan;
    }

    public TableDetailsApproveLoans() {
    }
}
