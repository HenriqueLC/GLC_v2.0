package sample.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Util {

    public static Connection getConnection() throws SQLException {
        // JDBC driver name and database URL
        String DB_URL = "jdbc:h2:./src/main/java/db/test";

        //  Database credentials
        String USER = "sa";
        String PASS = "";

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void Init() {

        System.out.println("Connecting to selected database...");
        try (Connection connection = getConnection()) {
            System.out.println("Successfully connected!");

            // State table
            System.out.println("Conditional creating States table...");
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS States (id tinyint NOT NULL, name varchar(32), Abbr varchar(2), PRIMARY KEY(ID));");
                System.out.println("Successfully created!");

                System.out.println("Checking States table...");
                ResultSet resultSet = statement.executeQuery("SELECT * FROM States LIMIT 1;");
                System.out.println("OK!");

                if (!resultSet.next()) {

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(Util.class.getClassLoader().getResourceAsStream("/sample/dump/states.csv")))) {

                        System.out.println("Populating States table...");
                        String line;
                        while ((line = br.readLine()) != null) {
                            // Comma separator
                            String[] states = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO States(id, name, abbr) VALUES (?, ?, ?);")) {
                                preparedStatement.setInt(1, Integer.parseInt(states[0]));
                                preparedStatement.setString(2, states[1]);
                                preparedStatement.setString(3, states[2]);
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Done!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
