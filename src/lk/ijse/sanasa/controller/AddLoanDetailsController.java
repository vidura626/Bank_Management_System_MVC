package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.db.DbConnection;
import lk.ijse.sanasa.model.AddLoanTypesModel;
import lk.ijse.sanasa.to.LoanDetails;
import lk.ijse.sanasa.to.table.TableDetailsLoanTypeDetails;
import lk.ijse.sanasa.util.FormValidate;
import lk.ijse.sanasa.util.ManageController;

import java.sql.SQLException;
import java.util.Optional;

public class AddLoanDetailsController {

    public TableView<TableDetailsLoanTypeDetails> tblLoanTypes;
    public TableColumn colLoanTypeID;
    public TableColumn colDescription;
    public TableColumn colInterest;
    public TableColumn colAmounts;
    public TableColumn colRemove;
    public JFXButton btnDelete;
    @FXML
    private JFXTabPane pane;

    @FXML
    private Tab tabLoanTypeDetails;

    @FXML
    private JFXTextField txtLoanTypeID;

    @FXML
    private Label lblNotifyNic;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private Label lblDescription;

    @FXML
    private JFXTextField txtInterest;

    @FXML
    private Label lblInterest;

    @FXML
    private Label lblNotifyAddLoanTypes;

    @FXML
    private JFXTextField txtAmounts;

    @FXML
    private Label lblAmounts;

    @FXML
    private Label lblNotifyCreateAccount;

    @FXML
    private AnchorPane paneSearchUpdate;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblNotifySearchUpdate;

    @FXML
    private Tab tabCloseAccount;

    @FXML
    private Tab tabViewAccount;

    @FXML
    private JFXButton btnOk;

    @FXML
    private JFXButton btnSaveChanges;

