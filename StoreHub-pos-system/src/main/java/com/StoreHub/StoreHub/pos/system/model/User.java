package com.StoreHub.StoreHub.pos.system.model;

import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    private String password;

    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime lastLogin;
}
