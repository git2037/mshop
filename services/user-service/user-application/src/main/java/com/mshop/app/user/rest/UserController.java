package com.mshop.app.user.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import com.mshop.app.security.anotation.IsAdmin;
import com.mshop.app.security.anotation.RequireAuthenticate;
import com.mshop.app.security.service.AuthenticationService;
import com.mshop.app.user.mapper.RequestMapper;
import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.User;
import com.mshop.app.user.request.UserCreationRequest;
import com.mshop.app.user.request.UserUpdateRequest;
import com.mshop.app.user.search.UserSearchConfig;
import com.mshop.app.user.service.AuthService;
import com.mshop.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final RequestMapper requestMapper;
    private final AuthService authService;
    private final UserSearchConfig searchConfig;
    private final UserService userservice;
    private final AuthenticationService authenticationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @IsAdmin
    public ApiResponse<List<User>> getAllUsers(@RequestParam(required = false, name = "sort") List<String> sort,
                                               @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, searchConfig);

        log.info("Fetching user list...");
        List<User> users = userservice.findAll(query);
        log.info("Successfully retrieved user list.");

        return ApiResponse.buildSuccessResponse("Users fetched successfully", users);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @IsAdmin
    public ApiResponse<User> getUserById(@PathVariable("id") String userId) {
        log.info("Getting user by id: {}", userId);
        User user = userservice.findById(userId);
        log.info("Successfully retrieved user by id: {}", userId);

        return ApiResponse.buildSuccessResponse("Get user information successfully", user);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @RequireAuthenticate
    public ApiResponse<User> getCurrentUser() {
        String keycloakId = authenticationService.getCurrentUserId();

        log.info("Getting user by Keycloak id: {}", keycloakId);
        User user = userservice.findByKeycloakId(keycloakId);
        log.info("Successfully retrieved user by Keycloak id: {}", keycloakId);

        return ApiResponse.buildSuccessResponse("Get user information successfully", user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<User> create(@RequestBody @Valid UserCreationRequest request) {
        User user = requestMapper.toUser(request);
        KeycloakAccount account = requestMapper.toAccunt(request);

        log.info("Creating user with email={}", request.getEmail());
        User createdUser = authService.register(user, account);
        log.info("Successfully created user");

        return ApiResponse.buildSuccessResponse("Create user successfully", createdUser);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @RequireAuthenticate
    public ApiResponse<User> update(@RequestBody @Valid UserUpdateRequest request) {
        String keycloakId = authenticationService.getCurrentUserId();

        User user = requestMapper.toUser(request);

        log.info("Updating user with keycloak id={}", keycloakId);
        User userUpdated = userservice.updateProfile(keycloakId, user);
        log.info("Successfully updated user");

        return ApiResponse.buildSuccessResponse("Update user successfully", userUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @IsAdmin
    public ApiResponse<Void> implementSoftDelete(@PathVariable("id") String userId) {
        log.info("Implementing soft-delete user with id={}", userId);
        userservice.disableUser(userId);
        log.info("Successfully implementing soft-delete user");

        return ApiResponse.buildSuccessResponse("Soft-delete user successfully", null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @IsAdmin
    public ApiResponse<Void> activeUser(@PathVariable("id") String userId) {
        log.info("Starting activate user with id={}", userId);
        userservice.enableUser(userId);
        log.info("Successfully activated user");

        return ApiResponse.buildSuccessResponse("Enable user successfully", null);
    }
}
