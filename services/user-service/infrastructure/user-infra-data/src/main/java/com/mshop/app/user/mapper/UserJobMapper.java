package com.mshop.app.user.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.user.jpa.entity.UserJobEntity;
import com.mshop.app.user.model.UserJob;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserJobMapper extends BaseMapper<UserJobEntity, UserJob> {
}