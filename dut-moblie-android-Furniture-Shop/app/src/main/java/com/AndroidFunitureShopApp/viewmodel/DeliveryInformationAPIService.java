package com.AndroidFunitureShopApp.viewmodel;

import android.util.Log;

import com.AndroidFunitureShopApp.model.DeliveryInformation.DeliveryInformationAPI;
import com.AndroidFunitureShopApp.model.DeliveryInformation.DeliveryInformationModel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeliveryInformationAPIService {
    private static final String BASE_URL = _Constant.baseUrl;
    private DeliveryInformationAPI api;

    public DeliveryInformationAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(DeliveryInformationAPI.class);
    }

    public Observable<DeliveryInformationModel> addDeliveryInformation(int userId, String name, String phone, String city, String district, String ward, String street, String notes) {
        Log.d("DEBUG", "userId: " + userId + ", name: " + name + ", phone: " + phone + ", city: " + city + ", district: " + district + ", ward: " + ward + ", street: " + street + ", notes: " + notes);
        return api.addDeliveryInformation(userId, name, phone, city, district, ward, street, notes);
    }
}
