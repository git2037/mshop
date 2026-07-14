package com.mshop.app.user.repository;

import com.mshop.app.user.exception.UserAlreadyExistsException;
import com.mshop.app.user.exception.UserCode;
import com.mshop.app.user.jpa.entity.UserEntity;
import com.mshop.app.user.jpa.repo.UserJPARepository;
import com.mshop.app.user.mapper.UserMapper;
import com.mshop.app.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        log.info("Fetching user with email = {} from the database", email);
        return userJPARepository.findByEmail(email).map(mapper::toUser);
    }

    @Override
    @Transactional
    public User create(User user) {
        try {
            log.info("Creating user with email = {}", user.getEmail());
            UserEntity createdUser = userJPARepository.save(mapper.toEntity(user));
            return mapper.toUser(createdUser);
        } catch (DataIntegrityViolationException exception) {
            log.warn("User with email {} already exists", user.getEmail());
            throw new UserAlreadyExistsException(UserCode.USER_ALREADY_EXIST);
        }
    }
}
