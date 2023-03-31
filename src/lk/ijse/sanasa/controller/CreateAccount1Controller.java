package lk.ijse.sanasa.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.CreateAccountModel;
import lk.ijse.sanasa.model.DepositModel;
import lk.ijse.sanasa.model.LoanInstallmentModel;
import lk.ijse.sanasa.model.WithdrawalModel;
import lk.ijse.sanasa.to.*;
import lk.ijse.sanasa.to.table.TableDetailsCloseAccBalances;
import lk.ijse.sanasa.to.table.TableDetailsCloseLoanBalances;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class CreateAccount1Controller {

    public JFXTabPane pane;
    public JFXDatePicker datePickerDob;
    public Label lblNotifyDob;
    public JFXRadioButton radioButtonOther;
    public Label lblNotifyGender;
    public JFXTextField txtAccNo;
    public Label lblNotifyAccType;
    public JFXTextField txtTime;
    public Label lblNotifyCreateAccount;
    public JFXTextField txtDob;
    public HBox hBoxGender;
    public JFXTextField txtAccIDSearch;
    public JFXComboBox cmbSerach;
    public Tab tabConfirm;
    public TableView<AccountDetails> tblAccounts;
    public TableColumn colID;
    public TableColumn colNic;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDOB;
    public TableColumn colEmail;
    public TableColumn colGender;
    public TableColumn colRegDate;
    public TableColumn colRegTime;
    public Tab tabViewAccount;
    public Tab tabCloseAccount;
    public AnchorPane paneSearchUpdate;
    public JFXTextField txtFindAccNo;
    public JFXButton btnFindAccNo;
    public Label lblNotifyFindAccNo;
    public JFXTextField txtAccNoo;
    public AnchorPane paneSearchUpdate1;
    public JFXTextField txtAccNumb;
    public Label lblAccNumberr;
    public Label lblNicc;
    public Label lblNamee;
    public Label lblAddresss;
    public Label lblContactt;
    public Label lblDobb;
    public Label lblEmaill;
    public Label lblGenderr;
    public Label lblRegDatee;
    public Label lblRegTimee;
    public TableView<TableDetailsCloseAccBalances> tblAccBalancee;
    public TableView<TableDetailsCloseLoanBalances> tblLoansAvailables;
    public TableColumn colDeposittType;
    public TableColumn colBalancee;
    public TableColumn colActionn1;
    public TableColumn colLoanTypee;
    public TableColumn colLoanBalancee;
    public TableColumn colActionn2;
    public JFXButton btnCloseeAccount;
    public Tab tabViewAccount1;
    public TableView<DepositAccount> tblDepositAccount;
    public TableColumn colDepositTypeAccountID;
    public TableColumn colDepositTypeID;
    public TableColumn colAccountID;
    public TableColumn colCreatedDate;
    public TableColumn colBalance;
    public Label lblAccountID;
    public Label lblNotifyAccNo;
    public JFXTextField txtDescription;
    public JFXTextField txtInterest;
    public JFXComboBox<DepositDetails> cmbDepositType;
    public Label lblDepositTypeName;
    @FXML
    private JFXTextField txtNic;

    @FXML
    private TextField txtSelectedID;

    @FXML
    private Label lblNotifyNic;

    @FXML
    private JFXTextField txtName;

    @FXML
    private Label lblNotifyName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private Label lblNotifyAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private Label lblNotifyContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private Label lblNotifyEmail;

    @FXML
    private JFXRadioButton radioButtonMale;

    @FXML
    private ToggleGroup toggleGroupGender;

    @FXML
    private JFXRadioButton radioButtonFemale;

    @FXML
    private JFXComboBox<?> cmbAccTypes;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtFirstDeposit;

    @FXML
    private Label lblNotifyFirstDeposit;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblNotifySearchUpdate;

    @FXML
    private JFXButton btnAddDepositType;

    public void initialize(){
        loadAccountTypes();
        btnUpdate.setDisable(true);

        //View Accounts Tab
        loanAccounts();

        //Account Deposit Balances
        loadCmdDepositTypes();
        setDepositAccounts();
    }

    private void loadCmdDepositTypes() {
        try {
            ObservableList<DepositDetails> depositTypeDetails = CreateAccountModel.getDepositTypeDetails();
            cmbDepositType.setItems(depositTypeDetails);
            Callback<ListView<DepositDetails>, ListCell<DepositDetails>> callback = new Callback<ListView<DepositDetails>, ListCell<DepositDetails>>() {
                @Override
                public ListCell<DepositDetails> call(ListView<DepositDetails> param) {
                    return new ListCell<DepositDetails>() {
                        @Override
                        protected void updateItem(DepositDetails item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setGraphic(null);
                            } else {
                                setText(item.getDepositTypeID()+ " "+ item.getDescription());
                            }
                        }
                    };
                }
            };
            if(cmbDepositType.getSelectionModel().isEmpty()){
                cmbDepositType.setPromptText(null);
            }
            cmbDepositType.setButtonCell(callback.call(null));
            cmbDepositType.setCellFactory(callback);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cmbDepositTypeOnAction(){
        if (!cmbDepositType.getSelectionModel().isEmpty()) {
            txtDescription.setText(cmbDepositType.getValue().getDescription());
            txtInterest.setText(String.valueOf(cmbDepositType.getValue().getInterest()));
        }
    }

    private void setDepositAccounts() {
        try {
            setCellValueFactoryDepositAccount();
            ObservableList<DepositAccount> list = CreateAccountModel.getDepositAccountsAll();
            tblDepositAccount.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblAccountsOnClicks(){
        if (!tblAccounts.getSelectionModel().isEmpty()) {
            txtSelectedID.setText(tblAccounts.getSelectionModel().getSelectedItem().getAccountID());
        } else {
            txtSelectedID.clear();
        }
    }

    @FXML
    void btnCopyOnAction(){
        if (!txtSelectedID.getText().isEmpty()) {
            StringSelection stringSelection = new StringSelection(txtSelectedID.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            new Alert(Alert.AlertType.CONFIRMATION, "Copied !").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Select Id not found !").show();
        }
    }

    @FXML
    private void onPasteOnAction(ActionEvent event){
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null)
            return;
        try {
            txtSearch.setText((String) t.getTransferData(DataFlavor.stringFlavor));
            txtSearchOnKeyReleased();
        } catch (Exception e){
            e.printStackTrace();
        }//try
    }//onPaste

    private void loanAccounts() {
        try {
            ObservableList<AccountDetails> acc = CreateAccountModel.getAccounts();
            tblAccounts.setItems(acc);

            colID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
            colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
            colRegTime.setCellValueFactory(new PropertyValueFactory<>("regTime"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAccountTypes() {
        try {
            ObservableList types = CreateAccountModel.getAccountTypes();
            cmbAccTypes.setItems(types);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnClearFormOnAction() {
        cmbAccTypes.getSelectionModel().clearSelection();
        ManageController.getInstance().setTextFields(
                txtNic,
                txtName,
                txtAddress,
                txtContact,
                txtEmail,
                txtDob,
                txtFirstDeposit,
                txtAccNoo,
                txtDate,
                txtTime
        );

        ManageController.getInstance().setLabels(
                lblNotifyNic,
                lblNotifyName,
                lblNotifyAddress,
                lblNotifyContact,
                lblNotifyEmail,
                lblNotifyDob,
                lblNotifyAccType,
                lblNotifyFirstDeposit,
                lblNotifyCreateAccount
        );

        ManageController.getInstance().clearForm();
    }

    @FXML
    void btnCreateAccountOnAction() throws SQLException, ClassNotFoundException {

        try {
                FormValidate.getInstance().setComboboxes(
                        cmbAccTypes
                );
                FormValidate.getInstance().setTextFieldsList(
                        txtNic,
                        txtName,
                        txtAddress,
                        txtContact,
                        txtEmail,
                        txtFirstDeposit
                );

                FormValidate.getInstance().setRegexList(
                        "^([0-9]{9}[x|X|v|V]|[0-9]{12})$",
                        "^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$",
                        "[A-Za-z0-9'\\.\\-\\s\\,]{2,}",
                        "^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
                        "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$",
                        "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$"
                );

            if (FormValidate.getInstance().checkValidation() && datePickerDob.getValue() != null) {

                AccountDetails accountDetails = new AccountDetails(
                        CreateAccountModel.generateAccountNumber(txtNic.getText()),
                        txtNic.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        txtContact.getText(),
                        txtDob.getText(),
                        txtEmail.getText(),
                        toggleGroupGender.getSelectedToggle() == radioButtonMale ? "MALE" : toggleGroupGender.getSelectedToggle() == radioButtonFemale ? "FEMALE" : "OTHER",
                        LocalDate.now().toString(),
                        LocalTime.now().toString(),
                        "ACTIVE"
                );
                //Create a new account
                DbConnection.getInstance().getConnection().setAutoCommit(false);
                if (CreateAccountModel.createAccount(accountDetails)) {
                    txtAccNoo.setText(accountDetails.getAccountID());
                    txtDate.setText(accountDetails.getRegDate());
                    txtTime.setText(accountDetails.getRegTime());

                    //Create a deposit account
                    String depositAccountID = ManageController.generateLastId("DepositTypeAccountID", "depositaccount", "DT");
                    if (CreateAccountModel.createDepositAccount(depositAccountID, cmbAccTypes.getValue().toString().split("[ ]")[0], accountDetails, txtFirstDeposit.getText())) {

                        //Make first deposit to the account
                        if (CreateAccountModel.makeFirstDeposit(depositAccountID, accountDetails.getAccountID(), txtFirstDeposit.getText())) {
                            DbConnection.getInstance().getConnection().commit();
                            FormValidate.getInstance().makeInstanceWarningConfirm(lblNotifyCreateAccount, "Account created!");
                            return;
                        } else {
                            DbConnection.getInstance().getConnection().rollback();
                            FormValidate.getInstance().makeInstanceWarningError(lblNotifyCreateAccount, "Account not created !");
                        }
                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                        FormValidate.getInstance().makeInstanceWarningError(lblNotifyCreateAccount, "Account not created !");
                    }
                } else {
                    FormValidate.getInstance().makeInstanceWarningError(lblNotifyCreateAccount, "Account not created !");
                    DbConnection.getInstance().getConnection().rollback();
                }
            } else {
                FormValidate.getInstance().makeInstanceWarning(lblNotifyCreateAccount, "Fill the form correctly!");
            }
        }catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
                if(e.toString().startsWith("java.sql.SQLIntegrityConstraintViolationException: Duplicate entry ")){
                    FormValidate.getInstance().makeInstanceWarning(lblNotifyCreateAccount,"Account Already Created!");
                }

            }finally {
                DbConnection.getInstance().getConnection().setAutoCommit(true);
            }
    }

    @FXML
    void txtAddressOnKeyReleased() {
        FormValidate.getInstance().instanceWarningLabel(
                lblNotifyAddress,
                FormValidate.getInstance().isValidate(
                        "[A-Za-z0-9'\\.\\-\\s\\,]{2,}",
                        txtAddress.getText()
                ),
                txtAddress
        );
    }

    @FXML
    void txtContactOnKeyReleased() {
        FormValidate.getInstance().instanceWarningLabel(
                lblNotifyContact,
                FormValidate.getInstance().isValidate(
                        "^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$",
                        txtContact.getText()
                ),
                txtContact
        );
    }

    @FXML
    void txtEmailOnKeyReleased() {
        FormValidate.getInstance().instanceWarningLabel(
                lblNotifyEmail,
                FormValidate.getInstance().isValidate(
                        "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$",
                        txtEmail.getText()
                ),
                txtEmail
        );
    }

    @FXML
    void txtFirstDepositOnKeyReleased() {
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyFirstDeposit,
                    FormValidate.getInstance().isValidate(
                            "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                            txtFirstDeposit.getText()
                    ),
                    txtFirstDeposit
            );
    }

    @FXML
    void txtNameOnKeyReleased() {
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyName,
                    FormValidate.getInstance().isValidate(
                            "^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$",
                            txtName.getText()
                    ),
                    txtName
            );
    }

    @FXML
    void txtNicOnKeyReleased() {
        FormValidate.getInstance().instanceWarningLabel(
                lblNotifyNic,
                FormValidate.getInstance().isValidate(
                        "^([0-9]{9}[x|X|v|V]|[0-9]{12})$",
                        txtNic.getText()
                ),
                txtNic
        );
    }

    public void txtFirstDepositOnAction() {
        boolean validate = FormValidate.getInstance().isValidate(
                "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$",
                txtFirstDeposit.getText()
        );
        if(validate){
            boolean validate1 = FormValidate.getInstance().isValidate(
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))$",
                    txtFirstDeposit.getText()
            );
            if(validate1){
                txtFirstDeposit.setText(txtFirstDeposit.getText()+".00");
            }
        }
    }

    public void datePickerOnAction() {
        txtDob.setText(datePickerDob.getValue().toString());
        lblNotifyDob.setText("Validate success....");
        lblNotifyDob.setTextFill(Paint.valueOf("GREEN"));
    }

    public void cmbAccTypesOnAction() {
        lblNotifyAccType.setText("Validate success....");
        lblNotifyAccType.setTextFill(Paint.valueOf("GREEN"));
    }

    public void radioButtonGenderOnAction() {
        lblNotifyGender.setText("Validate success....");
        lblNotifyGender.setTextFill(Paint.valueOf("GREEN"));
    }

    public void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            AccountDetails accountDetails = new AccountDetails(
                    txtAccNo.getText(),
                    txtNic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtDob.getText(),
                    txtEmail.getText(),
                    toggleGroupGender.getSelectedToggle() == radioButtonMale ? "MALE" : toggleGroupGender.getSelectedToggle() == radioButtonFemale ? "FEMALE" : "OTHER",
                    txtDate.getText(),
                    txtTime.getText(),
                    "ACTIVE"
            );
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isUpdated = CreateAccountModel.updateAccountInfo(accountDetails);
            if (isUpdated) {
                ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(cancel) == ok) {
                    DbConnection.getInstance().getConnection().commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Update Success !").show();
                    btnClearFormOnAction();
                    txtFirstDeposit.setDisable(false);
                    cmbAccTypes.setDisable(false);
                } else {
                    btnClearFormOnAction();
                    DbConnection.getInstance().getConnection().rollback();
                    new Alert(Alert.AlertType.ERROR, "Update canceled !").show();
                }
            } else {
                DbConnection.getInstance().getConnection().rollback();
                new Alert(Alert.AlertType.ERROR, "Update failed !").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void txtSearchOnKeyReleased() {

        try {
            btnClearFormOnAction();
            btnUpdate.setDisable(true);
            lblNotifySearchUpdate.setText("");
            lblNotifySearchUpdate.setStyle("-fx-text-fill: null");
            boolean validate = FormValidate.getInstance().isValidate("^([0-9]{14})$", txtSearch.getText());
            if(txtSearch.getText().equals("")){
                lblNotifySearchUpdate.setText("");
                lblNotifySearchUpdate.setStyle("-fx-text-fill: null");
                return;
            }
            if (validate) {
                AccountDetails account = CreateAccountModel.getAccount(txtSearch.getText());
                if (account!=null){
                    cmbAccTypes.setDisable(true);
                    txtFirstDeposit.setDisable(true);
                    btnUpdate.setDisable(false);
                    lblNotifySearchUpdate.setText("Account is founded !");
                    lblNotifySearchUpdate.setStyle("-fx-text-fill: green");
                    txtAccNoo.setText(txtSearch.getText());
                    txtNic.setText(account.getNic());
                    txtName.setText(account.getName());
                    txtAddress.setText(account.getAddress());
                    txtContact.setText(account.getContact());
                    txtEmail.setText(account.getEmail());
                    txtDob.setText(account.getDateOfBirth());
                    txtTime.setText(account.getRegTime());
                    txtAccNo.setText(account.getAccountID());
                    txtDate.setText(account.getRegDate());
                    if(account.getGender().equals("Male")){
                        radioButtonMale.setSelected(true);
                    } else if (account.getGender().equals("Female")) {
                        radioButtonFemale.setSelected(true);
                    } else {
                        radioButtonOther.setSelected(true);
                    }

                }else {
                    cmbAccTypes.setDisable(false);
                    txtFirstDeposit.setDisable(false);
                    lblNotifySearchUpdate.setText("Account is not founded !");
                    lblNotifySearchUpdate.setStyle("-fx-text-fill: red");
                    btnUpdate.setDisable(true);
                }
            } else {
                cmbAccTypes.setDisable(false);
                txtFirstDeposit.setDisable(false);
                btnUpdate.setDisable(true);
                lblNotifySearchUpdate.setText("Validate failed !");
                lblNotifySearchUpdate.setStyle("-fx-text-fill: red");
            }
            } catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();
            }
    }

    public void txtAccIDSearchOnAction(ActionEvent event) {
    }

    public void txtAccIDOnKeyReleased(KeyEvent keyEvent) {
    }

    public void btnFindOnAction(ActionEvent event) {
    }

    public void cmbSerachOnAction(ActionEvent event) {
    }

    public void btnRefreshOnAction(ActionEvent event) {
    }

    public void btnFindAccNoOnAction(ActionEvent event) {
        try {
            System.out.println("X");
            if (btnFindAccNo.getText().equals("Find account No")) {
                    String accountNumberFromNic = DepositModel.getAccountNumberFromNic(txtFindAccNo.getText());
                    if (accountNumberFromNic != null) {
                        txtFindAccNo.setText(accountNumberFromNic);
                        btnFindAccNo.setText("Founded !");
                        txtFindAccNo.requestFocus();
                        new Alert(Alert.AlertType.CONFIRMATION, "Account is found !").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Account is not found !").show();
                    }

            } else {
                btnFindAccNo.setText("Find account No");
                txtFindAccNo.clear();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void txtAccNoOnAction(){
        try {
            if(txtAccNo.getText().isEmpty()){
                setDepositAccounts();
                return;
            }
            FormValidate.getInstance().setRegexList("^([0-9]{14})$");
            FormValidate.getInstance().setTextFieldsList(txtAccNo);
            boolean isValidate = FormValidate.getInstance().checkValidation();
            if (isValidate) {
                AccountDetails account = CreateAccountModel.getAccount(txtAccNo.getText());
                if (account == null) {
                    new Alert(Alert.AlertType.ERROR, "Account not found !").show();
                } else {
                    setDepositAccountsforID(account.getAccountID());
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Check account number again !").show();
                setDepositAccounts();
            }
            } catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();
            }
    }

    private void setDepositAccountsforID(String accountID) {
        try {
            ObservableList<DepositAccount> ob=CreateAccountModel.getDepositAccountForID(accountID);
            tblDepositAccount.setItems(ob);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtAccNoOnKeyReleased() {
        /*try {
            String accNumber = txtAccNo.getText();
            AccountDetails account = CreateAccountModel.getAccount(accNumber);
            if (account != null) {
                setCellValueFactoryDepositAccount();
                ObservableList<DepositAccount> depositAccounts = CreateAccountModel.getDepositAccountsForccount();
                tblDepositAccount.setItems(depositAccounts);
                tblDepositAccount.refresh();
            }
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }*/
    }

    private void setCellValueFactoryDepositAccount() {
        colDepositTypeAccountID.setCellValueFactory(new PropertyValueFactory<>("depositTypeAccountID"));
        colDepositTypeID.setCellValueFactory(new PropertyValueFactory<>("depositTypeID"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    public void btnUpdateDepositTypeOnAction(ActionEvent event) {

        try {
            if (!cmbDepositType.getSelectionModel().isEmpty()) {
                DepositDetails depositDetails = new DepositDetails(cmbDepositType.getValue().getDepositTypeID(), cmbDepositType.getValue().getDescription(), Double.parseDouble(txtInterest.getText()));
                DbConnection.getInstance().getConnection().setAutoCommit(false);
                boolean isupdated = CreateAccountModel.updateDepositType(depositDetails);
                if (isupdated) {
                    ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        DbConnection.getInstance().getConnection().commit();
                        new Alert(Alert.AlertType.CONFIRMATION,"Updated !").show();
                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                        new Alert(Alert.AlertType.WARNING,"Canceled !").show();
                    }
                    setDepositAccounts();
                } else {
                    DbConnection.getInstance().getConnection().rollback();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnViewDepositTypeOnAction(ActionEvent event) {
        try {
            Stage stage=new Stage();
            stage.setTitle("View Deposit Types");
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/sanasa/view/ViewDepositTypesForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCloseAccounttOnAction(ActionEvent event) {

        try {
            ObservableList<TableDetailsCloseAccBalances> items = tblAccBalancee.getItems();
            ObservableList<TableDetailsCloseLoanBalances> loans = tblLoansAvailables.getItems();
            for (int i = 0; i < loans.size(); i++) {
                if (!loans.get(i).getButton().disableProperty().getValue()) {
                    return;
                }
                if (i == loans.size() - 1) {
                    ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to close your account ?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        boolean isUpdated = CreateAccountModel.closeAccount(txtAccNumb.getText());
                        new Alert(Alert.AlertType.CONFIRMATION,"Account closed !").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR,"canceled !").show();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtAccNumbOnKeyReleased() {
        try {
            FormValidate.getInstance().setTextFieldsList(txtAccNumb);
            FormValidate.getInstance().setRegexList("^([0-9]{14})$");
            boolean validation = FormValidate.getInstance().checkValidation();
            String accNo = txtAccNumb.getText();
            AccountDetails account = CreateAccountModel.getAccount(accNo);
            if (validation && account != null) {
                //set account details tro the labels
                lblAccNumberr.setText(account.getAccountID());
                lblNicc.setText(account.getNic());
                lblNamee.setText(account.getName());
                lblAddresss.setText(account.getAddress());
                lblContactt.setText(account.getContact());
                lblDobb.setText(account.getDateOfBirth());
                lblEmaill.setText(account.getEmail());
                lblGenderr.setText(account.getGender());
                lblRegDatee.setText(account.getRegDate());
                lblRegTimee.setText(account.getRegTime());
                //

                //setCellValueFactory

                // Table tblAccBalancee TableDetailsCloseAccBalances
                colDeposittType.setCellValueFactory(new PropertyValueFactory<>("depositTypeID"));
                colBalancee.setCellValueFactory(new PropertyValueFactory<>("balance"));
                colActionn1.setCellValueFactory(new PropertyValueFactory<>("button"));

                // Table tblLoansAvailables TableDetailsCloseLoanBalances
                colLoanTypee.setCellValueFactory(new PropertyValueFactory<>("loanType"));
                colLoanBalancee.setCellValueFactory(new PropertyValueFactory<>("balance"));
                colActionn2.setCellValueFactory(new PropertyValueFactory<>("button"));
                //

                //get data into tblLoansAvailables
                ObservableList<Loans> loans = CreateAccountModel.getLoans(accNo);
                ObservableList<TableDetailsCloseLoanBalances> list = FXCollections.observableArrayList();
                for (Loans l : loans) {
                    JFXButton fix = new JFXButton("Fix");
                    fix.setOnAction(event -> {
                        if (!tblLoansAvailables.getSelectionModel().isEmpty()) {
                            try {
                                settleSelectedLoan(loans.get(tblLoansAvailables.getSelectionModel().getSelectedIndex()));
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    if (l.getRemainingLoanAmount() == 0.0) {
                        fix.setDisable(true);
                    } else {
                        fix.setDisable(false);
                    }
                    list.add(
                            new TableDetailsCloseLoanBalances(
                                    l.getLoanTypeID(),
                                    l.getRemainingLoanAmount(),
                                    fix
                            )
                    );
                }
                tblLoansAvailables.setItems(list);
                //

                //get data into tblAccBalancee
                ObservableList<DepositAccount> list1 = CreateAccountModel.getDepositAccounts(accNo);
                ObservableList<TableDetailsCloseAccBalances> list2 = FXCollections.observableArrayList();
                for (DepositAccount d : list1) {
                    JFXButton closetype = new JFXButton("Close");
                    closetype.setOnAction(event -> {
                        if (!tblAccBalancee.getSelectionModel().isEmpty()) {
                            try {
                                TableDetailsCloseAccBalances selectedItem = tblAccBalancee.getSelectionModel().getSelectedItem();
                                makeDepositTypeWithdrawal(accNo, selectedItem, list1.get(tblAccBalancee.getSelectionModel().getSelectedIndex()).getDepositTypeAccountID());
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    if (d.getBalance() == 0.0) {
                        closetype.setDisable(true);
                    } else {
                        closetype.setDisable(false);
                    }
                    list2.add(
                            new TableDetailsCloseAccBalances(
                                    d.getDepositTypeID(),
                                    d.getBalance(),
                                    closetype
                            )
                    );
                }
                tblAccBalancee.setItems(list2);
                //


            } else {

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void makeDepositTypeWithdrawal(String accNo, TableDetailsCloseAccBalances selectedItem, String depositTypeAccountID) throws SQLException, ClassNotFoundException {
        try {
            double balance = selectedItem.getBalance();
            //transaction, withdrawal, depositaccount
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            //Make transaction
            String transasctionID = ManageController.generateLastId("TransactionID", "transaction", "T");
            boolean isTransactionSuccess = DepositModel.makeTransaction(
                    new Transaction(
                            transasctionID,
                            accNo,
                            balance,
                            LocalDate.now().toString(),
                            LocalTime.now().toString(),
                            "MONEY OUT"
                    )
            );
            if (isTransactionSuccess) {
                //Make withdrawal
                boolean isWithdraw = WithdrawalModel.makeWithdraw(
                        new Withdrawal(
                                ManageController.generateLastId("WithdrawalID", "withdrawal", "W"),
                                transasctionID,
                                depositTypeAccountID,
                                balance
                        )
                );
                if (isWithdraw) {
                    //Update balance to 0.0
                    boolean isUpdated = DepositModel.setAccountBalance(accNo, 0.0, selectedItem.getDepositTypeID());
                    if (isUpdated) {
                        ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                        if(result.orElse(no)==ok){
                            DbConnection.getInstance().getConnection().commit();
                            new Alert(Alert.AlertType.CONFIRMATION,"Account balance has been withdrawn !").show();
                            txtAccNumbOnKeyReleased();
                        }else {
                            DbConnection.getInstance().getConnection().rollback();
                            new Alert(Alert.AlertType.ERROR,"Withdrawal canceled !").show();
                        }

                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                        System.out.println("1");
                    }
                } else {
                    DbConnection.getInstance().getConnection().rollback();
                }
            } else {
                DbConnection.getInstance().getConnection().rollback();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }


    }

    private void settleSelectedLoan(Loans selectedItem) throws SQLException, ClassNotFoundException {
        try {
            double paidAmount = selectedItem.getRemainingLoanAmount()+selectedItem.getInterestAmount();

            //Transaction,LoanInstallment,loans
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            //Make transaction
            String transactionID = ManageController.generateLastId("TransactionID", "transaction", "T");
            boolean isTransactionSuccess = DepositModel.makeTransaction(
                    new Transaction(
                            transactionID,
                            selectedItem.getAccountID(),
                            paidAmount,
                            LocalDate.now().toString(),
                            LocalTime.now().toString(),
                            "MONEY IN"
                    )
            );
            if (isTransactionSuccess) {
                //Make Loan Installment
                boolean isLoanInstallmentSuccess = LoanInstallmentModel.makeLoanInstallment(
                        new LoanInstallment(
                                ManageController.generateLastId(
                                        "LoanInstallmentID",
                                        "loaninstallment",
                                        "LI"
                                ),
                                transactionID,
                                selectedItem.getLoanID()
                        )
                );
                if (isLoanInstallmentSuccess) {
                    //Update Loan balance
                    boolean isUpdated = LoanInstallmentModel.setLoanBalance(selectedItem.getLoanID(), 0.00);
                    if (isUpdated) {
                        ButtonType ok=new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ok, no).showAndWait();
                        if(result.orElse(no)==ok){
                            DbConnection.getInstance().getConnection().commit();
                            new Alert(Alert.AlertType.CONFIRMATION,"Loan has been settled !").show();
                            txtAccNumbOnKeyReleased();
                        }else {
                            DbConnection.getInstance().getConnection().rollback();
                        }

                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                    }
                } else {
                    DbConnection.getInstance().getConnection().rollback();
                }
            } else {
                DbConnection.getInstance().getConnection().rollback();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

}
