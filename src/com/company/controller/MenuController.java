package com.company.controller;

import com.company.model.model.Ingredient;
import com.company.model.model.Menu;
import com.company.model.repository.IngredientRepository;
import com.company.model.repository.MenuRepository;
import com.company.view.MenuView;

import java.util.ArrayList;

public class MenuController {
    MenuView menuView = new MenuView();
    MenuRepository menuRepository = new MenuRepository();
    IngredientRepository ingredientRepository = new IngredientRepository();
    ArrayList <Ingredient> allIngredients = new ArrayList<>();
    ArrayList <Menu> allMenus = new ArrayList<>();
    Menu menu;

    public void showPossibilities(){
        menuView.showPossibilities();
    }
    public void createMenuAndReturnNumber(String menuName){
        int menuNumber = menuRepository.addMenuNameAndReturnNumber(menuName);
        System.out.println("Dieses Gericht hat die Nummer: "+menuNumber);
    }
    public boolean addIngredientToMenu(){
        boolean isAdding = true;
        Ingredient ingredient;
        String ingredientString = menuView.askForIngredient();
        if (ingredientString.equalsIgnoreCase("0")) {
            isAdding = false;
        } else {
            ingredient = checkIngredient(ingredientString,allIngredients);
            menuRepository.addIngredient(ingredient);
        }
        return isAdding;
    }
    public boolean askForAdditionallyIngredientAndHandleIt(){
        boolean wantsToChangeSomething = true;
        Ingredient ingredient;
        String ingredientString = menuView.askForIngredient();
        if(ingredientString.equalsIgnoreCase("0")){
            wantsToChangeSomething = false;
        }
        else {
            ingredient = checkIngredient(ingredientString, allIngredients);
            if(ingredient.number == 0){
                menuRepository.insertIngredientToDatabase(ingredient);
            }
            menuRepository.connectMenuWithAdditionalyIngredient(ingredient, menu);
        }
        return wantsToChangeSomething;
    }
    public boolean askForRemovingIngredientAndHandleIt(){
        boolean wantsToChangeSomething = true;
        int ingredientNumber = menuView.askForRemovingIngredient();
        if(ingredientNumber == 0){
            wantsToChangeSomething = false;
        }
        else{
            menuRepository.removeIngredient(menu,ingredientNumber);
        }
        return wantsToChangeSomething;
    }
    public void showExistingIngredients(){
        ingredientRepository.fetchAllIngredients();
        allIngredients = ingredientRepository.getAllIngredients();
        menuView.viewAllIngredients(allIngredients);
    }
    public Ingredient checkIngredient(String ingredientString, ArrayList<Ingredient> allIngredients){
        Ingredient ingredient1 = null;
        //if ingredientString exists in ArrayList ingredient gets assigned
        for(Ingredient ingredient : allIngredients){
            if(ingredientString.equalsIgnoreCase(ingredient.name) || ingredientString.equalsIgnoreCase(Integer.toString(ingredient.number))){
                ingredient1 = ingredient;
                break;
            }
        }
        if (ingredient1 == null){
            ingredient1 =  new Ingredient(ingredientString);
            double priceIfAdded = menuView.askForIngredientPriceIfAdded();
            ingredient1.priceIfAdded = priceIfAdded;
        }
        return ingredient1;
    }
    public void saveMenu(){
        menuRepository.addMenuToDatabase();
    }
    public void getActualMenus(){
        menuRepository.fetchMenus();
        menuRepository.fetchIngredientsForAssignment();
        allMenus = menuRepository.getAllMenus();
    }
    public void showMenus(){
        menuView.viewMenu(allMenus);
    }
    public void askForAndSetMenuPrice(){
        double price = menuView.askForMenuPrice();
        menuRepository.setMenuPrice(price);
    }
    public void askForAndSetMenuPriceAtDatabase(){
        double price = menuView.askForMenuPrice();
        menu.price = price;
        menuRepository.setMenuPriceAtDatabase(menu);
    }
    public boolean askForMenuToChangeAndShowIt(){
        Menu choosedMenu = null;
        boolean isAdding = true;
        int menuNumber = menuView.askForMenuToChange();
        if(menuNumber == 0){
            isAdding = false;
        }
        else{
        choosedMenu = searchMenuInArrayList(menuNumber,allMenus);
        menuView.viewIngredients(choosedMenu);
        this.menu = choosedMenu;
        }
        return isAdding;
    }
    public Menu searchMenuInArrayList(int menuNumber, ArrayList<Menu> menus) {
        Menu searchedMenu = null;
        for (Menu menu : menus) {
            if (menu.number == menuNumber) {
                searchedMenu = menu;
            }
        }
        return searchedMenu;
    }
    public boolean askForMenuAndDeleteIt(){
        boolean isAdding = true;
        System.out.println("\nGeben Sie die Nummer des zu löschenden Gerichtes ein.");
        System.out.println("Beenden Sie die Eingabe mit 0.");
        int menuNumber = menuView.scanIntMethod();
        if (menuNumber == 0) {
            isAdding = false;
        } else {
            menuRepository.deleteMenu(menuNumber);
        }
        return isAdding;
    }
}

