package lk.ijse.sanasa.to.table;

import com.jfoenix.controls.JFXButton;

public class TableDetailsCloseAccBalances {
    String depositTypeID;
    double balance;
    JFXButton button;

    public String getDepositTypeID() {
        return depositTypeID;
    }

    public void setDepositTypeID(String depositTypeID) {
        this.depositTypeID = depositTypeID;
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

    public TableDetailsCloseAccBalances(String depositTypeID, double balance, JFXButton button) {
        this.depositTypeID = depositTypeID;
        this.balance = balance;
        this.button = button;
    }

    public TableDetailsCloseAccBalances() {
    }
}
