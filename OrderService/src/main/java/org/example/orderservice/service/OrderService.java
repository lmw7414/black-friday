package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.*;
import org.example.orderservice.entity.ProductOrder;
import org.example.orderservice.enums.OrderStatus;
import org.example.orderservice.feign.CatalogClient;
import org.example.orderservice.feign.DeliveryClient;
import org.example.orderservice.feign.PaymentClient;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;
    private final CatalogClient catalogClient;

    public StartOrderResponseDto startOrder(Long userId, Long productId, Long count) {
        // 1. 상품 정보 조회
        var product = catalogClient.getProduct(productId);

        // 2. 결제 수단 정보 조회
        var paymentMethod = paymentClient.getPaymentMethod(userId);

        // 3. 배송지 정보 조회
        var address = deliveryClient.getUserAddress(userId);

        // 4. 주문 정보 생성
        var order = new ProductOrder(userId, productId, count, OrderStatus.INITIATED, null, null);
        orderRepository.save(order);

        var startOrderDto = new StartOrderResponseDto();
        startOrderDto.orderId = order.id;
        startOrderDto.paymentMethod = paymentMethod;
        startOrderDto.address = address;

        return startOrderDto;
    }

    public ProductOrder finishOrder(Long orderId, Long paymentMethodId, Long addressId) {
        var order = orderRepository.findById(orderId).orElseThrow();

        // 1. 상품 정보 조회
        var product = catalogClient.getProduct(order.productId);

        // 2. 결제
        var processPaymentDto = new ProcessPaymentDto();
        processPaymentDto.orderId = order.id;
        processPaymentDto.userId = order.userId;
        processPaymentDto.amountKRW = Long.parseLong(product.get("price").toString()) * order.count;
        processPaymentDto.paymentMethodId = paymentMethodId;

        var payment = paymentClient.processPayment(processPaymentDto);

        // 3. 배송 요청
        var address = deliveryClient.getAddress(addressId);
        var processDeliveryDto = new ProcessDeliveryDto();
        processDeliveryDto.orderId = order.id;
        processDeliveryDto.productName = product.get("name").toString();
        processDeliveryDto.productCount = order.count;
        processDeliveryDto.address = address.get("address").toString();

        var delivery = deliveryClient.processDelivery(processDeliveryDto);

        // 4. 상품 재고 감소
        var decreaseStockCountDto = new DecreaseStockCountDto();
        decreaseStockCountDto.decreaseCount = order.count;
        catalogClient.decreaseStockCount(order.productId, decreaseStockCountDto);

        // 5. 주문 정보 업데이트
        order.paymentId = Long.parseLong(payment.get("id").toString());
        order.deliveryId = Long.parseLong(delivery.get("id").toString());
        order.orderStatus = OrderStatus.DELIVERY_REQUESTED;

        return orderRepository.save(order);
    }

    // 특정 유저의 모든 주문 조회
    public List<ProductOrder> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public ProductOrderDetailDto getOrderDetail(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow();

        var paymentRes = paymentClient.getPayment(order.paymentId);
        var deliveryRes = deliveryClient.getDelivery(order.deliveryId);

        return new ProductOrderDetailDto(
                order.id,
                order.userId,
                order.productId,
                order.paymentId,
                order.deliveryId,
                order.orderStatus,
                paymentRes.get("paymentStatus").toString(),
                deliveryRes.get("status").toString()
        );
    }


}
