package org.example.paymentservice.pg;

import org.springframework.stereotype.Service;

@Service
public class EasyCreditCardPaymentAdapter implements CreditCardPaymentAdapter{
    @Override
    public Long processCreditCardPayment(Long amountKRW, String creditCardNumber) {
        return Math.round(Math.random() * 10000000);
    }
}
