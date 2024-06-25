package com.example.adminfunitureshopapp.model.Revenue;

public class Revenue {
    private String totalMonth;
    private String month;


    private String nameProduct;
    private String totalProduct;

    public Revenue(String totalMonth, String month, String nameProduct, String totalProduct) {
        this.totalMonth = totalMonth;
        this.month = month;
        this.nameProduct = nameProduct;
        this.totalProduct = totalProduct;
    }

    public String getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(String totalMonth) {
        this.totalMonth = totalMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(String totalProduct) {
        this.totalProduct = totalProduct;
    }
}
