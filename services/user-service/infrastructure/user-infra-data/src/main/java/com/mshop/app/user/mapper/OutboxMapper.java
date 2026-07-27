package com.mshop.app.user.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.user.jpa.entity.OutboxEventEntity;
import com.mshop.app.user.model.OutboxEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxMapper extends BaseMapper<OutboxEventEntity, OutboxEvent> {
}
