package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.DepositModel;
import lk.ijse.sanasa.to.Deposit;
import lk.ijse.sanasa.to.DepositTable;
import lk.ijse.sanasa.to.Transaction;
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

public class Deposit1Controller {

    public Label lblNotifyAdd;
    public Label lblNotifyDeposit;
    public Tab tabDeposit;
    public Tab tab2;
    @FXML
    private JFXTabPane pane;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXComboBox<String> cmbDepositType;

    @FXML
    private Label lblNotifyAccType;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private Label lblNotifyAmount;

    @FXML
    private Label lblTransactionID;

    @FXML
    private TableView<DepositTable> tblDeposit;

    @FXML
    private TableColumn<?, ?> colDepositType;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private JFXButton btnDeposit;

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
    void btnClearAllOnAction(ActionEvent event) {
        txtAccNo.setEditable(true);
        txtAccNo.setAlignment(Pos.CENTER_LEFT);
        txtAccNo.setStyle(null);
        txtAccNo.clear();
        btnClearFormOnAction(event);
        tblDeposit.setItems(FXCollections.observableArrayList());
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        ManageController.getInstance().setTextFields(
                txtAmount
        );
        ManageController.getInstance().setLabels(
                lblNotifyAccNo,
                lblNotifyAccType,
                lblNotifyAmount,
                lblNotifyAdd
        );
        cmbDepositType.getSelectionModel().clearSelection();
        ManageController.getInstance().clearForm();
    }

