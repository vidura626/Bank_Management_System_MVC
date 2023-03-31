package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.CreateAccountModel;
import lk.ijse.sanasa.model.DepositModel;
import lk.ijse.sanasa.model.LoanInstallmentModel;
import lk.ijse.sanasa.to.*;
import lk.ijse.sanasa.to.table.TableDetailsLoanInstallmentTbl01;
import lk.ijse.sanasa.to.table.TableDetailsLoanInstallmentTbl02;
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
import java.time.Month;
import java.time.Period;
import java.util.Optional;

public class LoanInstallment1Controller {

    public Label lblInstallmentAmount;
    public Label lblLoanBalance;
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtAccNo;

    @FXML
    private Label lblNotifyAccountNo;

    @FXML
    private JFXComboBox<String> cmbLoanTypes;

    @FXML
    private Label lblNotifyLoanTypes;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private Label lblNotifyAmount;

    @FXML
    private Label lblNotifyAdd;

    @FXML
    private Label lblNotifyInstall;

    @FXML
    private Label lblTransactionID;

    @FXML
    private TableView<TableDetailsLoanInstallmentTbl02> tblLoanInstallment;

    @FXML
    private TableColumn<?, ?> colLoan;

    @FXML
    private TableColumn<?, ?> colInstallmentAmount;

    @FXML
    private Label lblInstallmentDate;

    @FXML
    private TableView<TableDetailsLoanInstallmentTbl01> tblViewLoanInstallment;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    public void initialize(){
        loanTransactionID();

        //Set loan interest
        setLoanInfo();
    }

