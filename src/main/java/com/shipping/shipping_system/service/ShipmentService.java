
package com.shipping.shipping_system.service;

import com.shipping.shipping_system.entity.Shipment;

import com.shipping.shipping_system.observer.EmailNotificationService;

import com.shipping.shipping_system.repository.ShipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ShipmentService {

    @Autowired private ShipmentRepository shipmentRepository;

    @Autowired private EmailNotificationService emailService;

    public List<Shipment> findAll() {

        return shipmentRepository.findAll();

    }

    public Shipment createShipment() {

        Shipment s = new Shipment();

        s.addObserver(emailService); // ← OBSERVER PATTERN IN ACTION

        return shipmentRepository.save(s);

    }

    public Shipment updateStatus(Long id, String status) {

        Shipment s = shipmentRepository.findById(id).orElseThrow();

        s.setStatus(status); // ← triggers Observer!

        return shipmentRepository.save(s);

    }

}

