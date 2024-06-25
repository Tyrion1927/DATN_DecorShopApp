package com.example.adminfunitureshopapp.viewmodel;//package com.AndroidFunitureShopApp.viewmodel;

import android.util.Log;

import com.example.adminfunitureshopapp.model.Product.Product;
import com.example.adminfunitureshopapp.model.Product.productsAPI;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class productsAPIService {

    private static final String BASE_URL = _Constant.baseUrl;
    private productsAPI api;


    public productsAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(productsAPI.class);
    }

    public Single<List<Product>> getProducts() {
        return api.getProducts();
    }

    public Single<Integer> addProduct(String name, int quantity, String imageUrl, int originalPrice, int discount, String detail, String type, String categoryId) {
        return api.addProduct(name, quantity, imageUrl, originalPrice, discount, detail, type, categoryId);
    }

    public Single<Integer> updateProduct(int id, String name, int quantity, String imageUrl, int originalPrice, int discount, String detail, String type)
    {
//        Log.d("API", "id: " + id);
//        Log.d("API", "name: " + name);
//        Log.d("API", "quantity: " + quantity);
//        Log.d("API", "imageUrl: " + imageUrl);
//        Log.d("API", "originalPrice: " + originalPrice);
//        Log.d("API", "discount: " + discount);
//        Log.d("API", "detail: " + detail);
//        Log.d("API", "type: " + type);
        return api.updateProduct(id, name, quantity, imageUrl, originalPrice, discount, detail, type);
    }

    public Single<Integer> deleteProduct(int productId) {
        Log.d("DELETE", String.valueOf(productId));
        return api.deleteProduct(productId);
    }
}