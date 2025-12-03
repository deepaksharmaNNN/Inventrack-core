package com.inventrack.inventrackcore.dto;

import com.inventrack.inventrackcore.enums.PurchaseOrderStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDto {

    private Long id;
    private Long supplierId;
    private String supplierName;

    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;

    private PurchaseOrderStatus status;
    private Double totalAmount;

    private List<PurchaseOrderItemDto> items;
}
