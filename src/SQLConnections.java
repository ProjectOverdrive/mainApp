/**
 * Created by Colten on 4/11/17.
 */

import javax.swing.*;
import java.sql.*;

public class SQLConnections {

    private static Connection connect() {
        String databaseURL = "jdbc:sqlite:mainApp/database.db";

        Connection connection = null;

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);
            //System.out.println("Connection to SQLite has been established.");

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        /*if (connection == null) {
            System.out.println("I didn't connect");
            System.exit(0);
        }*/

        return connection;
    }

    // Validate user for login, returns true if user exists, else returns false
    public Boolean validateUser(String username, String password) {

        String query = "SELECT username, password FROM employees";

        // Tries to execute query
        try (Connection conn = this.connect();
             // Prepares query statement
             Statement statement = conn.createStatement();
             // Gets results of query
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // If given username and password matches a row in the database
                if (resultSet.getString("username").equalsIgnoreCase(username) && resultSet.getString("password").equals(password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // Refresh table

    // Add customer

    // Delete Customer

    // Update Customer

    //

}