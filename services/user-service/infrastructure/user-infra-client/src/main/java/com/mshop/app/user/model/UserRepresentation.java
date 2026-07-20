package com.mshop.app.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserRepresentation {

    private String email;
    private Boolean enabled;
    private List<String> requiredActions;
    private List<Credential> credentials;

    public static UserRepresentation create(String email, String rawPassword) {
        List<Credential> credentials = List.of(Credential.builder()
                .temporary(false)
                .value(rawPassword).build());

        return new UserRepresentation(email, true, List.of(), credentials);
    }

    public static UserRepresentation buildFromEnabled(Boolean enabled) {
        return new UserRepresentation(null, enabled, null, null);
    }

    @Builder
    @Getter
    @Setter
    public static class Credential {
        private String value;
        private Boolean temporary;
    }
}
