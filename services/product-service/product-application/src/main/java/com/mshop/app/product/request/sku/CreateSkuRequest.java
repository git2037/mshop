package com.mshop.app.product.request.sku;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSkuRequest {

    @NotNull(message = "SKU_STOCK_NOT_NULL")
    @PositiveOrZero(message = "SKU_STOCK_NOT_POSITIVE_OR_ZERO")
    private Integer stock;

    @NotNull(message = "SKU_PRICE_NOT_NULL")
    @Positive(message = "SKU_PRICE_NOT_POSITIVE")
    private Float price;

    @NotEmpty(message = "ATTRIBUTE_VALUE_IDS_NOT_EMPTY")
    private Set<
            @NotBlank(message = "ATTRIBUTE_VALUE_ID_NOT_BLANK")
                    String> attributeValueIds;
}
