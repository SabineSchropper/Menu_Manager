package com.company.view;

import com.company.model.model.*;

import java.util.ArrayList;

public class EvaluationView {

    public void showPossibilities(){
        System.out.println("1: Gesamtbestellungen");
        System.out.println("2: Bestellungen je Kunde");
        System.out.println("3: Bestellungen je Ortschaft");
        System.out.println("4: Umsätze");
        System.out.println("5: Beliebtestes Gericht (Anzahl Bestellungen)");
        System.out.println("6: Gerichte nach Anzahl der Bestellungen absteigend sortiert");
        System.out.println("7: Daten exportieren (Kundennummer,Bestellnummer,Gesamtpreis)");
        System.out.println("8: Daten exportieren (Menge verbrauchter Zutaten)");
        System.out.println("Beenden Sie die Eingabe mit 0");
    }
    public void viewAllOrders(int totalOrders){
        System.out.println("Bestellungen gesamt: " + totalOrders +"\n");
    }
    public void viewOrdersPerCustomer(ArrayList<OrderPerCustomer> oderCustomersList){
        for(OrderPerCustomer orderPerCustomer : oderCustomersList){
            System.out.println("Kunde: " + orderPerCustomer.id  + " Bestellungen: "+ orderPerCustomer.orders);
        }
    }
    public void viewOrderPerLocation(ArrayList<OrderPerLocation> orderPerLocations){
        for(OrderPerLocation orderPerLocation : orderPerLocations) {
            System.out.println("Ort: " + orderPerLocation.location + ", Bestellungen: "+ orderPerLocation.orders);
        }
    }
    public void viewFavouriteMenusDescending(ArrayList<Menu> favouriteMenusDescending) {
        for(Menu menu : favouriteMenusDescending) {
            System.out.println("Gericht: " + menu.name + ", Bestellungen: " + menu.howOftenOrdered);
        }
    }
    public void viewFavouriteMenu(Menu menu){
        System.out.println("Beliebtestes Gericht: " + menu.name + ", Bestellungen: "+ menu.howOftenOrdered);
    }
    public void viewSales(double totalSale, ArrayList<SalePerCustomer> saleCustomers, ArrayList<SalePerLocation> saleLocations){
        System.out.println("Gesamtumsatz:\t"+ totalSale + " €");
        for(SalePerCustomer salePerCustomer : saleCustomers) {
            System.out.println("Kunde: " + salePerCustomer.id + "\t\t " + salePerCustomer.sale + " €");
        }
        for(SalePerLocation salePerLocation : saleLocations) {
            System.out.println("Ort : " + salePerLocation.location + "\t " + salePerLocation.sale + " €");
        }
    }
}
