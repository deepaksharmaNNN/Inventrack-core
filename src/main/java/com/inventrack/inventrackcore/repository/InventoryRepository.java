package com.inventrack.inventrackcore.repository;

import com.inventrack.inventrackcore.entity.Inventory;
import com.inventrack.inventrackcore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
    boolean existsByProduct(Product product);
}
