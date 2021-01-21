package com.dbms.cafe.repositories;

import com.dbms.cafe.models.Cart;
import com.dbms.cafe.models.Feedback;

import java.util.List;

public interface FeedbackRepository {
    public void saveFeedback(Feedback feedback);
    public List<Feedback> activeFeedback();
    public List<Feedback> findAll();
    public Feedback findFeedbackById(int Id);
}
