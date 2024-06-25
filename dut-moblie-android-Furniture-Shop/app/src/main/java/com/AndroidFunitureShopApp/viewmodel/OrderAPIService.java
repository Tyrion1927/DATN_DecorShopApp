package com.AndroidFunitureShopApp.viewmodel;

import io.reactivex.rxjava3.core.Observable;

import com.AndroidFunitureShopApp.model.Account.AccountModel;
import com.AndroidFunitureShopApp.model.Order.OrderAPI;
import com.AndroidFunitureShopApp.model.Order.OrderModel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderAPIService {
    private static final String BASE_URL = _Constant.baseUrl;
    private OrderAPI orderAPI;

    public OrderAPIService() {
        orderAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(OrderAPI.class);
    }

    public Observable<AccountModel> createOrder(String email, String phone, long totalPrice, int idUser, String address, int quantity, String orderDetail) {
        return orderAPI.createOrder(email, phone, totalPrice, idUser, address, quantity, orderDetail);
    }

    public Observable<OrderModel> viewOrder(int idUser) {
        return orderAPI.viewOrder(idUser);
    }
}
