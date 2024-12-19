package org.example.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymentservice.dto.PaymentMethodDto;
import org.example.paymentservice.entity.Payment;
import org.example.paymentservice.entity.PaymentMethod;
import org.example.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/methods")
    public PaymentMethod registerPaymentMethod(@RequestBody PaymentMethodDto dto) {
        return paymentService.registerPaymentMethod(
                dto.userId,
                dto.paymentMethodType,
                dto.creditCardNumber
        );
    }

    @GetMapping("/users/{userId}/first-method")
    public PaymentMethod getPaymentMethod(@PathVariable Long userId) {
        return paymentService.getPaymentMethodByUser(userId);
    }
    @GetMapping("/payments/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}
