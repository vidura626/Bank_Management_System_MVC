package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.table.TableDetailsViewDeposits;

import java.sql.SQLException;

public class ViewWithdrawalFormController {
    public TableView<TableDetailsViewDeposits> tblWithdrawal;
    public TableColumn colWithdrawalID;
    public TableColumn colTransactionID;
    public TableColumn colAmount;
    public TableColumn colDepositTypeAccountID;
    public TableColumn colAccountID;
    public TableColumn colDate;
    public TableColumn colTime;

    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<TableDetailsViewDeposits> list = ViewModel.getWithdrawals();
            tblWithdrawal.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colWithdrawalID.setCellValueFactory(new PropertyValueFactory<>("depositID"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        colDepositTypeAccountID.setCellValueFactory(new PropertyValueFactory<>("depositTypeAccountID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
}
