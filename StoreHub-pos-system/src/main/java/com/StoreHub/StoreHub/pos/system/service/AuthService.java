package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.exceptions.UserException;
import com.StoreHub.StoreHub.pos.system.payload.response.AuthResponse;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;

public interface AuthService {

   AuthResponse signup(UserDto userDto) throws UserException;
   AuthResponse login(UserDto userDto);
}