    public void initialize(){
        setLoanTypeID();
        btnOk.setVisible(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadLoanTypeDetailsTable() {
        try {
            ObservableList<LoanDetails> loanDetailsList = AddLoanTypesModel.getLoanDetailsList();
            ObservableList<TableDetailsLoanTypeDetails> loanTypeDetails = FXCollections.observableArrayList();
            for (int i = 0; i < loanDetailsList.size(); i++) {
                JFXButton remove = new JFXButton("X");
                remove.setStyle("-fx-text-fill: red");
                buttonSetOnAction(remove, tblLoanTypes);
                loanTypeDetails.add(
                        new TableDetailsLoanTypeDetails(
                                loanDetailsList.get(i).getLoanTypeID(),
                                loanDetailsList.get(i).getDescription(),
                                loanDetailsList.get(i).getInterest(),
                                loanDetailsList.get(i).getAmounts(),
                                remove
                        )
                );
            }
            setCellValueFactory();
            tblLoanTypes.setItems(loanTypeDetails);
            } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void setCellValueFactory() {
        colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>("loanTypeID"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        colAmounts.setCellValueFactory(new PropertyValueFactory<>("amounts"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    private void setLoanTypeID() {
        try {
            String s = ManageController.generateLastId(
                    "LoanTypeID",
                    "loandetails",
                    "LD"
            );
            txtLoanTypeID.setText(String.format("LD%02d",(Integer.parseInt(s.split("LD")[1]))));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            setCellValueFactory();

            if(!btnOk.visibleProperty().getValue()){
                colRemove.setVisible(true);
                btnOk.setVisible(true);
                txtAmounts.setDisable(false);
                txtDescription.setDisable(false);
                txtInterest.setDisable(false);
                tblLoanTypes.getItems().clear();
                return;
            }

            String loanTypeID = txtLoanTypeID.getText();
            String description = txtDescription.getText();
            double interest = Double.parseDouble(txtInterest.getText());
            String amounts = txtAmounts.getText();
            JFXButton remove = new JFXButton("X");

            buttonSetOnAction(remove,tblLoanTypes);

            TableDetailsLoanTypeDetails tableDetailsLoanTypeDetails = new TableDetailsLoanTypeDetails(loanTypeID, description, interest, amounts, remove);

            tblLoanTypes.getItems().add(tableDetailsLoanTypeDetails);
            tblLoanTypes.refresh();

            txtLoanTypeID.setText((String.format("LD%02d",Integer.parseInt(txtLoanTypeID.getText().split("LD")[1])+1)));

        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.WARNING,"Check the form again !").show();
        }
    }

    @FXML
    void btnViewLoanTypesOnAction(ActionEvent event){
        colRemove.setVisible(false);
        loadLoanTypeDetailsTable();
        btnOk.setVisible(false);
        txtAmounts.setDisable(true);
        txtDescription.setDisable(true);
        txtInterest.setDisable(true);
    }

    public void buttonSetOnAction(JFXButton remove, TableView<TableDetailsLoanTypeDetails> tblLoanTypes) {
        try {
            remove.setOnAction(e -> {
                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                if(!tblLoanTypes.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        tblLoanTypes.getItems().removeAll(tblLoanTypes.getSelectionModel().getSelectedItem());
                        refreshTable();
                    }
                }
            });
        }catch (NullPointerException e){
            System.out.println(e);
        }
    }

    private void refreshTable() {
        ObservableList<TableDetailsLoanTypeDetails> items = tblLoanTypes.getItems();
        int lastDigit = Integer.parseInt(txtLoanTypeID.getText().split("LD")[1])-1;
        String format = String.format("LD%02d", lastDigit);
        txtLoanTypeID.setText(format);
        for (int i = items.size()-1; i >= 0; i--) {
            lastDigit--;
            items.get(i).setLoanTypeID(String.format("LD%02d",lastDigit));
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

            try {
                boolean validate = FormValidate.getInstance().isValidate("^(LD)([0-9]{1,2})$", txtSearch.getText());
                if(validate){
                    DbConnection.getInstance().getConnection().setAutoCommit(false);
                    LoanDetails loanDetails = new LoanDetails(txtLoanTypeID.getText(), txtDescription.getText(), Double.parseDouble(txtInterest.getText()), txtAmounts.getText());
                    if(AddLoanTypesModel.update(loanDetails)){
                        ButtonType ok = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ok, cancel);
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.orElse(cancel)==ok){
                            DbConnection.getInstance().getConnection().commit();
                            new Alert(Alert.AlertType.CONFIRMATION,"Updated !").show();
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                DbConnection.getInstance().getConnection().setAutoCommit(true);
                btnClearOnAction(event);
                setLoanTypeID();
            }
    }

    @FXML
    void txtDescriptionOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtInterestOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event){
        ManageController.getInstance().setTextFields(txtDescription,txtSearch,txtAmounts,txtInterest);
        ManageController.getInstance().clearForm();
    }

    @FXML
    void txtAmountsOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {

        try {
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            txtDescription.setDisable(false);
            txtAmounts.setDisable(false);
            txtInterest.setDisable(false);
            boolean validate = FormValidate.getInstance().isValidate("^(LD)([0-9]{1,2})$", txtSearch.getText());
            if (validate) {
                if (AddLoanTypesModel.isFind(txtSearch.getText())) {
                    LoanDetails loanDetails = AddLoanTypesModel.search(txtSearch.getText());
                    txtLoanTypeID.setText(loanDetails.getLoanTypeID());
                    txtDescription.setText(loanDetails.getDescription());
                    txtInterest.setText(String.valueOf(loanDetails.getInterest()));
                    txtAmounts.setText(loanDetails.getAmounts());
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                } else {
                    txtLoanTypeID.setText("");
                    txtDescription.setText("");
                    txtInterest.setText("");
                    txtAmounts.setText("");
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                }
            }else {
                txtLoanTypeID.setText("");
                txtDescription.setText("");
                txtInterest.setText("");
                txtAmounts.setText("");
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnOkOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            ObservableList<TableDetailsLoanTypeDetails> items = tblLoanTypes.getItems();
            for (int i = 0; i < items.size(); i++) {
                boolean add = AddLoanTypesModel.add(new LoanDetails(items.get(i).getLoanTypeID(), items.get(i).getDescription(), items.get(i).getInterest(), items.get(i).getAmounts()));
                if(!add){
                        DbConnection.getInstance().getConnection().rollback();
                        new Alert(Alert.AlertType.ERROR,"Process failed !").show();
                        return;
                    }

                if(i==items.size()-1){
                    ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        DbConnection.getInstance().getConnection().commit();
                        new Alert(Alert.AlertType.CONFIRMATION,"Ok !").show();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            DbConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean validate = FormValidate.getInstance().isValidate("^(LD)([0-9]{1,2})$", txtSearch.getText());
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            if (validate && AddLoanTypesModel.isFind(txtSearch.getText())&&AddLoanTypesModel.delete(txtLoanTypeID.getText())) {
                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.orElse(no)==ok){
                    DbConnection.getInstance().getConnection().commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted !").show();
                    setLoanTypeID();
                    btnClearOnAction(event);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
