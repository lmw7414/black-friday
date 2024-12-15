package org.example.paymentservice.dto;


import org.example.paymentservice.enums.PaymentMethodType;

public class PaymentMethodDto {
    public Long userId;
    public PaymentMethodType paymentMethodType;
    public String creditCardNumber;
}
