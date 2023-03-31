package lk.ijse.sanasa.to;

public class BillDetails {
    String billId, description;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BillDetails(String billId, String description) {
        this.billId = billId;
        this.description = description;
    }

    public BillDetails() {
    }
}
