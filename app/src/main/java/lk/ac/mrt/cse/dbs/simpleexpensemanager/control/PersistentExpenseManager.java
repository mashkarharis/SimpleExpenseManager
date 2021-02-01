package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SQLiteAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SQLiteTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistentExpenseManager extends ExpenseManager {
    public PersistentExpenseManager(Context context) {
        super(context);
        setup(context);
    }

    @Override
    public void setup() throws ExpenseManagerException {

    }

    @Override
    public void setup(Context context) {
        /*** Begin generating dummy data for In-Memory implementation ***/

        TransactionDAO inMemoryTransactionDAO = new SQLiteTransactionDAO(context);
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO SQLiteAccountDAO = new SQLiteAccountDAO(context);
        setAccountsDAO(SQLiteAccountDAO);

        // dummy data
        //Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        //Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
       // getAccountsDAO().addAccount(dummyAcct1);
       // getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }

}
