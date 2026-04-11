package com.StoreHub.StoreHub.pos.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stripe.net.OAuth;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String token;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryDate;

    public boolean isExpired(){
        return expiryDate.isBefore(LocalDateTime.now());
    }


}
