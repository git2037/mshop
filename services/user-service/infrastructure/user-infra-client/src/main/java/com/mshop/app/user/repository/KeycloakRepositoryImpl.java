package com.mshop.app.user.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshop.app.common.core.exception.AppException;
import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.exception.SystemException;
import com.mshop.app.user.exception.KeycloakCode;
import com.mshop.app.user.exception.KeycloakConfigurationException;
import com.mshop.app.user.exception.UserAlreadyExistsException;
import com.mshop.app.user.exception.UserNotFoundException;
import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.KeycloakHttpRequest;
import com.mshop.app.user.model.UserRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mshop.app.user.constant.OAuth2ClientConstant.USER_SERVICE_CLIENT;
import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Repository
@Slf4j
@RequiredArgsConstructor
public class KeycloakRepositoryImpl implements KeycloakRepository {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${keycloak.admin-user-uri}")
    private String adminUserUri;

    private static final String LOCATION_HEADER = "location";

    @Override
    public Optional<String> createAccount(KeycloakAccount account) {
        UserRepresentation user = UserRepresentation.create(account.getEmail(), account.getPassword());
        log.info("Calling Keycloak to create user with email={}", user.getEmail());
        log.debug("Calling Keycloak to create user with body={}", getRequestBody(user));

        KeycloakHttpRequest request = KeycloakHttpRequest.builder()
                .method(HttpMethod.POST).uri(adminUserUri).body(user).build();
        ResponseEntity<Void> resp = callKeycloak(request, new ParameterizedTypeReference<>() {
        });

        if (resp.getStatusCode().is2xxSuccessful()) {
            log.info("User successfully processed in Keycloak");
            String location = resp.getHeaders().getFirst(LOCATION_HEADER);
            log.debug("Keycloak user created. Location header: {}", location);
            String id = extractKeycloakIdFromLocationHeader(location);
            return Optional.of(id);
        }

        return Optional.empty();
    }

    @Override
    public void deleteAccount(String id) {
        String userDetailUri = adminUserUri + "/" + id;

        log.info("Calling Keycloak to delete user with id={}", id);

        KeycloakHttpRequest request = KeycloakHttpRequest.builder()
                .method(HttpMethod.DELETE).uri(userDetailUri).body(null).build();
        callKeycloak(request, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public void disableAccount(String keycloakId) {
        String userDetailUri = adminUserUri + "/" + keycloakId;

        log.info("Calling Keycloak to disable user with id={}", keycloakId);
        UserRepresentation user = UserRepresentation.buildFromEnabled(Boolean.FALSE);
        log.debug("Calling Keycloak to disable user with body={}", getRequestBody(user));

        KeycloakHttpRequest request = KeycloakHttpRequest.builder()
                .method(HttpMethod.PUT).uri(userDetailUri).body(user).build();
        callKeycloak(request, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public void enableAccount(String keycloakId) {
        String userDetailUri = adminUserUri + "/" + keycloakId;

        log.info("Calling Keycloak to enable user with id={}", keycloakId);
        UserRepresentation user = UserRepresentation.buildFromEnabled(Boolean.TRUE);
        log.debug("Calling Keycloak to enable user with body={}", getRequestBody(user));

        KeycloakHttpRequest request = KeycloakHttpRequest.builder()
                .method(HttpMethod.PUT).uri(userDetailUri).body(user).build();
        callKeycloak(request, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public List<KeycloakAccount> getAccountsCreatedAfter(long createdAfter, int size) {
        log.info("Calling Keycloak to get accounts created after {}",
                Instant.ofEpochMilli(createdAfter));

        URI uri = UriComponentsBuilder.fromUriString(adminUserUri)
                .queryParam("createdAfter", createdAfter)
                .queryParam("max", size)
                .build().toUri();
        log.debug("Calling Keycloak to get accounts uri={}", uri);

        KeycloakHttpRequest request = KeycloakHttpRequest.builder()
                .method(HttpMethod.GET).uri(uri.toString()).body(null).build();
        ResponseEntity<List<KeycloakAccount>> responseEntity = callKeycloak(request,
                new ParameterizedTypeReference<>() {
                });

        List<KeycloakAccount> accounts = responseEntity.getBody();

        if (accounts == null) {
            log.info("No keycloak accounts found");
            return Collections.emptyList();
        }
        accounts.forEach(account -> log.info("{}", account.toString()));
        log.info("Fetching accounts from Keycloak successful");

        return accounts.stream()
                .filter(account -> account.getEmail() != null)
                .toList();
    }

    private <T> ResponseEntity<T> callKeycloak(KeycloakHttpRequest req, ParameterizedTypeReference<T> typeRef) {
        try {
            RestClient.RequestBodySpec request = restClient.method(req.getMethod())
                    .uri(req.getUri())
                    .attributes(clientRegistrationId(USER_SERVICE_CLIENT));

            if (req.getBody() != null) {
                request.body(req.getBody());
            }

            return request.retrieve()
                    .toEntity(typeRef);
        } catch (HttpStatusCodeException exception) {
            throw catchRestClientException(exception);
        }
    }

    private AppException catchRestClientException(HttpStatusCodeException exception) {
        if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) { //400
            log.error("Keycloak returned 400 Bad Request. Keycloak response: {}", exception.getResponseBodyAsString());
            return new KeycloakConfigurationException(KeycloakCode.KEYCLOAK_BAD_REQUEST, exception);
        }

        if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) { //401
            log.error("Keycloak returned 401 Bad Request. Keycloak response: {}", exception.getResponseBodyAsString());
            return new KeycloakConfigurationException(KeycloakCode.KEYCLOAK_UNAUTHORIZED, exception);
        }

        if (exception.getStatusCode() == HttpStatus.FORBIDDEN) { //403
            log.error("Keycloak returned 403 Forbidden. Reason: Client {} doesn't have permission to process. Response: {}",
                    USER_SERVICE_CLIENT, exception.getResponseBodyAsString());
            return new KeycloakConfigurationException(KeycloakCode.KEYCLOAK_FORBIDDEN, exception);
        }

        if (exception.getStatusCode() == HttpStatus.NOT_FOUND) { //404
            log.warn("Keycloak returned 404 Not Found. User missing in Keycloak.");
            return new UserNotFoundException(KeycloakCode.KEYCLOAK_NOT_FOUND);
        }

        if (exception.getStatusCode() == HttpStatus.CONFLICT) { //409
            log.warn("Keycloak returned 409 Conflict. User already exists");
            return new UserAlreadyExistsException(KeycloakCode.KEYCLOAK_CONFLICT);
        }

        if (exception.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) { //500
            log.error("Keycloak 500 response: Keycloak internal server error");
            return new SystemException(KeycloakCode.KEYCLOAK_ERROR, exception);
        }

        log.error("Unexpected error when calling Keycloak. HTTP status = {}", exception.getStatusCode());
        return new SystemException(SystemCode.UNEXPECTED_ERROR, exception);
    }

    private String extractKeycloakIdFromLocationHeader(String location) {
        if (location == null || location.isBlank()) {
            log.error("Keycloak registration succeeded (201) but 'Location' header is missing or blank. Cannot extract Keycloak UserId");
            throw new SystemException(KeycloakCode.KEYCLOAK_MISSING_LOCATION_HEADER);
        }

        return location.substring(location.lastIndexOf("/") + 1);
    }

    private String getRequestBody(Object body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.error("Error getting JSON body", e);
            return null;
        }
    }
}
