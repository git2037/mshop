package com.mshop.app.product.exception;

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
    PRODUCT_CODE_NOT_BLANK("PRODUCT_002", "Product code can not blank"),

    PRODUCT_NOT_FOUND("PRODUCT_404", "Product not found"),
    PRODUCT_ALREADY_EXIST("PRODUCT_400", "Product already exist"),
    PRODUCT_ATTRIBUTE_VALUE_ALREADY_EXIST("PRODUCT_401", "Duplicate attribute value detected in this product."),

    // attribute code
    ATTRIBUTE_NAME_NOT_BLANK("ATTRIBUTE_001", "Attribute name can not blank"),
    ATTRIBUTE_CODE_NOT_BLANK("ATTRIBUTE_002", "Attribute code can not blank"),

    ATTRIBUTE_ALREADY_EXIST("ATTRIBUTE_400", "Attribute already exist"),
    ATTRIBUTE_ALREADY_DISABLED("ATTRIBUTE_401", "Attribute already disabled"),
    ATTRIBUTE_CODE_ALREADY_EXIST_IN_PRODUCT("ATTRIBUTE_402", "Attribute code already exist in this product."),
    ATTRIBUTE_NOT_FOUND("ATTRIBUTE_404", "Attribute not found"),

    // attribute value code
    ATTRIBUTE_VALUE_NOT_BLANK("ATTRIBUTE_VALUE_001", "Attribute value can not blank"),
    ATTRIBUTE_VALUE_IDS_NOT_EMPTY("ATTRIBUTE_VALUE_002", "Attribute value id list must not be empty"),
    ATTRIBUTE_VALUE_CODE_NOT_BLANK("ATTRIBUTE_VALUE_003", "Attribute value code must not be blank"),

    ATTRIBUTE_VALUE_ID_NOT_BLANK("ATTRIBUTE_VALUE_403", "Attribute value id must not be blank"),
    ATTRIBUTE_VALUE_NOT_FOUND("ATTRIBUTE_VALUE_404", "Attribute value not found"),
    ATTRIBUTE_VALUE_ALREADY_EXIST("ATTRIBUTE_VALUE_400", "This attribute value already exists."),
    DUPLICATED_ATTRIBUTE_CODE("ATTRIBUTE_VALUE_401", "Duplicated attribute code"),

    // product image
    PRODUCT_IMAGES_NOT_NULL("PRODUCT_IMAGE_001", "Product images can not be null"),
    PRODUCT_IMAGE_FILE_NAMES_NOT_NULL("PRODUCT_IMAGE_002", "File names can not be null"),
    PRODUCT_IMAGE_FILE_NAME_NOT_BLANK("PRODUCT_IMAGE_003", "File name can not be blank"),

    PRODUCT_IMAGE_NOT_FOUND("IMAGE_404", "Image not found in product"),
    PRODUCT_IMAGE_ALREADY_EXIST("IMAGE_400", "Image already exist in this product."),

    // file
    FILE_EMPTY("FILE_001", "File is empty"),
    FILE_SIZE_EXCEEDED("FILE_002", "Image size exceeds the maximum allowed limit of {size}MB"),
    FILE_NOT_SUPPORTED("FILE_003", "Invalid file format. Only image files {types} are allowed."),
    FILE_CORRUPTED_OR_INVALID_IMAGE("FILE_004", "The uploaded file is corrupt or not a valid image format."),
    FILE_NULL("FILE_005", "File is null"),

    // sku
    SKU_STOCK_NOT_NULL("SKU_001", "SKU stock can not be null"),
    SKU_STOCK_NOT_POSITIVE_OR_ZERO("SKU_002", "SKU stock must be positive or zero"),
    SKU_PRICE_NOT_NULL("SKU_003", "SKU price can not be null"),
    SKU_PRICE_NOT_POSITIVE("SKU_004", "SKU price must be positive"),

    SKU_ALREADY_EXIST("SKU_400", "Sku already exists"),
    SKU_DUPLICATED_ATTRIBUTE_CODE("SKU_401", "Duplicated attribute code"),
    SKU_DUPLICATE_ATTRIBUTE_VALUE_IN_SKU("SKU_402", "Duplicated attribute value in this sku"),

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
