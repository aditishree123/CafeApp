package com.dbms.cafe.models;

public class Cart {

     private int id;
     private User user;
     private FoodItem product;
     private int quantity;
     private Offer offer;

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id= id;
     }

     public User getUser(){return user; }

     public void setUser(User user){ this.user=user;}

     public FoodItem getProduct(){return product;}

     public void setProduct(FoodItem product){ this.product=product;}

     public int getQuantity() {
          return quantity;
     }

     public void setQuantity(int quantity) {
          this.quantity= quantity;
     }

     public Offer getOffer(){return offer; }

     public void setOffer(Offer offer){ this.offer=offer;}
}
