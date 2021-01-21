package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Cart;
import com.dbms.cafe.models.FAQ;
import com.dbms.cafe.models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FaqRepositoryImpl implements FaqRepository{

    JdbcTemplate jdbcTemplate;
    UserRepository userRepository;
    FoodItemRepository foodItemRepository;
    OfferRepository offerRepository;

    private RowMapper<FAQ> FaqRowMapper =new RowMapper<FAQ>(){
        @Override
        public FAQ mapRow(ResultSet resultSet, int i) throws SQLException {
            FAQ faq =new FAQ();
            faq.setId(resultSet.getInt("id"));
            faq.setQuestion(resultSet.getString("question"));
            faq.setAnswer(resultSet.getString("answer"));
            faq.setStatus(resultSet.getString("status"));
            return faq;
        }
    };
    @Autowired
    public FaqRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void saveFAQ(FAQ faq) {
        String query="insert into faq(question,  status, answer) values(?,?,?)";
        jdbcTemplate.update(query, faq.getQuestion(),faq.getStatus(),faq.getAnswer());
    }

    @Override
    public List<FAQ> activeFAQ() {
        String query="select * from  faq where status='activated'";
        List<FAQ> faq=jdbcTemplate.query(query,FaqRowMapper);
        return faq;
    }

    @Override
    public List<FAQ> findUnanswered() {
        String query="select * from  faq where answer='---'";
        List<FAQ> faq=jdbcTemplate.query(query,FaqRowMapper);
        return faq;
    }
}
