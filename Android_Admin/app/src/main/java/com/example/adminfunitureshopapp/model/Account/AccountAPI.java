package com.example.adminfunitureshopapp.model.Account;

import com.example.adminfunitureshopapp.model.Product.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountAPI {
    @GET("getAccounts.php")
    Single<List<Account>> getAccounts();

    @POST("register-admin.php")
    @FormUrlEncoded
    Observable<AccountModel> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role,
            @Field("fullname") String fullname,
            @Field("imageAva") String imageAva,
            @Field("defaultAdress") String defaultAdress,
            @Field("email") String email,
            @Field("phone") String phone
    );

    @POST("login.php")
    @FormUrlEncoded
    Observable<AccountModel> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("updateUser.php")
    @FormUrlEncoded
    Observable<AccountModel> updateUser(
            @Field("id") int id,
            @Field("password") String password,
            //@Field("role") String role,
            @Field("fullname") String fullname,
            @Field("imageAva") String imageAva,
            @Field("defaultAdress") String defaultAdress,
            @Field("email") String email,
            @Field("phone") String phone
    );

    @POST("deleteAcc.php")
    @FormUrlEncoded
    Single<Integer> deleteAcc(@Field("id") int id);

    @POST("resetPassAcc.php")
    @FormUrlEncoded
    Single<Integer> resetPass(
            @Field("id") Integer id
    );
}
