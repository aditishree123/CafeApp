package com.dbms.cafe.repositories;
import com.dbms.cafe.models.Category;

import java.util.List;

public interface CategoryRepository {
    public void saveCategory(Category category);
    public Category findByCategoryId(int Id);
    public Category findByCategoryName(String name);
    public List<Category> findAll();

}