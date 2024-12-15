package org.example.paymentservice.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.paymentservice.enums.PaymentMethodType;
import org.example.paymentservice.enums.PaymentStatus;

@Entity
@Table(indexes = {@Index(name = "idx_userId", columnList = "userId")})
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long userId;
    @Column(unique = true)
    public Long orderId;
    public Long amountKRW;
    public PaymentMethodType paymentMethodType;
    public String paymentData;
    public PaymentStatus paymentStatus;
    @Column(unique = true)
    public Long referenceCode;

    public Payment(Long userId, Long orderId, Long amountKRW, PaymentMethodType paymentMethodType, String paymentData, PaymentStatus paymentStatus, Long referenceCode) {
        this.userId = userId;
        this.orderId = orderId;
        this.amountKRW = amountKRW;
        this.paymentMethodType = paymentMethodType;
        this.paymentData = paymentData;
        this.paymentStatus = paymentStatus;
        this.referenceCode = referenceCode;
    }
}
