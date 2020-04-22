package com.company;

import java.sql.*;

public class Manager {
    Connection connection;
    Statement statement;
    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    String sql = "";

    public void showPossibilities(){
        System.out.println("Bitte wählen Sie:");
        System.out.println("1: Eingabe eines neuen Gerichts");
        System.out.println("2: Festlegen des Preises");
        System.out.println("3: Verändern eines Gerichts");
        System.out.println("4: Eingabe der Zone/Preis");
        System.out.println("5: Eingabe der Ortschaft/Zone");
        System.out.println("6: Löschen eines Gerichts");
        System.out.println("Beenden Sie die Eingabe mit 0");
    }
    public int addMenuNameAndReturnNumber(String menuName) {
        int number = 0;
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            sql = "INSERT INTO menu (`name`) VALUES ('" + menuName + "')";
            statement.executeUpdate(sql);
            sql = "SELECT max(number) FROM menu";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            number = rs.getInt(1);
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return number;
    }

    public void addIngredient(String ingredient, String menuName) {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            //Search in Database if ingredient already exists
            sql = "SELECT * FROM ingredient WHERE name = '" + ingredient + "'";
            ResultSet rs = statement.executeQuery(sql);
            //add ingredient if it is not in Database yet
            if (!rs.next()) {
                sql = "INSERT INTO ingredient (`name`) VALUES ('" + ingredient + "')";
                statement.executeUpdate(sql);
            }
            //connect menu with ingredients in table menu_ingredient
            sql = "INSERT INTO menu_ingredient(`menu_number`, `ingredient_number`)";
            sql = sql + "VALUES ((SELECT number from menu Where name = '" + menuName + "')";
            sql = sql + ", (SELECT number from ingredient WHERE name = '" + ingredient + "'))";
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }

    public void addZoneAndPrice(double fee, int zone) {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            sql = "SELECT * FROM deliver_fee WHERE zone_nr = " + zone + "";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next() == false) {
                sql = "INSERT INTO deliver_fee (`price`, `zone_nr`) VALUES " +
                        "(" + fee + "," + zone + ")";
                statement.executeUpdate(sql);
            } else {
                System.out.println("Diese Zonen - Nummer gibt es bereits.");
            }

        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }

    public void addLocationAndConnectZone(String location, int postcode, int zone) {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            sql = "INSERT INTO location (`name`, `postcode`,`deliver_zone`)";
            sql = sql + "VALUES ('" + location + "','" + postcode + "'," + zone + ")";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }

    public void showMenu() {
        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            sql = "SELECT * FROM menu";
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("\nDiese Gerichte stehen auf Ihrer Karte:\n");
            while ((rs.next())) {
                String name = rs.getString("name");
                int number = rs.getInt("number");
                double price = rs.getDouble("price");
                System.out.println("Nr. " + number + " "+name+", " + price + " €");
            }
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }

    public void showIngredients(int menuNumber) {
        int ingredientNumber = 0;
        String ingredient = "";
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT ingredient.name, ingredient.number from menu_ingredient " +
                    "INNER JOIN ingredient ON menu_ingredient.ingredient_number = ingredient.number " +
                    "WHERE menu_number = " + menuNumber + "";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                ingredientNumber = rs.getInt("number");
                ingredient = rs.getString("name");
                System.out.println("Zutat: " + ingredient + " Nummer: " + ingredientNumber);
            }
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }

    public String getMenuName(int menuNumber) {
        String menuName = "";
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "SELECT * FROM menu WHERE number = " + menuNumber + "";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            menuName = rs.getString("name");

            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return menuName;
    }
    public void removeIngredient(int menuNumber, int ingredientNumber){
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "DELETE FROM `menu_ingredient` WHERE menu_number = "+ menuNumber + "" +
                    " AND ingredient_number = " + ingredientNumber +"";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }
    public void deleteMenu(int menuNumber){
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "DELETE FROM `menu_ingredient` WHERE menu_number = "+ menuNumber + "";
            statement.executeUpdate(sql);
            sql = "DELETE FROM `menu` WHERE number = "+ menuNumber + "";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }
    public void setPrice(int menuNumber, double price){
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            sql = "UPDATE `menu` SET price = "+ price +" WHERE number = "+ menuNumber + "";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }
}
