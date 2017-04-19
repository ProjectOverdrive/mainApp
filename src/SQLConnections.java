/**
 * Created by Colten on 4/11/17.
 */

//TODO: Set all errors to show window not print to console
//TODO: Update password for users
//TODO: Refresh tables on click
//TODO: Make table cells uneditable
//TODO: forgot password

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class SQLConnections {
    private Connection connection;

    public SQLConnections() {
        connect();
    }

    public Connection connect() {
        String databaseURL = "jdbc:sqlite:database.db";


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
        try (// Prepares query statement
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

    // Checks if logged in user is an admin
    public boolean isAdmin(String activeUser) {

        String query = "SELECT isManager FROM [employees] WHERE [Username] = ?";

        // Tries to execute query
        try {
            // Prepares query statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, activeUser);
            // Gets results of query
            ResultSet resultSet = statement.executeQuery();

            // If given username is a manager account
            if (resultSet.getInt("isManager") == 1) {

                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // isAdmin = 0 or null
        return false;
    }

    // Pull data for customer table from the database
    public ResultSet populateCustomerTable() {
        String query = "SELECT rowid AS 'ID', * FROM customers";

        try {
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

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
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

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedCusID);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Populates update customer box fields
    public String[] fillCustomerUpdate(int selectedCusID) {
        ResultSet resultSet;
        //TODO: Make this not hard coded
        String[] fieldResults = new String[8];
        String query = "SELECT * FROM customers WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
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

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
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

    public ResultSet populateWorkOrderTable() {
        String query = "SELECT rowid AS 'ID', * FROM workOrders";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String[] populateEmployeeDropdown() {

        String query = "SELECT [First Name] FROM employees";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> fieldResultsTemp = new ArrayList<>();

            while (resultSet.next()) {
                fieldResultsTemp.add(resultSet.getString("First Name"));
            }

            fieldResultsTemp.add(0, "");

            String[] fieldResults = new String[fieldResultsTemp.size()];
            fieldResults = fieldResultsTemp.toArray(fieldResults);

            return fieldResults;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String[] populateCustomerDropdown() {
        String query = "SELECT [First Name] FROM customers";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> fieldResultsTemp = new ArrayList<>();

            while (resultSet.next()) {
                fieldResultsTemp.add(resultSet.getString("First Name"));
            }

            fieldResultsTemp.add(0, "");

            String[] fieldResults = new String[fieldResultsTemp.size()];
            fieldResults = fieldResultsTemp.toArray(fieldResults);

            return fieldResults;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void addWorkOrder(String employee, String customer, String status, String priority,
                             String details) {
        String query = "INSERT INTO workOrders(Employee, Customer, Status, " +
                "Priority, Details) VALUES(?,?,?,?,?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee);
            statement.setString(2, customer);
            statement.setString(3, status);
            statement.setString(4, priority);
            statement.setString(5, details);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteWorkOrder(int selectedWorkOrderID) {
        String query = "DELETE FROM workOrders WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedWorkOrderID);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String[] fillWorkOrderUpdate(int selectedWorkOrderID) {

        String query = "SELECT * FROM workOrders WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedWorkOrderID);
            ResultSet resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] fieldResults = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                fieldResults[i] = resultSet.getString(i + 1);

                System.out.println(fieldResults[i]);
            }

            return fieldResults;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void updateWorkOrder(String employeeName, String customerName, String status,
                                String priority, String details, int selectedWorkOrderID) {
        String query = "UPDATE workOrders SET Employee = ?, Customer = ?, Status = ?, " +
                "Priority = ?, Details = ? WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employeeName);
            statement.setString(2, customerName);
            statement.setString(3, status);
            statement.setString(4, priority);
            statement.setString(5, details);
            statement.setInt(6, selectedWorkOrderID);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet populateInventoryTable() {
        String query = "SELECT rowid AS 'ID', * FROM inventory";

        try {
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

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
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

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedItemID);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String[] fillUpdateInventoryItem(int selectedInventoryItemID) {
        String query = "SELECT * FROM inventory WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedInventoryItemID);
            ResultSet resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] fieldResults = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                fieldResults[i] = resultSet.getString(i + 1);

                System.out.println(fieldResults[i]);
            }

            return fieldResults;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void updateInventoryItem(String number, String description, String vendor, String location,
                                    int quantity, double unitCost, String url, int selectedInventoryItemID) {
        String query = "UPDATE inventory SET 'Part Number' = ?, Description = ?, Vendor = ?, " +
                "Location = ?, Quantity = ?, 'Unit Cost' = ?, URL = ? WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, number);
            statement.setString(2, description);
            statement.setString(3, vendor);
            statement.setString(4, location);
            statement.setInt(5, quantity);
            statement.setDouble(6, unitCost);
            statement.setString(7, url);
            statement.setInt(8, selectedInventoryItemID);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public URL getItemURL(int selectedInventoryItemID) {
        String query = "SELECT URL FROM inventory WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedInventoryItemID);
            ResultSet resultSet = statement.executeQuery();

            String temp = resultSet.getString("URL");
            URL url = new URL(temp);


            return url;

        } catch (SQLException | MalformedURLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public ResultSet populateEmployeeTable() {
        String query = "SELECT rowid AS 'ID', [First Name], [Last Name], [Phone Number], " +
                "[Street Address], [City], [State], [Zipcode], [Email], [Hourly Pay], " +
                "[Username] FROM employees";

        try {

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void addNewEmployee(String firstName, String lastName, String phoneNumber, String streetAddress,
                               String city, String state, String zipcode, String email, double hourlyRate,
                               String username, String password, int isManager) {
        String query = "INSERT INTO employees('First Name', 'Last Name', 'Phone Number', " +
                "'Street Address', City, State, Zipcode, Email, 'Hourly Pay', " +
                "Username, Password, isManager) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, streetAddress);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, zipcode);
            statement.setString(8, email);
            statement.setDouble(9, hourlyRate);
            statement.setString(10, username);
            statement.setString(11, password);
            statement.setInt(12, isManager);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}