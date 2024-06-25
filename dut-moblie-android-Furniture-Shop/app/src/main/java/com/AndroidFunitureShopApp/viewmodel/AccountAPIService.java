package com.AndroidFunitureShopApp.viewmodel;

import com.AndroidFunitureShopApp.model.Account.AccountAPI;
import com.AndroidFunitureShopApp.model.Account.AccountModel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountAPIService {
    private static final String BASE_URL = _Constant.baseUrl;
    private AccountAPI api;

    public AccountAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(AccountAPI.class);
    }

    public Observable<AccountModel> register(String username, String password, String role, String fullname, String imageAva, String defaultAdress, String email, String phone, String uid) {
        return api.register(username, password, role, fullname, imageAva, defaultAdress, email, phone, uid);
    }

    public Observable<AccountModel> login(String username, String password) {
        return api.login(username, password);
    }

    public Observable<AccountModel> UpdateUser(int id, String password, String fullname, String imageAva, String defaultAdress, String email, String phone) {
        return api.updateUser(id, password, fullname, imageAva, defaultAdress, email, phone);
    }

    public Observable<AccountModel> checkPass2(String username, String pass2) {
        return api.checkpass2(username, pass2);
    }

    public Observable<AccountModel> UpdatePassword(int id, String password) {
        return api.UpdatePassword(id, password);
    }
}
