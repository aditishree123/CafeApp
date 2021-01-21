package com.dbms.cafe.services;
import com.dbms.cafe.models.Category;
import com.dbms.cafe.models.FoodItem;
import com.dbms.cafe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private CategoryRepository categoryRepository;
    private FoodItemRepository foodItemRepository;



    @Autowired
    public MenuServiceImpl(CategoryRepository categoryRepository,  FoodItemRepository foodItemRepository) {
        this.categoryRepository = categoryRepository;
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.saveCategory(category);
    }

    @Override
    public void saveItem(FoodItem item) {
        foodItemRepository.saveFoodItem(item);
    }

    @Override
    public Category findByCategoryId(int Id) {
        return categoryRepository.findByCategoryId(Id);
    }

    @Override
    public Category findByCategoryName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public List<FoodItem> findAllItems(int Id) {
        return foodItemRepository.findAllItems(Id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<FoodItem> findItems() {
        return foodItemRepository.findItems();
    }
}