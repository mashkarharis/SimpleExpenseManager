package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.SQLiteManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class SQLiteTransactionDAO implements TransactionDAO {
    private Context context;
    public SQLiteTransactionDAO(Context context){
        this.context=context;
    }
    @Override
    public void logTransaction(Transaction transaction,SQLiteDatabase db) {
        try {

            System.out.println(transaction.getExpenseType());
            SQLiteStatement stmt = db.compileStatement("INSERT INTO transactions VALUES (?,?,?,?)");
            stmt.bindString(1, transaction.getDate().toString());
            stmt.bindString(2, transaction.getAccountNo());
            stmt.bindString(3, transaction.getExpenseType().toString());
            stmt.bindDouble(4, transaction.getAmount());
            stmt.executeInsert();
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        try{
            SQLiteOpenHelper helper=new SQLiteManager(this.context);
            SQLiteDatabase db=helper.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from transactions",null);
            List<Transaction> list_transaction=new ArrayList<>();
            res.moveToFirst();
            while(!res.isAfterLast()){

                Transaction transaction=new Transaction(null,null,null,0);

                transaction.setAccountNo(res.getString(res.getColumnIndex("account_no")));
                transaction.setDate(res.getString(res.getColumnIndex("date")));
                ExpenseType expenseType=ExpenseType.valueOf(res.getString(res.getColumnIndex("expense_type")));
                transaction.setExpenseType(expenseType);
                transaction.setAmount(Double.parseDouble(res.getString(res.getColumnIndex("amount"))));

                list_transaction.add(transaction);
                res.moveToNext();
            }
            return  list_transaction;
        }catch (Exception ex){
            List<Transaction> list_transaction=new ArrayList<>();
            list_transaction.add(new Transaction(null,ex.toString(),null,00));
            System.out.println(ex.toString());
            return  list_transaction;
        }

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
            List<Transaction> list_transaction=getAllTransactionLogs();
            int size = list_transaction.size();
            if (size <= limit) {
                return list_transaction;
            }
            // return the last <code>limit</code> number of transaction logs
            return list_transaction.subList(size - limit, size);
    }
}
