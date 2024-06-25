package com.AndroidFunitureShopApp.model.DeliveryInformation;

import java.util.List;

public class DeliveryInformationModel {
    boolean success;
    String message;
    List<DeliveryInformation> result;

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

    public List<DeliveryInformation> getResult() {
        return result;
    }

    public void setResult(List<DeliveryInformation> result) {
        this.result = result;
    }
}
