package com.dbms.cafe.services;
import com.dbms.cafe.models.Category;
import com.dbms.cafe.models.FoodItem;

import java.util.List;


public interface MenuService {
    public void saveCategory(Category category);
    public void saveItem(FoodItem item);
    public Category findByCategoryId(int Id);
    public Category findByCategoryName(String name);
    public List<FoodItem> findAllItems(int Id);
    public List<Category> findAll();
    public List<FoodItem> findItems();
}
