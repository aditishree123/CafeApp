package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Category;
import com.dbms.cafe.models.FoodItem;

public interface AdminRepository {
    public void deleteCategoryById(int Id);
    public void updateCategory(String name, String description,String image, int Id);
    public void updateItem(String item, int price, int quantity, String description, int discount, int Id);
    public void deleteItemsById(int Id);
}
