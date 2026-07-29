package com.mshop.app.user.service.impl;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.user.util.OutboxEventFactory;
import com.mshop.app.user.exception.UserAlreadyExistsException;
import com.mshop.app.user.exception.UserCode;
import com.mshop.app.user.exception.UserNotFoundException;
import com.mshop.app.user.model.OutboxEvent;
import com.mshop.app.user.model.User;
import com.mshop.app.user.repository.OutboxRepository;
import com.mshop.app.user.repository.UserRepository;
import com.mshop.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;
    private final OutboxEventFactory outboxEventFactory;

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
    @Transactional
    public void disableUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> throwUserWithIdNotFound(id));
        if (user.getDeleted() != null) {
            log.warn("User with id {} has been disabled. Ignoring", id);
            return;
        }


        log.info("Disabling user with id {}", id);
        user.setDeleted(Instant.now());
        user.setVersion(user.getVersion() + 1);
        User disabledUser = userRepository.update(user);
        log.info("User with id {} is disabled", id);

        saveOutbox(outboxEventFactory.keycloakDisable(disabledUser));
    }

    @Override
    @Transactional
    public void enableUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> throwUserWithIdNotFound(id));
        if (user.getDeleted() == null) {
            log.warn("User with id {} has been enabled. Ignoring", id);
            return;
        }

        log.info("Enabling user with id {}", id);
        user.setDeleted(null);
        user.setVersion(user.getVersion() + 1);
        User enabledUser = userRepository.update(user);
        log.info("User with id {} is enabled", id);

        saveOutbox(outboxEventFactory.keycloakEnable(enabledUser));
    }

    @Override
    public User findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> {
                    log.warn("User with keycloak id={} not found", keycloakId);
                    return new UserNotFoundException(UserCode.USER_NOT_FOUND);
                });
    }

    private UserNotFoundException throwUserWithIdNotFound(String userId) {
        log.warn("User with id {} not found", userId);
        return new UserNotFoundException(UserCode.USER_NOT_FOUND);
    }

    private void saveOutbox(OutboxEvent event) {
        log.info("Save event to outbox table");
        outboxRepository.save(event);
        log.info("Successfully save Outbox event");
    }
}
