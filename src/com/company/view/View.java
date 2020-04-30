package com.company.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class View {

    public static double scanDoubleMethod() {
        Scanner numberScanner;
        double number = 0;
        try {
            numberScanner = new Scanner(System.in);
            number = numberScanner.nextDouble();
        } catch (InputMismatchException ex) {
            System.out.println("Die Eingabe muss eine Zahl sein.");
        }
        return number;
    }
    public static int scanIntMethod() {
        Scanner numberScanner;
        int number = 0;
        try {
            numberScanner = new Scanner(System.in);
            number = numberScanner.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Die Eingabe muss eine Zahl sein.");
        }
        return number;
    }
}
