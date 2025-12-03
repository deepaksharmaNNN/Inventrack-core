package com.inventrack.inventrackcore.dto;

import com.inventrack.inventrackcore.enums.SalesOrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderDto {
    private Long id;
    private Long customerId;
    private String customerName;

    private LocalDate orderDate;
    private LocalDate completionDate;

    private SalesOrderStatus status;

    private Double totalAmount;

    private List<SalesOrderItemDto> items;
}

