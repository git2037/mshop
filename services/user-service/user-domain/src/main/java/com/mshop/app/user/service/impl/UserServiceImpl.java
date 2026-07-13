package com.mshop.app.user.service.impl;

import com.mshop.app.user.exception.UserAlreadyExistsException;
import com.mshop.app.user.exception.UserCode;
import com.mshop.app.user.exception.UserNotFoundException;
import com.mshop.app.user.model.User;
import com.mshop.app.user.repository.UserRepository;
import com.mshop.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User createProfile(User user) {
        Optional<User> optionalUser = repository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            log.warn("User with email {} already exists", user.getEmail());
            throw new UserAlreadyExistsException(UserCode.USER_ALREADY_EXIST);
        }

        return repository.create(user);
    }

    @Override
    public User getUserProfile(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> {
                    log.warn("User with email {} not found", email);
                    return new UserNotFoundException(UserCode.USER_NOT_FOUND);
                }
        );
    }
}
