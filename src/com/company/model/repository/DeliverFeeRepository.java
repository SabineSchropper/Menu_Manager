package com.company.model.repository;

import com.company.model.DatabaseConnector;
import com.company.model.model.DeliverFee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliverFeeRepository {
    ArrayList<DeliverFee> deliverFees = new ArrayList<>();
    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    String sql;

    public void fetchDeliverFees() {
        deliverFees.clear();
        databaseConnector.buildConnection();
        sql = "SELECT * FROM deliver_fee";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while (rs.next()) {
                double deliverPrice = rs.getDouble("price");
                int zoneNr = rs.getInt("zone_nr");
                DeliverFee deliverFee = new DeliverFee(deliverPrice, zoneNr);
                deliverFees.add(deliverFee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get Deliver fees");
        }
    }
    public void updateDeliverFee(double fee, int zoneNr) {
        databaseConnector.buildConnection();
        sql = "UPDATE `deliver_fee` SET `price`=" + fee + " WHERE `zone_nr`=" + zoneNr + "";
        databaseConnector.updateData(sql);
    }
    public void insertDeliverFee(double fee, int zoneNr) {
        databaseConnector.buildConnection();
        sql = "INSERT INTO deliver_fee (`price`, `zone_nr`) VALUES " +
                "(" + fee + "," + zoneNr + ")";
        databaseConnector.insertData(sql);
    }
    public ArrayList<DeliverFee> getDeliverFees() {
        return deliverFees;
    }
}
