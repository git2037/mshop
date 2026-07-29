package com.mshop.app.user.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class User extends BaseDto {
    private String keycloakId;

    private String email;

    private String fullName;

    private String phoneNumber;

    @Builder.Default
    private Integer version = 0;
}
