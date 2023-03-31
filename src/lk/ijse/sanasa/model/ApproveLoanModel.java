package lk.ijse.sanasa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.sanasa.to.Loans;
import lk.ijse.sanasa.to.PendingLoans;
import lk.ijse.sanasa.to.table.TableDetailsApproveLoans;
import lk.ijse.sanasa.util.CrudUtil;
import lk.ijse.sanasa.util.ManageController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ApproveLoanModel {
    public static ObservableList<String> getPendingLoans() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from pendingloans");
        ObservableList<String> list= FXCollections.observableArrayList();
        while (rst.next()){
            list.add(rst.getString(1));
        }
        return list;
    }

    public static PendingLoans getPendingLoan(String pendingLoanId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from pendingloans where pendingloanID = ?", pendingLoanId);
        rst.next();
        return new PendingLoans(
                rst.getString(1),
                rst.getDouble(2),
                rst.getString(3),
                rst.getString(4),
                rst.getInt(5),
                rst.getDouble(6)
        );
    }

    public static boolean makeAprrovement(Loans loan) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into loans values (?,?,?,?,?,?,?,?,?,?,?)",
                loan.getLoanID(),
                loan.getAmount(),
                loan.getLoanTypeID(),
                loan.getAccountID(),
                loan.getInstallment(),
                loan.getInstallmentAmount(),
                loan.getIssuedDate(),
                loan.getRemainingInstallments(),
                loan.getRemainingLoanAmount(),
                loan.getMonthlyInstallmentDate(),
                loan.getSettledOrNot()
        );
    }

    public static boolean removeRelatedPendingLoan(String pendingLoanId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from pendingloans where pendingLoanID = ?",pendingLoanId);
    }

    public static boolean isApprove(ObservableList<TableDetailsApproveLoans> items) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            PendingLoans pendingLoan = getPendingLoan(items.get(i).getPendingLoanID());
            boolean approve = isApprove(new Loans(
                    ManageController.generateLastId(
                            "LoanID",
                            "loans",
                            "L"
                    ),
                    pendingLoan.getAmount(),
                    pendingLoan.getLoanTypeID(),
                    pendingLoan.getAccountID(),
                    pendingLoan.getInstallment(),
                    pendingLoan.getInstallmentAmount(),
                    LocalDate.now().toString(),
                    pendingLoan.getInstallment(),
                    pendingLoan.getInstallmentAmount(),
                    LocalDate.now().getDayOfMonth(),
                    "NotSettled",
                    pendingLoan.getInstallmentAmount()-pendingLoan.getAmount()
                    )
            );
            if (!approve) {
                return false;
            }
        }
        return true;
    }

    public static boolean isApprove(Loans loan) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into loans values (?,?,?,?,?,?,?,?,?,?,?,?)",
                loan.getLoanID(),
                loan.getAmount(),
                loan.getLoanTypeID(),
                loan.getAccountID(),
                loan.getInstallment(),
                loan.getInstallmentAmount(),
                loan.getIssuedDate(),
                loan.getRemainingInstallments(),
                loan.getRemainingLoanAmount(),
                loan.getMonthlyInstallmentDate(),
                loan.getSettledOrNot(),
                loan.getInterestAmount()
        );
    }
}
