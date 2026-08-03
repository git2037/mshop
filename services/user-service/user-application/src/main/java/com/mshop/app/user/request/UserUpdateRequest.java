package com.mshop.app.user.request;

import com.mshop.app.common.core.validator.NotBlankIfPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserUpdateRequest {
    @NotBlankIfPresent(message = "FULL_NAME_NOT_BLANK")
    private String fullName;

    @Pattern(regexp = "\\d{10}", message = "INVALID_PHONE_NUMBER")
    private String phoneNumber;
}