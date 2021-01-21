package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Cart;
import com.dbms.cafe.models.Category;
import com.dbms.cafe.models.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class CartRepositoryImpl implements CartRepository {

    JdbcTemplate jdbcTemplate;
    UserRepository userRepository;
    FoodItemRepository foodItemRepository;
    OfferRepository offerRepository;

    private RowMapper<Cart> cartRowMapper =new RowMapper<Cart>(){
        @Override
        public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
            Cart cart =new Cart();
            cart.setId(resultSet.getInt("id"));
            cart.setUser(userRepository.findByUserId(resultSet.getInt("user")));
            cart.setProduct(foodItemRepository.findById(resultSet.getInt("product")));
            cart.setQuantity(resultSet.getInt("quantity"));
            return cart;
        }
    };

    private RowMapper<Cart> cartOfferRowMapper =new RowMapper<Cart>(){
        @Override
        public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
            Cart cart =new Cart();
            cart.setId(resultSet.getInt("id"));
            cart.setUser(userRepository.findByUserId(resultSet.getInt("user")));
            cart.setOffer(offerRepository.findOfferById(resultSet.getInt("offer")));
            cart.setQuantity(resultSet.getInt("quantity"));
            return cart;
        }
    };

    @Autowired
    public CartRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository, FoodItemRepository foodItemRepository, OfferRepository offerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository=userRepository;
        this.foodItemRepository=foodItemRepository;
        this.offerRepository=offerRepository;
    }

    @Override
    public List<Cart> findCartItems(int id) {
        String query="select * from  cart where user='"+id+"' and offer is null";
        List<Cart> cart=jdbcTemplate.query(query,cartRowMapper);
        return cart;
    }

    @Override
    public List<Cart> findCartOffers(int id) {
        String query="select * from  cart where user='"+id+"' and product is null";
        List<Cart> cart=jdbcTemplate.query(query,cartOfferRowMapper);
        return cart;
    }

    @Override
    public void saveCart(Cart cart) {
        if(cart.getOffer()==null) {
            String query = "insert into cart(user, product,quantity) values(?,?,?)";
            jdbcTemplate.update(query, cart.getUser().getId(), cart.getProduct().getId(), cart.getQuantity());
        }
        else{
            String query = "insert into cart(user, offer,quantity) values(?,?,?)";
            jdbcTemplate.update(query, cart.getUser().getId(), cart.getOffer().getId(), cart.getQuantity());
        }
    }

    @Override
    public Cart findCartById(int userid, int productid) {
        try{
        String query="select * from  cart where user='"+userid+"' and product='"+productid+"'";
        Cart cart=jdbcTemplate.queryForObject(query,cartRowMapper);
        return cart;}
        catch (EmptyResultDataAccessException e) {

            return null;
        }
        }

    @Override
    public Cart findCartByIdOffer(int userid, int offerid) {
        try{
        String query="select * from  cart where user='"+userid+"' and offer='"+offerid+"'";
        Cart cart=jdbcTemplate.queryForObject(query,cartOfferRowMapper);
        return cart;}
        catch (EmptyResultDataAccessException e) {

            return null;
        }
    }


    @Override
    public void updateCart(int userid,int productid,int quantity,int Id) {
        String query="update cart set user='"+userid+"' ,product='"+productid+"',quantity='"+quantity+"' where id='"+Id+"'";
        jdbcTemplate.update(query);
    }

    @Override
    public void updateCartOffer(int userid, int offerid, int quantity, int Id) {
        String query="update cart set user='"+userid+"' ,offer='"+offerid+"',quantity='"+quantity+"' where id='"+Id+"'";
        jdbcTemplate.update(query);

    }

    @Override
    public void deleteCart(int Id) {
        String query="delete from cart where id='"+Id+"'";
        jdbcTemplate.update(query);
    }


    @Override
    public boolean cartExists(int userid, int productid) {

            String query = "select count(*) from cart where user='"+userid+"' and product='"+productid+"'";
            int cnt = jdbcTemplate.queryForObject(query,Integer.class);
           if(cnt>0)
            return true;
        return false;
    }

    @Override
    public boolean cartExistsOffer(int userid, int offerid) {
        String query = "select count(*) from cart where user='"+userid+"' and offer='"+offerid+"'";
        int cnt = jdbcTemplate.queryForObject(query,Integer.class);
        if(cnt>0)
            return true;
        return false;
    }


}
