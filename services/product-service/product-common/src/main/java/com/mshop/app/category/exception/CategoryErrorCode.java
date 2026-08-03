package com.mshop.app.category.exception;

import com.mshop.app.common.core.constant.ErrorMessage;
import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum CategoryErrorCode implements ErrorCode {
    NAME_NOT_BLANK("CATEGORY_001", "Category name can not blank"),
    CODE_NOT_BLANK("CATEGORY_002", "Category code can not blank"),
    NAME_NOT_BLANK_IF_PRESENT("CATEGORY_003", "Category name must not be blank if present"),
    CODE_NOT_BLANK_IF_PRESENT("CATEGORY_004", "Category code must not be blank if present"),
    PARENT_ID_NOT_BLANK_IF_PRESENT("CATEGORY_005", "Parent id must not be blank if present"),

    CATEGORY_NOT_FOUND("CATEGORY_404", "Category not found"),
    CATEGORY_ALREADY_EXIST("CATEGORY_400", "Category already exist"),
    CATEGORY_CAN_NOT_MOVE("CATEGORY_444", "Category cannot move to its own subtree"),

    // invalid UserCode
    INVALID_USER_CODE("USER_999", ErrorMessage.GENERIC),
    ;

    private final String code;
    private final String message;

    public static CategoryErrorCode fromName(String codeName) {
        try {
            return CategoryErrorCode.valueOf(codeName);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert parameter to category code Enum. Invalid value provided: '{}'", codeName);
            throw new SystemException(CategoryErrorCode.INVALID_USER_CODE);
        }
    }
}
