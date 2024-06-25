package com.example.adminfunitureshopapp.viewmodel;

import android.util.Log;

import com.example.adminfunitureshopapp.model.Order.Order;
import com.example.adminfunitureshopapp.model.Order.OrdersAPI;
import com.example.adminfunitureshopapp.model.Product.Product;
import com.example.adminfunitureshopapp.model.Product.productsAPI;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersAPIService {
    private static final String BASE_URL = _Constant.baseUrl;
    private OrdersAPI api;


    public OrdersAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(OrdersAPI.class);
    }

    public Single<List<Order>> getOrders() {
        return api.getOrders();
    }
    public Single<Integer> updateOrder(int id, int status)
    {
        return api.updateOrder(id, status);
    }
    public Single<Integer> CancelOrder(int id) {
        Log.d("id", String.valueOf(id));
        return api.CancelOrder(id);
    }
}
