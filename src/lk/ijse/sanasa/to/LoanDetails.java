package lk.ijse.sanasa.to;

public class LoanDetails {
    String loanTypeID,description;
    double interest;
    String Amounts;

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
        return Amounts;
    }

    public void setAmounts(String amounts) {
        Amounts = amounts;
    }

    public LoanDetails(String loanTypeID, String description, double interest, String amounts) {
        this.loanTypeID = loanTypeID;
        this.description = description;
        this.interest = interest;
        Amounts = amounts;
    }

    public LoanDetails() {
    }
}
