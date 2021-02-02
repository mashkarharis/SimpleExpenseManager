package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "180048D_V2.db";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table IF NOT EXISTS accounts " +
                        "(account_no TEXT primary key," +
                        "bank_name TEXT NOT NULL," +
                        "account_holder TEXT NOT NULL," +
                        "balance REAL NOT NULL)"
        );

        db.execSQL(
                "create table IF NOT EXISTS transactions " +
                        "(date TEXT NOT NULL," +
                        "account_no TEXT NOT NULL," +
                        "expense_type TEXT NOT NULL," +
                        "amount REAL NOT NULL," +
                        "FOREIGN KEY (account_no) REFERENCES accounts(account_no) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "CHECK (expense_type IN ('EXPENSE','INCOME') )" +
                        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }




}
