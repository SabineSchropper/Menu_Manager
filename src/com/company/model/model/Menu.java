package com.company.model.model;

import com.company.model.model.Ingredient;

import java.util.ArrayList;

public class Menu {

    public String name;
    public int number;
    public int howOftenOrdered;
    public double price;
    public ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }
}
