package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;

public class UserMapper {


    public static UserDto toDTO(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreateAt(savedUser.getCreateAt());
        userDto.setUpdateAt(savedUser.getUpdateAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setPhone(savedUser.getPhone());

        return  userDto;
    }
}
