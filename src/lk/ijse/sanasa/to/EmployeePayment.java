package lk.ijse.sanasa.to;

public class EmployeePayment {
    String empID,inBankPaymentID;

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getInBankPaymentID() {
        return inBankPaymentID;
    }

    public void setInBankPaymentID(String inBankPaymentID) {
        this.inBankPaymentID = inBankPaymentID;
    }

    public EmployeePayment(String empID, String inBankPaymentID) {
        this.empID = empID;
        this.inBankPaymentID = inBankPaymentID;
    }

    public EmployeePayment() {
    }
}
