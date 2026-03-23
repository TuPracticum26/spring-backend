package com.sap.documentmgn.mapper;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toEntity(UserDTO userDTO);
}
