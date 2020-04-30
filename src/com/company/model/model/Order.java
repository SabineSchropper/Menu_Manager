package com.company.model.model;

public class Order {
    public int customerId;
    public int orderNr;
    public double totalPrice;

    public Order(int customerId, int orderNr, double totalPrice){
        this.customerId = customerId;
        this.orderNr = orderNr;
        this.totalPrice = totalPrice;
    }
}
