package org.example.deliveryserive.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.deliveryserive.enums.DeliveryStatus;

@Entity
@NoArgsConstructor
@Table(indexes = {@Index(name = "idx_orderId", columnList = "orderId")})
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long orderId;
    public String productName;
    public Long productCount;
    public String address;
    public Long referenceCode;
    public DeliveryStatus status;


    public Delivery(Long orderId, String productName, Long productCount, String address, Long referenceCode, DeliveryStatus status) {
        this.orderId = orderId;
        this.productName = productName;
        this.productCount = productCount;
        this.address = address;
        this.referenceCode = referenceCode;
        this.status = status;
    }
}
