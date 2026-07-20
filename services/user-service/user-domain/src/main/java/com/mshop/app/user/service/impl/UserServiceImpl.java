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

    private static final String DISABLE_ACTION = "Disable";
    private static final String ENABLE_ACTION = "Enable";

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

        if (disabledUser.getKeycloakDisabled() == null) {
            String keycloakId = disabledUser.getKeycloakId();
            log.info("Disabling keycloak account with id {}", keycloakId);
            boolean isDisableAccountSuccess = callKeycloakAction(
                    () -> keycloakRepository.disableAccount(keycloakId),
                    keycloakId, DISABLE_ACTION);
            if (!isDisableAccountSuccess)
                return;
            log.info("Keycloak account with id {} is disabled", keycloakId);

            log.info("Setting 'keycloak_disabled' field in DB to disabled for userId={}", id);
            user.setKeycloakDisabled(LocalDateTime.now(ZoneId.systemDefault()));
            updateKeycloakDisabledField(user, DISABLE_ACTION);
        }
    }

    @Override
    public void enableUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> throwUserWithIdNotFound(id));
        if (user.getDeleted() == null)
            return;

        log.info("Enabling user with id {}", id);
        user.setDeleted(null);
        User enabledUser = userRepository.update(user);
        log.info("User with id {} is enabled", id);

        if (enabledUser.getKeycloakDisabled() != null) {
            String keycloakId = enabledUser.getKeycloakId();
            log.info("Enabling keycloak account with id {}", keycloakId);
            boolean isEnableAccountSuccess = callKeycloakAction(
                    () -> keycloakRepository.enableAccount(keycloakId),
                    keycloakId, ENABLE_ACTION);
            if (!isEnableAccountSuccess)
                return;
            log.info("Keycloak account with id {} is enabled", keycloakId);

            log.info("Setting 'keycloak_disabled' field in DB to enabled for userId={}", id);
            user.setKeycloakDisabled(null);
            updateKeycloakDisabledField(user, ENABLE_ACTION);
        }
    }

    private UserNotFoundException throwUserWithIdNotFound(String userId) {
        log.warn("User with id {} not found", userId);
        return new UserNotFoundException(UserCode.USER_NOT_FOUND);
    }

    private boolean callKeycloakAction(Runnable runnable, String keycloakId, String actionName) {
        try {
            runnable.run();
            return true;
        } catch (Exception e) {
            log.error("{} keycloak account with keycloak id={} failed", actionName, keycloakId, e);
            return false;
        }
    }

    private void updateKeycloakDisabledField(User user, String actionName) {
        try {
            userRepository.update(user);
            log.info("Set 'keycloak_disabled' field to {} successfully", actionName);
        } catch (Exception e) {
            log.error("Mark keycloak {} in DB failed id={}", actionName, user.getId(), e);
        }
    }
}
