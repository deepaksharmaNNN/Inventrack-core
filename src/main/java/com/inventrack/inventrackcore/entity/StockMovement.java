package com.inventrack.inventrackcore.entity;

import com.inventrack.inventrackcore.enums.StockMovementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "stock_movements", indexes = {
        @Index(name = "idx_sm_product", columnList = "product_id"),
        @Index(name = "idx_sm_time", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // product snapshot reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    private Integer previousStock;

    @NotNull
    private Integer changedByQty; // positive for IN, negative for OUT

    @NotNull
    private Integer newStock;

    @Enumerated(EnumType.STRING)
    private StockMovementType type;

    @Column(length = 500)
    private String reason; // optional description

    private Instant createdAt;

    // who performed this action - optional (user id)
    private Long performedByUserId;
}
