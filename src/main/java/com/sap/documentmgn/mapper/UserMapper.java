package com.sap.documentmgn.mapper;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", qualifiedByName = "copyRoles")
    @Mapping(target = "password", ignore = true)
    UserDTO toUserDTO(User user);

    @Mapping(target = "roles", qualifiedByName = "copyRoles")
    User toEntity(UserDTO userDTO);

    @Named("copyRoles")
    default List<ROLES> copyRoles(List<ROLES> roles) {
        if (roles == null) {
            return null;
        }
        return new ArrayList<>(roles);
    }
}