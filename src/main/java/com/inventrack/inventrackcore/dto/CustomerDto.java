package com.inventrack.inventrackcore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}