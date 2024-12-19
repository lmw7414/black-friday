package org.example.orderservice.service;

import blackfriday.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.*;
import org.example.orderservice.entity.ProductOrder;
import org.example.orderservice.enums.OrderStatus;
import org.example.orderservice.feign.CatalogClient;
import org.example.orderservice.feign.DeliveryClient;
import org.example.orderservice.feign.PaymentClient;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;
    private final CatalogClient catalogClient;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;


    public StartOrderResponseDto startOrder(Long userId, Long productId, Long count) {
        // 1. 상품 정보 조회
        var product = catalogClient.getProduct(productId);

        // 2. 결제 수단 정보 조회
        var paymentMethod = paymentClient.getPaymentMethod(userId);

        // 3. 배송지 정보 조회
        var address = deliveryClient.getUserAddress(userId);

        // 4. 주문 정보 생성
        var order = new ProductOrder(userId, productId, count, OrderStatus.INITIATED, null, null, null);
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

        // 2. 결제 요청
        var message = EdaMessage.PaymentRequestV1.newBuilder()
                .setOrderId(orderId)
                .setUserId(order.userId)
                .setAmountKRW(Long.parseLong(product.get("price").toString()) * order.count)
                .setPaymentMethodId(paymentMethodId)
                .build();
        kafkaTemplate.send("payment_request", message.toByteArray());

        //3. 주문 정보 업데이트
        var address = deliveryClient.getAddress(addressId);
        order.orderStatus = OrderStatus.PAYMENT_REQUESTED;
        order.deliveryAddress = address.get("address").toString();
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
