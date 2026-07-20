package com.mshop.app.user.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<User>> getAllUsers(@RequestParam(required = false, name = "sort") List<String> sort,
                                               @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, searchConfig);

        log.info("Fetching user list...");
        List<User> users = userservice.findAll(query);
        log.info("Successfully retrieved user list.");

        return ApiResponse.buidSuccessResponse("Users fetched successfully", users);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<User> create(@RequestBody @Valid UserCreationRequest request) {
        User user = requestMapper.toUser(request);
        KeycloakAccount account = requestMapper.toAccunt(request);

        log.info("Creating user with email={}", request.getEmail());
        User createdUser = authService.register(user, account);
        log.info("Successfully created user");

        return ApiResponse.buidSuccessResponse("Create user successfully", createdUser);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<User> update(@RequestBody @Valid UserUpdateRequest request) {
        // TODO: integrate Spring Security (JWT).
        //       Get current user from token
        //       Remove dependency on client-provided in UserUpdateRequest
        String id = "9c90b0f5-1bf5-4181-870a-3897fa280cc8";

        User user = requestMapper.toUser(request);

        log.info("Updating user with id={}", id);
        User userUpdated = userservice.updateProfile(id, user);
        log.info("Successfully updated user");

        return ApiResponse.buidSuccessResponse("Update user successfully", userUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> implementSoftDelete(@PathVariable("id") String id) {
        log.info("Implementing soft-delete user with id={}", id);
        userservice.disableUser(id);
        log.info("Successfully implementing soft-delete user");

        return ApiResponse.buidSuccessResponse("Soft-delete user successfully", null);
    }
}
