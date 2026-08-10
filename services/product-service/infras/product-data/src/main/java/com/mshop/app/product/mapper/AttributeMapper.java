package com.mshop.app.product.mapper;

import com.mshop.app.product.jpa.entity.AttributeEntity;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.common.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeMapper extends BaseMapper<AttributeEntity, Attribute> {
}