package com.company.model.repository;

import com.company.model.DatabaseConnector;
import com.company.model.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientRepository {
    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    ArrayList<Ingredient> allIngredients = new ArrayList<>();
    String sql;

    public void fetchAllIngredients() {
        //clear List allIngredients before filling it new
        allIngredients.clear();
        databaseConnector.buildConnection();
        sql = "SELECT * FROM ingredient";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int number = rs.getInt("number");
                int consumed = rs.getInt("consumed");
                Ingredient ingredient = new Ingredient(name);
                ingredient.number = number;
                ingredient.consumed = consumed;
                allIngredients.add(ingredient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not fetch all Ingredients");
        }
    }
    public ArrayList<Ingredient> getAllIngredients() {
        return allIngredients;
    }
}
