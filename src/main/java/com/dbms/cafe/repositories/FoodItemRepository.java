package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Category;
import com.dbms.cafe.models.FoodItem;

import java.util.List;

public interface FoodItemRepository {
    public void saveFoodItem(FoodItem foodItem) ;
    public List<FoodItem> findAllItems(int Id);
    public List<FoodItem> findItems();
    public FoodItem findById(int Id);
    public FoodItem findByName(String name);
    public void updateRating(String rate,int Id);
}
