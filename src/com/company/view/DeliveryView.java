package com.company.view;

import com.company.model.model.DeliverFee;
import com.company.model.model.Location;

import java.util.ArrayList;

public class DeliveryView extends View {

    public void viewDeliveryZonesAndPrices(ArrayList<DeliverFee> deliverFees){
        System.out.println("Diese Zonen und Preise sind bereits festgelegt.");
        for(DeliverFee deliverFee : deliverFees){
            System.out.println("Zone: "+ deliverFee.zoneNr + " Liefergebühr: "+ deliverFee.price + " €");
        }
    }
    public void viewLocationsWithZones(ArrayList<Location> locations){
        System.out.println("Diese Orte und Zonen sind bereits festgelegt.");
        for(Location location: locations){
            System.out.println("Ort: "+ location.name + " Postleitzahl : "+ location.postcode + " Zone : "+ location.deliverZone);
        }
    }
}
