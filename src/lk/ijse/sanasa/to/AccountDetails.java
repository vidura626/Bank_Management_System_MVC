package lk.ijse.sanasa.to;

public class AccountDetails {
    private String accountID;
    private String nic;
    private String name;
    private String address;
    private String contact;
    private String dateOfBirth;
    private String email;
    private String gender;
    private String regDate;
    private String regTime;
    private String state;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AccountDetails(String accountID, String nic, String name, String address, String contact, String dateOfBirth, String email, String gender, String regDate, String regTime, String state) {
        this.accountID = accountID;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.gender = gender;
        this.regDate = regDate;
        this.regTime = regTime;
        this.state = state;
    }

    public AccountDetails() {
    }
}
