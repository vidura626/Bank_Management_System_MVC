package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.table.TableDetailsViewLoanInstallment;

import java.sql.SQLException;

public class ViewLoanInstallmentFormController {
    public TableView<TableDetailsViewLoanInstallment> tblLoanInstallments;
    public TableColumn colLoanInstallmentID;
    public TableColumn colTransactionID;
    public TableColumn colLoanID;
    public TableColumn colAmount;
    public TableColumn colAccountID;
    public TableColumn colDate;
    public TableColumn colTime;
    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<TableDetailsViewLoanInstallment> list= ViewModel.getLoanInstallments();
            tblLoanInstallments.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colLoanInstallmentID.setCellValueFactory(new PropertyValueFactory<>("loanInstallmentID"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        colLoanID.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
}
