package com.example.adminfunitureshopapp.model.Order;

import com.example.adminfunitureshopapp.model.Product.Product;
import com.example.adminfunitureshopapp.model.Product.productsAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrdersAPI {
    @GET("getOrders.php")
    Single<List<Order>> getOrders();

    @POST("updateOrder.php")
    @FormUrlEncoded
    Single<Integer> updateOrder(
            @Field("id") Integer id,
            @Field("status") Integer status
    );

    @POST("CancelOrder.php")
    @FormUrlEncoded
    Single<Integer> CancelOrder(@Field("id") int id);
}
