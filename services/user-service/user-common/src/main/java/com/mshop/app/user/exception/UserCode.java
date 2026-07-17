package com.mshop.app.user.exception;

import com.mshop.app.common.core.constant.ErrorMessage;
import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public enum UserCode implements ErrorCode {
    // validation
    EMAIL_NOT_BLANK("USER_001", "Email cannot be blank"),
    INVALID_EMAIL_FORMAT("USER_002", "Invalid email format"),
    FULL_NAME_NOT_BLANK("USER_003", "Full name cannot be blank"),
    PASSWORD_NOT_BLANK("USER_004", "Password cannot be blank"),
    PASSWORD_MIN_LENGTH("USER_005", "Password length cannot be less than {min} characters"),
    INVALID_PHONE_NUMBER("USER_006", "Phone number must be a valid 10-digit format (e.g., 0912345678)"),

    // bussiness
    USER_NOT_FOUND("USER_404", "User not found."),
    USER_ALREADY_EXIST("USER_ALREADY_EXIST", "User already exist."),

    // invalid UserCode
    INVALID_USER_CODE("USER_999", ErrorMessage.GENERIC),
    ;


    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static UserCode fromName(String userCodeName){
        try {
            return UserCode.valueOf(userCodeName);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert parameter to user code Enum. Invalid value provided: '{}'", userCodeName);
            throw new SystemException(UserCode.INVALID_USER_CODE);
        }
    }
}
