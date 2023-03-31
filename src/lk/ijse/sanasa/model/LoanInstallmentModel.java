package lk.ijse.sanasa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.sanasa.to.LoanDetails;
import lk.ijse.sanasa.to.LoanInstallment;
import lk.ijse.sanasa.to.Loans;
import lk.ijse.sanasa.to.Transaction;
import lk.ijse.sanasa.to.table.TableDetailsLoanInstallmentTbl01;
import lk.ijse.sanasa.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

public class LoanInstallmentModel {
    public static ObservableList<Loans> getRelatedLoans(String accountNumber) throws SQLException, ClassNotFoundException {
        ObservableList<Loans> list= FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select l.* from loans l right join accountdetails a on a.AccountID = l.AccountID where a.AccountID=? AND a.State='ACTIVE'", accountNumber);
        while (rst.next()){
            list.add(new Loans(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getInt(8),
                    rst.getDouble(9),
                    rst.getInt(10),
                    rst.getString(11),
                    rst.getDouble(12)
                    )
            );
        }
        return list.size() > 0 ? list : null;
    }

    public static ObservableList<TableDetailsLoanInstallmentTbl01> getLoanInstallmentsForAccount(String accountNumber) throws SQLException, ClassNotFoundException {
        ResultSet loanIDRst = CrudUtil.execute("SELECT sanasa.loaninstallment.`LoanID`, sanasa.transaction.`Amount`, sanasa.transaction.`Date` FROM sanasa.loaninstallment INNER JOIN sanasa.transaction ON sanasa.loaninstallment.`TransactionID` = sanasa.transaction.`TransactionID` INNER JOIN sanasa.loans ON sanasa.loaninstallment.`LoanID` = sanasa.loans.`LoanID` INNER JOIN sanasa.accountdetails ON sanasa.loans.`AccountID` = sanasa.accountdetails.`AccountID` where loans.AccountID =? And State='ACTIVE'", accountNumber);
        ObservableList<TableDetailsLoanInstallmentTbl01> list =FXCollections.observableArrayList();
        while (loanIDRst.next()){
            list.add(
                    new TableDetailsLoanInstallmentTbl01(
                            loanIDRst.getString(1),
                            loanIDRst.getDouble(2),
                            loanIDRst.getString(3)
                    )
            );
        }
        return list;
    }

    private static ObservableList<LoanInstallment> getloanInstallments() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loaninstallment");
        ObservableList<LoanInstallment> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new LoanInstallment(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3)
                    )
            );
        }
        return list.size() > 0 ? list : null;
    }

    public static ObservableList<LoanInstallment> getLoanInstallmentsForAccountAndLoanType(String loadID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loaninstallment where loanID = ?", loadID);
        ObservableList<LoanInstallment> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new LoanInstallment(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3)
                    )
            );
        }
        return list.size() > 0 ? list : null;
    }

    public static ObservableList<Transaction> getTransactionIDs(String accountNumber) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from transaction where AccountID = ?", accountNumber);
        ObservableList<Transaction> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new Transaction(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6)
                    )
            );
        }
        return list.size() > 0 ? list : null;
    }

    public static Transaction getTransactionDetails(String transactionID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from transaction where transactionID = ?", transactionID);
        if(rst.next());
        return new Transaction(
                rst.getString(1),
                rst.getString(2),
                rst.getDouble(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6)
        );
    }

    public static Loans getLoanDetails(String loanID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loans where loanID = ? ", loanID);
        if(rst.next()){
            return new Loans(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getInt(8),
                    rst.getDouble(9),
                    rst.getInt(10),
                    rst.getString(11),
                    rst.getDouble(12)
            );
        }
        return null;
    }

    public static Month getLastInstallmentMonth(String loanID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select t.Date from transaction t right join loaninstallment l on t.TransactionID = l.TransactionID right join loans s on l.LoanID = s.LoanID where l.LoanID=? order by l.LoanInstallmentID desc limit 1", loanID);
        if(rst.next()){
            return rst.getDate(1).toLocalDate().getMonth().plus(1);
        }
        return null;
    }

    public static LocalDate getLastInstallmentDate(String loanID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select transactionID from loaninstallment where loanID = ? order by loanInstallmentID desc limit 1", loanID);
        if(rst.next()){
            Transaction transactionDetails = getTransactionDetails(rst.getString(1));
            LocalDate parse = LocalDate.parse(transactionDetails.getDate());
            return parse;
        }
        return null;
    }

    public static boolean makeLoanInstallment(LoanInstallment loanInstallment) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into loaninstallment values (?,?,?)",loanInstallment.getLoanInstallmentID(),loanInstallment.getTransactionID(),loanInstallment.getLoanID());
    }

    public static ObservableList<Loans> getLoanDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loans");
        ObservableList<Loans> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new Loans(
                            rst.getString(1),
                            rst.getDouble(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getInt(5),
                            rst.getDouble(6),
                            rst.getString(7),
                            rst.getInt(8),
                            rst.getDouble(9),
                            rst.getInt(10),
                            rst.getString(11),
                            rst.getDouble(12)
                    )
            );
        }
        return list;
    }

    public static ObservableList<LoanDetails> getLoanTypes() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loandetails");
        ObservableList<LoanDetails> list=FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new LoanDetails(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3),
                            rst.getString(4)
                    )
            );
        }
        return list;
    }

    public static boolean setInterest(String loanID, double interest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update loans set interestAmount = ? where loanID = ?",interest,loanID);
    }

    public static boolean setLoanBalance(String loanID, double balance) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update loans set RemainingLoanAmount = ? where loanID = ?",balance,loanID);
    }

    public static boolean setLoanBalancePlus(String loanID, double balance) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update loans set RemainingLoanAmount = RemainingLoanAmount + ? where loanID = ?",balance,loanID);
    }

    public static int getInstallmentsCounts(String loanID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loaninstallment where loanID = ?", loanID);
        int count=0;
        while(rst.next()){
            count++;
        }
        return count;
    }

    public static ObservableList<TableDetailsLoanInstallmentTbl01> getAccountLoanInstallments(String accountID, String loanID) throws SQLException, ClassNotFoundException {
        ObservableList<TableDetailsLoanInstallmentTbl01> ob= FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("SELECT sanasa.loaninstallment.`LoanID`, sanasa.transaction.`Amount`, sanasa.transaction.`Date` FROM sanasa.loaninstallment INNER JOIN sanasa.transaction ON sanasa.loaninstallment.`TransactionID` = sanasa.transaction.`TransactionID` INNER JOIN sanasa.loans ON sanasa.loaninstallment.`LoanID` = sanasa.loans.`LoanID` INNER JOIN sanasa.accountdetails ON sanasa.loans.`AccountID` = sanasa.accountdetails.`AccountID` where loans.AccountID =? AND State='ACTIVE' AND loans.LoanID=?", accountID, loanID);
        while (rst.next()){
            ob.add(
                    new TableDetailsLoanInstallmentTbl01(
                            rst.getString(1),
                            rst.getDouble(2),
                            rst.getString(3)
                    )
            );
        }
        return ob;
    }
}
