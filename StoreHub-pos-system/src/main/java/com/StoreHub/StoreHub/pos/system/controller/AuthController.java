package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.exceptions.UserException;
import com.StoreHub.StoreHub.pos.system.payload.request.ResetPasswordRequest;
import com.StoreHub.StoreHub.pos.system.payload.response.ApiResponse;
import com.StoreHub.StoreHub.pos.system.payload.response.AuthResponse;
import com.StoreHub.StoreHub.pos.system.payload.dto.UserDto;
import com.StoreHub.StoreHub.pos.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @RequestBody ResetPasswordRequest request) throws UserException {

        authService.createPasswordResetToken(request.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put(
                "message",
                "If the email exists, a password reset token has been sent."
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody ResetPasswordRequest req
    ) throws UserException {
        authService.restPassword(req.getToken(), req.getPassword());
        ApiResponse res = new ApiResponse();
        res.setMessage("Passoword Reset Successfull");
        return ResponseEntity.ok(res);
    }
}
