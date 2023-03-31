package lk.ijse.sanasa.to.table;

import com.jfoenix.controls.JFXButton;

public class TableDetailsLoanTypeDetails {
    String loanTypeID,description;
    double interest;
    String amounts;
    JFXButton button;

    public String getLoanTypeID() {
        return loanTypeID;
    }

    public void setLoanTypeID(String loanTypeID) {
        this.loanTypeID = loanTypeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    public TableDetailsLoanTypeDetails(String loanTypeID, String description, double interest, String amounts, JFXButton button) {
        this.loanTypeID = loanTypeID;
        this.description = description;
        this.interest = interest;
        this.amounts = amounts;
        this.button = button;
    }

    public TableDetailsLoanTypeDetails() {
    }
}
