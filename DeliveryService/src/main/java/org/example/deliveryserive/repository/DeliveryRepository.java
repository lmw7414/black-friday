package org.example.deliveryserive.repository;

import org.example.deliveryserive.entity.Delivery;
import org.example.deliveryserive.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findAllByOrderId(Long orderId);
    List<Delivery> findAllByStatus(DeliveryStatus status);

}
