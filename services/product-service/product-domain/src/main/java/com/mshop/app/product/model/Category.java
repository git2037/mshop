package com.mshop.app.product.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Category extends BaseDto {
    private String name;
    private String code;
    private String parentId;
    private String path;
}