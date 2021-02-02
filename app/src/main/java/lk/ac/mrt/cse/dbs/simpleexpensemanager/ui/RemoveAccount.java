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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.R;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants.EXPENSE_MANAGER;

/**
 *
 */
public class RemoveAccount extends Fragment implements View.OnClickListener {
    private Button removebutton;
    private TextView accid1;
    private Spinner accountSelector;
    private TextView accid2;
    private TextView accid3;
    private TextView accid4;
    private ExpenseManager currentExpenseManager;

    public static RemoveAccount newInstance(ExpenseManager expenseManager) {
        RemoveAccount rmaccFragment = new RemoveAccount();
        Bundle args = new Bundle();
        args.putSerializable(EXPENSE_MANAGER, expenseManager);
        rmaccFragment.setArguments(args);
        return rmaccFragment;
    }

    public RemoveAccount() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!firstload) {
            currentExpenseManager = (ExpenseManager) getArguments().get(EXPENSE_MANAGER);
            ArrayAdapter<String> adapter =
                    null;
            if (currentExpenseManager != null) {
                adapter = new ArrayAdapter<>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,
                        currentExpenseManager.getAccountNumbersList());
            }
            this.accountSelector.setAdapter(adapter);
        }
    }

    public boolean firstload=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.remove_account, container, false);
        removebutton = (Button) rootView.findViewById(R.id.remove);
        removebutton.setOnClickListener(this);

        accid1=(TextView) rootView.findViewById(R.id.accid1) ;
        accid2=(TextView) rootView.findViewById(R.id.accid2) ;
        accid3=(TextView) rootView.findViewById(R.id.accid3) ;
        accid4=(TextView) rootView.findViewById(R.id.accid4) ;


        accountSelector = (Spinner) rootView.findViewById(R.id.account_selector1);
        accountSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String account = (String) accountSelector.getSelectedItem();
                update(account);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        currentExpenseManager = (ExpenseManager) getArguments().get(EXPENSE_MANAGER);
        ArrayAdapter<String> adapter =
                null;
        if (currentExpenseManager != null) {
            adapter = new ArrayAdapter<>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,
                    currentExpenseManager.getAccountNumbersList());
        }
        accountSelector.setAdapter(adapter);

        this.firstload=false;
        return rootView;
    }

    public void update(String account){
        Account account_holder= currentExpenseManager.getaccount(account);
        accid1.setText(" :- "+account_holder.getAccountNo());
        accid2.setText(" :- "+account_holder.getBankName());
        accid3.setText(" :- "+account_holder.getAccountHolderName());
        accid4.setText(" :- "+account_holder.getBalance()+"");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.remove:
                currentExpenseManager.removeaccount(accountSelector.getSelectedItem().toString());
                ArrayAdapter<String> adapter =
                        null;
                if (currentExpenseManager != null) {
                    adapter = new ArrayAdapter<>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,
                            currentExpenseManager.getAccountNumbersList());
                }
                accountSelector.setAdapter(adapter);

                String account = (String) accountSelector.getSelectedItem();
                update(account);

        }
    }
}
