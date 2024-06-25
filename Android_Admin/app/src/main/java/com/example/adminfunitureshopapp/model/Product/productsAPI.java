package com.example.adminfunitureshopapp.model.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface productsAPI {
    @GET("getProducts.php")
    Single<List<Product>> getProducts();

    @POST("addProduct.php")
    @FormUrlEncoded
    Single<Integer> addProduct(
            @Field("name") String name,
            @Field("quantity") Integer quantity,
            @Field("imageUrl") String imageUrl,
            @Field("originalPrice") Integer originalPrice,
            @Field("discount") Integer discount,
            @Field("detail") String detail,
            @Field("type") String type,
            @Field("categoryId") String categoryId
    );

    //id, name, Integer.parseInt(quantity), imageUrl, Integer.parseInt(originalPrice), Integer.parseInt(discount), detail, type
    @POST("updateProduct.php")
    @FormUrlEncoded
    Single<Integer> updateProduct(
            @Field("id") Integer id,
            @Field("name") String name,
            @Field("quantity") Integer quantity,
            @Field("imageUrl") String imageUrl,
            @Field("originalPrice") Integer originalPrice,
            @Field("discount") Integer discount,
            @Field("detail") String detail,
            @Field("type") String type
    );

    @POST("deleteProduct.php")
    @FormUrlEncoded
    Single<Integer> deleteProduct(@Field("productId") int productId);

}
