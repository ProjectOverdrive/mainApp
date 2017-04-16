/**
 * Created by Colten on 4/11/17.
 */

//TODO: Set all errors to show window not print to console

import javax.swing.*;
import java.sql.*;

public class SQLConnections {

    private static Connection connect() {
        String databaseURL = "jdbc:sqlite:database.db";

        Connection connection = null;

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return connection;
    }

    // Validate user for login, returns true if user exists, else returns false
    public Boolean validateUser(String username, String password) {

        String query = "SELECT username, password FROM employees";

        // Tries to execute query
        try (Connection connection = this.connect();
             // Prepares query statement
             Statement statement = connection.createStatement();
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

    public ResultSet populateCustomerTable() {
        String query = "SELECT * FROM customers";

        try {
            Connection connection = this.connect();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Attempts to add new customer to the database
    public void addNewCustomer(String firstName, String lastName, String phoneNumber, String streetAddress,
                               String city, String state, String zipcode, String email) {
        String query = "INSERT INTO customers('First Name', 'Last Name', 'Phone Number', 'Street Address'," +
                "City, State, Zipcode, Email) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, streetAddress);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, zipcode);
            statement.setString(8, email);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Attempts to add new inventory item to the database
    public void addNewInventoryItem(String description, String vendor, String location,
                                    int quantity, double unitCost, String url) {
        String query = "INSERT INTO inventory(Description, Vendor, Location, Quantity," +
                "'Unit Cost', URL) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, description);
            statement.setString(2, vendor);
            statement.setString(3, location);
            statement.setInt(4, quantity);
            statement.setDouble(5, unitCost);
            statement.setString(6, url);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}