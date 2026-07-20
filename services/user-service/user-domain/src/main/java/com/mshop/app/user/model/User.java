package com.mshop.app.user.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
public class User extends BaseDto {
    private String keycloakId;

    private String email;

    private String fullName;

    private String phoneNumber;

    private LocalDateTime keycloakDisabled;
}
