package com.mshop.app;

import com.mshop.app.common.core.constant.ErrorMessage;
import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum ProductServiceCode implements ErrorCode {

    //category code
    CATEGORY_NAME_NOT_BLANK("CATEGORY_001", "Category name can not blank"),
    CATEGORY_CODE_NOT_BLANK("CATEGORY_002", "Category code can not blank"),
    CATEGORY_NAME_NOT_BLANK_IF_PRESENT("CATEGORY_003", "Category name must not be blank if present"),
    CATEGORY_CODE_NOT_BLANK_IF_PRESENT("CATEGORY_004", "Category code must not be blank if present"),
    CATEGORY_PARENT_ID_NOT_BLANK_IF_PRESENT("CATEGORY_005", "Parent id must not be blank if present"),

    CATEGORY_ALREADY_EXIST("CATEGORY_400", "Category already exist"),
    CATEGORY_IS_NOT_LEAF("CATEGORY_401", "Category is not a leaf"),
    CATEGORY_IDS_NOT_EMPTY("CATEGORY_402", "Category id list must not be empty"),
    CATEGORY_ID_NOT_BLANK("CATEGORY_403", "Category id must not be blank"),
    CATEGORY_NOT_FOUND("CATEGORY_404", "Category not found"),
    CATEGORY_CAN_NOT_MOVE("CATEGORY_444", "Category cannot move to its own subtree"),

    // product code
    PRODUCT_NAME_NOT_BLANK("PRODUCT_001", "Product name can not blank"),

    PRODUCT_NOT_FOUND("PRODUCT_404", "Product not found"),
    PRODUCT_ALREADY_EXIST("PRODUCT_400", "Product already exist"),

    // attribute code
    ATTRIBUTE_NAME_NOT_BLANK("ATTRIBUTE_001", "Attribute name can not blank"),
    ATTRIBUTE_CODE_NOT_BLANK("ATTRIBUTE_002", "Attribute code can not blank"),
    ATTRIBUTE_VALUE_NOT_BLANK("ATTRIBUTE_003", "Attribute value can not blank"),
    ATTRIBUTE_VALUE_INVALID("ATTRIBUTE_003", "Invalid attribute value. Allowed values are: {values}"),

    ATTRIBUTE_ALREADY_EXIST("ATTRIBUTE_400", "Attribute already exist"),
    ATTRIBUTE_NOT_FOUND("ATTRIBUTE_404", "Attribute not found"),

    // invalid code
    INVALID_PRODUCT_CODE("PRODUCT_999", ErrorMessage.GENERIC),
    ;

    private final String code;
    private final String message;

    public static ProductServiceCode fromName(String codeName) {
        try {
            return ProductServiceCode.valueOf(codeName);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert parameter to category code Enum. Invalid value provided: '{}'", codeName);
            throw new SystemException(ProductServiceCode.INVALID_PRODUCT_CODE);
        }
    }
}
