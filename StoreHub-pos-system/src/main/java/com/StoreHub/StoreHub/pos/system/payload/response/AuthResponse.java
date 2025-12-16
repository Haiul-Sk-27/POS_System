package com.StoreHub.StoreHub.pos.system.payload.response;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;

public class AuthResponse {
    private String jwt;
    private String message;
    private UserDto userDto;
}
