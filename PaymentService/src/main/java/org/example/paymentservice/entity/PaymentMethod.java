package org.example.paymentservice.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.paymentservice.enums.PaymentMethodType;

@Entity
@Table(indexes = {@Index(name = "idx_userId", columnList = "userId")})
@NoArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long userId;
    public PaymentMethodType paymentMethodType;
    public String creditCardNumber;

    public PaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }
}
