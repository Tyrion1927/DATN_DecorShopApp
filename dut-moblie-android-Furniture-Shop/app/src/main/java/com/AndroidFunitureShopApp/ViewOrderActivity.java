package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.AndroidFunitureShopApp.databinding.ActivityPaymentBinding;
import com.AndroidFunitureShopApp.databinding.ActivityViewOrderBinding;
import com.AndroidFunitureShopApp.model.Order.OrderAdapter;
import com.AndroidFunitureShopApp.viewmodel.OrderAPIService;
import com.AndroidFunitureShopApp.viewmodel.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewOrderActivity extends AppCompatActivity {
    OrderAPIService orderAPIService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ActivityViewOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOrderBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvViewOrder.setLayoutManager(layoutManager);

        orderAPIService = new OrderAPIService();

        compositeDisposable.add(orderAPIService.viewOrder(Utils.account.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            OrderAdapter adapter = new OrderAdapter(getApplicationContext(), orderModel.getResult());
                            binding.rvViewOrder.setAdapter(adapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}