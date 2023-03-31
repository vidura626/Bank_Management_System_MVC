package lk.ijse.sanasa.to;

public class BillPayment {
    String billId,inBankPaymentId;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getInBankPaymentId() {
        return inBankPaymentId;
    }

    public void setInBankPaymentId(String inBankPaymentId) {
        this.inBankPaymentId = inBankPaymentId;
    }

    public BillPayment(String billId, String inBankPaymentId) {
        this.billId = billId;
        this.inBankPaymentId = inBankPaymentId;
    }

    public BillPayment() {
    }
}
