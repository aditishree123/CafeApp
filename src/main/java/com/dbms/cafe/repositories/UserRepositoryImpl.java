package com.dbms.cafe.repositories;

import com.dbms.cafe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImpl implements UserRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByEmail(String email) {
        try{
        String query = "select * from user where email='"+email+"'";
        return jdbcTemplate.queryForObject(query, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNo(resultSet.getString("phone_no"));
                user.setRole(resultSet.getString("role"));
                user.setStatus(resultSet.getString("status"));
                user.setPassword(resultSet.getString("password"));
                user.setLandmark(resultSet.getString("landmark"));
                user.setLocality(resultSet.getString("locality"));
                user.setFlatNo(resultSet.getString("flatNo"));
                return user;
            }
        });}
       catch (EmptyResultDataAccessException e){
            return null;
       }
    }

    @Override
    public boolean userExists(String email) {
        String query = "select count(*) from user where email='"+email+"'";
        int cnt = jdbcTemplate.queryForObject(query,Integer.class);
        if(cnt>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void save(User user) {
        String query = "insert into user(first_name,middle_name,last_name,email,phone_no,password,landmark,flatNo,locality,status,role) values(?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(query,user.getFirstName(),user.getMiddleName(),user.getLastName(),user.getEmail(),user.getPhoneNo(),user.getPassword(),user.getLandmark(),user.getFlatNo(),
                user.getLocality(),user.getStatus(),user.getRole());
    }

    @Override
    public void enableUser(User user) {
        String query = "update user set status = 'verified' where email='"+user.getEmail()+"'";
        jdbcTemplate.update(query);
    }

    @Override
    public User findByUserId(int id) {
        String query = "select * from user where id='"+id+"'";
        return jdbcTemplate.queryForObject(query, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setMiddleName(resultSet.getString("middle_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNo(resultSet.getString("phone_no"));
                user.setRole(resultSet.getString("role"));
                user.setStatus(resultSet.getString("status"));
                user.setPassword(resultSet.getString("password"));
                user.setLastName(resultSet.getString("locality"));
                user.setLastName(resultSet.getString("flatNo"));
                user.setLastName(resultSet.getString("landmark"));
                return user;
            }
        });
    }

    @Override
    public void updateUser(String email,String firstname, String middlename, String lastname, String flat, String landmark, String locality, String contact) {
        String query = "update user set first_name = '"+firstname+"',middle_name='"+middlename+"',last_name='"+lastname+"',flatNo='"+flat+"',landmark='"+landmark+"',locality='"+locality+"',phone_no='"+contact+"' where email='"+email+"'";
        jdbcTemplate.update(query);
    }


}
