package org.example.deliveryserive.service;

import blackfriday.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventListener {

    private final DeliveryService deliveryService;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @KafkaListener(topics = "delivery_request")
    public void consumeDeliveryRequest(byte[] message) throws Exception {
        var object = EdaMessage.DeliveryRequestV1.parseFrom(message);
        log.info("[delivery_request] consumed: {}", object);

        var delivery = deliveryService.processDelivery(
                object.getOrderId(),
                object.getProductName(),
                object.getProductCount(),
                object.getAddress()
        );

        // 배송상태 publish
        var deliveryStatusMessage = EdaMessage.DeliveryStatusUpdateV1.newBuilder()
                .setOrderId(delivery.orderId)
                .setDeliveryId(delivery.id)
                .setDeliveryStatus(delivery.status.toString())
                .build();

        kafkaTemplate.send("delivery_status_update", deliveryStatusMessage.toByteArray());
    }

}
