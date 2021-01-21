package com.dbms.cafe.models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Order {
    private int id;
    private  User user;
    private String status;
    private LocalDateTime time;
    private int amount;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }

    public LocalDateTime getTime(){
    return time;}

    public void setTime(LocalDateTime time){
        this.time =time;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount= amount;
    }
}
