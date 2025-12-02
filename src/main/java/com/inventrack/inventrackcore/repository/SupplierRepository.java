package com.inventrack.inventrackcore.repository;

import com.inventrack.inventrackcore.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByContactEmail(String contactEmail);
    Optional<Supplier> findByContactEmail(String contactEmail);
}
