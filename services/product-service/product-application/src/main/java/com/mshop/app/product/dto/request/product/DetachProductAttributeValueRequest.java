package com.mshop.app.product.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DetachProductAttributeValueRequest {
    @NotEmpty(message = "ATTRIBUTE_VALUE_IDS_NOT_EMPTY")
    private Set<
            @NotBlank(message = "ATTRIBUTE_VALUE_ID_NOT_BLANK")
                    String> attributeValueIds;
}
