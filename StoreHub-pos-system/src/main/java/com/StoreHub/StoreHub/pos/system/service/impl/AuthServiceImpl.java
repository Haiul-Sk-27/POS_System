package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.configuration.JwtProvider;
import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import com.StoreHub.StoreHub.pos.system.exceptions.UserException;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.AuthResponse;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;
import com.StoreHub.StoreHub.pos.system.repository.UserRepository;
import com.StoreHub.StoreHub.pos.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw  new UserException("Email-id alredy register !");
        }

        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)){
            throw  new UserException("Role admin is not allowed");
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreateAt(LocalDateTime.now());

        newUser.setUpdateAt(LocalDateTime.now());
        userRepository.save(newUser);
        return null;
    }

    @Override
    public AuthResponse login(UserDto userDto) {
        return null;
    }
}
