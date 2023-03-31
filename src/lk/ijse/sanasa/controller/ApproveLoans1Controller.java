package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.ApproveLoanModel;
import lk.ijse.sanasa.model.RequestLoanModel;
import lk.ijse.sanasa.to.PendingLoans;
import lk.ijse.sanasa.to.table.TableDetailsApproveLoans;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class ApproveLoans1Controller {

    public JFXComboBox<String> cmbLoanIDs;
    public TableColumn colPendID;
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblNotifyApprove;

    @FXML
    private TableView<TableDetailsApproveLoans> tblLoans;

    @FXML
    private TableColumn<?, ?> colLoan;

    @FXML
    private TableColumn<?, ?> colInstallmentAmount;

    @FXML
    private TableColumn<?, ?> colInstallmentCount;

    @FXML
    private TableColumn<?, ?> colInstallmentDate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private Label lblLoan;

    @FXML
    private Label lblAccNo;

    @FXML
    private Label lblAmount;


    public void initialize() {
        loadPendingLoanIds();
    }

    private void loadPendingLoanIds() {
        try {
            ObservableList<String> list = ApproveLoanModel.getPendingLoans();
            cmbLoanIDs.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {

            boolean isValidate = cmbLoanIDs.getValue().isEmpty();
            if (!isValidate) {
                String pendingLoanId = cmbLoanIDs.getValue();
                PendingLoans pendingLoan = ApproveLoanModel.getPendingLoan(pendingLoanId);

                String lastID = null;
                if (tblLoans.getItems().size()==0) {
                    lastID = ManageController.generateLastId(
                            "loanID",
                            "loans",
                            "L"
                    );
                } else {
                    ObservableList<TableDetailsApproveLoans> items = tblLoans.getItems();
                    String loanID = items.get(items.size() - 1).getLoanID();
                    int lastDigit = Integer.parseInt(loanID.split("[L]")[1])+1;
                    lastID =String.format("L%08d",lastDigit);
                }

                TableDetailsApproveLoans loan = new TableDetailsApproveLoans(
                        lastID,
                        pendingLoan.getInstallmentAmount(),
                        pendingLoan.getInstallment(),
                        LocalDate.now().getDayOfMonth(),
                        new Button("Delete"),
                        pendingLoanId
                );
                colLoan.setCellValueFactory(new PropertyValueFactory<>("loanID"));
                colInstallmentAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
                colInstallmentCount.setCellValueFactory(new PropertyValueFactory<>("installment"));
                colInstallmentDate.setCellValueFactory(new PropertyValueFactory<>("monthlyInstallmentDate"));
                colDelete.setCellValueFactory(new PropertyValueFactory<>("button"));
                colPendID.setCellValueFactory(new PropertyValueFactory<>("pendingLoanID"));

                int index = checkAvailableItem(tblLoans, loan);
                if (index>-1) {
                    new Alert(Alert.AlertType.WARNING,"Already Added !").show();
                } else {
                    int selectedIndex = cmbLoanIDs.getSelectionModel().getSelectedIndex();
                    cmbLoanIDs.getItems().remove(selectedIndex);
                    tblLoans.getItems().add(loan);
                    lblLoan.setText("Loan");
                    lblAccNo.setText("Account Number");
                    lblAmount.setText("Amount");
                }
            } else {
                FormValidate.getInstance().makeInstanceWarningConfirm(
                        lblNotifyApprove,
                        "Fill the form correctly ! "
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private int checkAvailableItem(TableView<TableDetailsApproveLoans> tblLoans, TableDetailsApproveLoans loan) {
        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(tblLoans.getItems().get(i).getLoanID().equals(loan.getLoanID())){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnApproveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            if(tblLoans.getItems().size()==0){
                new Alert(Alert.AlertType.WARNING,"Loans are not added !").show();
                return;
            }
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isApproved = ApproveLoanModel.isApprove(tblLoans.getItems());
            if (isApproved) {
                boolean isRemoved = true;
                for (int i = 0; i < tblLoans.getItems().size(); i++) {
                    if (!ApproveLoanModel.removeRelatedPendingLoan(tblLoans.getItems().get(i).getPendingLoanID())) {
                        isRemoved=false;
                        break;
                    }
                }

                if (isRemoved) {

                    ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(cancel) == ok) {
                        DbConnection.getInstance().getConnection().commit();
                        new Alert(Alert.AlertType.CONFIRMATION, "Approved Success !").show();
                    } else {
                        DbConnection.getInstance().getConnection().rollback();
                        new Alert(Alert.AlertType.ERROR, "Approved canceled !").show();
                    }
                    tblLoans.getItems().clear();
                    tblLoans.refresh();
                } else {
                    DbConnection.getInstance().getConnection().rollback();
                    new Alert(Alert.AlertType.ERROR, "Approval failed !").show();
                }
            } else {
                DbConnection.getInstance().getConnection().rollback();
                new Alert(Alert.AlertType.ERROR, "Approval failed !").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e+" yes");
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }

    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        if(tblLoans.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Loans cannot found !").show();
            return;
        }
        String pendingLoanIDtbl = tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID();

        if (!checkComboBox(pendingLoanIDtbl,cmbLoanIDs)) {
            cmbLoanIDs.getItems().add(tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID());
            ObservableList<String> items = cmbLoanIDs.getItems();
            Collections.sort(items,RejectLoans1Controller.getComparator());
            tblLoans.getItems().remove(tblLoans.getSelectionModel().getSelectedItem());
            tblLoans.refresh();
        }
    }

    private boolean checkComboBox(String pendingLoanIDtbl, JFXComboBox<String> tblLoans) {

        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(cmbLoanIDs.getItems().get(i).equals(pendingLoanIDtbl)){
                return true;
            }
        }
        return false;
    }

    public void cmbLoanIDsOnAction(ActionEvent event) {
        try {
            PendingLoans pendingLoan = ApproveLoanModel.getPendingLoan(cmbLoanIDs.getValue());
            ObservableList<String> loanTypes = RequestLoanModel.getLoanTypes();
            for (int i = 0; i < Objects.requireNonNull(loanTypes).size(); i++) {
                if (cmbLoanIDs.getValue().equals(pendingLoan.getPendingLoansID())) {
                    lblLoan.setText(ManageController.generateLastId("LoanID", "loans", "L") + " " + loanTypes.get(i).split("[ ]")[1]);
                }
            }
            lblAccNo.setText(pendingLoan.getAccountID());
            lblAmount.setText(String.valueOf(pendingLoan.getAmount()));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}