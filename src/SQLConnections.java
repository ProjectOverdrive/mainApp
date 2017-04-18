/**
 * Created by Colten on 4/11/17.
 */

//TODO: Set all errors to show window not print to console
//TODO: Update password for users
//TODO: Refresh tables on click
//TODO: Make table cells uneditable

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

    // Pull data for customer table from the database
    public ResultSet populateCustomerTable() {
        String query = "SELECT rowid AS 'ID', * FROM customers";

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
        String query = "INSERT INTO customers('First Name', 'Last Name', 'Phone Number', " +
                "'Street Address', City, State, Zipcode, Email) VALUES(?,?,?,?,?,?,?,?)";

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

    // Removes customer from the database
    // Value will always be unique as customers are deleted by row ID
    public void deleteCustomer(int selectedCusID) {
        String query = "DELETE FROM customers WHERE rowid = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, selectedCusID);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Populates update customer box fields
    public String[] fillCustomerUpdate(int selectedCusID) {
        ResultSet resultSet;
        String[] fieldResults = new String[8];
        String query = "SELECT * FROM customers WHERE rowid = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, selectedCusID);
            resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < columnCount; i++) {
                fieldResults[i] = resultSet.getString(i + 1);
            }

            return fieldResults;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    // Updates selected customer
    public void updateCustomer(String firstName, String lastName, String phoneNumber, String email,
                               String streetAddress, String city, String state, String zipcode,
                               int selectedCusID) {
        String query = "UPDATE customers SET 'First Name' = ?, 'Last Name' = ?, 'Phone Number' = ?, " +
                "'Street Address' = ?, City = ?, State = ?, Zipcode = ?, Email = ? WHERE rowid = ?";

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
            statement.setInt(9, selectedCusID);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet populateInventoryTable() {
        String query = "SELECT rowid AS 'ID', * FROM inventory";

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

    // Attempts to add new inventory item to the database
    public void addNewInventoryItem(String partNumber, String description, String vendor, String location,
                                    int quantity, double unitCost, String url) {
        String query = "INSERT INTO inventory('Part Number', Description, Vendor, Location, Quantity," +
                "'Unit Cost', URL) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, partNumber);
            statement.setString(2, description);
            statement.setString(3, vendor);
            statement.setString(4, location);
            statement.setInt(5, quantity);
            statement.setDouble(6, unitCost);
            statement.setString(7, url);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Removes inventory item from the database
    public void deleteInventoryItem(int selectedItemID) {
        String query = "DELETE FROM inventory WHERE rowid = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, selectedItemID);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet populateEmployeeTable() {
        String query = "SELECT rowid AS 'ID', [First Name], [Last Name], [Phone Number], " +
                "[Street Address], [City], [State], [Zipcode], [Email], [Hourly Pay], " +
                "[Username] FROM employees";

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
}