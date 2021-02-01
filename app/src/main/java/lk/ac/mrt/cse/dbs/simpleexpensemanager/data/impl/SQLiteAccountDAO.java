package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.SQLiteHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class SQLiteAccountDAO implements AccountDAO {
    private Context context;
    public SQLiteAccountDAO(Context context){
        this.context=context;
    }
    @Override
    public List<String> getAccountNumbersList() {
        try{
            SQLiteOpenHelper helper=new SQLiteHelper(this.context);
            SQLiteDatabase db=helper.getReadableDatabase();
            Cursor res =  db.rawQuery( "select account_no from accounts",null);
            List<String> list_account=new ArrayList<>();
            res.moveToFirst();
            while(!res.isAfterLast()){
                list_account.add(res.getString(res.getColumnIndex("account_no")));
                res.moveToNext();
            }
            return list_account;
        }catch (Exception ex){
            List<String> list_account=new ArrayList<>();
            list_account.add(ex.toString());
            System.out.println(ex.toString());
            return  list_account;
        }
    }

    @Override
    public List<Account> getAccountsList() {
        try{
            SQLiteOpenHelper helper=new SQLiteHelper(this.context);
            SQLiteDatabase db=helper.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from accounts",null);
            List<Account> list_account=new ArrayList<>();
            res.moveToFirst();
            while(!res.isAfterLast()){

                Account account=new Account(null,null,null,0);

                account.setAccountNo(res.getString(res.getColumnIndex("account_no")));
                account.setBankName(res.getString(res.getColumnIndex("bank_name")));
                account.setAccountHolderName(res.getString(res.getColumnIndex("account_holder")));
                account.setBalance(Double.parseDouble(res.getString(res.getColumnIndex("balance"))));

                list_account.add(account);
                res.moveToNext();
            }
            return  list_account;
        }catch (Exception ex){
            List<Account> list_account=new ArrayList<>();
            list_account.add(new Account(null,ex.toString(),null,00));
            System.out.println(ex.toString());
            return  list_account;
        }

    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {
        try {
            SQLiteOpenHelper helper = new SQLiteHelper(this.context);
            SQLiteDatabase db = helper.getWritableDatabase();
            System.out.println(account.getAccountNo());
            SQLiteStatement stmt = db.compileStatement("INSERT INTO accounts VALUES (?,?,?,?)");
            stmt.bindString(1, account.getAccountNo());
            stmt.bindString(2, account.getBankName());
            stmt.bindString(3, account.getAccountHolderName());
            stmt.bindDouble(4, account.getBalance());
            stmt.executeInsert();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount,SQLiteDatabase db) throws InvalidAccountException {

        try {
            SQLiteStatement stmt = null;
            switch (expenseType) {
                case EXPENSE:
                    stmt = db.compileStatement("UPDATE accounts SET balance = balance - ? where account_no=?;");
                    stmt.bindString(2,accountNo);
                    stmt.bindDouble(1, amount);

                    break;
                case INCOME:
                    stmt = db.compileStatement("UPDATE accounts SET balance = balance + ? where account_no=?;");
                    stmt.bindString(2,accountNo);
                    stmt.bindDouble(1, amount);

                    break;
            }
            stmt.execute();

        }catch (Exception ex){
            throw ex;
        }


    }
}
