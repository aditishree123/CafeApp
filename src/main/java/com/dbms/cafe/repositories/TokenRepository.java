package com.dbms.cafe.repositories;

import com.dbms.cafe.models.VerificationToken;

public interface TokenRepository {
    public VerificationToken getVerificationToken(String token);
    public void save(VerificationToken verificationToken);
}