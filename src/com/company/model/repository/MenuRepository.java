package com.company.model.repository;

import com.company.model.DatabaseConnector;
import com.company.model.model.Ingredient;
import com.company.model.model.Menu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuRepository {
    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    ArrayList<Menu> allMenus = new ArrayList<>();
    String sql;
    Menu menu;

    public int addMenuNameAndReturnNumber(String menuName) {
        int number = 0;
        try {
            databaseConnector.buildConnection();
            sql = "INSERT INTO menu (`name`) VALUES ('" + menuName + "')";
            databaseConnector.insertData(sql);
            //method insertData has closed connection, so we build a new connection
            databaseConnector.buildConnection();
            sql = "SELECT max(number) FROM menu";
            ResultSet rs = databaseConnector.fetchData(sql);
            rs.next();
            number = rs.getInt(1);
            menu = new Menu(menuName);
            menu.number = number;
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return number;
    }
    public void addIngredient(Ingredient ingredient){
        menu.ingredients.add(ingredient);
    }
    public void addMenuToDatabase(){
        setMenuPriceAtDatabase(menu);
        for(Ingredient ingredient : menu.ingredients){
            if(ingredient.number == 0){
                insertIngredientToDatabase(ingredient);
            }
        }
        connectMenuWithIngredients(menu);
    }
    public void setMenuPriceAtDatabase(Menu menu) {
        databaseConnector.buildConnection();
        sql = "UPDATE `menu` SET price = " + menu.price + " WHERE number = " + menu.number + "";
        databaseConnector.updateData(sql);
    }
    public void insertIngredientToDatabase(Ingredient ingredient) {
        databaseConnector.buildConnection();
        sql = "INSERT INTO ingredient (`name`,`price_if_added`) " +
                "VALUES ('" + ingredient.name + "'," + ingredient.priceIfAdded + ")";
        databaseConnector.insertData(sql);
    }
    public void connectMenuWithIngredients(Menu menu){
        //connect menu with ingredients in table menu_ingredient
        for(Ingredient ingredient : menu.ingredients) {
            databaseConnector.buildConnection();
            sql = "INSERT INTO menu_ingredient(`menu_number`, `ingredient_number`)";
            sql = sql + "VALUES ((SELECT number from menu Where name = '" + menu.name + "')";
            sql = sql + ", (SELECT number from ingredient WHERE name = '" + ingredient.name + "'))";
            databaseConnector.insertData(sql);
        }
    }
    public void connectMenuWithAdditionalyIngredient(Ingredient ingredient, Menu menu){
        databaseConnector.buildConnection();
        sql = "INSERT INTO menu_ingredient(`menu_number`, `ingredient_number`)";
        sql = sql + "VALUES ((SELECT number from menu Where name = '" + menu.name + "')";
        sql = sql + ", (SELECT number from ingredient WHERE name = '" + ingredient.name + "'))";
        databaseConnector.insertData(sql);
    }
    public void fetchMenus() {
        databaseConnector.buildConnection();
        sql = "SELECT * FROM menu";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while ((rs.next())) {
                String name = rs.getString("name");
                int number = rs.getInt("number");
                double price = rs.getDouble("price");
                menu = new Menu(name);
                menu.number = number;
                menu.price = price;
                allMenus.add(menu);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get menus");
        }
    }
    public void fetchIngredientsForAssignment(){
        //fetch ingredients and assign it to the right menu
        for(Menu menu : allMenus) {
            try {
                databaseConnector.buildConnection();
                sql = "SELECT ingredient.name, ingredient.number, ingredient.consumed, " +
                        "ingredient.price_if_added from menu_ingredient " +
                        "INNER JOIN ingredient ON menu_ingredient.ingredient_number = ingredient.number " +
                        "WHERE menu_number = " + menu.number + "";
                ResultSet rs = databaseConnector.fetchData(sql);
                while (rs.next()) {
                    int number = rs.getInt("number");
                    String name = rs.getString("name");
                    int consumed = rs.getInt("consumed");
                    double priceIfAdded = rs.getDouble("price_if_added");
                    Ingredient ingredient = new Ingredient(name);
                    ingredient.number = number;
                    ingredient.consumed = consumed;
                    ingredient.priceIfAdded = priceIfAdded;
                    menu.ingredients.add(ingredient);
                }
                databaseConnector.closeConnection();
            } catch (SQLException ex) {
                ex.fillInStackTrace();
            }
        }
    }
    public void removeIngredient(Menu menu, int ingredientNumber){
            databaseConnector.buildConnection();
            sql = "DELETE FROM `menu_ingredient` WHERE menu_number = "+ menu.number + "" +
                    " AND ingredient_number = " + ingredientNumber +"";
            databaseConnector.deleteData(sql);
    }
    public void deleteMenu(int menuNumber){
            databaseConnector.buildConnection();
            sql = "DELETE FROM `menu_ingredient` WHERE menu_number = "+ menuNumber + "";
            databaseConnector.deleteData(sql);
            databaseConnector.buildConnection();
            sql = "DELETE FROM `menu` WHERE number = "+ menuNumber + "";
            databaseConnector.deleteData(sql);
    }

    public void setMenuPrice(double price){
        menu.price = price;
    }
    public ArrayList<Menu> getAllMenus() {
        return allMenus;
    }
}
