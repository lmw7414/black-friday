package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.FinishOrderDto;
import org.example.orderservice.dto.ProductOrderDetailDto;
import org.example.orderservice.dto.StartOrderDto;
import org.example.orderservice.dto.StartOrderResponseDto;
import org.example.orderservice.entity.ProductOrder;
import org.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/start-order")
    public StartOrderResponseDto startOrder(@RequestBody StartOrderDto dto) throws Exception {
        return orderService.startOrder(dto.userId, dto.productId, dto.count);
    }

    @PostMapping("/finish-order")
    public ProductOrder finishOrder(@RequestBody FinishOrderDto dto) throws Exception {
        return orderService.finishOrder(dto.orderId, dto.paymentMethodId, dto.addressId);
    }

    @GetMapping("/users/{userId}/orders")
    public List<ProductOrder> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/orders/{orderId}")
    public ProductOrderDetailDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }
}