    private void setLoanInfo() {
        try {
            ObservableList<Loans> list = LoanInstallmentModel.getLoanDetails();
            ObservableList<LoanDetails> loanDetails = LoanInstallmentModel.getLoanTypes();
            String loanTypeID;
            double interest;
            double installmentAmount;
            double amount;
            int installments;
            LocalDate date;
            for (int j = 0; j < list.size(); j++) {
                for (int i = 0; i < loanDetails.size(); i++) {
                    if(list.get(j).getLoanTypeID().equals(loanDetails.get(i).getLoanTypeID())){
                        loanTypeID=loanDetails.get(i).getLoanTypeID();
                        installmentAmount=list.get(j).getInstallmentAmount();
                        installments=list.get(j).getInstallment();
                        amount=list.get(j).getAmount();
                        String loanID = list.get(j).getLoanID();
                        date= LocalDate.parse(list.get(j).getIssuedDate());

                        //Get last installment date for the related loan
                        LocalDate lastInstallmentDate = LoanInstallmentModel.getLastInstallmentDate(loanID);
                        if(lastInstallmentDate==null){
                            lastInstallmentDate=date;
                        }

                        //Calculate interest for a month for the related loan
                        double interestAmountForMonth = installmentAmount-(amount/(double)installments);

                        //Calculate loan balance
                        Period between = Period.between(lastInstallmentDate, date);
                        int years = between.getYears();
                        int months = between.getMonths();
                        months+=(years*12);
                        if(months==0){
                            months=1;
                        }
                        LoanInstallmentModel.setInterest(loanID, (double) months * interestAmountForMonth);
                        int count = LoanInstallmentModel.getInstallmentsCounts(loanID);
                        if (count > 0) {
                            double balance = amount - (count * (installmentAmount - interestAmountForMonth));
                            LoanInstallmentModel.setLoanBalance(loanID, balance);
                        } else {
                            LoanInstallmentModel.setLoanBalance(loanID, amount);
                        }
                    }
                }
            }
            System.gc();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void loanTransactionID() {
        try {
            lblTransactionID.setText(ManageController.generateLastId("TransactionID", "transaction", "T"));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        FormValidate.getInstance().setComboboxes(cmbLoanTypes);
        FormValidate.getInstance().setTextFieldsList(txtAccNo,txtAmount);
        FormValidate.getInstance().setRegexList(
                "^([0-9]{14})$",
                "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$"
        );
        boolean validate = FormValidate.getInstance().checkValidation();
        if(validate){
            colLoan.setCellValueFactory(new PropertyValueFactory<>("loan"));
            colInstallmentAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
            TableDetailsLoanInstallmentTbl02 tbl2=new TableDetailsLoanInstallmentTbl02(cmbLoanTypes.getValue(),Double.parseDouble(txtAmount.getText()));

            //Add or update rows
            String loanType = cmbLoanTypes.getValue();
            ObservableList<TableDetailsLoanInstallmentTbl02> items = tblLoanInstallment.getItems();
            int index = isNewRow(items, loanType);
            if (index==-1) {
                items.add(tbl2);
            } else {
                items.get(index).setLoan(loanType);
                double newAmount = items.get(index).getInstallmentAmount() + tbl2.getInstallmentAmount();
                items.get(index).setInstallmentAmount(newAmount);
            }
            tblLoanInstallment.setItems(items);
            tblLoanInstallment.refresh();

        }
    }

    private int isNewRow(ObservableList<TableDetailsLoanInstallmentTbl02> items, String loantype) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getLoan().equals(loantype)){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {

    }

    @FXML
    void btnInstallOnAction(ActionEvent event) {

        try {
            FormValidate.getInstance().setTextFieldsList(txtAccNo, txtAmount);
            FormValidate.getInstance().setRegexList(
                    "^([0-9]{14})$",
                    "^(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$"
            );
            boolean isValidate = FormValidate.getInstance().checkValidation();
            if (isValidate) {
                DbConnection.getInstance().getConnection().setAutoCommit(false);
                String transactionID = ManageController.generateLastId("TransactionID","transaction","T");
                String accountID=txtAccNo.getText();
                double amount= Double.parseDouble(txtAmount.getText());
                String date=LocalDate.now().toString();
                String time= LocalTime.now().toString();
                Transaction transaction = new Transaction(transactionID,accountID,amount,date,time,"MONEY IN");
                //Make transaction
                boolean isTransactionAdded = DepositModel.makeTransaction(transaction);
                if (isTransactionAdded) {
                    String loanInstallmentID = ManageController.generateLastId("LoanInstallmentID", "loaninstallment", "LI");
                    String loanID = cmbLoanTypes.getValue().split("[ ]")[0];
                    LoanInstallment loanInstallment = new LoanInstallment(loanInstallmentID, transactionID, loanID);
                    //Make loan installment
                    boolean isAdded = LoanInstallmentModel.makeLoanInstallment(loanInstallment);
                    if (isAdded) {
                        //Update rows at loans table (loan balance, interest)

                        //Update interest
                        boolean isUpdated1 = LoanInstallmentModel.setInterest(loanID, 0);
                        if (isUpdated1) {
                            Loans loanDetails = LoanInstallmentModel.getLoanDetails(loanID);
                            //Update loan balance
                            double v = loanDetails.getRemainingLoanAmount() - amount;
                            boolean isUpdated2 = LoanInstallmentModel.setLoanBalance(loanID, v);
                            if (isUpdated2) {
                                ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.orElse(cancel) == ok) {
                                    DbConnection.getInstance().getConnection().commit();
                                    new Alert(Alert.AlertType.CONFIRMATION, "Approved Success !").show();
                                    showReport();

                                } else {
                                    DbConnection.getInstance().getConnection().rollback();
                                    new Alert(Alert.AlertType.ERROR, "Approved canceled !").show();
                                }
                                tblLoanInstallment.getItems().clear();
                                txtAmount.clear();
                                cmbLoanTypes.getItems().clear();
                                txtAccNo.requestFocus();
                            } else {
                                DbConnection.getInstance().getConnection().rollback();
                                FormValidate.getInstance().makeInstanceWarningError(lblNotifyInstall, "Installment Failed !");
                            }
                        } else {
                            DbConnection.getInstance().getConnection().rollback();
                            FormValidate.getInstance().makeInstanceWarningError(lblNotifyInstall, "Installment Failed !");
                        }
                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                        FormValidate.getInstance().makeInstanceWarningError(lblNotifyInstall, "Installment Failed !");
                    }
                } else {
                    DbConnection.getInstance().getConnection().rollback();
                    FormValidate.getInstance().makeInstanceWarningError(lblNotifyInstall, "Installment Failed !");
                }

            } else {
                FormValidate.getInstance().makeInstanceWarningError(lblNotifyInstall, "Fill the form correctly !");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void showReport(){

        try {
            JasperDesign jasperDesign= JRXmlLoader.load("D:\\Working_Directory\\Sanasa\\Final_Project_IDSE_1st_Semester\\src\\lk\\ijse\\sanasa\\report\\loanReportjrxml.jrxml");
            String query="SELECT sanasa.transaction.`TransactionID`, sanasa.transaction.`AccountID`, sanasa.transaction.`Amount`, sanasa.loans.`LoanID`, sanasa.loans.`RemainingLoanAmount`, sanasa.loandetails.`Description`, sanasa.transaction.`TransactionID`, sanasa.transaction.`AccountID`, sanasa.transaction.`Amount`, sanasa.loaninstallment.`LoanInstallmentID`, sanasa.loandetails.`Description`, sanasa.loans.`RemainingLoanAmount`, sanasa.loans.`SettledOrNot` FROM sanasa.loaninstallment INNER JOIN sanasa.loans ON sanasa.loaninstallment.`LoanID` = sanasa.loans.`LoanID` INNER JOIN sanasa.transaction ON sanasa.loaninstallment.`TransactionID` = sanasa.transaction.`TransactionID` INNER JOIN sanasa.loandetails ON sanasa.loans.`LoanTypeID` = sanasa.loandetails.`LoanTypeID` ORDER BY sanasa.loaninstallment.`LoanInstallmentID` DESC limit 1\n";

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
    void txtAccNoOnKeyReleased(KeyEvent event) {
        cmbLoanTypes.getItems().clear();
        txtAmount.clear();
        lblNotifyAccountNo.setText("");
        lblNotifyAccountNo.setStyle("-fx-text-fill: null");
        try {
            boolean validate = FormValidate.getInstance().isValidate(
                    "^([0-9]{14})$",
                    txtAccNo.getText()
            );
            FormValidate.getInstance().instanceWarningLabel(
                    lblNotifyAccountNo,
                    validate,
                    txtAccNo
            );
            if (validate) {
                AccountDetails accountNumber = CreateAccountModel.getAccount(txtAccNo.getText());
                if (accountNumber != null) {
                    lblNotifyAccountNo.setText("Account is founded !");
                    lblNotifyAccountNo.setStyle("-fx-text-fill: green");
                    boolean isLoaded = loadLoanTypesForCombobox(accountNumber.getAccountID());
                    if(isLoaded){
                        loadRelatedLoanInstallmentsForTable01(accountNumber.getAccountID());
                    }
                } else {
                    lblNotifyAccountNo.setText("Account is not found !");
                    lblNotifyAccountNo.setStyle("-fx-text-fill: red");
                }
            } else {
                cmbLoanTypes.getItems().clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void txtAccNoOnAction(ActionEvent event){
        cmbLoanTypes.getSelectionModel().clearSelection();
    }

    @FXML
    void cmbLoanTypesOnAction(ActionEvent event) {
            try {
                FormValidate.getInstance().setRegexList("^([0-9]{14})$");
                FormValidate.getInstance().setTextFieldsList(txtAccNo);
                boolean validate = FormValidate.getInstance().checkValidation();
                if(validate){
                    FormValidate.getInstance().setComboboxes(cmbLoanTypes);
                    boolean validate1 = FormValidate.getInstance().checkValidation();
                    AccountDetails accountNumber = CreateAccountModel.getAccount(txtAccNo.getText());
                    if (accountNumber != null && validate1) {
                        Loans loan = LoanInstallmentModel.getLoanDetails(cmbLoanTypes.getValue().split("[ ]")[0]);
                        //set installment date
                            Month nextInstallmentMonth = LoanInstallmentModel.getLastInstallmentMonth(cmbLoanTypes.getValue().split("[ ]")[0]);
                            /*if (nextInstallmentMonth == null) {
                                nextInstallmentMonth = LocalDate.parse(loan.getIssuedDate()).plusMonths(1).getMonth();
                            }*/
                            lblInstallmentDate.setText(nextInstallmentMonth+ " " + loan.getMonthlyInstallmentDate());
                            //set installment amount
                            lblInstallmentAmount.setText(String.valueOf(loan.getInstallmentAmount()));
                            lblLoanBalance.setText(String.valueOf(loan.getRemainingLoanAmount()));
                            String type = cmbLoanTypes.getValue();
                            ObservableList<TableDetailsLoanInstallmentTbl01> ob = LoanInstallmentModel.getAccountLoanInstallments(accountNumber.getAccountID(), cmbLoanTypes.getValue().split("[ ]")[0]);
                            colID.setCellValueFactory(new PropertyValueFactory<>("loanID"));
                            colAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
                            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                            tblViewLoanInstallment.setItems(ob);
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
            }
    }

    private void loadRelatedLoanInstallmentsForTable01(String accountNumber) {
        try {
            ObservableList<TableDetailsLoanInstallmentTbl01> list = LoanInstallmentModel.getLoanInstallmentsForAccount(accountNumber);
            if(list!=null){
                ObservableList<TableDetailsLoanInstallmentTbl01> tblList = FXCollections.observableArrayList();
                for (int i = 0; i < list.size(); i++) {
                    tblList.add(
                            new TableDetailsLoanInstallmentTbl01(
                                    list.get(i).getLoanID(),
                                    list.get(i).getInstallmentAmount(),
                                    list.get(i).getDate()
                            )
                    );
                }
                colID.setCellValueFactory(new PropertyValueFactory<>("loanID"));
                colAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                tblViewLoanInstallment.setItems(tblList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private boolean loadLoanTypesForCombobox(String accountNumber) throws SQLException, ClassNotFoundException {
        ObservableList<Loans> list = LoanInstallmentModel.getRelatedLoans(accountNumber);
        if(list!=null){
            ObservableList<String> listCmb = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); i++) {
                listCmb.add(list.get(i).getLoanID() + " " + list.get(i).getAmount());
            }
            cmbLoanTypes.setItems(listCmb);
            return true;
        }
        return false;
    }

}
