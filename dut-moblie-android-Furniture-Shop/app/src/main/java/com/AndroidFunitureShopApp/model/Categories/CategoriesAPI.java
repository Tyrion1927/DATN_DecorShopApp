package com.AndroidFunitureShopApp.model.Categories;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface CategoriesAPI {
    @GET("getCategories.php")
    Single<List<Categories>> getCategories();
}
