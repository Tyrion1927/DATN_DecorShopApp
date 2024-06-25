package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.AndroidFunitureShopApp.databinding.ActivityProductByCategoryBinding;
import com.AndroidFunitureShopApp.model.Product.Product;
import com.AndroidFunitureShopApp.model.Product.productsAdapter;
import com.AndroidFunitureShopApp.viewmodel.productsAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductByCategory extends AppCompatActivity {
    private ActivityProductByCategoryBinding binding;
    private productsAPIService apiServices;
    private ArrayList<Product> newProducts;
    private productsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);

        binding = ActivityProductByCategoryBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        Bundle bundle = getIntent().getExtras();

        int category_id = -1;
        if (bundle != null) {
            category_id = bundle.getInt("category_id");
        }

        newProducts = new ArrayList<Product>();
        productsAdapter = new productsAdapter(newProducts);
        binding.rvProductByCategory.setAdapter(productsAdapter);
        binding.rvProductByCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        apiServices = new productsAPIService();
        apiServices.getProductsByCategories(category_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Product>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Product> products) {
                        Log.d("DEBUG", "Success");
                        for (Product dog : products) {
                            newProducts.add(dog);
                        }
                        productsAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });
    }
}