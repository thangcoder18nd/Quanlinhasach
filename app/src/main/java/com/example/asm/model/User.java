package com.example.asm.model;

public class User {
    private String username;
    private String password;
    private String phone;
    private String hoten;
    private String ghichu;

    public User(String username, String password, String phone, String hoten, String ghichu) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.hoten = hoten;
        this.ghichu = ghichu;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
