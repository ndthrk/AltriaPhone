package com.example.altriaphone.Object;

import java.io.Serializable;

public class User implements Serializable {
    private String username, name, phone, email, password, address;
    private double money;
    int ID;

    public User(){
        this.email = "";
        this.ID = -1;
        this.username = "";
        this.password = "";
        this.name = "";
        this.phone = "";
        this.money = 0;
        this.address = "";
    }

    public User(int ID, String username, String password, String name, String email, String phone, double money, String address) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.money = money;
        this.address = address;
    }
    public String getAddress(){ return address; }
    public void setAddress(String address){ this.address = address; }
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getID() {
        return ID;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
