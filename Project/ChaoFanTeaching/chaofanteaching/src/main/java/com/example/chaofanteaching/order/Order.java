package com.example.chaofanteaching.order;

public class Order {
    private int id;
    private String status;
    private String user;
    private String price;
    private String time;

    public Order(int id,String status,String user,String price,String time){
        this.id = id;
        this.status = status;
        this.user = user;
        this.price = price;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}