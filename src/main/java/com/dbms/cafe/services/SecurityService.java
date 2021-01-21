package com.dbms.cafe.services;

import com.dbms.cafe.models.User;

public interface SecurityService {
    public String findLoggedInUsername();
    public void autoLogin(String username, String password);
    public void notVerified();
}

