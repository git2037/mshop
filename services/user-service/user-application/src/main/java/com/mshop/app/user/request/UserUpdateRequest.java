package com.mshop.app.user.request;

import com.mshop.app.user.validator.NotBlankIfPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserUpdateRequest {
    @NotBlankIfPresent
    private String fullName;

    @Pattern(regexp = "\\d{10}", message = "INVALID_PHONE_NUMBER")
    private String phoneNumber;
}