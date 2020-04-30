package com.company.controller;

import com.company.model.model.DeliverFee;
import com.company.model.model.Location;
import com.company.model.repository.DeliverFeeRepository;
import com.company.model.repository.LocationRepository;
import com.company.view.DeliveryView;

import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryController {
    DeliverFeeRepository deliverFeeRepository = new DeliverFeeRepository();
    DeliveryView deliveryView = new DeliveryView();
    ArrayList <DeliverFee> deliverFees = new ArrayList<>();
    LocationRepository locationRepository = new LocationRepository();
    ArrayList <Location> locations = new ArrayList<>();

    public boolean askForPricePerZone() {
        int zone;
        boolean isAdding = true;
        System.out.println("Geben Sie den Lieferpreis für eine Zone ein:");
        System.out.println("Beenden Sie die Eingabe mit 0");
        double fee = deliveryView.scanDoubleMethod();
        if (fee == 0) {
            isAdding = false;
        } else {
            System.out.println("Zonen - Nummer:");
            zone = deliveryView.scanIntMethod();
            boolean doesZoneExist = checkZoneNr(deliverFees,zone);
            if(doesZoneExist){
                deliverFeeRepository.updateDeliverFee(fee,zone);
            }
            else{
                deliverFeeRepository.insertDeliverFee(fee,zone);
            }
        }
        return isAdding;
    }
    public boolean askForLocationAndZone(){
        Scanner scan = new Scanner(System.in);
        boolean isAdding = true;
        boolean doesLocationExist;
        System.out.println("Geben Sie die Ortschaft ein:");
        System.out.println("Beenden Sie die Eingabe mit 0.");
        String name = scan.nextLine();
        if (name.equalsIgnoreCase("0")) {
            isAdding = false;
        } else {
            doesLocationExist = checkLocation(locations,name);
            if(doesLocationExist){
                System.out.println("Zone:");
                int zone = deliveryView.scanIntMethod();
                locationRepository.updateLocation(name,zone);
            }
            else {
                System.out.println("Postleitzahl:");
                int postcode = deliveryView.scanIntMethod();
                System.out.println("Zone:");
                int zone = deliveryView.scanIntMethod();
                locationRepository.insertLocation(name,postcode,zone);
            }
        }
        return isAdding;
    }
    public void showExistingDeliveryZonesAndPrices(){
        deliverFeeRepository.fetchDeliverFees();
        deliverFees = deliverFeeRepository.getDeliverFees();
        deliveryView.viewDeliveryZonesAndPrices(deliverFees);
    }
    public void showExistingLocationsWithZones(){
        locationRepository.fetchLocations();
        locations = locationRepository.getLocations();
        deliveryView.viewLocationsWithZones(locations);
    }
    public boolean checkZoneNr(ArrayList<DeliverFee> deliverFees, int zone){
        boolean doesZoneExist = false;
        for(DeliverFee deliverFee : deliverFees){
            if(deliverFee.zoneNr == zone){
                doesZoneExist = true;
                break;
            }
        }
        return doesZoneExist;
    }
    public boolean checkLocation(ArrayList<Location> locations, String name){
        boolean doesLocationExist = false;
        for(Location location : locations){
            if(location.name.equalsIgnoreCase(""+ name +"")){
                doesLocationExist = true;
                break;
            }
        }
        return doesLocationExist;
    }
}
