package com.AndroidFunitureShopApp.model.DeliveryInformation;

import android.util.Log;

import com.AndroidFunitureShopApp.model.DeliveryInformation.DeliveryInformationModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeliveryInformationAPI {

    @POST("addDeliveryInformation.php")
    @FormUrlEncoded
    Observable<DeliveryInformationModel> addDeliveryInformation(
            @Field("user_id") int userId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("city") String city,
            @Field("district") String district,
            @Field("ward") String ward,
            @Field("street") String street,
            @Field("notes") String notes
    );

}