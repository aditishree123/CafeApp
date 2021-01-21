package com.dbms.cafe.models;

public class Offer_product {
    private int id;
    private  Offer offerid;
    private  FoodItem itemid;


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public Offer getOfferid() {return offerid;}
    public void setOfferid(Offer offerid) {this.offerid=offerid;}

    public FoodItem getItemid() { return itemid; }
    public void setItemid(FoodItem itemid) { this.itemid = itemid; }
}
