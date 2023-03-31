package lk.ijse.sanasa.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LoanApprovalProgressController {

    @FXML
    private JFXTabPane pane;

    @FXML
    private Tab tabCreateAccount;

    @FXML
    private JFXComboBox<?> cmbBlanceFixing;

    @FXML
    private AnchorPane paneSearchUpdate;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnFind;

    @FXML
    private Label lblNotifySearchUpdate;

    @FXML
    private Label lblPendingLoanID;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblLoanTypeID;

    @FXML
    private Label lblAccountID;

    @FXML
    private Label lblInstallments;

    @FXML
    private Label lblInstallmentAmount;

    @FXML
    private TableView<?> tblBalancesDeposits;

    @FXML
    private TableColumn<?, ?> colDepositTypes;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colRecomendedBalance;

    @FXML
    private JFXButton btnUpdate1;

    @FXML
    private JFXComboBox<?> cmbLoanFixing;

    @FXML
    private Tab tabCloseAccount;

    @FXML
    private AnchorPane paneFailed;

    @FXML
    private Label lblLoanIDrst;

    @FXML
    private Label lblAccountIDrst;

    @FXML
    private Label lblAmountrst;

    @FXML
    private Label lblDateTimerst;

    @FXML
    private Label lblChackBalancerst;

    @FXML
    private Label lblCheckAttendancerst;

    @FXML
    private Label lblCheckLoanrst;

    @FXML
    private Label lblCheckArrearsLoansrst;

    @FXML
    private AnchorPane paneApprove;

    @FXML
    private Label lblLoanIDrst1;

    @FXML
    private Label lblAccountIDrst1;

    @FXML
    private Label lblAmountrst1;

    @FXML
    private Label lblDateTimerst1;

    @FXML
    private Label lblChackBalancerst1;

    @FXML
    private Label lblCheckAttendancerst1;

    @FXML
    private Label lblCheckLoanrst1;

    @FXML
    private Label lblCheckArrearsLoansrst1;

    @FXML
    void btnFindOnAction(ActionEvent event) {

    }

    @FXML
    void btnFixed1OnAction(ActionEvent event) {

    }

    @FXML
    void btnFixed2OnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {

    }

}
