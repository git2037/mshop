package com.mshop.app.product.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.product.jpa.entity.AttributeValueEntity;
import com.mshop.app.product.model.AttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeValueMapper extends BaseMapper<AttributeValueEntity, AttributeValue> {
}