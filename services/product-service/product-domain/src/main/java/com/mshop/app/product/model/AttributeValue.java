package com.mshop.app.product.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@SuperBuilder
public class AttributeValue extends BaseDto {
    private String attributeCode;
    private String value;
    private String valueCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", super.getId())
                .append("attributeCode", attributeCode)
                .append("value", value)
                .append("valueCode", valueCode)
                .toString();
    }
}