package lk.ijse.sanasa.model;

import lk.ijse.sanasa.to.InterAccountTransaction;
import lk.ijse.sanasa.util.CrudUtil;

import java.sql.SQLException;

public class InterAccountTransactionModel {
    public static boolean makeInterAccountTransaction(InterAccountTransaction interAccountTransaction) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into interaccounttransaction values (?,?,?,?,?)",
                interAccountTransaction.getInterAccountTransactionID(),
                interAccountTransaction.getTransactionID(),
                interAccountTransaction.getAccount01ID(),
                interAccountTransaction.getAccount02ID(),
                interAccountTransaction.getAmount()
                );
    }
}
