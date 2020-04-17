package com.company;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();
        Scanner scan = new Scanner(System.in);
        Scanner numberScanner;
        boolean isInProgress = true;
        boolean isAdding = true;
        String choice = "";
        String menuName = "";
        String ingredient = "";
        String location = "";
        int postcode = 0;
        int zone = 0;
        double fee = 0.0;
        int menuNumber = 0;
        int ingredientNumber = 0;

        while (isInProgress) {
            System.out.println("Bitte wählen Sie:");
            System.out.println("1: Eingabe eines neuen Gerichts");
            System.out.println("2: Verändern eines Gerichts");
            System.out.println("3: Eingabe der Zone/Preis");
            System.out.println("4: Eingabe der Ortschaft/Zone");
            System.out.println("Beenden Sie die Eingabe mit x");

            choice = scan.nextLine();
            if (choice.equalsIgnoreCase("x")) {
                isInProgress = false;
            } else {
                if (choice.equalsIgnoreCase("1")) {
                    isAdding = true;
                    System.out.println("Wie lautet der Name des Gerichtes?");
                    menuName = scan.nextLine();
                    manager.addMenuName(menuName);

                    System.out.println("Jetzt können Sie die Zutaten eingeben:");
                    System.out.println("Beenden Sie die Eingabe mit x.");

                    while (isAdding) {
                        System.out.println("Zutat:");
                        ingredient = scan.nextLine();
                        if (ingredient.equalsIgnoreCase("x")) {
                            isAdding = false;
                        } else {
                            manager.addIngredient(ingredient, menuName);
                        }
                    }
                }
                if (choice.equalsIgnoreCase("2")) {
                    isAdding = true;
                    manager.showMenu();
                    while(isAdding) {
                        System.out.println("Welches Gericht möchten Sie verändern? Bitte Zahl eingeben.");
                        System.out.println("Mit 0 kehren Sie zum Anfang zurück.");
                        try {
                            numberScanner = new Scanner(System.in);
                            menuNumber = numberScanner.nextInt();
                        } catch (InputMismatchException ex) {
                            System.out.println("Die Eingabe muss eine Zahl sein.");
                            continue;
                        }
                        if (menuNumber == 0) {
                            isAdding = false;
                        } else {
                            boolean wantsToChangeSomething = true;
                            while(wantsToChangeSomething) {
                                manager.showIngredients(menuNumber);
                                System.out.println("\nZutat hinzufügen (1)\nZutat entfernen (2)");
                                System.out.println("Mit 0 kehren Sie einen Schritt zurück.");
                                if(choice.equalsIgnoreCase("0")){
                                    wantsToChangeSomething = false;
                                }
                                else if(choice.equalsIgnoreCase("1")){
                                    System.out.println("Zutat, die Sie hinzufügen möchten: ");
                                    ingredient = scan.nextLine();
                                    if(ingredient.equalsIgnoreCase("0")){
                                        continue;
                                    }
                                    menuName = manager.getMenuName(menuNumber);
                                    manager.addIngredient(ingredient,menuName);
                                }
                                else if (choice.equalsIgnoreCase("2")){
                                    System.out.println("Zutat, die Sie entfernen möchten: ");
                                    System.out.println("Bitte Zahl in der Klammer eingeben.");
                                    try {
                                        numberScanner = new Scanner(System.in);
                                        ingredientNumber = numberScanner.nextInt();
                                    } catch (InputMismatchException ex) {
                                        System.out.println("Die Eingabe muss eine Zahl sein.");
                                        continue;
                                    }
                                    if(ingredientNumber ==0){
                                        continue;
                                    }
                                    manager.removeIngredient(menuNumber, ingredientNumber);
                                }
                                else{
                                    System.out.println("Versuchen Sie es bitte noch einmal.");
                                }
                            }
                        }
                    }
                }
                if (choice.equalsIgnoreCase("3")) {
                    isAdding = true;
                    while (isAdding) {
                        System.out.println("Geben Sie den Preis für eine Zone ein:");
                        System.out.println("Beenden Sie die Eingabe mit 0");
                        numberScanner = new Scanner(System.in);
                        fee = numberScanner.nextDouble();
                        if (fee == 0) {
                            isAdding = false;
                        } else {
                            System.out.println("Zonen - Nummer:");
                            zone = numberScanner.nextInt();
                            manager.addZoneAndPrice(fee, zone);
                        }
                    }
                }
                if (choice.equalsIgnoreCase("4")) {
                    isAdding = true;
                    while (isAdding) {
                        System.out.println("Geben Sie die Ortschaft ein:");
                        System.out.println("Beenden Sie die Eingabe mit x.");
                        location = scan.nextLine();
                        if (location.equalsIgnoreCase("x")) {
                            isAdding = false;
                        } else {
                            System.out.println("Postleitzahl:");
                            numberScanner = new Scanner(System.in);
                            postcode = numberScanner.nextInt();
                            System.out.println("Zone:");
                            zone = numberScanner.nextInt();
                            manager.addLocationAndConnectZone(location, postcode, zone);
                        }
                    }
                }
            }
        }
    }
}
