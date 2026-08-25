package com.mshop.app.product.jpa.projection;

public interface SkuAttributeValueProjection {
    String getSkuId();

    String getSkuCode();

    int getStock();

    float getPrice();

    String getAttributeCode();

    String getAttributeName();

    String getAttributeValue();
}
