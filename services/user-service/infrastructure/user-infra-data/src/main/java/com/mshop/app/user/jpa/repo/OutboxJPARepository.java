package com.mshop.app.user.jpa.repo;

import com.mshop.app.user.jpa.entity.OutboxEventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJPARepository extends JpaRepository<OutboxEventEntity, String> {

    List<OutboxEventEntity> findAllBySentAtIsNullOrderByCreatedAtDesc(Pageable pageable);
}
