package com.mshop.app.product.request.attribute;

import com.mshop.app.product.constant.AttributeValueType;
import com.mshop.app.common.core.validator.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttributeRequest {

    @NotBlank(message = "ATTRIBUTE_NAME_NOT_BLANK")
    private String name;

    @ValueOfEnum(enumClass = AttributeValueType.class,
            message = "ATTRIBUTE_VALUE_INVALID")
    private String valueType;
}
