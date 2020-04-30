package com.company.model.repository;

import com.company.model.DatabaseConnector;
import com.company.model.model.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationRepository {
    ArrayList<Location> locations = new ArrayList<>();
    String url = "jdbc:mysql://localhost:3306/gastro?user=root";
    DatabaseConnector databaseConnector = new DatabaseConnector(url);
    String sql;

    public void fetchLocations(){
        locations.clear();
        databaseConnector.buildConnection();
        sql = "SELECT * FROM location";
        ResultSet rs = databaseConnector.fetchData(sql);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int postcode = rs.getInt("postcode");
                int deliverZone = rs.getInt("deliver_zone");
                Location location = new Location(name,postcode,deliverZone);
                locations.add(location);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not get Locations");
        }
    }
    public ArrayList<Location> getLocations() {
        return locations;
    }
    public void insertLocation(String name, int postcode, int zone) {
            databaseConnector.buildConnection();
            sql = "INSERT INTO location (`name`, `postcode`,`deliver_zone`)";
            sql = sql + "VALUES ('" + name + "','" + postcode + "'," + zone + ")";
            databaseConnector.insertData(sql);
    }
    public void updateLocation(String name, int zone){
            databaseConnector.buildConnection();
            sql = "UPDATE `location` SET `deliver_zone`="+ zone +" WHERE name = '"+ name +"'";
            databaseConnector.updateData(sql);
    }
}
