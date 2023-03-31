package lk.ijse.sanasa.model;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lk.ijse.sanasa.to.Deposit;
import lk.ijse.sanasa.to.DepositDetails;
import lk.ijse.sanasa.to.Transaction;
import lk.ijse.sanasa.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepositModel {
    public static ObservableList<String> getDepositTypes() throws SQLException, ClassNotFoundException {
        ObservableList<String> depositTypes = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from depositdetails");
        while(rst.next()){
            depositTypes.add(rst.getString(2));
        }
        return depositTypes;
    }

    public static void clearForm(JFXTextField[] jfxTextFields, Label[] labels, ComboBox[] comboBoxes) {
        for (JFXTextField jfxTextField : jfxTextFields) {
            jfxTextField.clear();
        }
        for (Label label : labels) {
            label.setText("");
        }
        for (ComboBox combobox : comboBoxes) {
            combobox.getSelectionModel().clearSelection();
        }
    }

    public static ObservableList<String> getAccountTypes(String accountNumber) throws SQLException, ClassNotFoundException {
        ObservableList<String> accTypesList=FXCollections.observableArrayList();
        if (accountNumber!=null) {
            ResultSet rstDepTypeAccIds = CrudUtil.execute("select DepositTypeID from depositaccount where AccountID = ?",accountNumber);
            ArrayList<String> ar= new ArrayList<>();
            while (rstDepTypeAccIds.next()){
                ar.add(rstDepTypeAccIds.getString(1));
            }
            for (String type:ar){
                ResultSet resultSet = CrudUtil.execute("select * from depositdetails where DepositTypeID = ?",type);
                resultSet.next();
                accTypesList.add(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            return accTypesList;
        }
        return null;
    }

    public static double getAccountBalance(String accountNumber, String depositTypeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select balance from depositaccount where accountID = ? AND depositTypeID = ?", accountNumber, depositTypeID);
        rst.next();
        return rst.getDouble(1);
    }

    public static String getAccountNumberFromNic(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rstAccNo = CrudUtil.execute("select accountID from accountdetails where nic = ? AND state='ACTIVE' ", nic);
        if(rstAccNo.next()){
            return rstAccNo.getString(1);
        }
        return null;
    }

    public static boolean setAccountBalance(String accountNo, double balance, String depositTypeID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update depositaccount set balance = ? where accountID = ? AND depositTypeID = ?;", balance, accountNo,depositTypeID);
    }

    public static boolean makeTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Transaction values (?,?,?,?,?,?)",
                transaction.getTransactionID(),
                transaction.getAccountID(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getTime(),
                transaction.getType()
        );
    }

    public static boolean makeDeposit(Deposit deposit) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Deposit values (?,?,?,?)",
                deposit.getDepositID(),
                deposit.getTransactionID(),
                deposit.getDepositTypeAccountID(),
                deposit.getAmount()
        );
    }

    public static String getDepositAccountID(String accountNumber, String accountTypeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select depositTypeAccountID from depositaccount where accountID = ? AND depositTypeID = ?",accountNumber,accountTypeID);
        rst.next();
        return rst.getString(1);
    }

    public static ObservableList<DepositDetails> getDepositTypesAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from depositdetails");
        ObservableList<DepositDetails> ob=FXCollections.observableArrayList();
        while (rst.next()){
            ob.add(
                    new DepositDetails(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3)
                    )
            );
        }
        return ob;
    }
}
