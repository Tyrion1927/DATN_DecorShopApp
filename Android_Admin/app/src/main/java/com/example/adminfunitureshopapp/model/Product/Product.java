package com.example.adminfunitureshopapp.model.Product;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private String imageUrl;
    private int originalPrice;
    private int discount;
    private int price;
    private String detail;
    private String type;
    private String categoryId;

    public Product(int id, String name, int quantity, String imageUrl, int originalPrice, int discount, int price, String detail, String type, String categoryId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.price = price;
        this.detail = detail;
        this.type = type;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public int getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
