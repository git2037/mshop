package com.mshop.app.user.scheduling;

import com.mshop.app.user.service.UserWorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserScheduler {
    private final UserWorkerService  userWorkerService;

    @Scheduled(fixedDelay = 600000)
    public void runMissingProfilesInDBJob() {
        log.info("Starting missing profiles in db job...");
        userWorkerService.removeAccountNotExistInDB();
        log.info("Missing profiles in db job finished.");
    }

    @Scheduled(fixedDelay = 600000)
    public void publishEventFromOutbox() {
        log.info("Starting publish event from outbox job...");
        userWorkerService.publishEventFromOutbox();
        log.info("Publish event from outbox job finished.");
    }
}
