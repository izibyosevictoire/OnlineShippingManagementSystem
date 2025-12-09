
package com.shipping.shipping_system.observer;

import com.shipping.shipping_system.entity.Shipment;

public interface ShipmentStatusObserver {

    void onStatusChanged(Shipment shipment);

}

