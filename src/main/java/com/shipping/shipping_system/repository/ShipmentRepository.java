
package com.shipping.shipping_system.repository;

import com.shipping.shipping_system.entity.Shipment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {}

