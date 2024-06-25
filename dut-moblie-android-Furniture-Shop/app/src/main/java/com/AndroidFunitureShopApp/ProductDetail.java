package com.AndroidFunitureShopApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.AndroidFunitureShopApp.databinding.ActivityProductDetailBinding;
import com.AndroidFunitureShopApp.model.Product.Product;
import com.AndroidFunitureShopApp.viewmodel.Utils;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {
    private ActivityProductDetailBinding binding;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
        }

        binding.tvProductDetailName.setText(product.getName().toString());
        binding.tvProductDetailPrice.setText("$" + product.getPrice());
        binding.tvProductDetail.setText(product.getDetail());
        Picasso.get().load(product.getImageUrl().toString()).into(binding.imgProductDetail);

        binding.btnAddToCarProdudctDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.addToCart(product);
            }
        });
    }


}