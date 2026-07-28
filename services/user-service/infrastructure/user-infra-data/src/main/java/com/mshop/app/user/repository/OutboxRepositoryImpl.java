package com.mshop.app.user.repository;

import com.mshop.app.user.jpa.entity.OutboxEventEntity;
import com.mshop.app.user.jpa.repo.OutboxJPARepository;
import com.mshop.app.user.mapper.OutboxMapper;
import com.mshop.app.user.model.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJPARepository outboxJPARepository;
    private final OutboxMapper outboxMapper;

    @Override
    @Transactional
    public void saveAll(List<OutboxEvent> outboxEventList) {
        log.info("Save all event to outbox");
        List<OutboxEventEntity> eventEntities = outboxEventList.stream()
                .map(outboxMapper::toEntity).toList();
        outboxJPARepository.saveAll(eventEntities);
    }

    @Override
    public List<OutboxEvent> getEventNotSent(int size) {
        log.info("Fetching unsent outbox events");
        Pageable pageable = PageRequest.of(0, size);
        return outboxJPARepository.findAllBySentAtIsNullOrderByCreatedAtDesc(pageable)
                .stream().map(outboxMapper::toDto)
                .toList();
    }

    @Override
    public void markSent(OutboxEvent outboxEvent) {
        log.info("Marked outbox event id={} as SENT", outboxEvent.getId());
        OutboxEventEntity entity = outboxMapper.toEntity(outboxEvent);
        entity.setSentAt(Instant.now());
        outboxJPARepository.save(entity);
    }
}
