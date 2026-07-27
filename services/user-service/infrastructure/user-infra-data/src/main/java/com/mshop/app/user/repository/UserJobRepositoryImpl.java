package com.mshop.app.user.repository;

import com.mshop.app.user.jpa.repo.UserJobJPARepository;
import com.mshop.app.user.mapper.UserJobMapper;
import com.mshop.app.user.model.UserJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserJobRepositoryImpl implements UserJobRepository{

    private final UserJobMapper jobMapper;
    private final UserJobJPARepository jpaRepository;

    @Override
    public Optional<UserJob> findById(String id) {
        log.info("Fetching user job with name = {} from the database", id);
        return jpaRepository.findById(id).map(jobMapper::toDto);
    }

    @Override
    public void updateLastProcessedTime(UserJob userJob) {
        log.info("Updating user job with name = {} to the database", userJob.getName());
        jpaRepository.save(jobMapper.toEntity(userJob));
    }
}
