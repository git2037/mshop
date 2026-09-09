package com.mshop.app.product.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

@Getter
@Setter
@SuperBuilder
public class Product extends BaseDto {
    private String name;
    private String description;
    private String thumbnail;
    private String code;

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + StringUtils.abbreviate(description, 15) + "'")
                .add("code='" + code + "'")
                .toString();
    }
}