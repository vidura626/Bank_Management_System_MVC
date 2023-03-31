package lk.ijse.sanasa.to;

public class DepositDetails {
    String depositTypeID,description;
    double interest;

    public String getDepositTypeID() {
        return depositTypeID;
    }

    public void setDepositTypeID(String depositTypeID) {
        this.depositTypeID = depositTypeID;
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

    public DepositDetails(String depositTypeID, String description, double interest) {
        this.depositTypeID = depositTypeID;
        this.description = description;
        this.interest = interest;
    }

    public DepositDetails() {
    }
}
