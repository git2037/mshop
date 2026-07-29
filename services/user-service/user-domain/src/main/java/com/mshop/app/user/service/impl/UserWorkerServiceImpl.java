package com.mshop.app.user.service.impl;

import com.mshop.app.common.core.anotation.ConditionalOnKafkaEnabled;
import com.mshop.app.user.event.EventPublisher;
import com.mshop.app.user.util.OutboxEventFactory;
import com.mshop.app.user.exception.UserCode;
import com.mshop.app.user.exception.UserJobFoundException;
import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.OutboxEvent;
import com.mshop.app.user.model.UserJob;
import com.mshop.app.user.repository.KeycloakRepository;
import com.mshop.app.user.repository.OutboxRepository;
import com.mshop.app.user.repository.UserJobRepository;
import com.mshop.app.user.repository.UserRepository;
import com.mshop.app.user.service.UserWorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
@ConditionalOnKafkaEnabled
public class UserWorkerServiceImpl implements UserWorkerService {

    private final UserRepository userRepository;
    private final UserJobRepository userJobRepository;
    private final KeycloakRepository keycloakRepository;
    private final OutboxRepository outboxRepository;
    private final EventPublisher eventPublisher;
    private final OutboxEventFactory outboxEventFactory;

    private static final int KEYCLOAK_MAX_RESULTS = 100;
    private static final String SYNC_MISSING_PROFILES_JOB_NAME = "sync-missing-profiles";
    private static final int DB_MAX_RESULTS = 100;

    @Override
    public void removeAccountNotExistInDB() {
        UserJob syncMissingProfilesJob = getSyncMissingJobOrThrow();
        Long lastProcessedTime = syncMissingProfilesJob.getLastProcessedTime();

        List<KeycloakAccount> accounts = keycloakRepository
                .getAccountsCreatedAfter(lastProcessedTime, KEYCLOAK_MAX_RESULTS);

        if (accounts.isEmpty()) {
            log.info("No keycloak users found to clean up.");
            return;
        }

        List<String> missingKeycloakIds = getMissingKeycloakIds(accounts);
        List<OutboxEvent> outboxEvents = missingKeycloakIds.stream()
                .map(outboxEventFactory::keycloakDeleted)
                .toList();
        outboxRepository.saveAll(outboxEvents);

        updateLastProcessedTime(syncMissingProfilesJob, accounts);
    }

    @Override
    public void publishEventFromOutbox() {
        List<OutboxEvent> outboxEvents = outboxRepository.getEventNotSent(DB_MAX_RESULTS);

        outboxEvents.forEach(outboxEvent -> {
            eventPublisher.send(outboxEvent);
            markOutboxEventSent(outboxEvent);
        });
    }

    private UserJob getSyncMissingJobOrThrow() {
        return userJobRepository.findById(SYNC_MISSING_PROFILES_JOB_NAME)
                .orElseThrow(() -> {
                    log.error("User job with name={} not found", SYNC_MISSING_PROFILES_JOB_NAME);
                    return new UserJobFoundException(UserCode.USER_JOB_NOT_FOUND);
                });
    }

    private List<String> getMissingKeycloakIds(List<KeycloakAccount> accounts) {
        List<String> keycloakIds = accounts.stream()
                .map(KeycloakAccount::getId)
                .toList();

        Set<String> existingKeycloakIds = userRepository.findKeycloakIdIn(keycloakIds);

        return keycloakIds.stream()
                .filter(id -> !existingKeycloakIds.contains(id))
                .toList();
    }

    private void updateLastProcessedTime(UserJob job, List<KeycloakAccount> accounts) {
        long maxCreatedTimestamp = accounts.stream()
                .mapToLong(KeycloakAccount::getCreatedTimestamp)
                .max()
                .orElseThrow();

        job.setLastProcessedTime(maxCreatedTimestamp + 1);
        userJobRepository.updateLastProcessedTime(job);
    }

    private void markOutboxEventSent(OutboxEvent outboxEvent) {
        try {
            outboxRepository.markSent(outboxEvent);
        } catch (Exception e) {
            log.error("Event sent to Kafka but not mark sent. It may be duplicated event");
        }
    }
}
