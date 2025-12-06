package com.inventrack.inventrackcore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
}