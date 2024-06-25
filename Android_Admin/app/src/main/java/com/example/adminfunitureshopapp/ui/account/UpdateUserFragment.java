package com.example.adminfunitureshopapp.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.RoomSQLiteQuery;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentAddUserBinding;
import com.example.adminfunitureshopapp.databinding.FragmentUpdateUserBinding;
import com.example.adminfunitureshopapp.ui.category.CategoryFragment;
import com.example.adminfunitureshopapp.viewmodel.AccountAPIService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UpdateUserFragment extends DialogFragment {
    private FragmentUpdateUserBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AccountAPIService accountAPIService;

    private int User_id;
    private String username;
    private String pass;
    private String fullname;
    private String address;
    private String email;
    private String phone;
    private String role;

    // TODO: Rename and change types and number of parameters
    public UpdateUserFragment (int id,String username, String pass, String fullname, String address, String email, String phone, String role) {
        this.User_id = id;
        this.username = username;
        this.pass = pass;
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //set value
        binding.etUpdateUserUsername.setText(username);
        binding.etUpdateUserPassword.setText(pass);
        binding.etUpdateUserFullname.setText(fullname);
        binding.etUpdateUserAddress.setText(address);
        binding.etUpdateUserEmail.setText(email);
        binding.etUpdateUserPhone.setText(phone);
        if(role.equals("user")) binding.radioUpdateUserUser.setChecked(true);
        else if(role.equals("admin")) binding.radioUpdateUserAdmin.setChecked(true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnUpdateUserSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        accountAPIService = new AccountAPIService();
        int id = User_id;
        String str_username = binding.etUpdateUserUsername.getText().toString().trim();
        String str_fullname = binding.etUpdateUserFullname.getText().toString().trim();
        String str_password = binding.etUpdateUserPassword.getText().toString().trim();
        String str_role = "";
        if (binding.radioUpdateUserAdmin.isChecked()) str_role = "admin";
        else if(binding.radioUpdateUserUser.isChecked()) str_role = "user";
        String str_imageAva = "";
        String str_defaultAdress = binding.etUpdateUserAddress.getText().toString().trim();
        String str_email = binding.etUpdateUserEmail.getText().toString().trim();
        String str_phone = binding.etUpdateUserPhone.getText().toString().trim();

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
            compositeDisposable.add(accountAPIService.UpdateUser(id, str_password, str_fullname, str_imageAva, str_defaultAdress, str_email, str_phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            accountModel -> {
                                if (accountModel.isSuccess()){
                                    Toast.makeText(getContext(), "Update Account User Success!", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                    removeFrag();
                                }else {
                                    Toast.makeText(getContext(), accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    dismiss();
                                    removeFrag();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                dismiss();
                                removeFrag();
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