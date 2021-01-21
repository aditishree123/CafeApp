package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Category;
import com.dbms.cafe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    JdbcTemplate jdbcTemplate;

    private RowMapper<Category>  categoryRowMapper =new RowMapper<Category>(){
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException{
            Category category =new Category();
            category.setId(resultSet.getInt("id"));
            category.setName(resultSet.getString("name"));
            category.setDescription(resultSet.getString("description"));
            category.setImage(resultSet.getString("image"));
            return category;
        }
    };


    @Autowired
    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void saveCategory(Category category) {
        String query = "insert into category(name, description,image) values(?,?,?)";
        jdbcTemplate.update(query,category.getName(),category.getDescription(),category.getImage());
    }

    @Override
    public Category findByCategoryId(int Id) {
        String query = "select * from category where id='"+Id+"'";
        Category category=jdbcTemplate.queryForObject(query,categoryRowMapper);
        return category;
    }

    @Override
    public Category findByCategoryName(String name) {
        String query = "select * from category where name='"+name+"'";
        Category category= jdbcTemplate.queryForObject(query,categoryRowMapper);
        return category;
    }

    @Override
    public List<Category> findAll() {
        String query ="select * from category";
        List<Category> categories=jdbcTemplate.query(query,categoryRowMapper);
        return categories;
    }
}
