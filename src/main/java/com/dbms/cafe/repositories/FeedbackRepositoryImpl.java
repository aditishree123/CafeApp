package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Cart;
import com.dbms.cafe.models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository{

    JdbcTemplate jdbcTemplate;
    UserRepository userRepository;
    FoodItemRepository foodItemRepository;
    OfferRepository offerRepository;

    private RowMapper<Feedback> feedbackRowMapper =new RowMapper<Feedback>(){
        @Override
        public Feedback mapRow(ResultSet resultSet, int i) throws SQLException {
            Feedback feedback =new Feedback();
            feedback.setId(resultSet.getInt("id"));
            feedback.setUser(userRepository.findByUserId(resultSet.getInt("user")));
            feedback.setDescription(resultSet.getString("description"));
            feedback.setStatus(resultSet.getString("status"));
            return feedback;
        }
    };
    @Autowired
    public FeedbackRepositoryImpl(JdbcTemplate jdbcTemplate,FoodItemRepository foodItemRepository, UserRepository userRepository, OfferRepository offerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.foodItemRepository=foodItemRepository;
        this.userRepository=userRepository;
        this.offerRepository=offerRepository;
    }
    @Override
    public void saveFeedback(Feedback feedback) {
        String query="insert into feedback(userid, description, status) values(?,?,?)";
        jdbcTemplate.update(query, feedback.getUser().getId(),feedback.getDescription(),feedback.getStatus());
    }

    @Override
    public List<Feedback> activeFeedback() {
        String query="select * from  feedback where status='activated'";
        List<Feedback> feedback=jdbcTemplate.query(query,feedbackRowMapper);
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {
        String query="select * from  feedback ";
        List<Feedback> feedback=jdbcTemplate.query(query,feedbackRowMapper);
        return feedback;
    }

    @Override
    public Feedback findFeedbackById(int Id) {
        String query="select * from  feedback where id='"+Id+"'";
        Feedback feedback=jdbcTemplate.queryForObject(query,feedbackRowMapper);
        return feedback;
    }
}
