package com.mshop.app.user.mapper;

import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.User;
import com.mshop.app.user.request.UserCreationRequest;
import com.mshop.app.user.request.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper {
    User toUser(UserCreationRequest request);

    KeycloakAccount toAccunt(UserCreationRequest request);

    User toUser(UserUpdateRequest request);
}
