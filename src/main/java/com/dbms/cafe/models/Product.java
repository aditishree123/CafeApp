package com.dbms.cafe.models;

public class Product {
    private int id;
    private  Order orderid;
    private Offer offer;
    private FoodItem item;
    private int quantity;
    private int rating;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public Order getOrderid(){
        return orderid;
    }
    public void setOrderid(Order orderid){
        this.orderid=orderid;
    }

    public FoodItem getItem(){
        return item;
    }
    public void setItem(FoodItem item){
        this.item=item;
    }
    public Offer getOffer(){
        return offer;
    }
    public void setOffer(Offer offer){
        this.offer=offer;
    }

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity=quantity;}

    public int getRating(){return rating;}
    public void setRating(int rating){this.rating=rating;}

}
