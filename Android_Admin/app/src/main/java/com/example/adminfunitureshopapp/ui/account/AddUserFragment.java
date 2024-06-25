package com.example.adminfunitureshopapp.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentAccountBinding;
import com.example.adminfunitureshopapp.databinding.FragmentAddUserBinding;
import com.example.adminfunitureshopapp.viewmodel.AccountAPIService;
import com.example.adminfunitureshopapp.viewmodel.CategoriesAPIService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUserFragment extends Fragment {
    private FragmentAddUserBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AccountAPIService accountAPIService;
    // TODO: Rename parameter arguments, choose names that match


    public AddUserFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAcc();
            }
        });

        binding.btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFrag();
            }
        });
    }

    private void addAcc() {
        accountAPIService = new AccountAPIService();
        String str_username = binding.etNewUserUsername.getText().toString().trim();
        String str_fullname = binding.etNewUserFullname.getText().toString().trim();
        String str_password = binding.etNewUserPassword.getText().toString().trim();
        String str_role = "";
        if (binding.radioAdmin.isChecked()) str_role = "admin";
            else if(binding.radioUser.isChecked()) str_role = "user";
        String str_imageAva = "";
        String str_defaultAdress = binding.etNewUserAddress.getText().toString().trim();
        String str_email = binding.etNewUserEmail.getText().toString().trim();
        String str_phone = binding.etNewUserPhone.getText().toString().trim();

        if(TextUtils.isEmpty(str_username)){
            Toast.makeText(getContext(), "Please enter your username!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_fullname)){
            Toast.makeText(getContext(), "Please enter your name!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_password)){
            Toast.makeText(getContext(), "Please enter your password!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_role)){
            Toast.makeText(getContext(), "Please check access level account!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_role)){
            Toast.makeText(getContext(), "Please check access level account!", Toast.LENGTH_SHORT).show();
        }   else {
            compositeDisposable.add(accountAPIService.register(str_username, str_password, str_role, str_fullname, str_imageAva, str_defaultAdress, str_email, str_phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            accountModel -> {
                                if (accountModel.isSuccess()){
                                    Toast.makeText(getContext(), "Add Success!", Toast.LENGTH_SHORT).show();
                                    removeFrag();
                                }else {
                                    Toast.makeText(getContext(), accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    private void removeFrag(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, new AccountFragment())
                .commit();
    }
}