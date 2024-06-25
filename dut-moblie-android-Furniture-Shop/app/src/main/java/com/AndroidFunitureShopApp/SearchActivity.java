package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.AndroidFunitureShopApp.databinding.ActivitySearchBinding;
import com.AndroidFunitureShopApp.viewmodel.productsAPIService;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.AndroidFunitureShopApp.model.Product.Product;
import com.AndroidFunitureShopApp.model.Product.productsAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Product> newProducts;
    private productsAdapter productssAdapter;
    private productsAPIService apiServices;
    private ActivitySearchBinding binding;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        apiServices = new productsAPIService();
        newProducts = new ArrayList<Product>();
        productssAdapter = new productsAdapter(newProducts);
        binding.rvProductsearch.setAdapter(productssAdapter);

        binding.rvProductsearch.setLayoutManager(new GridLayoutManager(this, 2));
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    newProducts.clear();
                    productssAdapter.notifyDataSetChanged();

                }
                getSearchProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getSearchProducts(String text) {
        newProducts.clear();
        apiServices.searchProduct(text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Product>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Product> products) {
                        Log.d("DEBUG", "Success");
                        for (Product dog : products) {
                            newProducts.add(dog);
                        }
                        productssAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });
    }
}