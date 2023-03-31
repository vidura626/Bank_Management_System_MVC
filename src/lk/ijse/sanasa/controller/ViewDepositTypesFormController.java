package lk.ijse.sanasa.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.model.DepositModel;
import lk.ijse.sanasa.to.DepositDetails;

import java.sql.SQLException;

public class ViewDepositTypesFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<DepositDetails> tblViewDepositDetails;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colInterest;

    public void initialize(){
        setTableInfo();
    }

    private void setTableInfo() {
        try {
            ObservableList<DepositDetails> depositTypes = DepositModel.getDepositTypesAll();
            tblViewDepositDetails.setItems(depositTypes);
            colId.setCellValueFactory(new PropertyValueFactory<>("depositTypeID"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
