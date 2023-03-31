package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.PendingLoans;

import java.sql.SQLException;

public class ViewPendingLoansFormController {
    public TableView<PendingLoans> tblPendingLoans;
    public TableColumn colPendingLoanID;
    public TableColumn colAmount;
    public TableColumn colAccountID;
    public TableColumn colInstallments;
    public TableColumn colLoanTypeID;
    public TableColumn colInstallmentAmount;

    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<PendingLoans> list = ViewModel.getPendingLoans();
            tblPendingLoans.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colPendingLoanID.setCellValueFactory(new PropertyValueFactory<>("pendingLoansID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colInstallments.setCellValueFactory(new PropertyValueFactory<>("installment"));
        colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>("loanTypeID"));
        colInstallmentAmount.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
    }
}
