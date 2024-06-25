package com.AndroidFunitureShopApp.model.Order;

import io.reactivex.rxjava3.core.Observable;

import com.AndroidFunitureShopApp.model.Account.AccountModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderAPI {
    @POST("order.php")
    @FormUrlEncoded
    Observable<AccountModel> createOrder(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("totalPrice") long totalPrice,
            @Field("idUser") int idUser,
            @Field("address") String address,
            @Field("quantity") int quantity,
            @Field("orderDetail") String orderDetail
    );

    @POST("viewOrder.php")
    @FormUrlEncoded
    Observable<OrderModel> viewOrder(
            @Field("idUser") int idUser
    );
}
