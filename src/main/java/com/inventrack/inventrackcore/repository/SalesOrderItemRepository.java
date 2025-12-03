package com.inventrack.inventrackcore.repository;

import com.inventrack.inventrackcore.entity.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
}
