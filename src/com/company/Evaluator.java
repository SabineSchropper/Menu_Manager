package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Evaluator {

    Connection connection;
    Statement statement;
    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    String sql = "";

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
    public void showAllOrders(){
        int totalOrders = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT count(order_nr) FROM orders";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            totalOrders = rs.getInt(1);
            System.out.println("Bestellungen gesamt: " +totalOrders +"\n");
        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
    }
    public void showOrdersPerCustomer(){
        int id = 0;
        int orders = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT customer_id, count(order_nr) FROM orders GROUP by customer_id";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                id = rs.getInt("customer_id");
                orders = rs.getInt("count(order_nr)");
                System.out.println("Kunde: " + id + " Bestellungen: "+orders);
            }
            System.out.println("");
        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
    }
    public void showOrdersPerLocation(){
        String location = "";
        int orders = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT customer.location, count(orders.order_nr) FROM orders " +
                    "INNER JOIN customer ON orders.customer_id = customer.customer_id " +
                    "GROUP BY customer.location";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                location = rs.getString("location");
                orders = rs.getInt("count(orders.order_nr)");
                System.out.println("Ort: " + location + ", Bestellungen: "+orders);
            }
            System.out.println("");
        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
    }
    public void showSales() {
        String location = "";
        double salesTotal = 0;
        int id = 0;
        double salesPerCustomer = 0;
        double salesPerLocation = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT sum(total_price)from orders";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            salesTotal = rs.getDouble("sum(total_price)");
            System.out.println("Gesamtumsatz:\t "+ salesTotal + " €");
            sql = "SELECT customer_id, sum(total_price) from orders GROUP BY customer_id";
            rs = statement.executeQuery(sql);
            while (rs.next()){
                id = rs.getInt("customer_id");
                salesPerCustomer = rs.getDouble("sum(total_price)");
                System.out.println("Kunde: "+ id + "\t\t "+salesPerCustomer + " €");
            }
            sql = "SELECT sum(orders.total_price), customer.location FROM orders " +
                    "INNER JOIN customer ON orders.customer_id = customer.customer_id " +
                    "GROUP BY customer.location";
            rs = statement.executeQuery(sql);
            while (rs.next()){
                location = rs.getString("location");
                salesPerLocation = rs.getDouble("sum(orders.total_price)");
                System.out.println("Ort : "+ location + "\t "+ salesPerLocation + " €" );
            }
            System.out.println("\n");

        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }
    public void showFavouriteMenu(){
        String favouriteMenu = "";
        int favouriteMenuOrders = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT menu.name, count(order_details.menu_nr) FROM order_details " +
                    "INNER JOIN menu ON order_details.menu_nr = menu.number GROUP BY menu_nr " +
                    "ORDER BY count(order_details.menu_nr) DESC LIMIT 1";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                favouriteMenu = rs.getString("name");
                favouriteMenuOrders = rs.getInt("count(order_details.menu_nr)");
                System.out.println("Beliebtestes Gericht: " + favouriteMenu + ", Bestellungen: "+ favouriteMenuOrders);
            }
            System.out.println("");
        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
    }
    public void showFavouriteMenusDescending(){
        String menu = "";
        int order = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT menu.name, count(order_details.menu_nr) FROM order_details " +
                    "INNER JOIN menu ON order_details.menu_nr = menu.number GROUP BY menu_nr " +
                    "ORDER BY count(order_details.menu_nr) DESC";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                menu = rs.getString("name");
                order = rs.getInt("count(order_details.menu_nr)");
                System.out.println("Gericht: " + menu + ", Bestellungen: "+ order);
            }
            System.out.println("");
        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
    }
    public void exportOrders(){
        int customerId = 0;
        int orderNr = 0;
        double totalPrice = 0;

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT customer_id, order_nr, total_price FROM orders";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                customerId = rs.getInt("customer_id");
                orderNr = rs.getInt("order_nr");
                totalPrice = rs.getDouble("total_price");
                fileWriterOrders(customerId,orderNr,totalPrice);
            }
            System.out.println("");

        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
    }
    public void exportConsumedIngredients(){
        String ingredientName = "";
        int amount = 0;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT name, consumed FROM ingredient";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                ingredientName = rs.getString("name");
                amount = rs.getInt("consumed");
                fileWriterIngredients(ingredientName,amount);
            }
            System.out.println("");
        }
        catch (SQLException ex){
            ex.fillInStackTrace();
        }
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
