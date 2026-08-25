package com.mshop.app.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkuResponse {
    private String id;
    private String code;
    private int stock;
    private Float price;
    List<AttributeValueResponse> attributes;
}
