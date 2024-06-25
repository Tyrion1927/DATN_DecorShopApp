package com.AndroidFunitureShopApp.model.DeliveryInformation;

public class DeliveryInformation {

    private int id;
    private int user_id;
    private String name;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String street;
    private String notes;

    public DeliveryInformation(int id, int user_id, String name, String phone, String city, String district, String ward, String street, String notes) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.notes = notes;
    }

    public int getUser_id() {
        return user_id;
    }

    public int setUser_id() {
        return user_id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
