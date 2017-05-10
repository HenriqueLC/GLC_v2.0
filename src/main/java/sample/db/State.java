package sample.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class State {

    // Create table

    // Populate table

    // Select
    void findByID(int ID) {


        try (Connection con = ConnectionManager.getInstance().getConnection(); Statement st = con.createStatement()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    //
}
