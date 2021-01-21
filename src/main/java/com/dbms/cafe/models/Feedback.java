package com.dbms.cafe.models;

public class Feedback {
    private int id;
    private User user;
    private String description;
    private  String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {return status;}

    public void setStatus(String status){this.status=status;}


}
