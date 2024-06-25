package com.example.adminfunitureshopapp.viewmodel;

import com.example.adminfunitureshopapp.model.Revenue.Revenue;
import com.example.adminfunitureshopapp.model.Revenue.RevenueAPI;
import com.example.adminfunitureshopapp.model.Revenue.revenueModel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RevenueAPIService {
    private static final String BASE_URL = _Constant.baseUrl;
    private RevenueAPI api;
    public RevenueAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RevenueAPI.class);
    }

    public Single<List<Revenue>> getRevenue() {
        return api.getRevenue();
    }
    public Single<List<Revenue>> getRevenueByProducts() {
        return api.getRevenueByProducts();
    }
}
