package com.dbms.cafe.models;

public class Offer {
    private int id;
    private  int price;
    private String status;
    private String description;
    private  String rating;


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }

    public String getRating(){return rating;}
    public void setRating(String  rating){this.rating=rating;}
}
