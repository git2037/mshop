package com.mshop.app.product.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.product.jpa.entity.SkuEntity;
import com.mshop.app.product.jpa.projection.SkuAttributeValueProjection;
import com.mshop.app.product.model.Sku;
import com.mshop.app.product.model.SkuAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuMapper extends BaseMapper<SkuEntity, Sku> {
    SkuAttributeValue toSkuAttributeValue(SkuAttributeValueProjection projection);
}