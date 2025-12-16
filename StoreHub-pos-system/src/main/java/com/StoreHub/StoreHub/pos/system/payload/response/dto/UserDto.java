package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {


    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String password;

    private UserRole role;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime lastLogin;
}
