package org.example.deliveryserive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DeliverySeriveApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliverySeriveApplication.class, args);
    }

}
