package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.AndroidFunitureShopApp.databinding.ActivityForgotPassBinding;
import com.AndroidFunitureShopApp.databinding.ActivitySuccessRepassBinding;

public class SuccessRepassActivity extends AppCompatActivity {
    private ActivitySuccessRepassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessRepassBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.successMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessRepassActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}