package com.mshop.app.user.service;

public interface UserWorkerService {
    void removeAccountNotExistInDB();
    void publishEventFromOutbox();
}
