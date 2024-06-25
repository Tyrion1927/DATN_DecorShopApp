package com.example.adminfunitureshopapp.model.Categories;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoriesAPI {
    @GET("getCategories.php")
    Single<List<Categories>> getCategories();

    @POST("addCategory.php")
    @FormUrlEncoded
    Single<Integer> addCategory(
            @Field("name") String name,
            @Field("imageUrl") String imageUrl
    );

    @POST("updateCategory.php")
    @FormUrlEncoded
    Single<Integer> updateCategory(
            @Field("id") Integer id,
            @Field("name") String name,
            @Field("imageUrl") String imageUrl
    );

    @POST("deleteCategory.php")
    @FormUrlEncoded
    Single<Integer> deleteCategory(@Field("categoryId") int categoryId);
}
