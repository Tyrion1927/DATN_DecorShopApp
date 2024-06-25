package com.example.adminfunitureshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.adminfunitureshopapp.databinding.ActivityLoginBinding;
import com.example.adminfunitureshopapp.model.Account.Account;
import com.example.adminfunitureshopapp.model.Account.UserInfo;
import com.example.adminfunitureshopapp.ui.product.ProductFragment;
import com.example.adminfunitureshopapp.viewmodel.AccountAPIService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String id;
    private AccountAPIService accountAPIService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        binding.extRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        String username = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        accountAPIService = new AccountAPIService();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Please, enter your username!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please, enter your password!", Toast.LENGTH_SHORT).show();
        }   else {
            compositeDisposable.add(accountAPIService.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            accountModel -> {
                                if (accountModel.isSuccess()){
                                    Account account = accountModel.getResult().get(0);
                                    String role = account.getRole().toString();
                                    Log.d("DEBUG", "Account");
                                    UserInfo.userInfo = account;

                                    if(role.equals("admin")){
                                        Log.d("DEBUG","user");
                                        Intent intent = new Intent(this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    Toast.makeText(getApplicationContext(), accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}