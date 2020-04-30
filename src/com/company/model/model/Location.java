package com.company.model.model;

public class Location {
    public String name;
    public int postcode;
    public int deliverZone;

    public Location(String name, int postcode, int deliverZone){
        this.name = name;
        this.postcode = postcode;
        this.deliverZone = deliverZone;
    }
}
