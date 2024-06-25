package com.example.adminfunitureshopapp.model.Order;

import com.example.adminfunitureshopapp.model.Item.Item;

import java.util.ArrayList;

public class Order {
    int id;
    int idUser;
    String address;
    String phone;
    String email;
    int quantity;
    long totalPrice;
    String fullname;
    String date;
    int status;
    ArrayList<Item> item;

    public Order(int id, int idUser, String address, String phone, String email, int quantity, long totalPrice, String fullname, String date,int status, ArrayList<Item> item) {
        this.id = id;
        this.idUser = idUser;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.fullname = fullname;
        this.date = date;
        this.status = status;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }
}
