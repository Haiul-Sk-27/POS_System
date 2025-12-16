package com.StoreHub.StoreHub.pos.system.payload.response;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDto user;
}
