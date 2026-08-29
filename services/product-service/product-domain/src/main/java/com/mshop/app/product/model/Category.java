package com.mshop.app.product.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.StringJoiner;

@SuperBuilder
@Getter
@Setter
public class Category extends BaseDto {
    private String name;
    private String code;
    private String parentId;
    private String path;

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("code='" + code + "'")
                .add("parentId='" + parentId + "'")
                .add("path='" + path + "'")
                .toString();
    }
}