package com.example.back.service;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.back.dao.UserDao;
import com.example.back.dto.UserDto;
import com.example.back.entity.User;
import com.example.back.exception.UserExistsException;
import com.example.back.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public User save(SignupRequest request) {
        User user = userMapper.fromRequest(request);

        try {
            LOG.info("Saving user withusername {}", user.getUsername());
            return userDao.save(user);
        } catch (Exception ex) {
            LOG.error("Error during registration. {}", ex.getMessage());
            throw new UserExistsException(String.format("User with username %s already exist.", user.getUsername()));
        }
    }

    public User update(UserDto userDto, Principal principal) {
        User user = getUserByPrincipal(principal);
        LOG.info(String.format("Updating user with username %s", user.getUsername()));
        return userDao.save(userMapper.fromDto(userDto, user));
    }

    public User getById(Long userId) {
        return userDao.findUserById(userId).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with id %s not found.", userId))
        );
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userDao.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

}
