package com.example.adminfunitureshopapp.viewmodel;

import com.example.adminfunitureshopapp.model.Account.Account;
import com.example.adminfunitureshopapp.model.Account.AccountAPI;
import com.example.adminfunitureshopapp.model.Account.AccountModel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
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

    public Observable<AccountModel> register(String username, String password, String role, String fullname, String imageAva, String defaultAdress, String email, String phone) {
        return api.register(username, password, role, fullname, imageAva, defaultAdress, email, phone);
    }

    public Observable<AccountModel> login(String username, String password) {
        return api.login(username, password);
    }

    public Observable<AccountModel> UpdateUser(int id, String password, String fullname, String imageAva, String defaultAdress, String email, String phone) {
        return api.updateUser(id, password, fullname, imageAva, defaultAdress, email, phone);
    }

    public Single<List<Account>> getAccounts() {
        return api.getAccounts();
    }

    public Single<Integer> deleteAcc(int id) {
        return api.deleteAcc(id);
    }

    public Single<Integer> resetPass(int id) {
        return api.resetPass(id);
    }
}
