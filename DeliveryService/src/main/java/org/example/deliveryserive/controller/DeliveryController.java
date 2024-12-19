package org.example.deliveryserive.controller;

import lombok.RequiredArgsConstructor;
import org.example.deliveryserive.dto.RegisterAddressDto;
import org.example.deliveryserive.entity.Delivery;
import org.example.deliveryserive.entity.UserAddress;
import org.example.deliveryserive.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/addresses")
    public UserAddress registerAddress(@RequestBody RegisterAddressDto dto) {
        return deliveryService.addUserAddress(
                dto.userId,
                dto.address,
                dto.alias
        );
    }

    @GetMapping("/deliveries/{deliveryId}")
    public Delivery getDelivery(@PathVariable Long deliveryId) {
        return deliveryService.getDelivery(deliveryId);
    }

    @GetMapping("/address/{addressId}")
    public UserAddress getAddress(@PathVariable Long addressId) throws Exception {
        return deliveryService.getAddress(addressId);
    }

    @GetMapping("/users/{userId}/first-address")
    public UserAddress getUserAddress(@PathVariable Long userId) throws Exception {
        return deliveryService.getUserAddress(userId);
    }
}
