package lk.ijse.sanasa.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        pane.setStyle("-fx-background-color: darkgray");
        Stage window = (Stage)Navigation.pane.getScene().getWindow();

        switch (route) {
            case CREATE_ACCOUNT:
                window.setTitle("Create Account");
                initUI("CreateAccount1.fxml");
                break;
            case APPROVE_lOANS:
                window.setTitle("Approve Loans");
                initUI("ApproveLoans1.fxml");
                break;
            case DEPOSIT:
                window.setTitle("Deposit");
                initUI("Deposit1.fxml");
                break;
            case INTER_ACCOUNT_TRANSACTION:
                window.setTitle("Inter Account Transaction");
                initUI("InterAccountTransaction1.fxml");
                break;
            case LOAN_INSTALLMENT:
                window.setTitle("Loan Installment");
                initUI("LoanInstallment1.fxml");
                break;
            case MAIN_DASHBOARD:
                window.setTitle("Main Dashboard");
                initUI("MainDashboardForm.fxml");
                break;
            case REJECTED_LOANS:
                window.setTitle("Rejected Loans");
                initUI("RejectLoans1.fxml");
                break;
            case REQUEST_LOANS:
                window.setTitle("Request Loans");
                initUI("RequestLoan1.fxml");
                break;
            case WITHDRAWAL:
                window.setTitle("Withdrawal");
                initUI("Withdraw1.fxml");
                break;
            case REPORT_DASHBOARD:
                window.setTitle("Report_Dashboard");
                initUI("ReportDashboardForm1.fxml");
                break;
            case VIEW_ACCOUNT:
                window.setTitle("View Account");
                initUI("reports/ViewAccountForm.fxml");
                break;
            case VIEW_DAILY_TRANSACTIONS:
                window.setTitle("View_Daily_Transactions");
                initUI("reports/ViewDailyTransactionForm.fxml");
                break;
            case VIEW_DEPOSITS:
                window.setTitle("View_Deposits");
                initUI("reports/ViewDepositForm.fxml");
                break;
            case VIEW_MONTHLY_TRANSACTIONS:
                window.setTitle("View_Monthly_Transactions");
                initUI("reports/ViewMonthlyTransactionForm.fxml");
                break;
            case VIEW_WITHDRAWALS:
                window.setTitle("View_Withdrawals");
                initUI("reports/ViewWithdrawalForm.fxml");
                break;
            case VIEW_LOAN_INSTALLMENTS:
                window.setTitle("View_Loan_Installments");
                initUI("reports/ViewLoanInstallmentForm.fxml");
                break;
            case LOGIN:
                window.setTitle("Login_form");
                initUI("LoginForm.fxml");
                break;
            case VIEW_PENDING_LOANS:
                window.setTitle("View_Pending_Loans");
                initUI("reports/ViewPendingLoansForm.fxml");
                break;
            case VIEW_ARREARS_LOANS:
                window.setTitle("View_Arrears_Loans");
                initUI("reports/ViewArrearsLoansForm.fxml");
                break;
            case VIEW_APPROVED_LOANS:
                window.setTitle("View_Approved_Loans");
                initUI("reports/ViewApprovedLoansForm.fxml");
                break;
            case VIEW_SETTLED_LOANS:
                window.setTitle("View_Settled_Loans");
                initUI("reports/ViewSettledLoansForm.fxml");
                break;
            case LOAN_TYPES_DASHBOARD:
                window.setTitle("Loan_Types_Dashboard");
                initUI("AddLoanDetailsForm.fxml");
                break;
                default:
                new Alert(Alert.AlertType.ERROR, "UI Not Found!").show();
        }
    }
    public static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/sanasa/view/" + location)));
    }
}
