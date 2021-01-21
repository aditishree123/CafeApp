package com.dbms.cafe.models;

public class FoodItem {
    private int id;
    private Category category;
    private String item;
    private int price;
    private int quantity;
    private String description;
    private String avg;
    private int discount;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvg(){return avg;}
    public void setAvg(String  avg){this.avg=avg;}

    public int getDiscount() {
        return discount;
    }

    public void setDiscount (int discount) {
        this.discount = discount;
    }

}
