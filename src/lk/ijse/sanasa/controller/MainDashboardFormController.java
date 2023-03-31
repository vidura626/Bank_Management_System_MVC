package lk.ijse.sanasa.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.util.Navigation;
import lk.ijse.sanasa.util.Routes;

import java.io.IOException;

public class MainDashboardFormController {

    public AnchorPane pane;
    public FontAwesomeIconView drg;

    public void btnAccountOnAction() throws IOException {
        Navigation.navigate(Routes.CREATE_ACCOUNT,pane);
    }

    public void btnDashboardOnAction() throws IOException {
        pane.getChildren().clear();
    }

    public void btnDepositOnAction() throws IOException {

        Navigation.navigate(Routes.DEPOSIT,pane);
    }

    public void btnWithdrawalOnAction() throws IOException {
        Navigation.navigate(Routes.WITHDRAWAL,pane);
    }

    public void btnInterAccountTransaction() throws IOException {
        Navigation.navigate(Routes.INTER_ACCOUNT_TRANSACTION,pane);
    }

    public void btynLoanOnAction() throws IOException {
        Navigation.navigate(Routes.REQUEST_LOANS,pane);
    }

    public void btynApproveLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.APPROVE_lOANS,pane);
    }

    public void btnRejectLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.REJECTED_LOANS,pane);
    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.REPORT_DASHBOARD,pane);
    }

    public void btnLoanInstallmentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOAN_INSTALLMENT,pane);
    }

    public void btnLoanTypesDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOAN_TYPES_DASHBOARD,pane);
    }
}
