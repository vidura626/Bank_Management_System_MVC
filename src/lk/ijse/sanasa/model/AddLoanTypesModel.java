package lk.ijse.sanasa.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.sanasa.to.LoanDetails;
import lk.ijse.sanasa.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddLoanTypesModel {
    public static boolean add(LoanDetails loanDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into loandetails values (?,?,?,?)",
                loanDetails.getLoanTypeID(),
                loanDetails.getDescription(),
                loanDetails.getInterest(),
                loanDetails.getAmounts()
        );
    }

    public static boolean check(String loanTypeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loandetails where LoanTypeID = ?", loanTypeID);
        return rst.next();
    }

    public static boolean delete(String loanTypeID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from loandetails where LoanTypeID = ?",loanTypeID);
    }

    public static ObservableList<LoanDetails> getLoanDetailsList() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loandetails");
        ObservableList<LoanDetails> list = FXCollections.observableArrayList();
        while (rst.next()) {
            list.add(
                    new LoanDetails(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3),
                            rst.getString(4)
                    )
            );
        }
        return list.size() > 0 ? list : null;
    }

    public static LoanDetails search(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loandetails where loanTypeId=?", text);
        rst.next();
        return new LoanDetails(
                rst.getString(1),
                rst.getString(2),
                rst.getDouble(3),
                rst.getString(4)
        );
    }

    public static boolean   isFind(String text) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from loandetails where loanTypeId=?", text);
        return rst.next();
    }

    public static boolean update(LoanDetails loanDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update loandetails set Description = ?, Interest = ?, Amounts = ? where LoanTypeID = ?",
                loanDetails.getDescription(),
                loanDetails.getInterest(),
                loanDetails.getAmounts(),
                loanDetails.getLoanTypeID()
                );
    }
}
