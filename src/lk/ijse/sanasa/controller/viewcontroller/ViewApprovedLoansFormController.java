package lk.ijse.sanasa.controller.viewcontroller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.sanasa.model.ViewModel;
import lk.ijse.sanasa.to.Loans;

import java.sql.SQLException;

public class ViewApprovedLoansFormController {
    public TableView<Loans> tblLoans;
    public TableColumn colID;
    public TableColumn colAmount;
    public TableColumn colLoanTypeID;
    public TableColumn colAccountID;
    public TableColumn colINstallment;
    public TableColumn colIssuedDate;
    public TableColumn colInsDate;
    public TableColumn colRemLoanAmount;

    public void initialize(){
        setCellValueFactory();
        loadTableData();
    }

    private void loadTableData() {
        try {
            ObservableList<Loans> list= ViewModel.getLoans();
            tblLoans.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colLoanTypeID.setCellValueFactory(new PropertyValueFactory<>("loanTypeID"));
        colAccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        colINstallment.setCellValueFactory(new PropertyValueFactory<>("installment"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colInsDate.setCellValueFactory(new PropertyValueFactory<>("monthlyInstallmentDate"));
        colRemLoanAmount.setCellValueFactory(new PropertyValueFactory<>("remainingLoanAmount"));
    }
}
