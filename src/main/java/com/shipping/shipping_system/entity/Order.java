
package com.shipping.shipping_system.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;

@Entity

@Table(name = "orders")

@Data

public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String orderNumber;

    private String customerName;

    private String customerEmail;

    private String shippingAddress;

    private Double weight;

    private LocalDateTime createdAt = LocalDateTime.now();

    

    @OneToOne(cascade = CascadeType.ALL)

    private Shipment shipment;

}

