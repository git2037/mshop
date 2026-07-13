package com.mshop.app.user.request;

import com.mshop.app.user.validator.PasswordLengthConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCreationRequest {
    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "INVALID_EMAIL_FORMAT")
    private String email;

    @NotBlank(message = "FULL_NAME_NOT_BLANK")
    private String fullName;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    @PasswordLengthConstraint(min = 6)
    private String password;

    private String phoneNumber;
}