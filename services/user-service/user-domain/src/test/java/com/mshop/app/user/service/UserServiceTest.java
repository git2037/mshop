package com.mshop.app.user.service;

import com.mshop.app.user.exception.UserNotFoundException;
import com.mshop.app.user.model.User;
import com.mshop.app.user.repository.KeycloakRepository;
import com.mshop.app.user.repository.UserRepository;
import com.mshop.app.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private KeycloakRepository keycloakRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .keycloakId("1234")
                .email("email@gmail.com")
                .fullName("test")
                .phoneNumber("0123456789")
                .keycloakDisabled(null)
                .id("123")
                .deleted(null)
                .build();
    }

    @Test
    void disableUser_should_throw_exception_when_not_found_user() {
        String id = "123";
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.disableUser(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void disableUser_should_not_call_keycloak_when_user_disabled() {
        String id = user.getId();
        user.setDeleted(LocalDateTime.now(ZoneId.systemDefault()));
        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        userService.disableUser(id);

        verify(userRepository, never()).update(any());
        verify(keycloakRepository, never()).disableAccount(any());
    }

    @Test
    void disableUser_should_not_mark_disable_field_in_db_when_keycloak_throw_exception() {
        String id = user.getId();
        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        when(userRepository.update(any()))
                .thenReturn(user);

        doThrow(RuntimeException.class).when(keycloakRepository).disableAccount(any());

        userService.disableUser(id);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).update(captor.capture());
        User captured = captor.getValue();
        assertThat(captured.getDeleted()).isNotNull();
    }

    @Test
    void disableUser_should_not_mark_disable_field_in_db_when_mark_disable_field_in_db_throw_exception() {
        String id = user.getId();
        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        when(userRepository.update(any()))
                .thenReturn(user)
                .thenThrow(RuntimeException.class);

        doNothing().when(keycloakRepository).disableAccount(any());

        userService.disableUser(id);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(2)).update(captor.capture());
        List<User> captured = captor.getAllValues();
        assertThat(captured.get(0).getDeleted()).isNotNull();
        assertThat(captured.get(1).getKeycloakDisabled()).isNotNull();
    }

    @Test
    void disableUser_should_mark_disable_field_in_db_when_mark_disable_field_in_db_success() {
        String id = user.getId();
        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        when(userRepository.update(any()))
                .thenReturn(user)
                .thenReturn(user);

        doNothing().when(keycloakRepository).disableAccount(any());

        userService.disableUser(id);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(2)).update(captor.capture());
        List<User> captured = captor.getAllValues();
        assertThat(captured.get(0).getDeleted()).isNotNull();
        assertThat(captured.get(1).getKeycloakDisabled()).isNotNull();
    }
}
