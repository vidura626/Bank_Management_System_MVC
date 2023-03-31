package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.Transaction;

import java.sql.SQLException;

public class ViewDailyTransactionFormController {
    public TableView<Transaction> tblDailyTransaction;
    public TableColumn colTransactionID;
    public TableColumn colAccountID;
    public TableColumn colAmount;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colType;
    public DatePicker datePicker;

    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<Transaction> list = ViewModel.getDailyTransaction();
            tblDailyTransaction.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void datePickerOnAction(ActionEvent event) {
        try {
            ObservableList<Transaction> dailyTransaction = ViewModel.getDailyTransaction(datePicker.getValue());
            tblDailyTransaction.setItems(dailyTransaction);
            tblDailyTransaction.refresh();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
