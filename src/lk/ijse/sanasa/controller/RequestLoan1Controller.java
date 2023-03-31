package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
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
import lk.ijse.sanasa.model.RequestLoanModel;
import lk.ijse.sanasa.to.AccountDetails;
import lk.ijse.sanasa.to.LoanDetails;
import lk.ijse.sanasa.to.PendingLoans;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public class RequestLoan1Controller {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccNo;

    @FXML
    private JFXComboBox<String> cmdLoanTypes;

    @FXML
    private Label lblNotifyLoanTypes;

    @FXML
    private JFXComboBox<String> cmbAmount;

    @FXML
    private Label lblNotifyAmountTypes;

    @FXML
    private JFXComboBox<String> cmbInstallmentCounts;

    @FXML
    private Label lblNotifyInstallmentCountTypes;

    @FXML
    private Label lblInterest;

    @FXML
    private Label lblInstallmentAmount;

    @FXML
    private Label lblNotifyRequestLoan;

    @FXML
    private Label lblPendingLoanID;


    public void initialize(){
        //Set pending loan Id
        loadPendingLoanID();

        //Set loan types
        loadLoans();
    }

    private void loadLoans() {
        try {
            ObservableList<String> loanTypes = RequestLoanModel.getLoanTypes();
            cmdLoanTypes.setItems(loanTypes);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void loadPendingLoanID() {
        try {
            lblPendingLoanID.setText(
                    ManageController.generateLastId(
                            "PendingLoanID",
                            "pendingloans",
                            "PL"
                    )
            );
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void cmdLoanTypesOnAction(ActionEvent event){
        try {
            if(!cmdLoanTypes.getSelectionModel().isEmpty()){
                //Clear combo boxes
                cmbInstallmentCounts.getSelectionModel().clearSelection();
                cmbAmount.getSelectionModel().clearSelection();
                //Set interest
                LoanDetails loanDetails = RequestLoanModel.getLoanDetails(cmdLoanTypes.getValue().split("[ ]")[0]);
                assert loanDetails != null;
                lblInterest.setText(loanDetails.getInterest() + "%");

                //Set loan amounts
                String[] split = loanDetails.getAmounts().split("[,]");
                ObservableList<String> amounts = FXCollections.observableArrayList();
                amounts.addAll(Arrays.asList(split));
                cmbAmount.setItems(amounts);

                //Set loan installments
                ObservableList<String> installmentsCount = FXCollections.observableArrayList();
                installmentsCount.clear();
                installmentsCount.add("6 months");
                installmentsCount.add("12 months");
                installmentsCount.add("18 months");
                installmentsCount.add("24 months");
                installmentsCount.add("48 months");
                installmentsCount.add("60 months");
                cmbInstallmentCounts.setItems(installmentsCount);

                //set Installment Amount
                setInstallmentAmount(lblInstallmentAmount,lblInterest,cmbAmount,cmbInstallmentCounts);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void setInstallmentAmount(Label lblInstallmentAmount, Label lblInterest, JFXComboBox<String> cmbAmount, JFXComboBox<String> cmbInstallmentCounts) {

        boolean isSelectedAmount = cmbAmount.getSelectionModel().getSelectedIndex() > -1;
        boolean isSelectedMonths = cmbInstallmentCounts.getSelectionModel().getSelectedIndex() > -1;
        boolean isSelectedLoanTypes = cmdLoanTypes.getSelectionModel().getSelectedIndex() > -1;
        if (isSelectedAmount && isSelectedMonths && isSelectedLoanTypes) {
            double interest = Double.parseDouble(lblInterest.getText().split("[%]")[0])/100.00;
            double amount = Double.parseDouble(cmbAmount.getValue());
            double months = Double.parseDouble(cmbInstallmentCounts.getValue().split("[ ]")[0]);
            //Calculate installment
            double installmentAmount = (amount/months)+(amount*interest/12.00);
            String format = String.format("%.2f", installmentAmount);
            lblInstallmentAmount.setText(format);
        } else {
            lblInstallmentAmount.setText("");
        }
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {
        ManageController.getInstance().setTextFields(
                txtAccNo
        );
        ManageController.getInstance().setLabels(
                lblNotifyAccNo,
                lblNotifyLoanTypes,
                lblNotifyInstallmentCountTypes,
                lblNotifyInstallmentCountTypes,
                lblNotifyRequestLoan,
                lblInterest
        );
        ManageController.getInstance().clearForm();
        cmbAmount.getSelectionModel().clearSelection();
        cmbInstallmentCounts.getSelectionModel().clearSelection();
        cmdLoanTypes.getSelectionModel().clearSelection();
    }

    @FXML
    void btnRequestLoanOnAction(ActionEvent event) {
        try {

            FormValidate.getInstance().setRegexList(
                    "^([0-9]{14})$"
            );
            FormValidate.getInstance().setTextFieldsList(
                    txtAccNo
            );
            FormValidate.getInstance().setComboboxes(
                    cmdLoanTypes,
                    cmbAmount,
                    cmbInstallmentCounts
            );
            boolean validate1 = FormValidate.getInstance().checkValidation();
            boolean validate2 = false;
            if (FormValidate.getInstance().isValidate(
                    "^([0-9]{14})$",
                    txtAccNo.getText())
            ) {
                validate2 = CreateAccountModel.getAccount(txtAccNo.getText())!=null;
            }
            if (validate1 && validate2) {
                //Set auto commit false
                DbConnection.getInstance().getConnection().setAutoCommit(false);
                PendingLoans pendingLoans = new PendingLoans(
                        lblPendingLoanID.getText(),
                        Double.parseDouble(cmbAmount.getValue()),
                        cmdLoanTypes.getValue().split("[ ]")[0],
                        txtAccNo.getText(),
                        Integer.parseInt(cmbInstallmentCounts.getValue().split("[ ]")[0]),
                        Double.parseDouble(lblInstallmentAmount.getText())
                );
                boolean isRequested = RequestLoanModel.makeRequestALoan(pendingLoans);
                if (isRequested) {
                    ButtonType yes =new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no =new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Request this loan ?", yes, no);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.orElse(no) == yes) {
                        DbConnection.getInstance().getConnection().commit();
                        FormValidate.getInstance().makeInstanceWarningConfirm(
                                lblNotifyRequestLoan,
                                "Request Success !"
                        );
                        loadPendingLoanID();
                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                    }
                    btnClearFormOnAction(event);
                } else {
                    DbConnection.getInstance().getConnection().rollback();
                    FormValidate.getInstance().makeInstanceWarningError(
                            lblNotifyRequestLoan,
                            "Request Failed !"
                    );
                }
            }else {
                System.out.println("Validate Failed ! ");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void txtAccNoOnKeyReleased(KeyEvent event) {
        try {
            lblNotifyRequestLoan.setText("");
            boolean validate = FormValidate.getInstance().isValidate(
                    "^([0-9]{14})$",
                    txtAccNo.getText()
            );
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyAccNo,
                    validate,
                    txtAccNo
            );
            if (validate) {
                if (CreateAccountModel.getAccount(txtAccNo.getText())==null) {
                    lblNotifyAccNo.setText("Account not founded !");
                    lblNotifyAccNo.setStyle("-fx-text-fill: red");
                } else {
                    lblNotifyAccNo.setText("Account founded !");
                    lblNotifyAccNo.setStyle("-fx-text-fill: green");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean isCreatedAccount(String accountNumber) throws SQLException, ClassNotFoundException {
        AccountDetails accountDetails = CreateAccountModel.getAccount(accountNumber);
        return accountDetails != null;
    }

    public void cmbInstallmentCountsOnAction(ActionEvent event) {
        //set Installment Amount
        setInstallmentAmount(lblInstallmentAmount,lblInterest,cmbAmount,cmbInstallmentCounts);
    }

    public void cmbAmountOnAction(ActionEvent event) {
        //set Installment Amount
        setInstallmentAmount(lblInstallmentAmount,lblInterest,cmbAmount,cmbInstallmentCounts);

    }
}
