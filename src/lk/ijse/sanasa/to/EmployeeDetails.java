package lk.ijse.sanasa.to;

public class EmployeeDetails {
    String empID,name,nic,address,contact,email,gender,dateOfBirth,joinedDate,role,account;
    double salary;

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public EmployeeDetails(String empID, String name, String nic, String address, String contact, String email, String gender, String dateOfBirth, String joinedDate, String role, String account, double salary) {
        this.empID = empID;
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.joinedDate = joinedDate;
        this.role = role;
        this.account = account;
        this.salary = salary;
    }

    public EmployeeDetails() {
    }
}
