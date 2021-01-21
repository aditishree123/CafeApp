package com.dbms.cafe.repositories;

import com.dbms.cafe.models.FoodItem;
import com.dbms.cafe.models.Order;
import com.dbms.cafe.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository{
    JdbcTemplate jdbcTemplate;
    FoodItemRepository foodItemRepository;
    UserRepository userRepository;
    OfferRepository offerRepository;

    private RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setOrderid(findByOrderId(resultSet.getInt("orderid")));
            product.setItem(foodItemRepository.findById(resultSet.getInt("item")));
            product.setQuantity(resultSet.getInt("quantity"));
            product.setRating(resultSet.getInt("rating"));
            return product;
        }
    };

    private RowMapper<Product> productRowMapperOffer = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setOrderid(findByOrderId(resultSet.getInt("orderid")));
            product.setOffer(offerRepository.findOfferById(resultSet.getInt("offer")));
            product.setQuantity(resultSet.getInt("quantity"));
            product.setRating(resultSet.getInt("rating"));
            return product;
        }
    };

    private RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            Order order = new Order();
            order.setId(resultSet.getInt("id"));
            order.setUser(userRepository.findByUserId(resultSet.getInt("user")));
            order.setStatus((resultSet.getString("status")));
            order.setTime(resultSet.getDate("time").toLocalDate().atTime(LocalTime.now()));
            order.setAmount(resultSet.getInt("amount"));
            return order;
        }
    };
    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate,FoodItemRepository foodItemRepository, UserRepository userRepository, OfferRepository offerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.foodItemRepository=foodItemRepository;
        this.userRepository=userRepository;
        this.offerRepository=offerRepository;
    }
    @Override
    public void saveOrder(Order order) {
        String query = "insert into orders(user, status, time , amount) values(?,?,?,?)";
        jdbcTemplate.update(query,order.getUser().getId(),order.getStatus(),order.getTime(),order.getAmount());

    }

    @Override
    public void saveProduct(Product product) {

        if(product.getOffer()==null) {
            String query="insert into products(orderid, item, quantity, rating) values(?,?,?,?)";
            jdbcTemplate.update(query, product.getOrderid().getId(),product.getItem().getId(),product.getQuantity(),product.getRating());
        }
        else{
            String query="insert into products(orderid, offer, quantity, rating) values(?,?,?,?)";
            jdbcTemplate.update(query, product.getOrderid().getId(),product.getOffer().getId(),product.getQuantity(), product.getRating());
        }
    }



    @Override
    public Order findByOrderId(int Id) {
        String query="select * from orders where id='"+Id+"'";
        Order order=jdbcTemplate.queryForObject(query,orderRowMapper);
        return order;
    }

    @Override
    public List<Order> ByUserId(int Id) {
        String query="select * from orders where user='"+Id+"'";
        List< Order > orders=jdbcTemplate.query(query,orderRowMapper);
        return orders;
    }

    @Override
    public List<Product> ByOrderId(int Id) {
        String query="select * from products where offer is null and orderid='"+Id+"'";
        List<Product> product=jdbcTemplate.query(query,productRowMapper);
        return product;
    }

    @Override
    public List<Product> ByOrderIdOffer(int Id) {
        String query="select * from products where item is null and orderid='"+Id+"'";
        List<Product> product=jdbcTemplate.query(query,productRowMapperOffer);
        return product;
    }

    @Override
    public List<Order> AllOrders() {
        String query="select * from orders";
        List< Order > orders=jdbcTemplate.query(query,orderRowMapper);
        return orders;
    }

    @Override
    public Order findLast(int Id) {
        String query="SELECT * FROM orders WHERE id = (SELECT MAX(id) FROM orders WHERE user = '"+Id+"')";
        Order order=jdbcTemplate.queryForObject(query,orderRowMapper);
        return order;
    }

    @Override
    public void setStatus(String status,int Id) {
        String query="update orders set status='"+status+"' where id='"+Id+"' ";
        jdbcTemplate.update(query);
    }

    @Override
    public float AvgRating(int Id) {
        String query="select avg(rating) from products where item='"+Id+"'";
        float avg=jdbcTemplate.queryForObject(query,Float.class);
        return avg;
    }

    @Override
    public float AvgOfferRating(int Id) {
        String query="select avg(rating) from products where offer='"+Id+"'";
        float avg=jdbcTemplate.queryForObject(query,Float.class);
        return avg;
    }

    @Override
    public void updateRating(int rate,int Id) {
      String query="update products set rating='"+rate+"' where id='"+Id+"' ";
      jdbcTemplate.update(query);
    }

    @Override
    public Product findProductById(int Id) {
        String query="select * from products where id='"+Id+"'";
        Product order=jdbcTemplate.queryForObject(query,productRowMapper);
        return order;
    }

    @Override
    public Product findProductByIdO(int Id) {

            String query="select * from products where id='"+Id+"'";
            Product order=jdbcTemplate.queryForObject(query,productRowMapperOffer);
            return order;
    }


}
