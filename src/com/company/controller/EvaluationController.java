package com.company.controller;

import com.company.model.model.*;
import com.company.model.repository.EvaluationRepository;
import com.company.model.repository.IngredientRepository;
import com.company.view.EvaluationView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvaluationController {

    EvaluationView evaluationView = new EvaluationView();
    EvaluationRepository evaluationRepository = new EvaluationRepository();
    IngredientRepository ingredientRepository = new IngredientRepository();
    ArrayList<Ingredient> allIngredients = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();

    public void showPossibilities(){
        evaluationView.showPossibilities();
    }
    public void showAllOrders(){
        int totalOrders = evaluationRepository.countAllOrders();
        evaluationView.viewAllOrders(totalOrders);
    }
    public void showOrdersPerCustomer(){
        ArrayList<OrderPerCustomer> orderCustomersList = evaluationRepository.fetchOrdersPerCustomer();
        evaluationView.viewOrdersPerCustomer(orderCustomersList);
    }
    public void showOrdersPerLocation(){
        ArrayList<OrderPerLocation> orderPerLocations = evaluationRepository.fetchOrdersPerLocation();
        evaluationView.viewOrderPerLocation(orderPerLocations);
    }
    public void showFavouriteMenu(){
        Menu favouriteMenu = evaluationRepository.fetchFavouriteMenu();
        evaluationView.viewFavouriteMenu(favouriteMenu);
    }
    public void showFavouriteMenusDescending(){
        ArrayList<Menu> favouriteMenusDescending = evaluationRepository.fetchFavouriteMenusDescending();
        evaluationView.viewFavouriteMenusDescending(favouriteMenusDescending);
    }
    public void showSales(){
        double totalSale = evaluationRepository.fetchTotalSale();
        ArrayList<SalePerCustomer> saleCustomersList = evaluationRepository.fetchSalePerCustomer();
        ArrayList<SalePerLocation> saleLocationsList = evaluationRepository.fetchSalePerLocation();
        evaluationView.viewSales(totalSale, saleCustomersList, saleLocationsList);
    }
    public void exportOrders(){
        evaluationRepository.fetchAllOrders();
        orders = evaluationRepository.getAllOrders();
        for(Order order : orders) {
            fileWriterOrders(order.customerId, order.orderNr, order.totalPrice);
        }
        System.out.println("Daten exportiert.");
    }
    public void exportConsumedIngredients(){
        ingredientRepository.fetchAllIngredients();
        allIngredients = ingredientRepository.getAllIngredients();

        for(Ingredient ingredient : allIngredients) {
            fileWriterIngredients(ingredient.name, ingredient.consumed);
        }
        System.out.println("Daten exportiert.");
    }
    public static void fileWriterOrders(int customerId, int orderNr, double totalPrice){
        File fileOrders = new File("C:\\Users\\DCV\\Documents\\orders.csv");
        try {
            FileWriter writer = new FileWriter(fileOrders, true);
            writer.write("\n"+customerId + ";" + orderNr + ";" + totalPrice + ";");
            writer.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public static void fileWriterIngredients(String ingredientName, int amount){
        File fileIngredients = new File("C:\\Users\\DCV\\Documents\\consumed_ingredients.csv");
        try {
            FileWriter writer = new FileWriter(fileIngredients, true);
            writer.write("\n"+ ingredientName + ";" + amount + ";");
            writer.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

}


