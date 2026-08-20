package com.mshop.app.product.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Product extends BaseDto {
    private String name;
    private String description;
    private String thumbnail;
    private String code;
}