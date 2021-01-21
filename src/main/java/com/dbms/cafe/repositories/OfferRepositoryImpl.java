package com.dbms.cafe.repositories;

import com.dbms.cafe.models.FoodItem;
import com.dbms.cafe.models.Offer;
import com.dbms.cafe.models.Offer_product;
import com.dbms.cafe.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OfferRepositoryImpl implements OfferRepository{
    JdbcTemplate jdbcTemplate;
    FoodItemRepository foodItemRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public OfferRepositoryImpl(JdbcTemplate jdbcTemplate,FoodItemRepository foodItemRepository, CategoryRepository categoryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.foodItemRepository = foodItemRepository;
        this.categoryRepository = categoryRepository;
    }

    private RowMapper<Offer_product> ComboRowMapper = new RowMapper<Offer_product>() {
        @Override
        public Offer_product mapRow(ResultSet resultSet, int i) throws SQLException {
            Offer_product combo = new Offer_product();
            combo.setId(resultSet.getInt("id"));
            combo.setOfferid(findOfferById(resultSet.getInt("offerid")));
            combo.setItemid(foodItemRepository.findById(resultSet.getInt("itemid")));
            return combo;
        }
    };

    private RowMapper<Offer> offerRowMapper = new RowMapper<Offer>() {
        @Override
        public Offer mapRow(ResultSet resultSet, int i) throws SQLException {
            Offer offer = new Offer();
            offer.setId(resultSet.getInt("id"));
            offer.setPrice(resultSet.getInt("price"));
            offer.setStatus(resultSet.getString("status"));
            offer.setDescription(resultSet.getString("description"));
            offer.setRating(resultSet.getString("rating"));
            return offer;
        }
    };


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
            return foodItem;
        }
    };


    @Override
    public void saveOffer(Offer offer) {
        String query="insert into offers(price, status, description, rating) values(?,?,?,?)";
        jdbcTemplate.update(query, offer.getPrice(),offer.getStatus(),offer.getDescription(), offer.getRating());
    }

    @Override
    public void saveCombo(Offer_product combo) {
        String query="insert into offer_products(offerid, itemid) values(?,?)";
        jdbcTemplate.update(query, combo.getOfferid().getId(),combo.getItemid().getId());
    }

    @Override
    public List<Offer_product> findComboProducts(int Id) {
        String query="select * from offer_products where offerid='"+Id+"'";
        List<Offer_product> combo=jdbcTemplate.query(query,ComboRowMapper);
        return combo;
    }

    @Override
    public List<Offer> allOffers() {
        String query="select * from offers";
        List<Offer> offers=jdbcTemplate.query(query,offerRowMapper);
        return offers;
    }

    @Override
    public Offer findOfferById(int Id) {
        String query="select * from offers where id='"+Id+"'";
        Offer offer=jdbcTemplate.queryForObject(query,offerRowMapper);
        return offer;
    }

    @Override
    public Offer findLast() {
        String query="SELECT * FROM offers WHERE id = (SELECT MAX(id) FROM offers)";
        Offer offer=jdbcTemplate.queryForObject(query,offerRowMapper);
        return offer;
    }

    @Override
    public void setStatus(String status, int Id) {
        String query="update offers set status='"+status+"' where id='"+Id+"' ";
        jdbcTemplate.update(query);
    }

    @Override
    public List<Offer> activatedOffers() {
        String query="select * from offers where status='activated'";
        List<Offer> offers=jdbcTemplate.query(query,offerRowMapper);
        return offers;
    }

    @Override
    public void updateRating(String rate, int Id) {
        String query="update offers set rating='"+rate+"' where id='"+Id+"' ";
        jdbcTemplate.update(query);
    }

}
