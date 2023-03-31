package lk.ijse.sanasa.to.table;

import com.jfoenix.controls.JFXButton;

public class TableDetailsCloseLoanBalances {
    String loanType;
    double balance;
    JFXButton button;

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    public TableDetailsCloseLoanBalances(String loanType, double balance, JFXButton button) {
        this.loanType = loanType;
        this.balance = balance;
        this.button = button;
    }

    public TableDetailsCloseLoanBalances() {
    }
}
