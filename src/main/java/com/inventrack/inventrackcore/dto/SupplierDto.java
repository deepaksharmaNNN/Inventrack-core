package com.inventrack.inventrackcore.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierDto {
    Long id;
    String name;
    String contactEmail;
    String contactPhone;
    String address;
}
