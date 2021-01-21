package com.dbms.cafe.services;
import com.dbms.cafe.models.User;
import com.dbms.cafe.models.VerificationToken;
import com.dbms.cafe.repositories.TokenRepository;
import com.dbms.cafe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("client");
        user.setStatus("not-verified");
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.userExists(email);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken newUserToken = new VerificationToken(token,user);
        tokenRepository.save(newUserToken);
    }

    @Override
    public void enableRegisteredUser(User user) {
        userRepository.enableUser(user);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.getVerificationToken(token);
    }

    @Override
    public User findByUserId(int id) {
        return userRepository.findByUserId(id);
    }
}