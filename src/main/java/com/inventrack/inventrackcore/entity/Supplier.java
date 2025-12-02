package com.inventrack.inventrackcore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "suppliers",
        indexes = {
        @Index(name = "idx_supplier_name", columnList = "name"),
        @Index(name = "idx_supplier_email", columnList = "contactEmail")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Supplier name is required")
    String name;

    @Email(message = "Invalid email format")
    @Column(name = "contact_email", unique = true)
    String contactEmail;

    @Size(min = 6, max = 15, message = "Contact phone must be between 6 and 15 characters")
    @Column(name = "contact_phone")
    String contactPhone;

    @Column(columnDefinition = "TEXT")
    String address;
}
