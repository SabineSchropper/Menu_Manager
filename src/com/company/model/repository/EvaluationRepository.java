package com.company.model.repository;

import com.company.model.DatabaseConnector;
import com.company.model.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvaluationRepository {

    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    String sql;
    ArrayList<Order> orders = new ArrayList<>();

    public int countAllOrders() {
        int totalOrders = 0;
        try {
            databaseConnector.buildConnection();
            sql = "SELECT count(order_nr) FROM orders";
            ResultSet rs = databaseConnector.fetchData(sql);
            rs.next();
            totalOrders = rs.getInt(1);
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return totalOrders;
    }
    public ArrayList fetchOrdersPerCustomer() {
        ArrayList<OrderPerCustomer> orderCustomersList = new ArrayList<>();
        int id = 0;
        int orders = 0;
        try {
            databaseConnector.buildConnection();
            sql = "SELECT customer_id, count(order_nr) FROM orders GROUP by customer_id";
            ResultSet rs = databaseConnector.fetchData(sql);
            while (rs.next()) {
                id = rs.getInt("customer_id");
                orders = rs.getInt("count(order_nr)");
                OrderPerCustomer orderPerCustomer = new OrderPerCustomer(id, orders);
                orderCustomersList.add(orderPerCustomer);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return orderCustomersList;
    }
    public ArrayList fetchOrdersPerLocation() {
        ArrayList<OrderPerLocation> orderPerLocations = new ArrayList<>();
        try {
            databaseConnector.buildConnection();
            sql = "SELECT customer.location, count(orders.order_nr) FROM orders " +
                    "INNER JOIN customer ON orders.customer_id = customer.customer_id " +
                    "GROUP BY customer.location";
            ResultSet rs = databaseConnector.fetchData(sql);
            while (rs.next()) {
                String location = rs.getString("location");
                int orders = rs.getInt("count(orders.order_nr)");
                OrderPerLocation orderPerLocation = new OrderPerLocation(location, orders);
                orderPerLocations.add(orderPerLocation);
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return orderPerLocations;
    }
    public void fetchAllOrders() {
        orders.clear();
        try {
            databaseConnector.buildConnection();
            sql = "SELECT customer_id, order_nr, total_price FROM orders";
            ResultSet rs = databaseConnector.fetchData(sql);

            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                int orderNr = rs.getInt("order_nr");
                double totalPrice = rs.getDouble("total_price");
                Order order = new Order(customerId, orderNr, totalPrice);
                orders.add(order);
            }
            databaseConnector.closeConnection();
            System.out.println("");
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
    }
    public ArrayList fetchFavouriteMenusDescending() {
        ArrayList<Menu> favouriteMenusDescending = new ArrayList<>();
        try {
            databaseConnector.buildConnection();
            sql = "SELECT menu.name, count(order_details.menu_nr) FROM order_details " +
                    "INNER JOIN menu ON order_details.menu_nr = menu.number GROUP BY menu_nr " +
                    "ORDER BY count(order_details.menu_nr) DESC";
            ResultSet rs = databaseConnector.fetchData(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int order = rs.getInt("count(order_details.menu_nr)");
                Menu menu = new Menu(name);
                menu.howOftenOrdered = order;
                favouriteMenusDescending.add(menu);
            }
            databaseConnector.closeConnection();
            System.out.println("");
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return favouriteMenusDescending;
    }
    public Menu fetchFavouriteMenu() {
        Menu menu = null;
        try {
            databaseConnector.buildConnection();
            sql = "SELECT menu.name, count(order_details.menu_nr) FROM order_details " +
                    "INNER JOIN menu ON order_details.menu_nr = menu.number GROUP BY menu_nr " +
                    "ORDER BY count(order_details.menu_nr) DESC LIMIT 1";
            ResultSet rs = databaseConnector.fetchData(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int order = rs.getInt("count(order_details.menu_nr)");
                menu = new Menu(name);
                menu.howOftenOrdered = order;
            }
            databaseConnector.closeConnection();
        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return menu;
    }
    public double fetchTotalSale() {
        double salesTotal = 0;
        databaseConnector.buildConnection();
        sql = "SELECT sum(total_price)from orders";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            rs.next();
            salesTotal = rs.getDouble("sum(total_price)");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get total Sale");
        }
        return salesTotal;
    }
    public ArrayList fetchSalePerCustomer() {
        ArrayList<SalePerCustomer> saleCustomersList = new ArrayList<>();
        try {
            databaseConnector.buildConnection();
            sql = "SELECT customer_id, sum(total_price) from orders GROUP BY customer_id";
            ResultSet rs = databaseConnector.fetchData(sql);
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                double sale = rs.getDouble("sum(total_price)");
                SalePerCustomer salePerCustomer = new SalePerCustomer(id, sale);
                saleCustomersList.add(salePerCustomer);
            }
            databaseConnector.closeConnection();

        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return saleCustomersList;
    }
    public ArrayList fetchSalePerLocation() {
        ArrayList<SalePerLocation> saleLocationsList = new ArrayList<>();
        databaseConnector.buildConnection();
        sql = "SELECT sum(orders.total_price), customer.location FROM orders " +
                "INNER JOIN customer ON orders.customer_id = customer.customer_id " +
                "GROUP BY customer.location";
        try {
            ResultSet rs = databaseConnector.fetchData(sql);
            while (rs.next()) {
                String location = rs.getString("location");
                double sale = rs.getDouble("sum(orders.total_price)");
                SalePerLocation salePerLocation = new SalePerLocation(location,sale);
                saleLocationsList.add(salePerLocation);
            }
            databaseConnector.closeConnection();

        } catch (SQLException ex) {
            ex.fillInStackTrace();
        }
        return saleLocationsList;
    }

    public ArrayList<Order> getAllOrders() {
        return orders;
    }
}
