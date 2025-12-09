
package com.shipping.shipping_system.controller;

import com.shipping.shipping_system.service.ShipmentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/")

public class ShipmentController {

    @Autowired private ShipmentService shipmentService;

    @GetMapping

    public String dashboard(Model model) {

        model.addAttribute("shipments", shipmentService.findAll());

        return "shipment/dashboard";

    }

    @PostMapping("/create")

    public String create() {

        shipmentService.createShipment();

        return "redirect:/";

    }

    @PostMapping("/update/{id}")

    public String update(@PathVariable Long id, @RequestParam String status) {

        shipmentService.updateStatus(id, status);

        return "redirect:/";

    }

}

