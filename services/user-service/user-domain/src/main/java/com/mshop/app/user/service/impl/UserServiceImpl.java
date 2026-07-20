package com.mshop.app.user.service.impl;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.user.exception.UserAlreadyExistsException;
import com.mshop.app.user.exception.UserCode;
import com.mshop.app.user.exception.UserNotFoundException;
import com.mshop.app.user.model.User;
import com.mshop.app.user.repository.KeycloakRepository;
import com.mshop.app.user.repository.UserRepository;
import com.mshop.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakRepository keycloakRepository;

    @Override
    public User createProfile(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            log.warn("User with email {} already exists", user.getEmail());
            throw new UserAlreadyExistsException(UserCode.USER_ALREADY_EXIST);
        }

        return userRepository.create(user);
    }

    @Override
    public User getUserProfile(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.warn("User with email {} not found", email);
                    return new UserNotFoundException(UserCode.USER_NOT_FOUND);
                }
        );
    }

    @Override
    public List<User> findAll(Query request) {
        return userRepository.findAll(request);
    }

    @Override
    public User updateProfile(String userId, User user) {
        if (userRepository.existsActiveUserById(userId)) {
            return userRepository.update(userId, user);
        } else {
            throw throwUserWithIdNotFound(userId);
        }
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> throwUserWithIdNotFound(id));
    }

    @Override
    public void disableUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> throwUserWithIdNotFound(id));
        if (user.getDeleted() != null)
            return;

        log.info("Disabling user with id {}", id);
        user.setDeleted(LocalDateTime.now(ZoneId.systemDefault()));
        User disabledUser = userRepository.update(user);
        log.info("User with id {} is disabled", id);

        String keycloakId = disabledUser.getKeycloakId();
        log.info("Disabling keycloak account with id {}", keycloakId);
        boolean isDisableAccountSuccess = isDisableKeycloakAccountSuccess(keycloakId);
        if (!isDisableAccountSuccess)
            return;
        log.info("Keycloak account with id {} is disabled", keycloakId);

        log.info("Setting 'keycloak_disabled' field in DB to disabled for userId={}", id);
        markKeycloakDisabledInDB(disabledUser);
    }

    private UserNotFoundException throwUserWithIdNotFound(String userId) {
        log.warn("User with id {} not found", userId);
        return new UserNotFoundException(UserCode.USER_NOT_FOUND);
    }

    private boolean isDisableKeycloakAccountSuccess(String keycloakId) {
        try {
            keycloakRepository.disableAccount(keycloakId);
            return true;
        } catch (Exception e) {
            log.error("Disable keycloak account with keycloak id={} failed", keycloakId, e);
            return false;
        }
    }

    private void markKeycloakDisabledInDB(User user) {
        try {
            user.setKeycloakDisabled(LocalDateTime.now(ZoneId.systemDefault()));
            userRepository.update(user);
            log.info("Set 'keycloak_disabled' field successfully");
        } catch (Exception e) {
            log.error("Mark keycloak disable in DB failed id={}", user.getId(), e);
        }
    }
}
