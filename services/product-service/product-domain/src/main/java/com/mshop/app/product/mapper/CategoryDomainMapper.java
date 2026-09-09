package com.mshop.app.product.mapper;

import com.mshop.app.product.constant.ProductServiceConstant;
import com.mshop.app.product.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDomainMapper {

    public void update(Category updateRequest, Category category) {
        String name = category.getName();
        String payloadName = updateRequest.getName();
        if (isUpdate(name, payloadName)) {
            category.setName(payloadName);
        }

        String path = category.getPath();
        String payloadCode = updateRequest.getCode();
        if (isUpdate(path, payloadCode)) {
            category.setPath(createNewPath(path, payloadCode));
            category.setCode(payloadCode);
        }
    }

    private boolean isUpdate(String oldValue, String newValue) {
        return (newValue != null) && (!newValue.equals(oldValue));
    }

    private String createNewPath(String oldPath, String newCode) {
        return oldPath.substring(0,
                oldPath.lastIndexOf(ProductServiceConstant.FORWARD_SLASH) + 1)
                + newCode;
    }
}
