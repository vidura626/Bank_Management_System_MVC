package lk.ijse.sanasa.model;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import lk.ijse.sanasa.to.AccountDetails;
import lk.ijse.sanasa.to.DepositAccount;
import lk.ijse.sanasa.to.DepositDetails;
import lk.ijse.sanasa.to.Loans;
import lk.ijse.sanasa.util.CrudUtil;
import lk.ijse.sanasa.util.ManageController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAccountModel {

    public static String generateAccountNumber(String txtNIC) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from accountdetails where NIC = ? AND state = 'ACTIVE'", txtNIC);
        if (!rst.next()) {
            return String.format("%02d", LocalTime.now().getSecond())
                    + "" +
                    String.format("%03d", LocalDate.now().getDayOfYear())
                    + String.format("%03d", LocalTime.now().getHour()+100)
                    + "" +
                    +LocalDate.now().getYear()
                    + String.format("%02d", LocalTime.now().getMinute());
        } else {
            return rst.getString(1);
        }
    }

    public static void clearForm(JFXTextField[] jfxTextFields, Label[] labels, JFXTextField[] resetTestFields) {
        for (JFXTextField jfxTextField : jfxTextFields) {
            jfxTextField.clear();
        }
        for (Label label : labels) {
            label.setText("");
        }
        for (JFXTextField resetTestField : resetTestFields) {
            resetTestField.setText("<Auto_generated_by_the_system>");
        }
    }

    public static boolean createAccount(AccountDetails accountDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into accountdetails values (?,?,?,?,?,?,?,?,?,?,?)",
                accountDetails.getAccountID(),
                accountDetails.getNic(),
                accountDetails.getName(),
                accountDetails.getAddress(),
                accountDetails.getContact(),
                accountDetails.getDateOfBirth(),
                accountDetails.getEmail(),
                accountDetails.getGender(),
                accountDetails.getRegDate(),
                accountDetails.getRegTime(),
                "ACTIVE"
        );
    }

    public static boolean isFormEmpty(JFXTextField[] jfxTextFields) {
        for (JFXTextField jfxTextField : jfxTextFields) {
            if (!jfxTextField.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean createDepositAccount(String depositAccountID, String depositTypeID, AccountDetails accountDetails, String amount) throws SQLException, ClassNotFoundException {
        boolean b = CrudUtil.execute("insert into depositaccount values (?,?,?,?,?)",
                depositAccountID,
                depositTypeID,
                accountDetails.getAccountID(),
                accountDetails.getRegDate(),
                Double.parseDouble(amount)
        );
        return b;
    }

    public static ObservableList getAccountTypes() throws SQLException, ClassNotFoundException {
        ObservableList ob= FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("select * from depositdetails");
        while (resultSet.next()){
            ob.add(resultSet.getString(1) +" "+ resultSet.getString(2));
        }
        return ob;
    }

    public static boolean makeFirstDeposit(String depositAccountID, String accountID, String amount) throws SQLException, ClassNotFoundException {
        String transactionID = ManageController.generateLastId(
                "TransactionID",
                "transaction",
                "T"
        );
        boolean b = CrudUtil.execute(
                "insert into transaction values (?,?,?,?,?,?)",
                transactionID,
                accountID,
                amount,
                LocalDate.now().toString(),
                LocalTime.now().toString(),
                "MONEY IN"
                );
        if(b){
            boolean b1 = CrudUtil.execute(
                    "insert into deposit values (?,?,?,?)",
                    ManageController.generateLastId(
                            "DepositID",
                            "deposit",
                            "D"
                    ),
                    transactionID,
                    depositAccountID,
                    amount
            );
            return b1;
        }
        return false;
    }

    public static AccountDetails getAccount(String accountNumber) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from accountdetails where AccountID = ? AND state='ACTIVE' ", accountNumber);
        return rst.next() ? new AccountDetails(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6),
                rst.getString(7),
                rst.getString(8),
                rst.getString(9),
                rst.getString(10),
                rst.getString(11)
        ) :null;
    }

    public static boolean updateAccountInfo(AccountDetails accountDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "Update accountdetails set NIC=?,Name=?,address=?,Contact=?,DateOfBirth=?,Email=?,Gender=?,RegDate=?,regTime=? where AccountID=? AND state = 'ACTIVE'",
                accountDetails.getNic(),
                accountDetails.getName(),
                accountDetails.getAddress(),
                accountDetails.getContact(),
                accountDetails.getDateOfBirth(),
                accountDetails.getEmail(),
                accountDetails.getGender(),
                accountDetails.getRegDate(),
                accountDetails.getRegTime(),
                accountDetails.getAccountID()
        );
    }

    public static ObservableList<AccountDetails> getAccounts() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from accountdetails where state='ACTIVE'");
        ObservableList<AccountDetails> ob = FXCollections.observableArrayList();
        while (rst.next()){
            ob.add(
                    new AccountDetails(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getString(7),
                            rst.getString(8),
                            rst.getString(9),
                            rst.getString(10),
                            rst.getString(11)
                    )
            );
        }
        return ob;
    }

    public static ObservableList<Loans> getLoans(String accountNumber) throws SQLException, ClassNotFoundException {
        ObservableList<Loans> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from loans where accountID=?", accountNumber);
        while (rst.next()){
            list.add(
                    new Loans(
                            rst.getString(1),
                            rst.getDouble(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getInt(5),
                            rst.getDouble(6),
                            rst.getString(7),
                            rst.getInt(8),
                            rst.getDouble(9),
                            rst.getInt(10),
                            rst.getString(11),
                            rst.getDouble(12)
                    )
            );
        }
        return list;
    }

    public static ObservableList<DepositAccount> getDepositAccounts(String accNo) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from depositaccount where AccountID = ?", accNo);
        ObservableList<DepositAccount> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new DepositAccount(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getDouble(5)
                    )
            );
        }
        return list;
    }

    public static boolean closeAccount(String accountNumber) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update accountdetails set state = 'CLOSED' where accountID = ?",accountNumber);
    }

    public static ObservableList<DepositAccount> getDepositAccountsAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select d.* from depositaccount d right join accountdetails a on a.AccountID = d.AccountID where State='ACTIVE'");
        ObservableList<DepositAccount> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new DepositAccount(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getDouble(5)
                    )
            );
        }
        return list;
    }

    public static ObservableList<DepositAccount> getDepositAccountsForccount() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select d.* from depositaccount d right join accountdetails a on a.AccountID = d.AccountID where a.AccountID='52335103202223' AND State='ACTIVE'");
        ObservableList<DepositAccount> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new DepositAccount(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getDouble(5)
                    )
            );
        }
        return list;
    }

    public static ObservableList<DepositDetails> getDepositTypeDetails() throws SQLException, ClassNotFoundException {
        ObservableList<DepositDetails> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from depositdetails");
        while (rst.next()){
            list.add(
                    new DepositDetails(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3)
                    )
            );
        }
        return list;
    }

    public static boolean deleteDepositType(String depositTypeID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from depositdetails where DepositTypeID = ?", depositTypeID);
    }

    public static boolean createDepositType(DepositDetails depositDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into depositdetails values (?,?,?)",depositDetails.getDepositTypeID(),depositDetails.getDescription(),depositDetails.getInterest());
    }

    public static boolean updateDepositType(DepositDetails depositTypeID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update depositdetails set Description = ?,Interest = ? where DepositTypeID = ?",depositTypeID.getDescription(),depositTypeID.getInterest(),depositTypeID.getDepositTypeID());
    }

    public static ObservableList<DepositAccount> getDepositAccountForID(String accountID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select d.* from depositaccount d right join accountdetails a on a.AccountID = d.AccountID where a.AccountID= ? AND a.state='ACTIVE'", accountID);
        ObservableList<DepositAccount> ob=FXCollections.observableArrayList();
        while (rst.next()){
            ob.add(
                    new DepositAccount(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getDouble(5)
                    )
            );
        }
        return ob;
    }
}
