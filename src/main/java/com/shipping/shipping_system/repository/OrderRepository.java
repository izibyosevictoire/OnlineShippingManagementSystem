
package com.shipping.shipping_system.repository;

import com.shipping.shipping_system.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}

