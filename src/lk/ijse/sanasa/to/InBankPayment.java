package lk.ijse.sanasa.to;

public class InBankPayment {
    String inBankPaymentID,type,date,time;
    double amount;

    public String getInBankPaymentID() {
        return inBankPaymentID;
    }

    public void setInBankPaymentID(String inBankPaymentID) {
        this.inBankPaymentID = inBankPaymentID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public InBankPayment(String inBankPaymentID, String type, String date, String time, double amount) {
        this.inBankPaymentID = inBankPaymentID;
        this.type = type;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public InBankPayment() {
    }
}
