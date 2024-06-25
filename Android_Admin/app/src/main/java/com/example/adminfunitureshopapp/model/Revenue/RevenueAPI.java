package com.example.adminfunitureshopapp.model.Revenue;


import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface RevenueAPI {
    @GET("getRevenue.php")
    Single<List<Revenue>> getRevenue();
    @GET("getRevenueByProducts.php")
    Single<List<Revenue>> getRevenueByProducts();
}
