package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.CreateAccountModel;
import lk.ijse.sanasa.model.DepositModel;
import lk.ijse.sanasa.model.InterAccountTransactionModel;
import lk.ijse.sanasa.to.InterAccountTransaction;
import lk.ijse.sanasa.to.Transaction;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class InterAccountTransaction1Controller {

    public JFXTextField txtAmount;
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblNotifyTransferMoney;

    @FXML
    private Label lblTransactionID;

    @FXML
    private JFXTextField txtAcc1;

    @FXML
    private Label lblNotifyAcc1;

    @FXML
    private JFXComboBox<String> cmbDepositType1;

    @FXML
    private Label lblNotifyDepositType1;

    @FXML
    private Label lblNotifyAmount;

    @FXML
    private JFXTextField txtAcc2;

    @FXML
    private Label lblNotifyAcc2;

    @FXML
    private JFXComboBox<String> cmbDepositType2;

    @FXML
    private Label lblNotifyDepositType2;

    public void initialize(){
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
    void btnClearFormOnAction(ActionEvent event) {
        ManageController.getInstance().setTextFields(
                txtAcc1,
                txtAcc2,
                txtAmount
        );
        ManageController.getInstance().setComboBoxes(
                cmbDepositType1,
                cmbDepositType2
        );
        ManageController.getInstance().setLabels(
                lblNotifyAcc1,
                lblNotifyAcc2,
                lblNotifyAmount,
                lblNotifyTransferMoney,
                lblNotifyDepositType1,
                lblNotifyDepositType2
        );
        ManageController.getInstance().clearForm();
    }

    @FXML
    void btnTransferOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        try {

            FormValidate.getInstance().setRegexList(
                    "^([0-9]{14})$",
                    "^([0-9]{14})$",
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$"
            );
            FormValidate.getInstance().setTextFieldsList(
                    txtAcc1,
                    txtAcc2,
                    txtAmount
            );
            FormValidate.getInstance().setComboboxes(
                    cmbDepositType1,
                    cmbDepositType2
            );

            boolean validation1 = false;

            if (FormValidate.getInstance().isValidate("^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$", txtAmount.getText())) {
                validation1 = isAvailableBalance(Double.parseDouble(txtAmount.getText()));
            }

            boolean validation2 = FormValidate.getInstance().checkValidation();

            boolean isFormValidate = validation1 && validation2;

            if (isFormValidate) {
                //Set auto commit false
                //Get account number
                String accountNumber1 = txtAcc1.getText();
                String accountNumber2 = txtAcc2.getText();
                //Make transaction
                String lastID = ManageController.generateLastId("TransactionID", "transaction", "T");
                Transaction transaction = new Transaction(
                        lastID,
                        accountNumber1,
                        Double.parseDouble(txtAmount.getText()),
                        LocalDate.now().toString(),
                        LocalTime.now().toString(),
                        "MONEY TRANSFER"
                );
                DbConnection.getInstance().getConnection().setAutoCommit(false);
                boolean isAddedTransaction = DepositModel.makeTransaction(transaction);
                if (isAddedTransaction) {
                    //Make inter account transaction
                    double amount = Double.parseDouble(txtAmount.getText());
                    InterAccountTransaction interAccountTransaction = new InterAccountTransaction(
                            ManageController.generateLastId(
                                    "InterAccountTransactionID",
                                    "interaccounttransaction",
                                    "A"
                            ),
                            transaction.getTransactionID(),
                            accountNumber1,
                            accountNumber2,
                            amount
                    );

                    boolean isAddedInterAccountTransaction = InterAccountTransactionModel.makeInterAccountTransaction(interAccountTransaction);
                    if (isAddedInterAccountTransaction) {

                        //Make first account withdrawal
                        double accountBalance1 = DepositModel.getAccountBalance(accountNumber1, cmbDepositType1.getValue().split("[ ]")[0]);
                        accountBalance1-=amount;
                        boolean isWithdrawSuccess = DepositModel.setAccountBalance(accountNumber1, accountBalance1, cmbDepositType1.getValue().split("[ ]")[0]);

                        if (isWithdrawSuccess) {
                            //Make deposit
                            double accountBalance2 = DepositModel.getAccountBalance(accountNumber2, cmbDepositType2.getValue().split("[ ]")[0]);
                            accountBalance2+=amount;
                            boolean isDepositSuccess = DepositModel.setAccountBalance(accountNumber2, accountBalance2, cmbDepositType2.getValue().split("[ ]")[0]);


                            if (isDepositSuccess) {
                                ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancel=new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, cancel);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.orElse(cancel) == ok) {
                                    DbConnection.getInstance().getConnection().commit();
                                    FormValidate.getInstance().makeInstanceWarningConfirm(
                                            lblNotifyTransferMoney,
                                            "Transfer Successful !"
                                    );
                                } else {
                                    DbConnection.getInstance().getConnection().rollback();
                                    FormValidate.getInstance().makeInstanceWarningError(
                                            lblNotifyTransferMoney,
                                            "Transfer Cancelled !"
                                    );
                                }
                                loadTransactionID();
                                btnClearFormOnAction(event);
                            }else{
                                DbConnection.getInstance().getConnection().rollback();
                                FormValidate.getInstance().makeInstanceWarningError(
                                        lblNotifyTransferMoney,
                                        "Transfer failed !"
                                );
                            }
                        } else {
                            DbConnection.getInstance().getConnection().rollback();
                            FormValidate.getInstance().makeInstanceWarningError(
                                    lblNotifyTransferMoney,
                                    "Transfer failed !"
                            );
                        }
                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                        FormValidate.getInstance().makeInstanceWarningError(
                                lblNotifyTransferMoney,
                                "Transfer failed !"
                        );
                    }
                }else {
                    DbConnection.getInstance().getConnection().rollback();
                    FormValidate.getInstance().makeInstanceWarningError(
                            lblNotifyTransferMoney,
                            "Transfer failed !"
                    );
                }

            } else {
                FormValidate.getInstance().makeInstanceWarningError(
                        lblNotifyTransferMoney,
                        "Fill the form correctly !"
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }

    }

    @FXML
    void txtAcc1OnKeyReleased(KeyEvent event) {
        try {
            lblNotifyTransferMoney.setText("");
            boolean validate = FormValidate.getInstance().isValidate("^([0-9]{14})$", txtAcc1.getText());
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyAcc1,
                    validate,
                    txtAcc1
            );
            if (validate) {
                loadAccTypes(txtAcc1, cmbDepositType1, lblNotifyAcc1);
            } else {
                cmbDepositType1.setItems(null);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void txtAcc2OnKeyReleased(KeyEvent event) {
        try {
            lblNotifyTransferMoney.setText("");
            boolean validate = FormValidate.getInstance().isValidate("^([0-9]{14})$", txtAcc2.getText());
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyAcc2,
                    validate,
                    txtAcc2
            );
            if (validate) {
                loadAccTypes(txtAcc2,cmbDepositType2,lblNotifyAcc2);
            } else {
                cmbDepositType2.setItems(null);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void loadAccTypes(JFXTextField txtAcc, JFXComboBox<String> cmbDepositType, Label lblNotifyAcc) throws SQLException, ClassNotFoundException {
        String accountNumber = txtAcc.getText();
        ObservableList<String> accTypes = DepositModel.getAccountTypes(accountNumber);
        if(accTypes!=null){
            cmbDepositType.setItems(accTypes);
            lblNotifyAcc.setText("Account is founded ! ");
            lblNotifyAcc.setStyle("-fx-text-fill: green");
        } else {
            lblNotifyAcc.setText("Account is not found ! ");
            lblNotifyAcc.setStyle("-fx-text-fill: red");
        }
    }

    public void txtAmountOnAction(ActionEvent event) {
        lblNotifyTransferMoney.setText("");
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
            }
        }
    }

    public void txtAmountOnKeyReleased(KeyEvent keyEvent) {
        try {
            lblNotifyTransferMoney.setText("");
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
                if(CreateAccountModel.getAccount(txtAcc1.getText())!=null && cmbDepositType1.getValue()!=null){
                    isAvailableBalance(Double.parseDouble(txtAmount.getText()));
                }else {
                    lblNotifyAmount.setText("Some details are missing !");
                    lblNotifyAmount.setStyle("-fx-text-fill: red");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean isAvailableBalance(double amount) throws SQLException, ClassNotFoundException {
            String accountNumber = txtAcc1.getText();
            double accountBalance = DepositModel.getAccountBalance(accountNumber, cmbDepositType1.getValue().split("[ ]")[0]);
            boolean b2 = amount<=accountBalance;
            if(!b2){
                lblNotifyAmount.setStyle("-fx-text-fill: red");
                lblNotifyAmount.setText("Account balance is not sufficient");
            }
            return b2;
    }
}
