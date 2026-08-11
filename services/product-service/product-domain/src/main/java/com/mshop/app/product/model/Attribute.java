package com.mshop.app.product.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@SuperBuilder
public class Attribute extends BaseDto {
    private String name;
    private String code;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("code", code)
                .toString();
    }
}