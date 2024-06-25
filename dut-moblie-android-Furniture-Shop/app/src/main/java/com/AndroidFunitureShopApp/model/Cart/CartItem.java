package com.AndroidFunitureShopApp.model.Cart;

public class CartItem {
    private int id;
    private String name;
    private int quantity;
    private int productQuantity;
    private String imageUrl;
    private int price;


    public CartItem() {
    }

    public CartItem(int id, String name, int quantity, int productQuantity,  String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.productQuantity = productQuantity;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public int getproductQuantity() {
        return productQuantity;
    }

    public void setproductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
