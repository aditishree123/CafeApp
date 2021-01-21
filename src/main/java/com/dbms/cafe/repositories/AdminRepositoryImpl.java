package com.dbms.cafe.repositories;

import com.dbms.cafe.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdminRepositoryImpl implements AdminRepository{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteCategoryById(int id) {
        String query= "delete from category where id='"+id+"'";
        String query1="delete from food_items where category='"+id+"'";
        jdbcTemplate.update(query1);
        jdbcTemplate.update(query);
    }

    @Override
    public void updateCategory(String name, String description,String image, int Id){
        String query="update category set name='"+name+"' ,description='"+description+"',image='"+image+"' where id='"+Id+"' ";
        jdbcTemplate.update(query);
    }


    @Override
    public void updateItem(String item, int price, int quantity, String description,int discount, int Id) {
       String query="update food_items set item='"+item+"', price='"+price+"', quantity='"+quantity+"', description='"+description+"', discount='"+discount+"' where id='"+Id+"'";
       jdbcTemplate.update(query);
    }

    @Override
    public void deleteItemsById(int Id) {
        String query="delete from food_items where id='"+Id+"'";
        jdbcTemplate.update(query);
    }

}
