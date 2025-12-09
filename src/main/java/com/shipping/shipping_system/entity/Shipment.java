package com.shipping.shipping_system.entity;

import com.shipping.shipping_system.observer.ShipmentStatusObserver;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;

@Entity

@Data

public class Shipment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    

    private String trackingNumber;

    private String carrier = "DHL";

    private String status = "PENDING"; // PENDING → PICKED_UP → IN_TRANSIT → DELIVERED

    

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @Transient

    private final List<ShipmentStatusObserver> observers = new ArrayList<>();

    public void addObserver(ShipmentStatusObserver observer) {

        observers.add(observer);

    }

    public void setStatus(String newStatus) {

        this.status = newStatus;

        this.updatedAt = LocalDateTime.now();

        notifyObservers();

    }

    private void notifyObservers() {

        observers.forEach(obs -> obs.onStatusChanged(this));

    }

    @PrePersist

    private void generateTracking() {

        if (trackingNumber == null) {

            trackingNumber = "TRK" + System.currentTimeMillis();

        }

    }

}

