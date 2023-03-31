package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
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
import lk.ijse.sanasa.to.table.TableDetailsRejectLoans;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class RejectLoans1Controller {

    public TableColumn colPendingLoanID;
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblNotifyReject;

    @FXML
    private TableView<TableDetailsRejectLoans> tblLoans;

    @FXML
    private TableColumn<?, ?> colLoan;

    @FXML
    private TableColumn<?, ?> colRejectedReason;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private Label lblLoanID;

    @FXML
    private JFXTextField txtRejectedReason;

    @FXML
    private JFXComboBox<String> cmbPendingLoans;

    @FXML
    private Label lblNotifyAdd;

    public void initialize() {
        loadPendingLoanIds();
    }

    private void loadPendingLoanIds() {
        try {
            ObservableList<String> list = ApproveLoanModel.getPendingLoans();
            cmbPendingLoans.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {

            boolean isValidate = cmbPendingLoans.getValue().isEmpty();
            if (!isValidate) {
                String selectedItem = cmbPendingLoans.getValue();
                PendingLoans pendingLoan = ApproveLoanModel.getPendingLoan(selectedItem);

                String lastID = null;
                if (tblLoans.getItems().size()==0) {
                    lastID = ManageController.generateLastId(
                            "RejLoanID",
                            "rejectedloans",
                            "RJ"
                    );
                } else {
                    ObservableList<TableDetailsRejectLoans> items = tblLoans.getItems();
                    String loanID = items.get(items.size() - 1).getLoanID();
                    int lastDigit = Integer.parseInt(loanID.split("[RJ]")[2])+1;
                    lastID =String.format("RJ%08d",lastDigit);
                }

                TableDetailsRejectLoans loan=new TableDetailsRejectLoans(
                        lastID,
                        txtRejectedReason.getText().isEmpty()?"<No Reason>":txtRejectedReason.getText(),
                        new Button("Delete"),
                        pendingLoan.getPendingLoansID()
                );
                ObservableList<TableDetailsRejectLoans> loans = FXCollections.observableArrayList();
                colLoan.setCellValueFactory(new PropertyValueFactory<>("loanID"));
                colRejectedReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
                colDelete.setCellValueFactory(new PropertyValueFactory<>("button"));
                colPendingLoanID.setCellValueFactory(new PropertyValueFactory<>("pendingLoanID"));

                int index = checkAvailableItem(tblLoans, loan);
                if (index>-1) {
                    new Alert(Alert.AlertType.WARNING,"Already Added !").show();
                } else {
                    cmbPendingLoans.getItems().remove(selectedItem);
                    tblLoans.getItems().add(loan);
                    lblLoanID.setText("Loan");
                    txtRejectedReason.setText("");
                }
            } else {
                FormValidate.getInstance().makeInstanceWarningConfirm(
                        lblNotifyReject,
                        "Fill the form correctly ! "
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    private int checkAvailableItem(TableView<TableDetailsRejectLoans> tblLoans, TableDetailsRejectLoans loan) {
        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(tblLoans.getItems().get(i).getLoanID().equals(loan.getLoanID())){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void btnClearFormOnAction(ActionEvent event) {

    }

    @FXML
    void btnRejectOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            if(tblLoans.getItems().size()==0){
                new Alert(Alert.AlertType.WARNING,"Loans are not added !").show();
                return;
            }
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isRejected = RequestLoanModel.isReject(tblLoans.getItems());
            if (isRejected) {
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
    void btnRemveOnAction(ActionEvent event) {
        if(tblLoans.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Loans cannot found !").show();
            return;
        }
        String pendingLoanIDtbl = tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID();

        if (!checkComboBox(pendingLoanIDtbl,cmbPendingLoans)) {
            cmbPendingLoans.getItems().add(tblLoans.getSelectionModel().getSelectedItem().getPendingLoanID());
            ObservableList<String> items = cmbPendingLoans.getItems();
            Collections.sort(items,getComparator());
            tblLoans.getItems().remove(tblLoans.getSelectionModel().getSelectedItem());
            tblLoans.refresh();
        }
    }

    public static Comparator<String> getComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // ordering is the natural String ordering in your example
                return o1.compareTo(o2);
            }
        };
    }

    private boolean checkComboBox(String pendingLoanIDtbl, JFXComboBox<String> tblLoans) {

        for (int i = 0; i < tblLoans.getItems().size(); i++) {
            if(tblLoans.getItems().get(i).equals(pendingLoanIDtbl)){
                return true;
            }
        }
        return false;
    }

    @FXML
    void cmbPendingLoansOnAction(ActionEvent event) {
        try {
            PendingLoans pendingLoan = ApproveLoanModel.getPendingLoan(cmbPendingLoans.getValue());
            ObservableList<String> loanTypes = RequestLoanModel.getLoanTypes();
            for (int i = 0; i < Objects.requireNonNull(loanTypes).size(); i++) {
                if (cmbPendingLoans.getValue().equals(pendingLoan.getPendingLoansID())) {
                    lblLoanID.setText(ManageController.generateLastId("LoanID", "loans", "L") + " " + loanTypes.get(i).split("[ ]")[1]);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}