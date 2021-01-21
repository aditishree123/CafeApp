package com.dbms.cafe.repositories;

import com.dbms.cafe.models.User;
import com.dbms.cafe.models.VerificationToken;

public interface UserRepository {

    public User findByEmail(String email);
    public boolean userExists(String email);
    public void save(User user);
    public void enableUser(User user);
    public User findByUserId(int id);
    public void updateUser(String email,String firstname,String middlename,String lastname, String flat, String landmark,
                           String locality,String contact);
}

