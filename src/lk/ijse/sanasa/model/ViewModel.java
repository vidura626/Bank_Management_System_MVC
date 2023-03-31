package lk.ijse.sanasa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.sanasa.to.AccountDetails;
import lk.ijse.sanasa.to.Loans;
import lk.ijse.sanasa.to.PendingLoans;
import lk.ijse.sanasa.to.Transaction;
import lk.ijse.sanasa.to.table.TableDetailsViewDeposits;
import lk.ijse.sanasa.to.table.TableDetailsViewInterAccountTransaction;
import lk.ijse.sanasa.to.table.TableDetailsViewLoanInstallment;
import lk.ijse.sanasa.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ViewModel {
    public static ObservableList<TableDetailsViewDeposits> getDeposits() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select d.*,t.AccountID,t.Date,t.Time,t.Type from deposit d,transaction t right join accountdetails a on t.AccountID=a.AccountID where a.State='ACTIVE' group by depositID");
        ObservableList<TableDetailsViewDeposits> list= FXCollections.observableArrayList();
        while (rst.next()){
            list.add(
                    new TableDetailsViewDeposits(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getString(7),
                            rst.getString(8)
                    )
            );
        }
        return list;
    }

    public static ObservableList<TableDetailsViewDeposits> getWithdrawals() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select d.*,t.AccountID,t.Date,t.Time,t.Type from withdrawal d,transaction t right join accountdetails a on t.AccountID=a.AccountID where a.State='ACTIVE' group by WithdrawalID");
        ObservableList<TableDetailsViewDeposits> list= FXCollections.observableArrayList();
        while (rst.next()){
                    list.add(
                            new TableDetailsViewDeposits(
                                    rst.getString(1),
                                    rst.getString(2),
                                    rst.getString(3),
                                    rst.getDouble(4),
                                    rst.getString(5),
                                    rst.getString(6),
                                    rst.getString(7),
                                    rst.getString(8)
                            )
                    );
        }
        return list;
    }

    public static ObservableList<Loans> getLoans() throws SQLException, ClassNotFoundException {
        ObservableList<Loans> list= FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select l.* from loans l right join accountdetails a on l.AccountID = a.AccountID where a.State='ACTIVE' group by l.LoanID");
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
                            rst.getInt(12)
                    )
            );
        }
        return list;
    }

    public static ObservableList<PendingLoans> getPendingLoans() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from pendingloans");
        ObservableList<PendingLoans> list=FXCollections.observableArrayList();
        while (rst.next()) {
            list.add(
                    new PendingLoans(
                            rst.getString(1),
                            rst.getDouble(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getInt(5),
                            rst.getDouble(6)
                    )
            );
        }
        return list;
    }

    public static ObservableList<Loans> getSettledLoans() throws SQLException, ClassNotFoundException {
        ObservableList<Loans> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from loans where SettledOrNot = 'Settled'");
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
                            rst.getInt(12)
                    )
            );
        }
        return list;
    }

    public static ObservableList<TableDetailsViewLoanInstallment> getLoanInstallments() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loaninstallment");
        ObservableList<TableDetailsViewLoanInstallment> list=FXCollections.observableArrayList();
        while (rst.next()){
            ResultSet rst1 = CrudUtil.execute("select * from transaction where TransactionID = ?",rst.getString(2));
            rst1.next();
            list.add(
                    new TableDetailsViewLoanInstallment(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst1.getString(2),
                            rst1.getDouble(3),
                            rst1.getString(4),
                            rst1.getString(5)
                    )
            );
        }
        return list;
    }

    public static ObservableList<TableDetailsViewInterAccountTransaction> getInterAccountTransaction() throws SQLException, ClassNotFoundException {
        ObservableList<TableDetailsViewInterAccountTransaction> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from interaccounttransaction");
        while (rst.next()){
            ResultSet rst1 = CrudUtil.execute("select * from transaction where TransactionID = ?",rst.getString(2));
            list.add(
                    new TableDetailsViewInterAccountTransaction(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getDouble(5),
                            rst.getString(4),
                            rst.getString(5)
                    )
            );
        }
        return list;
    }

    public static ObservableList<Transaction> getDailyTransaction() throws SQLException, ClassNotFoundException {
        ObservableList<Transaction> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from transaction where date = ?", LocalDate.now());
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
        return list;
    }

    public static ObservableList<Transaction> getDailyTransaction(LocalDate date) throws SQLException, ClassNotFoundException {
        ObservableList<Transaction> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from transaction where date = ?", date);
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
        return list;
    }

    public static ObservableList<AccountDetails> getAccounts() throws SQLException, ClassNotFoundException {
        ObservableList<AccountDetails> list=FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.execute("select * from accountdetails where state='ACTIVE'");
        while (rst.next()){
            list.add(
                    new AccountDetails(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getString(7),
                            rst.getString(8),
                            rst.getString(9),
                            rst.getString(10),
                            rst.getString(11)
                    )
            );
        }
        return list;
    }
}
