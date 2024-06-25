package com.example.adminfunitureshopapp.ui.product;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.adminfunitureshopapp.R;
import com.example.adminfunitureshopapp.databinding.FragmentUpdateProductBinding;
import com.example.adminfunitureshopapp.viewmodel.productsAPIService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateProductFragment extends DialogFragment {
    private int productId;
    private FragmentUpdateProductBinding binding;
    private String productName;
    private int productQuantity;
    private String productImageUrl;
    private int productOriginalPrice;
    private int productDiscount;
    private int productPrice;
    private String productDetail;
    private String productType;
    private productsAPIService APIService;

    @Override
    public void onStart() {
        super.onStart();

        // Set the width of the dialog to match the parent
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public UpdateProductFragment(int id, String productName, int productQuantity, String productImageUrl,
                                 int productOriginalPrice, int productDiscount, int productPrice,
                                 String productDetail, String productType) {
        this.productId = id;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productImageUrl = productImageUrl;
        this.productOriginalPrice = productOriginalPrice;
        this.productDiscount = productDiscount;
        this.productPrice = productPrice;
        this.productDetail = productDetail;
        this.productType = productType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentUpdateProductBinding.inflate(inflater, container, false);

        // Set the values of the product to the views using data binding
        binding.edittextName.setText(productName);
        binding.edittextQuantity.setText(String.valueOf(productQuantity));
        binding.edittextImageurl.setText(productImageUrl);
        binding.edittextOriginalprice.setText(String.valueOf(productOriginalPrice));
        binding.edittextDiscount.setText(String.valueOf(productDiscount));
        binding.edittextPrice.setText(String.valueOf(productPrice));
        binding.edittextDetail.setText(productDetail);
        binding.edittextType.setText(productType);

        binding.edittextOriginalprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePrice();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.edittextDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePrice();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return binding.getRoot();
    }

    private void calculatePrice() {
        if (TextUtils.isEmpty(binding.edittextOriginalprice.getText().toString())| TextUtils.isEmpty(binding.edittextDiscount.getText().toString()))
            binding.edittextPrice.setText("");
        else{
        int originalPrice = Integer.parseInt(binding.edittextOriginalprice.getText().toString());
        int discount = Integer.parseInt(binding.edittextDiscount.getText().toString());
        int price = originalPrice - (originalPrice * discount / 100);
        binding.edittextPrice.setText(String.valueOf(price));}
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị của trường edittextName
                int id = productId;
                Log.d("id", String.valueOf(productId));
                String name = binding.edittextName.getText().toString();
                Log.d("name", name);
                String quantity = binding.edittextQuantity.getText().toString();
                Log.d("quantity", quantity);
                String imageUrl = binding.edittextImageurl.getText().toString();
                Log.d("imageUrl", imageUrl);
                String originalPrice = binding.edittextOriginalprice.getText().toString();
                Log.d("originalPrice", originalPrice);
                String discount = binding.edittextDiscount.getText().toString();
                Log.d("discount", discount);
                String detail = binding.edittextDetail.getText().toString();
                Log.d("detail", detail);
                String type = binding.edittextType.getText().toString();
                Log.d("type", type);
                updateProduct(productId, name, quantity, imageUrl, originalPrice, discount, detail, type);
            }
        });
    }

    private void updateProduct(int id, String name, String quantity, String imageUrl, String originalPrice, String discount, String detail, String type) {
        APIService = new productsAPIService();
        APIService.updateProduct(id, name, Integer.parseInt(quantity), imageUrl, Integer.parseInt(originalPrice), Integer.parseInt(discount), detail, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Integer productId) {
                        Toast.makeText(getActivity().getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                        dismiss();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new ProductFragment())
                                .commit();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                        dismiss();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, new ProductFragment())
                                .commit();

                    }
                });

    }

}
