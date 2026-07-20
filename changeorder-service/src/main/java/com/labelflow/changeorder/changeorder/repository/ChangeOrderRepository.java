package com.labelflow.changeorder.changeorder.repository;

import com.labelflow.changeorder.changeorder.entity.ChangeOrder;
import com.labelflow.changeorder.changeorder.enums.ChangeOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChangeOrderRepository extends JpaRepository<ChangeOrder, UUID> {
  boolean existsByLabelChangeId(UUID labelChangeId);

  List<ChangeOrder> findByStatus(ChangeOrderStatus status);

  List<ChangeOrder> findByLabelChangeId(UUID labelChangeId);
}
