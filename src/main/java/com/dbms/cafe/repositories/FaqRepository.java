package com.dbms.cafe.repositories;

import com.dbms.cafe.models.FAQ;

import java.util.List;

public interface FaqRepository {
    public void saveFAQ(FAQ faq);
    public List<FAQ> activeFAQ();
    public List<FAQ> findUnanswered();
}
