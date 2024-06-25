package com.AndroidFunitureShopApp.model.Account;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountAPI {
    @POST("register.php")
    @FormUrlEncoded
    Observable<AccountModel> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role,
            @Field("fullname") String fullname,
            @Field("imageAva") String imageAva,
            @Field("defaultAdress") String defaultAdress,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("uid") String uid

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

    @POST("checkpass2.php")
    @FormUrlEncoded
    Observable<AccountModel> checkpass2(
            @Field("username") String username,
            @Field("pass2") String pass2
    );

    @POST("updatePassword.php")
    @FormUrlEncoded
    Observable<AccountModel> UpdatePassword(
            @Field("id") int id,
            @Field("password") String password
    );
}
