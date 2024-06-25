package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.AndroidFunitureShopApp.databinding.ActivityRegisterBinding;
import com.AndroidFunitureShopApp.model.Account.AccountAPI;
import com.AndroidFunitureShopApp.model.Account.AccountModel;
import com.AndroidFunitureShopApp.model.Account.UserInfo;
import com.AndroidFunitureShopApp.view.AccountFragment;
import com.AndroidFunitureShopApp.viewmodel.AccountAPIService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    AccountAPIService accountAPIService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FirebaseAuth firebaseAuth;
    private boolean valid = false;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void register() {
        accountAPIService = new AccountAPIService();
        String str_username = binding.etUsername.getText().toString().trim();
        String str_fullname = binding.etName.getText().toString().trim();
        String str_password = binding.etPassword.getText().toString().trim();
        String str_repass = binding.etRepass.getText().toString().trim();
        String str_role = "user";
        String str_imageAva = "";
        String str_defaultAdress = "";
        String str_email = "";
        String str_phone = "";

        if(TextUtils.isEmpty(str_username) && !Patterns.EMAIL_ADDRESS.matcher(str_username).matches()){
            Toast.makeText(getApplicationContext(), "Please enter correct email!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_fullname)){
            Toast.makeText(getApplicationContext(), "Please enter your name!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_password)){
            Toast.makeText(getApplicationContext(), "Please enter your password!", Toast.LENGTH_SHORT).show();
        }   else if(TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(), "Please re_enter your password!", Toast.LENGTH_SHORT).show();
        }   else {
            if(str_password.equals(str_repass)){
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(str_username,str_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
//                                    FirebaseAuth auth = FirebaseAuth.getInstance();

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                if(user != null){
                                                    postData(str_username,str_password,str_role,str_fullname,str_imageAva,str_defaultAdress,str_email,str_phone,user.getUid());
                                                }
                                                Toast.makeText(getApplicationContext(), "Registered successfully, Please check your email for verification!", Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                                } else {
                                    Toast.makeText(getApplicationContext(), "Cannot register or maybe your email already exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
            else {
                Toast.makeText(getApplicationContext(), "Password & Confirm Password do not match!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void postData(String str_username, String str_password , String str_role, String str_fullname, String str_imageAva, String str_defaultAdress, String str_email, String str_phone, String uid){
        compositeDisposable.add(accountAPIService.register(str_username, str_password, str_role, str_fullname, str_imageAva, str_defaultAdress, str_email, str_phone , uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        accountModel -> {
                            if (accountModel.isSuccess()){
                                Toast.makeText(getApplicationContext(), "Register Success!", Toast.LENGTH_SHORT).show();
                                valid = true;
                            }else {
                                Toast.makeText(getApplicationContext(), accountModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}