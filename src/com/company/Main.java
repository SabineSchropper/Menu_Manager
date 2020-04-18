package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();
        Scanner scan = new Scanner(System.in);
        Scanner numberScanner = new Scanner(System.in);
        boolean isInProgress = true;
        boolean isAdding = true;
        String choice = "";
        String menuName = "";
        String ingredient = "";
        String location = "";
        int postcode = 0;
        int zone = 0;
        double fee = 0;
        double price = 0;
        int menuNumber = 0;
        int ingredientNumber = 0;

        while (isInProgress) {
            System.out.println("Bitte wählen Sie:");
            System.out.println("1: Eingabe eines neuen Gerichts");
            System.out.println("2: Festlegen des Preises");
            System.out.println("3: Verändern eines Gerichts");
            System.out.println("4: Eingabe der Zone/Preis");
            System.out.println("5: Eingabe der Ortschaft/Zone");
            System.out.println("6: Löschen eines Gerichts");
            System.out.println("Beenden Sie die Eingabe mit 0");

            choice = scan.nextLine();
            if (choice.equalsIgnoreCase("0")) {
                isInProgress = false;
            } else {
                if (choice.equalsIgnoreCase("1")) {
                    isAdding = true;
                    System.out.println("Wie lautet der Name des Gerichtes?");
                    menuName = scan.nextLine();
                    if(menuName.equalsIgnoreCase("0")){
                        continue;
                    }
                    menuNumber = manager.addMenuNameAndReturnNumber(menuName);

                    System.out.println("Jetzt können Sie die Zutaten eingeben:");
                    System.out.println("Beenden Sie die Eingabe mit 0.");

                    while (isAdding) {
                        System.out.println("Zutat:");
                        ingredient = scan.nextLine();
                        if (ingredient.equalsIgnoreCase("0")) {
                            isAdding = false;
                        } else {
                            manager.addIngredient(ingredient, menuName);
                        }
                    }
                    System.out.println("Wie viel soll dieses Gericht kosten?");
                    price = scanDoubleMethod(numberScanner);
                    manager.setPrice(menuNumber, price);

                }
                if (choice.equalsIgnoreCase("2")) {
                    isAdding = true;
                    while (isAdding) {
                        manager.showMenu();
                        System.out.println("\nFür welches Gericht möchten Sie einen Preis eingeben? Bitte Zahl eingeben.");
                        System.out.println("Mit 0 kehren Sie zum Anfang zurück.");
                        menuNumber = scanIntMethod(numberScanner);
                        if (menuNumber == 0) {
                            isAdding = false;
                        } else {
                            menuName = manager.getMenuName(menuNumber);
                            System.out.println(menuName);
                            manager.showIngredients(menuNumber);
                            System.out.println("Wie viel soll dieses Gericht kosten?");
                            price = scanDoubleMethod(numberScanner);
                            manager.setPrice(menuNumber, price);
                        }
                    }
                }
                else if (choice.equalsIgnoreCase("3")) {
                    isAdding = true;
                    manager.showMenu();
                    while (isAdding) {
                        System.out.println("\nWelches Gericht möchten Sie verändern? Bitte Zahl eingeben.");
                        System.out.println("Mit 0 kehren Sie zum Anfang zurück.");
                        menuNumber = scanIntMethod(numberScanner);
                        if (menuNumber == 0) {
                            isAdding = false;
                        } else {
                            boolean wantsToChangeSomething = true;
                            while (wantsToChangeSomething) {
                                manager.showIngredients(menuNumber);
                                System.out.println("\nZutat hinzufügen (1)\nZutat entfernen (2)");
                                System.out.println("Mit 0 kehren Sie einen Schritt zurück.");
                                choice = scan.nextLine();
                                if (choice.equalsIgnoreCase("0")) {
                                    wantsToChangeSomething = false;
                                } else if (choice.equalsIgnoreCase("1")) {
                                    System.out.println("Zutat, die Sie dem Gericht hinzufügen möchten: ");
                                    ingredient = scan.nextLine();
                                    if (ingredient.equalsIgnoreCase("0")) {
                                        continue;
                                    }
                                    menuName = manager.getMenuName(menuNumber);
                                    manager.addIngredient(ingredient, menuName);
                                } else if (choice.equalsIgnoreCase("2")) {
                                    System.out.println("Zutat, die Sie aus dem Gericht entfernen möchten: ");
                                    System.out.println("Bitte Zahl eingeben.");
                                    ingredientNumber = scanIntMethod(numberScanner);
                                    if (ingredientNumber == 0) {
                                        continue;
                                    }
                                    manager.removeIngredient(menuNumber, ingredientNumber);
                                } else {
                                    System.out.println("Versuchen Sie es bitte noch einmal.");
                                }
                            }
                        }
                    }
                }
                else if (choice.equalsIgnoreCase("4")) {
                    isAdding = true;
                    while (isAdding) {
                        System.out.println("Geben Sie den Preis für eine Zone ein:");
                        System.out.println("Beenden Sie die Eingabe mit 0");
                        fee = scanDoubleMethod(numberScanner);
                        if (fee == 0) {
                            isAdding = false;
                        } else {
                            System.out.println("Zonen - Nummer:");
                            zone = scanIntMethod(numberScanner);
                            manager.addZoneAndPrice(fee, zone);
                        }
                    }
                }
                else if (choice.equalsIgnoreCase("5")) {
                    isAdding = true;
                    while (isAdding) {
                        System.out.println("Geben Sie die Ortschaft ein:");
                        System.out.println("Beenden Sie die Eingabe mit 0.");
                        location = scan.nextLine();
                        if (location.equalsIgnoreCase("0")) {
                            isAdding = false;
                        } else {
                            System.out.println("Postleitzahl:");
                            postcode = scanIntMethod(numberScanner);
                            System.out.println("Zone:");
                            zone = scanIntMethod(numberScanner);
                            manager.addLocationAndConnectZone(location, postcode, zone);
                        }
                    }
                }
                else if (choice.equalsIgnoreCase("6")) {
                    isAdding = true;
                    while (isAdding) {
                        manager.showMenu();
                        System.out.println("\nGeben Sie die Nummer des zu löschenden Gerichtes ein.");
                        System.out.println("Beenden Sie die Eingabe mit 0.");
                        menuNumber = scanIntMethod(numberScanner);
                        if (menuNumber == 0) {
                            isAdding = false;
                        } else {
                            manager.deleteMenu(menuNumber);
                        }
                    }
                }
                else{
                    System.out.println("Geben Sie bitte eine der angeführten Zahlen ein.");
                }
            }
        }
    }
    public static int scanIntMethod(Scanner numberScanner){
        int number = 0;
        try {
            numberScanner = new Scanner(System.in);
            number = numberScanner.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Die Eingabe muss eine Zahl sein.");
        }
        return number;
    }
    public static double scanDoubleMethod(Scanner numberScanner){
        double number = 0;
        try {
            numberScanner = new Scanner(System.in);
            number = numberScanner.nextDouble();
        } catch (InputMismatchException ex) {
            System.out.println("Die Eingabe muss eine Zahl sein.");
        }
        return number;
    }
}
