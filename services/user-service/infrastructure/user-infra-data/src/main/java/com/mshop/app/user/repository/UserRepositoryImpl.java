package com.mshop.app.user.repository;

import com.mshop.app.common.core.jpa.spec.SpecificationBuilder;
import com.mshop.app.common.core.searching.filter.FilterCondition;
import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.sort.SortBuilder;
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

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(Query query) {
        Sort sort = SortBuilder.buildSort(query.getSortBy());
        Pagination pagination = query.getPagination();
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize(), sort);

        Specification<UserEntity> specification = Specification.unrestricted();
        for (FilterCondition condition : query.getFilters()) {
            specification = specification.and(SpecificationBuilder.buildSpecification(condition));
        }

        log.info("Fetching user list from database.");
        log.debug("Fetching user list with page: {}, size: {} from db", pageable.getPageNumber(), pageable.getPageSize());
        Page<UserEntity> entityPage = userJPARepository.findAll(specification, pageable);

        return entityPage.getContent().stream().map(mapper::toUser).toList();
    }

    @Override
    @Transactional
    public User update(String userId, User user) {
        UserEntity entity = userJPARepository.findByIdAndDeletedIsNull(userId)
                .orElseThrow();

        mapper.updateUserEntityFromDto(user, entity);

        log.info("Updating user with id = {} from db", userId);
        UserEntity updatedUser =  userJPARepository.save(entity);
        return mapper.toUser(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsActiveUserById(String userId) {
        return userJPARepository.existsByIdAndDeletedIsNull(userId);
    }
}
