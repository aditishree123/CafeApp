package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.dbms.cafe.models.FoodItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodItemRepositoryImpl implements FoodItemRepository {
    JdbcTemplate jdbcTemplate;
    CategoryRepository categoryRepository;


    @Autowired
    public FoodItemRepositoryImpl(JdbcTemplate jdbcTemplate, CategoryRepository categoryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRepository = categoryRepository;

    }

    private RowMapper<FoodItem> foodItemRowMapper = new RowMapper<FoodItem>() {
        @Override
        public FoodItem mapRow(ResultSet resultSet, int i) throws SQLException {
            FoodItem foodItem = new FoodItem();
            foodItem.setId(resultSet.getInt("id"));
            foodItem.setCategory(categoryRepository.findByCategoryId(resultSet.getInt("category")));
            foodItem.setItem(resultSet.getString("item"));
            foodItem.setPrice(resultSet.getInt("price"));
            foodItem.setQuantity(resultSet.getInt("quantity"));
            foodItem.setDescription(resultSet.getString("description"));
            foodItem.setAvg(resultSet.getString("avg"));
            foodItem.setDiscount(resultSet.getInt("discount"));
            return foodItem;
        }
    };



    @Override
    public void saveFoodItem(FoodItem foodItem) {
        String query = "insert into food_items(category,item,price,quantity,description,avg,discount) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(query,foodItem.getCategory().getId(),foodItem.getItem(),foodItem.getPrice(),foodItem.getQuantity(),foodItem.getDescription(), foodItem.getAvg(), foodItem.getDiscount());
    }

    @Override
    public List<FoodItem> findAllItems(int Id) {
        String sqlQuery = "select * from food_items where category = '"+Id+"'";
        List<FoodItem> foodItems = jdbcTemplate.query(sqlQuery,foodItemRowMapper);
        return foodItems;
    }

    @Override
    public List<FoodItem> findItems() {
        String query ="select * from food_items";
        List<FoodItem> items=jdbcTemplate.query(query,foodItemRowMapper);
        return items;
    }

    @Override
    public FoodItem findById(int Id) {
        try{
        String query = "select * from food_items where id='"+Id+"'";
        FoodItem item= (FoodItem) jdbcTemplate.queryForObject(query,foodItemRowMapper);
        return item;}catch (EmptyResultDataAccessException e) {
            return null;}

        }

    @Override
    public FoodItem findByName(String name) {
        String query = "select * from food_items where item='"+name+"'";
        FoodItem item= (FoodItem) jdbcTemplate.queryForObject(query,foodItemRowMapper);
        return item;
    }

    @Override
    public void updateRating(String rate,int Id) {
        String query="update food_items set avg='"+rate+"' where id='"+Id+"' ";
        jdbcTemplate.update(query);
    }
}
