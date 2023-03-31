package lk.ijse.sanasa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.sanasa.to.LoanDetails;
import lk.ijse.sanasa.to.PendingLoans;
import lk.ijse.sanasa.to.RejectedLoans;
import lk.ijse.sanasa.to.table.TableDetailsRejectLoans;
import lk.ijse.sanasa.util.CrudUtil;
import lk.ijse.sanasa.util.ManageController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestLoanModel {
    public static ObservableList<String> getLoanTypes() throws SQLException, ClassNotFoundException {
        ObservableList<String> types= FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from loandetails");
        while (rst.next()){
            types.add(rst.getString(1) + " " + rst.getString(2));
        }
        return types.size() > 0 ? types : null;
    }

    public static boolean makeRequestALoan(PendingLoans pendingLoans) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into pendingloans values (?,?,?,?,?,?)",
                pendingLoans.getPendingLoansID(),
                pendingLoans.getAmount(),
                pendingLoans.getLoanTypeID(),
                pendingLoans.getAccountID(),
                pendingLoans.getInstallment(),
                pendingLoans.getInstallmentAmount()
        );
    }

    public static LoanDetails getLoanDetails(String loanTypeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loandetails where LoanTypeID = ?", loanTypeID);
        return rst.next() ?
                new LoanDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)
            ) : null;
    }

    public static boolean isReject(ObservableList<TableDetailsRejectLoans> items) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            PendingLoans pendingLoan = ApproveLoanModel.getPendingLoan(items.get(i).getPendingLoanID());
            boolean reject = isReject(new RejectedLoans(
                            ManageController.generateLastId(
                                    "RejLoanID",
                                    "rejectedloans",
                                    "RJ"
                            ),
                            pendingLoan.getAmount(),
                            pendingLoan.getLoanTypeID(),
                            pendingLoan.getAccountID(),
                            items.get(i).getReason()
                    )
            );
            if (!reject) {
                return false;
            }
        }
        return true;
    }

    public static boolean isReject(RejectedLoans loan) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into rejectedloans values (?,?,?,?,?)",
                loan.getRejLoanID(),
                loan.getAmount(),
                loan.getLoanTypeID(),
                loan.getAccountID(),
                loan.getReason()
        );
    }
}
