package lk.ijse.sanasa.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.sanasa.util.Navigation;
import lk.ijse.sanasa.util.Routes;

import java.io.IOException;

public class ReportDashboardFormController {

    public AnchorPane pane;

    public void btnViewAccountsOnAction() throws IOException {
        Navigation.navigate(Routes.VIEW_ACCOUNT,pane);
    }

    public void btnDailyTransactionsOnAction() throws IOException {
        Navigation.navigate(Routes.VIEW_DAILY_TRANSACTIONS,pane);
    }

    public void btnMonthlyTransactionsOnAction() throws IOException {
        Navigation.navigate(Routes.VIEW_MONTHLY_TRANSACTIONS,pane);
    }

    public void btnDepositsOnAction() throws IOException {
        Navigation.navigate(Routes.VIEW_DEPOSITS,pane);
    }

    public void btnWithdrawalsOnAction() throws IOException {
        Navigation.navigate(Routes.VIEW_WITHDRAWALS,pane);
    }

    public void btnLoanInstallmentsOnAction() throws IOException {
        Navigation.navigate(Routes.VIEW_LOAN_INSTALLMENTS,pane);
    }

    public void btnPendingLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VIEW_PENDING_LOANS,pane);
    }

    public void btnApprovedLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VIEW_APPROVED_LOANS,pane);
    }

    public void btnSettledLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VIEW_SETTLED_LOANS,pane);
    }

    public void btnArrearsLoansOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VIEW_ARREARS_LOANS,pane);
    }
}
