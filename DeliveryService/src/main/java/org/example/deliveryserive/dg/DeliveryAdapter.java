package org.example.deliveryserive.dg;

public interface DeliveryAdapter {
    Long processDelivery(String productName, Long productCount, String address);
}
