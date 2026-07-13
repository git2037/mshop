package com.mshop.app.user.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.user.mapper.RequestMapper;
import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.User;
import com.mshop.app.user.request.UserCreationRequest;
import com.mshop.app.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<User> create(@RequestBody @Valid UserCreationRequest request) {
        User user = requestMapper.toUser(request);
        KeycloakAccount account = requestMapper.toAccunt(request);

        log.info("Creating user...");
        User createdUser = authService.register(user, account);
        log.info("Successfully created user");

        return ApiResponse.buidSuccessResponse("Create user successfully", createdUser);
    }
}
