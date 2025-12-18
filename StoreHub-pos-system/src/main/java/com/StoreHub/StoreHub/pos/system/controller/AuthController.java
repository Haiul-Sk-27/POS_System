package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.exceptions.UserException;
import com.StoreHub.StoreHub.pos.system.payload.response.AuthResponse;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;
import com.StoreHub.StoreHub.pos.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUpHandler(@RequestBody UserDto userDto)throws UserException {
        return ResponseEntity.ok(
                authService.signup(userDto)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDto userDto)throws UserException {
        return ResponseEntity.ok(
                authService.login(userDto)
        );
    }
}
