package com.example.adminfunitureshopapp.model.Revenue;

import com.example.adminfunitureshopapp.model.Account.Account;

import java.util.List;

public class revenueModel {
    boolean success;
    String message;
    List<Revenue> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Revenue> getResult() {
        return result;
    }

    public void setResult(List<Revenue> result) {
        this.result = result;
    }
}
