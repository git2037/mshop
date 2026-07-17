package com.mshop.app.user.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.user.jpa.entity.UserEntity;
import com.mshop.app.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<UserEntity, User> {

    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "maskPhone")
    User toUser(UserEntity entity);

    @Named("maskPhone")
    default String mask(String phone) {
        if (phone == null || phone.length() < 7) return phone;

        int prefix = 3;
        int suffix = 3;

        return phone.substring(0, prefix) +
                "*".repeat(phone.length() - prefix - suffix) +
                phone.substring(phone.length() - suffix);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserEntityFromDto(User dto, @MappingTarget UserEntity entity);
}
