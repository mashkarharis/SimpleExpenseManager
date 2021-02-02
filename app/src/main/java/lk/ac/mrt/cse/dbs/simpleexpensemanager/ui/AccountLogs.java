/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.R;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants.EXPENSE_MANAGER;

/**
 *
 */
public class AccountLogs extends Fragment {
    private ExpenseManager currentExpenseManager;
    public boolean firstload=true;
    TableLayout linflater;
    View vgroup;

    public static AccountLogs newInstance(ExpenseManager expenseManager) {
        AccountLogs AccountLogsFragment = new AccountLogs();
        Bundle args = new Bundle();
        args.putSerializable(EXPENSE_MANAGER, expenseManager);
        AccountLogsFragment.setArguments(args);
        return AccountLogsFragment;
    }

    public AccountLogs() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_accounts, container, false);
        TableLayout logsTableLayout = (TableLayout) rootView.findViewById(R.id.logs_table1);
        TableRow tableRowHeader = (TableRow) rootView.findViewById(R.id.logs_table_header1);

        currentExpenseManager = (ExpenseManager) getArguments().get(EXPENSE_MANAGER);
        List<Account> accountList = new ArrayList<>();
        if (currentExpenseManager != null) {
            accountList = currentExpenseManager.getAccountsList();
        }
        this.linflater=logsTableLayout;
        this.vgroup=rootView;

        generateAccountsTable(rootView, logsTableLayout, accountList);
        this.firstload=false;
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!firstload) {
            currentExpenseManager = (ExpenseManager) getArguments().get(EXPENSE_MANAGER);
            List<Account> accountList = new ArrayList<>();
            if (currentExpenseManager != null) {
                accountList = currentExpenseManager.getAccountsList();
            }
            this.linflater.removeAllViews();
            List<Account> acc=new ArrayList<>();

            acc.add(new Account("Account No","Bank Name","Account Holder",000000));
            for (Account a:accountList
                 ) {
                acc.add(a);
            }
            generateAccountsTable(this.vgroup, this.linflater, acc);


        }
    }

    private void generateAccountsTable(View rootView, TableLayout logsTableLayout,
                                           List<Account> accountList) {
        for (Account account : accountList) {
            TableRow tr = new TableRow(rootView.getContext());
            TextView lDateVal = new TextView(rootView.getContext());

            //SimpleDateFormat sdf = new SimpleDateFormat(getActivity().getString(R.string.config_date_log_pattern));
           // String formattedDate = sdf.format(transaction.getDate());
            lDateVal.setText(account.getAccountNo());
            tr.addView(lDateVal);

            TextView lAccountNoVal = new TextView(rootView.getContext());
            lAccountNoVal.setText(account.getBankName());
            tr.addView(lAccountNoVal);

            TextView lExpenseTypeVal = new TextView(rootView.getContext());
            lExpenseTypeVal.setText(account.getAccountHolderName());
            tr.addView(lExpenseTypeVal);

            TextView lAmountVal = new TextView(rootView.getContext());
            lAmountVal.setText(String.valueOf(account.getBalance()));
            tr.addView(lAmountVal);

            logsTableLayout.addView(tr);
        }
    }
}
