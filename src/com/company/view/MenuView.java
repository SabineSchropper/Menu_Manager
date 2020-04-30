package com.company.view;

import com.company.model.model.Ingredient;
import com.company.model.model.Menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuView extends View {
    Scanner scan = new Scanner(System.in);
    //Scanner numberScanner = new Scanner(System.in);

    public void showPossibilities() {
        System.out.println("Bitte wählen Sie:");
        System.out.println("1: Eingabe eines neuen Gerichts");
        System.out.println("2: Preis eines Gerichts ändern");
        System.out.println("3: Verändern eines Gerichts");
        System.out.println("4: Eingabe der Zone/Preis");
        System.out.println("5: Eingabe der Ortschaft/Zone");
        System.out.println("6: Löschen eines Gerichts");
        System.out.println("Beenden Sie die Eingabe mit 0");
    }
    public String askForIngredient() {
        System.out.println("Sie können Zutaten aus der Liste oder neue Zutaten eingeben.");
        System.out.println("Zutat:");
        String ingredient = scan.nextLine();
        return ingredient;
    }
    public void viewAllIngredients(ArrayList<Ingredient> allIngredients) {
        System.out.println("Diese Zutaten gibt es bereits:");
        for (Ingredient ingredient : allIngredients) {
            System.out.println("Zutat: " + ingredient.name + " Nummer: " + ingredient.number);
        }
    }
    public double askForIngredientPriceIfAdded() {
        System.out.println("Geben Sie den Preis für diese Zutat ein, wenn Sie vom Kunden hinzugefügt wird.");
        double priceIfAdded = scanDoubleMethod();
        return priceIfAdded;
    }

    public double askForMenuPrice() {
        System.out.println("Wie viel soll dieses Gericht kosten?");
        double price = scanDoubleMethod();
        return price;
    }
    public void viewIngredients(Menu menu) {
        System.out.println(menu.name + " :");
        for (Ingredient ingredient : menu.ingredients) {
            System.out.println("Zutat: " + ingredient.name + " Nummer: " + ingredient.number);
        }
    }
    public void viewMenu(ArrayList<Menu> menus) {
        for (Menu menu : menus) {
            System.out.println("Nr. " + menu.number + " " + menu.name + ", " + menu.price + " €");
        }
    }
    public int askForMenuToChange() {
        System.out.println("\nWelches Gericht möchten Sie ändern? Bitte Zahl eingeben.");
        System.out.println("Mit 0 kehren Sie zum Anfang zurück.");
        int menuNumber = scanIntMethod();
        return menuNumber;
    }
    public int askForRemovingIngredient(){
        System.out.println("Zutat, die Sie aus dem Gericht entfernen möchten: ");
        System.out.println("Bitte Zahl eingeben.");
        int ingredientNumber = scanIntMethod();
        return ingredientNumber;
    }

}
