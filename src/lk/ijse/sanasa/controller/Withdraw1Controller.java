package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.DepositModel;
import lk.ijse.sanasa.model.WithdrawalModel;
import lk.ijse.sanasa.to.DepositTable;
import lk.ijse.sanasa.to.Transaction;
import lk.ijse.sanasa.to.Withdrawal;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class Withdraw1Controller {

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXComboBox<String> cmbDepositType;

    @FXML
    private Label lblNotifyWithdrawalType;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private Label lblNotifyAmount;

    @FXML
    private Label lblNotifyAdd;

    @FXML
    private Label lblNotifyWithdraw;

    @FXML
    private Label lblTransactionID;

    @FXML
    private TableView<DepositTable> tblWithdraw;

    @FXML
    private TableColumn<?, ?> colWithdrawType;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBalance;

    public void initialize(){
        //Set transaction ID
        loadTransactionID();
    }

    private void loadTransactionID() {
        try {
            lblTransactionID.setText(
                    ManageController.generateLastId(
                            "TransactionID",
                            "transaction",
                            "T"
                    )
            );
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnClearAllOnAction() {
        txtAccNo.setEditable(true);
        txtAccNo.setAlignment(Pos.CENTER_LEFT);
        txtAccNo.setStyle(null);
        txtAccNo.clear();
        btnClearFormOnAction();
        tblWithdraw.setItems(FXCollections.observableArrayList());
    }

    @FXML
    void btnClearFormOnAction() {
        ManageController.getInstance().setTextFields(
                txtAmount
        );
        ManageController.getInstance().setLabels(
                lblNotifyAccNo,
                lblNotifyWithdrawalType,
                lblNotifyAmount,
                lblNotifyAdd
        );
        cmbDepositType.getSelectionModel().clearSelection();
        ManageController.getInstance().clearForm();
    }

    @FXML
    void btnWithdrawOnAction() throws SQLException, ClassNotFoundException {
        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            //Get account number
            String accountNumber = txtAccNo.getText();
            //Make transaction
            String lastID = ManageController.generateLastId("TransactionID", "transaction", "T");
            Transaction transaction = new Transaction(
                    lastID,
                    accountNumber,
                    calculateTotal(tblWithdraw),
                    LocalDate.now().toString(),
                    LocalTime.now().toString(),
                    "MONEY OUT"
            );
            boolean isAddedTransaction = DepositModel.makeTransaction(transaction);

            if (isAddedTransaction) {

                for (int i = 0; i < tblWithdraw.getItems().size(); i++) {
                    //make withdrawal
                    Withdrawal withdraw = new Withdrawal(
                            ManageController.generateLastId(
                                    "WithdrawalID",
                                    "withdrawal",
                                    "W"
                            ),
                            transaction.getTransactionID(),
                            DepositModel.getDepositAccountID(accountNumber,tblWithdraw.getItems().get(i).getDepositType().split("[ ]")[0]),
                            tblWithdraw.getItems().get(i).getAmount()
                    );
                    boolean isAddedWithdrawal = WithdrawalModel.makeWithdraw(withdraw);
                    if (isAddedWithdrawal) {
                        //Set account balance
                        boolean b = DepositModel.setAccountBalance(
                                accountNumber,
                                tblWithdraw.getItems().get(i).getBalance(),
                                tblWithdraw.getItems().get(i).getDepositType().split("[ ]")[0]
                        );
                        if (b) {
                            if (i == tblWithdraw.getItems().size() - 1) {
                                ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                                if(result.orElse(no)==ok){
                                    DbConnection.getInstance().getConnection().commit();
                                    new Alert(Alert.AlertType.CONFIRMATION,"Withdrawal Success !").show();
                                    showReport();
                                }else {
                                    DbConnection.getInstance().getConnection().rollback();
                                    new Alert(Alert.AlertType.WARNING,"Canceled !").show();
                                }

                                lblTransactionID.setText(
                                        ManageController.generateLastId(
                                                "TransactionID",
                                                "transaction",
                                                "T"
                                        )
                                );
                                btnClearAllOnAction();
                            }
                        } else {
                            DbConnection.getInstance().getConnection().rollback();
                            FormValidate.getInstance().makeInstanceWarningError(
                                    lblNotifyWithdraw,
                                    "Withdrawal Failed !"
                            );
                            return;
                        }
                    }else {
                        DbConnection.getInstance().getConnection().rollback();
                        FormValidate.getInstance().makeInstanceWarningError(
                                lblNotifyWithdraw,
                                "Withdrawal Failed !"
                        );
                        return;
                    }
                }
            }else {
                DbConnection.getInstance().getConnection().rollback();
                FormValidate.getInstance().makeInstanceWarningError(
                        lblNotifyWithdraw,
                        "Withdrawal Failed !"
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            DbConnection.getInstance().getConnection().rollback();
            System.out.println(e);
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private void showReport(){

        try {
            JasperDesign jasperDesign= JRXmlLoader.load("D:\\Working_Directory\\Sanasa\\Final_Project_IDSE_1st_Semester\\src\\lk\\ijse\\sanasa\\report\\WithdrawReport.jrxml");
            String query="SELECT sanasa.withdrawal.`TransactionID`, sanasa.withdrawal.`Amount`, sanasa.depositdetails.`Description`, sanasa.depositaccount.`Balance`, sanasa.transaction.`AccountID`, sanasa.withdrawal.`WithdrawalID` FROM sanasa.withdrawal INNER JOIN sanasa.transaction ON sanasa.withdrawal.`TransactionID` = sanasa.transaction.`TransactionID` INNER JOIN sanasa.depositaccount ON sanasa.withdrawal.`DepositTypeAccountID` = sanasa.depositaccount.`DepositTypeAccountID` INNER JOIN sanasa.depositdetails ON sanasa.depositaccount.`DepositTypeID` = sanasa.depositdetails.`DepositTypeID` ORDER BY sanasa.withdrawal.`WithdrawalID` DESC limit 1";

            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(query);

            jasperDesign.setQuery(updateQuery);

            JasperReport jasperReport= null;
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DbConnection.getInstance().getConnection());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtAmountOnAction() {
        lblNotifyAdd.setText("");
        boolean validate = FormValidate.getInstance().isValidate(
                "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                txtAmount.getText()
        );
        if(validate){
            boolean validate1 = FormValidate.getInstance().isValidate(
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))$",
                    txtAmount.getText()
            );
            if(validate1){
                txtAmount.setText(txtAmount.getText()+".00");
                btnAddOnAction();
            }
        }
    }

    @FXML
    void txtAmountOnKeyReleased() {
        try {
            lblNotifyAdd.setText("");
            boolean validate = FormValidate.getInstance().isValidate(
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                    txtAmount.getText()
            );
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyAmount,
                    validate,
                    txtAmount
            );
            if(validate){
                isAvailableBalance(Double.parseDouble(txtAmount.getText()),tblWithdraw.getItems());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean isAvailableBalance(double amount, ObservableList<DepositTable> items) throws SQLException, ClassNotFoundException {

        if (tblWithdraw.getItems().size() > 0) {
            boolean b1 = false;
            for (DepositTable item : items) {
                if (item.getDepositType().equals(cmbDepositType.getValue())) {
                    b1 = item.getBalance() >= amount;
                }
            }
            if (!b1) {
                lblNotifyAmount.setStyle("-fx-text-fill: red");
                lblNotifyAmount.setText("Account balance is not sufficient");
            }
            return b1;
        } else {
            boolean b2;
            String accountNumber = txtAccNo.getText();
            double accountBalance = DepositModel.getAccountBalance(accountNumber, cmbDepositType.getValue().split("[ ]")[0]);
            b2 = amount<=accountBalance;
            if(!b2){
                lblNotifyAmount.setStyle("-fx-text-fill: red");
                lblNotifyAmount.setText("Account balance is not sufficient");
            }
            return b2;
        }
    }

    @FXML
    void txtIdOnKeyReleased()       {
        try {
            lblNotifyAdd.setStyle("-fx-text-fill: null");
            lblNotifyAdd.setText("");
            boolean validate = FormValidate.getInstance().isValidate("^([0-9]{14})$", txtAccNo.getText());
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyAccNo,
                    validate,
                    txtAccNo
            );
            if (validate) {
                loadAccTypes();
            } else {
                cmbDepositType.setItems(null);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }

    }

    private void loadAccTypes() throws SQLException, ClassNotFoundException {
        String accountNumber = txtAccNo.getText();
        ObservableList<String> accTypes = DepositModel.getAccountTypes(accountNumber);
        if(accTypes!=null){
            cmbDepositType.setItems(accTypes);
            lblNotifyAccNo.setText("Account founded ! ");
            lblNotifyAccNo.setStyle("-fx-text-fill: green");
        } else {
            lblNotifyAccNo.setText("Account is not found ! ");
            lblNotifyAccNo.setStyle("-fx-text-fill: red");
        }
    }

    public void btnAddOnAction() {
        try {
            FormValidate.getInstance().setRegexList(
                    "^([0-9]{14})$",
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$"
            );
            FormValidate.getInstance().setTextFieldsList(
                    txtAccNo,
                    txtAmount
            );
            FormValidate.getInstance().setComboboxes(
                    cmbDepositType
            );

            boolean isValidate = FormValidate.getInstance().checkValidation();

            boolean validate =false;
            if(
                FormValidate.getInstance().isValidate(
                        "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                        txtAmount.getText()
                )
            ){
                validate = isAvailableBalance(Double.parseDouble(txtAmount.getText()),tblWithdraw.getItems());
            }
            boolean isFormValidate = isValidate && validate;

            if (isFormValidate) {
                //Account filed
                txtAccNo.setEditable(false);
                txtAccNo.setAlignment(Pos.BASELINE_CENTER);
                txtAccNo.setStyle("-fx-background-color: #9a9a9a");

                //Prepare data to add row
                //get account_number and depositTypeID
                String accountNo = txtAccNo.getText();
                String depositTypeID = cmbDepositType.getValue().split("[ ]")[0];

                //get and set balance
                double balance = DepositModel.getAccountBalance(accountNo,depositTypeID);
                double amount = Double.parseDouble(txtAmount.getText());
                balance-=amount;
                String depositType = cmbDepositType.getValue();

                //Set visible table
                colWithdrawType.setCellValueFactory(new PropertyValueFactory<>("depositType"));
                colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
                colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

                //Add or update rows
                ObservableList<DepositTable> items = tblWithdraw.getItems();
                int index = isNewRow(items, depositType);
                if (index==-1) {
                    items.add(new DepositTable(depositType, amount, balance));
                } else {
                    items.get(index).setAmount(items.get(index).getAmount() + amount);
                    items.get(index).setBalance(items.get(index).getBalance() - amount);
                }
                tblWithdraw.setItems(items);
                tblWithdraw.refresh();
            } else {
                FormValidate.getInstance().makeInstanceWarningError(
                        lblNotifyAdd,
                        "Fill the form correctly"
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void btnRemoveOnAction() {

    }

    private int isNewRow(ObservableList<DepositTable> items, String depositType) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getDepositType().equals(depositType)){
                return i;
            }
        }
        return -1;
    }

    private double calculateTotal(TableView<DepositTable> tblDeposit) {
        double sum=0.0;
        for (int i = 0; i < tblDeposit.getItems().size(); i++) {
            sum+=tblDeposit.getItems().get(i).getAmount();
        }
        return sum;
    }
}
