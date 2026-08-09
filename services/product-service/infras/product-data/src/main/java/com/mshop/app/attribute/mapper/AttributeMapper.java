package com.mshop.app.attribute.mapper;

import com.mshop.app.attribute.jpa.entity.AttributeEntity;
import com.mshop.app.attribute.model.Attribute;
import com.mshop.app.common.core.mapper.BaseMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeMapper extends BaseMapper<AttributeEntity, Attribute> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AttributeEntity partialUpdate(Attribute attribute, @MappingTarget AttributeEntity attributeEntity);
}