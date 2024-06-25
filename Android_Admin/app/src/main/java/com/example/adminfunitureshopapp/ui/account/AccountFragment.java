package com.example.adminfunitureshopapp.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentAccountBinding;
import com.example.adminfunitureshopapp.model.Account.Account;
import com.example.adminfunitureshopapp.model.Account.AccountAdapter;
import com.example.adminfunitureshopapp.ui.product.AddProductFragment;
import com.example.adminfunitureshopapp.viewmodel.AccountAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private ArrayList<Account> accounts;
    private AccountAdapter accountAdapter;
    private AccountAPIService accountAPIService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    public AccountFragment(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accounts = new ArrayList<Account>();
        accountAdapter = new AccountAdapter(accounts, getActivity());
        binding.rvAccounts.setAdapter(accountAdapter);
        binding.rvAccounts.setLayoutManager(new LinearLayoutManager(getContext()));

        accountAPIService = new AccountAPIService();
        accountAPIService.getAccounts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Account>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Account> Accounts) {
                        Log.d("DEBUG", "Success");
                        for (Account account : Accounts) {
                            accounts.add(account);
                            Log.d("Check: ",   account.getUsername());
                        }
                        accountAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });

        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khởi tạo FragmentManager
                //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                // Create a new instance of the AddProductFragment
                AddUserFragment addUserFragment = new AddUserFragment();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                // Replace the current fragment with the new AddProductFragment
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, addUserFragment);

                // Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
