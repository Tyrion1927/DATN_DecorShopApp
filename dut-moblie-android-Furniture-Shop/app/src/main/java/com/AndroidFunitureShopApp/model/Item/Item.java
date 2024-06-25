package com.AndroidFunitureShopApp.model.Item;

public class Item {
    int idProduct;
    String name;
    int quantity;
    String imageUrl;

    public Item(int idProduct, String name, int quantity, String imageUrl) {
        this.idProduct = idProduct;
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
