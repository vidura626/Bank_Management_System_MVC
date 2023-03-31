package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.table.TableDetailsViewDeposits;

import java.sql.SQLException;

public class ViewDepositFormController {

    public TableColumn colAccountID;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colType;
    @FXML
    private TableView<TableDetailsViewDeposits> tblDeposits;

    @FXML
    private TableColumn<?, ?> colDepositID;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private TableColumn<?, ?> colDepositTypeAccountID;

    @FXML
    private TableColumn<?, ?> colAmount;

    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<TableDetailsViewDeposits> list = ViewModel.getDeposits();
            tblDeposits.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colDepositID.setCellValueFactory(new PropertyValueFactory<>("depositID"));
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        colDepositTypeAccountID.setCellValueFactory(new PropertyValueFactory<>("depositTypeAccountID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
}
