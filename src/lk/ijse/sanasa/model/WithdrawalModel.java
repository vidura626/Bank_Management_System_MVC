package lk.ijse.sanasa.model;

import lk.ijse.sanasa.to.Withdrawal;
import lk.ijse.sanasa.util.CrudUtil;

import java.sql.SQLException;

public class WithdrawalModel {
    public static boolean makeWithdraw(Withdrawal deposit) throws SQLException, ClassNotFoundException {
            return CrudUtil.execute(
                    "insert into withdrawal values (?,?,?,?)",
                    deposit.getWithdrawalID(),
                    deposit.getTransactionID(),
                    deposit.getDepositTypeAccountID(),
                    deposit.getAmount()
            );

    }
}
