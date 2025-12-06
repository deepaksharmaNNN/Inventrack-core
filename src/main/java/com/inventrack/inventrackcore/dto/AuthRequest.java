package com.inventrack.inventrackcore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    private String usernameOrEmail;
    private String password;
}