    @FXML
    void txtAmountOnAction(ActionEvent event){
        lblNotifyAdd.setText("");
        boolean validate = FormValidate.getInstance().isValidate(
                "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                txtAmount.getText()
        );
        if (validate) {
            boolean validate1 = FormValidate.getInstance().isValidate(
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))$",
                    txtAmount.getText()
            );
            if (validate1) {
                txtAmount.setText(txtAmount.getText() + ".00");
                btnAddOnAction(event);
            }
        }

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

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
                if (isValidate) {
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
                    balance+=amount;
                    String depositType = cmbDepositType.getValue();

                    //Set visible table
                    colDepositType.setCellValueFactory(new PropertyValueFactory<>("depositType"));
                    colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
                    colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

                    //Add or update rows
                    ObservableList<DepositTable> items = tblDeposit.getItems();
                        int index = isNewRow(items, depositType);
                    if (index==-1) {
                        items.add(new DepositTable(depositType, amount, balance));
                    } else {
                        items.get(index).setAmount(items.get(index).getAmount() + amount);
                        items.get(index).setBalance(items.get(index).getBalance() + amount);
                    }
                    tblDeposit.setItems(items);
                    tblDeposit.refresh();
                    btnClearFormOnAction(event);
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

    private int isNewRow(ObservableList<DepositTable> items, String depositType) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getDepositType().equals(depositType)){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnDepositOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            //Get account number
            String accountNumber = txtAccNo.getText();

            //Make transaction
            String lastID = ManageController.generateLastId("TransactionID", "transaction", "T");
            Transaction transaction = new Transaction(
                    lastID,
                    accountNumber,
                    calculateTotal(tblDeposit),
                    LocalDate.now().toString(),
                    LocalTime.now().toString(),
                    "MONEY IN"
            );
            boolean isAddedTransaction = DepositModel.makeTransaction(transaction);

            if (isAddedTransaction) {

                    for (int i = 0; i < tblDeposit.getItems().size(); i++) {
                        //make deposit
                        Deposit deposit = new Deposit(
                                ManageController.generateLastId(
                                        "DepositID",
                                        "deposit",
                                        "D"
                                ),
                                transaction.getTransactionID(),
                                DepositModel.getDepositAccountID(accountNumber,tblDeposit.getItems().get(i).getDepositType().split("[ ]")[0]),
                                tblDeposit.getItems().get(i).getAmount()
                        );
                        boolean isAddedDeposit = DepositModel.makeDeposit(deposit);
                        if (isAddedDeposit) {
                            //Set account balance
                            boolean b = DepositModel.setAccountBalance(
                                    accountNumber,
                                    tblDeposit.getItems().get(i).getBalance(),
                                    tblDeposit.getItems().get(i).getDepositType().split("[ ]")[0]
                            );
                            if (b) {
                                if (i == tblDeposit.getItems().size() - 1) {

                                    ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                    ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                                    if(result.orElse(no)==ok){
                                        DbConnection.getInstance().getConnection().commit();
                                        new Alert(Alert.AlertType.CONFIRMATION,"Deposit Success !").show();
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
                                    btnClearAllOnAction(event);
                                }
                            } else {
                                DbConnection.getInstance().getConnection().rollback();
                                FormValidate.getInstance().makeInstanceWarningError(
                                        lblNotifyDeposit,
                                        "Deposit Failed !"
                                );
                                return;
                            }
                        }else {
                            DbConnection.getInstance().getConnection().rollback();
                            FormValidate.getInstance().makeInstanceWarningError(
                                    lblNotifyDeposit,
                                    "Deposit Failed !"
                            );
                            return;
                        }
                    }
                }else {
                DbConnection.getInstance().getConnection().rollback();
                FormValidate.getInstance().makeInstanceWarningError(
                        lblNotifyDeposit,
                        "Deposit Failed !"
                );
            }
            } catch (SQLException | ClassNotFoundException e) {
            DbConnection.getInstance().getConnection().rollback();
            System.out.println(e);
        } finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }

    }

    private void showReport(){

        try {
            JasperDesign jasperDesign=JRXmlLoader.load("D:\\Working_Directory\\Sanasa\\Final_Project_IDSE_1st_Semester\\src\\lk\\ijse\\sanasa\\report\\DepositReport.jrxml");
            String query="SELECT sanasa.transaction.`TransactionID`, sanasa.deposit.`DepositID`, sanasa.depositdetails.`Description`, sanasa.deposit.`Amount`, sanasa.depositaccount.`Balance`, sanasa.transaction.`AccountID` FROM sanasa.deposit INNER JOIN sanasa.transaction ON sanasa.deposit.`TransactionID` = sanasa.transaction.`TransactionID` INNER JOIN sanasa.depositaccount ON sanasa.deposit.`DepositTypeAccountID` = sanasa.depositaccount.`DepositTypeAccountID` INNER JOIN sanasa.depositdetails ON sanasa.depositaccount.`DepositTypeID` = sanasa.depositdetails.`DepositTypeID` ORDER BY sanasa.deposit.`DepositID` DESC limit 1";

            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(query);

            jasperDesign.setQuery(updateQuery);

            JasperReport jasperReport= null;
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,null,DbConnection.getInstance().getConnection());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private double calculateTotal(TableView<DepositTable> tblDeposit) {
        double sum=0.0;
        for (int i = 0; i < tblDeposit.getItems().size(); i++) {
            sum+=tblDeposit.getItems().get(i).getAmount();
        }
        return sum;
    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {

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

    public void txtAmountOnKeyReleased(KeyEvent keyEvent) {
        lblNotifyAdd.setText("");
        FormValidate.getInstance().instanceWarningLabel(
                lblNotifyAmount,
                FormValidate.getInstance().isValidate(
                        "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                        txtAmount.getText()
                ),
                txtAmount
        );
    }

    public void btnRemoveOnAction(ActionEvent event) {
        if(!tblDeposit.getSelectionModel().isEmpty()){
            tblDeposit.getItems().remove(tblDeposit.getSelectionModel().getFocusedIndex());
        }else {
            new Alert(Alert.AlertType.ERROR,"Select a row").show();
        }
    }

    public void btnPrintlOnAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign jasperDesign= JRXmlLoader.load("D:\\Working_Directory\\Sanasa\\Final_Project_IDSE_1st_Semester\\src\\lk\\ijse\\sanasa\\reports\\Blank_A4_2.jasper");
        String query= "SELECT sanasa.transaction.TransactionID AS 'Transaction ID', sanasa.deposit.DepositID AS 'Deposit ID', sanasa.depositdetails.Description AS 'Deposit Name', sanasa.deposit.Amount, sanasa.transaction.Date, sanasa.transaction.Time FROM sanasa.deposit INNER JOIN sanasa.transaction ON sanasa.deposit.TransactionID = sanasa.transaction.`TransactionID` INNER JOIN sanasa.depositaccount ON sanasa.deposit.DepositTypeAccountID = sanasa.depositaccount.DepositTypeAccountID INNER JOIN sanasa.depositdetails ON sanasa.depositaccount.`DepositTypeID` = sanasa.depositdetails.DepositTypeID where DepositID='D00000006'";

        JRDesignQuery jrDesignQuery=new JRDesignQuery();
        jrDesignQuery.setText(query);

        jasperDesign.setQuery(jrDesignQuery);

        JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint);

    }
}
