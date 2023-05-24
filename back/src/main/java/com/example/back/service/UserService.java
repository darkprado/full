package com.example.back.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.back.dao.UserDao;
import com.example.back.entity.User;
import com.example.back.enums.ERole;
import com.example.back.exception.UserExistsException;
import com.example.back.payload.request.SignupRequest;

import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;
    private final BCryptPasswordEncoder encoder;

    public User save(SignupRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPassword(encoder.encode(request.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user withusername {}", user.getUsername());
            return userDao.save(user);
        } catch (Exception ex) {
            LOG.error("Error during registration. {}", ex.getMessage());
            throw new UserExistsException(String.format("User with username %s alredy exist.", user.getUsername()));
        }
    }

}
