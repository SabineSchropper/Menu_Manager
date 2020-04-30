package com.company;

import com.company.controller.DeliveryController;
import com.company.controller.EvaluationController;
import com.company.controller.MenuController;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MenuController menuController = new MenuController();
        DeliveryController deliveryController = new DeliveryController();
        EvaluationController evaluationController = new EvaluationController();
        Scanner scan = new Scanner(System.in);
        boolean managerIsInProgress = true;
        boolean evaluationIsInProgress = true;
        boolean isAdding;
        boolean mainLoopIsRunning = true;
        String choice = "";
        String menuName = "";

        while (mainLoopIsRunning) {
            System.out.println("Möchten Sie Daten eingeben (1) oder Daten auswerten (2)?");
            choice = scan.nextLine();
            if (choice.equalsIgnoreCase("0")) {
                mainLoopIsRunning = false;
            } else if (choice.equalsIgnoreCase("1")) {
                while (managerIsInProgress) {
                    menuController.showPossibilities();
                    choice = scan.nextLine();
                    if (choice.equalsIgnoreCase("0")) {
                        managerIsInProgress = false;
                    } else {
                        if (choice.equalsIgnoreCase("1")) {
                            isAdding = true;
                            System.out.println("Wie lautet der Name des Gerichtes?");
                            menuName = scan.nextLine();
                            menuController.createMenuAndReturnNumber(menuName);

                            System.out.println("Jetzt können Sie die Zutaten eingeben:");
                            System.out.println("Beenden Sie die Eingabe mit 0.");
                            menuController.showExistingIngredients();
                            while (isAdding) {
                                isAdding = menuController.addIngredientToMenu();
                            }
                            menuController.askForAndSetMenuPrice();
                            menuController.saveMenu();
                        }
                        if (choice.equalsIgnoreCase("2")) {
                            isAdding = true;
                            while (isAdding) {
                                menuController.getActualMenus();
                                menuController.showMenus();
                                isAdding = menuController.askForMenuToChangeAndShowIt();
                                if (isAdding) {
                                    menuController.askForAndSetMenuPriceAtDatabase();
                                }
                            }
                        } else if (choice.equalsIgnoreCase("3")) {
                            isAdding = true;
                            while (isAdding) {
                                menuController.getActualMenus();
                                menuController.showMenus();
                                isAdding = menuController.askForMenuToChangeAndShowIt();
                                if (isAdding) {
                                    boolean wantsToChangeSomething = true;
                                    while (wantsToChangeSomething) {
                                        System.out.println("\nZutat hinzufügen (1)\nZutat entfernen (2)");
                                        System.out.println("Mit 0 kehren Sie einen Schritt zurück.");
                                        choice = scan.nextLine();
                                        if (choice.equalsIgnoreCase("0")) {
                                            wantsToChangeSomething = false;

                                        } else if (choice.equalsIgnoreCase("1")) {
                                            menuController.showExistingIngredients();
                                            wantsToChangeSomething = menuController.askForAdditionallyIngredientAndHandleIt();

                                        } else if (choice.equalsIgnoreCase("2")) {
                                            wantsToChangeSomething = menuController.askForRemovingIngredientAndHandleIt();

                                        } else {
                                            System.out.println("Versuchen Sie es bitte noch einmal.");
                                        }
                                    }
                                }
                            }
                        } else if (choice.equalsIgnoreCase("4")) {
                            isAdding = true;
                            while (isAdding) {
                                deliveryController.showExistingDeliveryZonesAndPrices();
                                isAdding = deliveryController.askForPricePerZone();
                            }
                        } else if (choice.equalsIgnoreCase("5")) {
                            isAdding = true;
                            while (isAdding) {
                                deliveryController.showExistingLocationsWithZones();
                                isAdding = deliveryController.askForLocationAndZone();
                            }
                        } else if (choice.equalsIgnoreCase("6")) {
                            isAdding = true;
                            while (isAdding) {
                                menuController.getActualMenus();
                                menuController.showMenus();
                                isAdding = menuController.askForMenuAndDeleteIt();
                            }
                        } else {
                            System.out.println("Geben Sie bitte eine der angeführten Zahlen ein.");
                        }
                    }
                }
            } else if (choice.equalsIgnoreCase("2")) {
                while (evaluationIsInProgress) {
                    evaluationController.showPossibilities();
                    choice = scan.nextLine();
                    if (choice.equalsIgnoreCase("0")) {
                        evaluationIsInProgress = false;
                    } else if (choice.equalsIgnoreCase("1")) {
                        evaluationController.showAllOrders();
                    } else if (choice.equalsIgnoreCase("2")) {
                        evaluationController.showOrdersPerCustomer();
                    } else if (choice.equalsIgnoreCase("3")) {
                        evaluationController.showOrdersPerLocation();
                    } else if (choice.equalsIgnoreCase("4")) {
                        evaluationController.showSales();
                    } else if (choice.equalsIgnoreCase("5")) {
                        evaluationController.showFavouriteMenu();
                    } else if (choice.equalsIgnoreCase("6")) {
                        evaluationController.showFavouriteMenusDescending();
                    } else if (choice.equalsIgnoreCase("7")) {
                        evaluationController.exportOrders();
                    } else if (choice.equalsIgnoreCase("8")) {
                        evaluationController.exportConsumedIngredients();
                    } else {
                        System.out.println("Geben Sie bitte eine der angeführten Zahlen ein.");
                    }
                }
            } else {
                System.out.println("Wählen Sie 1 oder 2");
            }
        }
    }
}
