package lk.ijse.sanasa.to;

public class ArrearsLoans {
    String arrearsLoanId, loanId,arrearsDateFrom;
    double amount;

    public String getArrearsLoanId() {
        return arrearsLoanId;
    }

    public void setArrearsLoanId(String arrearsLoanId) {
        this.arrearsLoanId = arrearsLoanId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getArrearsDateFrom() {
        return arrearsDateFrom;
    }

    public void setArrearsDateFrom(String arrearsDateFrom) {
        this.arrearsDateFrom = arrearsDateFrom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ArrearsLoans(String arrearsLoanId, String loanId, String arrearsDateFrom, double amount) {
        this.arrearsLoanId = arrearsLoanId;
        this.loanId = loanId;
        this.arrearsDateFrom = arrearsDateFrom;
        this.amount = amount;
    }

    public ArrearsLoans() {
    }
}
