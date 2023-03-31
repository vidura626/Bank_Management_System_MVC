package lk.ijse.sanasa.to.table;

public class TableDetailsLoanInstallmentTbl01 {
    String loanID;
    double installmentAmount;
    String date;

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  TableDetailsLoanInstallmentTbl01(String loanID, double installmentAmount, String date) {
        this.loanID = loanID;
        this.installmentAmount = installmentAmount;
        this.date = date;
    }

    public TableDetailsLoanInstallmentTbl01() {
    }
}
