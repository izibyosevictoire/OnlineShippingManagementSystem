
package com.shipping.shipping_system.observer;

import com.shipping.shipping_system.entity.Shipment;

import org.springframework.stereotype.Service;

@Service

public class EmailNotificationService implements ShipmentStatusObserver {

    @Override

    public void onStatusChanged(Shipment shipment) {

        System.out.println("EMAIL NOTIFICATION → Tracking " + shipment.getTrackingNumber() + 

            " is now " + shipment.getStatus() + "!");

        // In real app: send via SendGrid/Mailgun

    }

}

