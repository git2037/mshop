package com.mshop.app.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakAccount {
    private String id;
    private String email;
    private String password;
    private Long createdTimestamp;

    @Override
    public String toString() {
        return "KeycloakAccount{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
