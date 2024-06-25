package com.example.adminfunitureshopapp.model.Account;

public class Account {
    private int id;
    private String username;
    private String password;
    private String role;
    private String fullname;
    private String imageAva;
    private String defaultAdress;
    private String phone;
    private String email;

    public Account(int id, String username, String password, String role, String fullName, String imageAva, String defaultAdress, String phone, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullName;
        this.imageAva = imageAva;
        this.defaultAdress = defaultAdress;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getFullName() {
        return fullname;
    }
    public void setFullName(String fullName) {
        this.fullname = fullName;
    }
    public String getImageAva() {
        return imageAva;
    }
    public void setImageAva(String imageAva) {
        this.imageAva = imageAva;
    }
    public String getDefaultAdress() {
        return defaultAdress;
    }
    public void setDefaultAdress(String defaultAdress) {
        this.defaultAdress = defaultAdress;
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

}
