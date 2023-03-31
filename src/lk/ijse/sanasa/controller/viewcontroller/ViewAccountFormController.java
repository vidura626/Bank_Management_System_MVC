package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.AccountDetails;

import java.sql.SQLException;

public class ViewAccountFormController {

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
    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<AccountDetails> list = ViewModel.getAccounts();
            tblAccounts.setItems(list);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colRegTime.setCellValueFactory(new PropertyValueFactory<>("regTime"));
    }
}
