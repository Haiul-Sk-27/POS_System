package com.StoreHub.StoreHub.pos.system.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    private String token;
    private String password;
    private String email;
}
