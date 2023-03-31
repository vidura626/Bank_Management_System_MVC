package lk.ijse.sanasa.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.table.TableDetailsViewInterAccountTransaction;

import java.sql.SQLException;

public class ViewInterAccountTransactionFormController {
    public TableView<TableDetailsViewInterAccountTransaction> tblInterAccTransaction;
    public TableColumn colIInterAccountTransactionID;
    public TableColumn colTransactionID;
    public TableColumn colAccount01;
    public TableColumn colAccount02;
    public TableColumn colAmount;
    public TableColumn colDate;
    public TableColumn colTime;
    public void initialize(){
        setCellValueFactory();
        loanTableData();
    }

    private void loanTableData() {
        try {
            ObservableList<TableDetailsViewInterAccountTransaction> list= ViewModel.getInterAccountTransaction();
            tblInterAccTransaction.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colIInterAccountTransactionID.setCellValueFactory(new PropertyValueFactory<>("interAccountTransactionID"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        colAccount01.setCellValueFactory(new PropertyValueFactory<>("account01ID"));
        colAccount02.setCellValueFactory(new PropertyValueFactory<>("account02ID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
}
