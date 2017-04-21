/**
 * Created by Colten on 4/11/17.
 */

//TODO: Tidy up email class

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class SQLConnections {
    private static Connection connection;

    // Singleton design pattern implementation
    private static SQLConnections connectionInstance;

    private SQLConnections() {
    }

    public static SQLConnections getConnectionInstance() {
        if (null == connectionInstance) {
            connectionInstance = new SQLConnections();
        }
        return connectionInstance;
    }
    // End of singleton


    // Establishes database connection
    public static void connect() {
        String databaseURL = "jdbc:sqlite:database.db";

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseURL);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Closes database connection
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Validate user for login, returns true if user exists, else returns false
    public Boolean validateLogin(String username, String password) {

        String query = "SELECT username, password FROM employees";

        // Tries to execute query
        try (// Prepares query statement
             Statement statement = connection.createStatement();
             // Gets results of query
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // If given username and password matches a row in the database
                if (resultSet.getString("username").equalsIgnoreCase(username) && resultSet.getString("password").equals(password)) {

                    statement.close();
                    resultSet.close();
                    return true;
                }
            }

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return false;
    }

    // Validates username and email when resetting password
    public boolean validateUser(String username, String email) {

        String query = "SELECT Username, Email FROM employees";

        // Tries to execute query
        try (// Prepares query statement
             Statement statement = connection.createStatement();
             // Gets results of query
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // If given username and password matches a row in the database
                if (resultSet.getString("username").equalsIgnoreCase(username) && resultSet.getString("email").equalsIgnoreCase(email)) {

                    statement.close();
                    resultSet.close();
                    return true;
                }
            }

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

                statement.close();
                resultSet.close();
                return true;
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // isAdmin = 0 or null
        return false;
    }

    // Pull data for customer table from the database
    public CachedRowSet populateCustomerTable() {
        String query = "SELECT rowid AS 'ID', * FROM customers";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            CachedRowSet rowSet = new CachedRowSetImpl();

            rowSet.populate(resultSet);

            statement.close();
            resultSet.close();

            return rowSet;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return null;
    }

    // Attempts to add new customer to the database
    public void addNewCustomer(String firstName, String lastName, String phoneNumber, String streetAddress,
                               String city, String state, String zipcode, String email) {
        String query = "INSERT INTO customers('First Name', 'Last Name', 'Phone Number', " +
                "'Street Address', City, State, Zipcode, Email) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, streetAddress);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, zipcode);
            statement.setString(8, email);
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Populates update customer box fields
    public String[] fillCustomerUpdate(int selectedCusID) {
        ResultSet resultSet;
        String query = "SELECT * FROM customers WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedCusID);
            resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] fieldResults = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                fieldResults[i] = resultSet.getString(i + 1);
            }

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return null;

    }

    // Updates selected customer info
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Populates work order table
    public CachedRowSet populateWorkOrderTable() {
        String query = "SELECT rowid AS 'ID', * FROM workOrders";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            CachedRowSet rowSet = new CachedRowSetImpl();
            rowSet.populate(resultSet);

            statement.close();
            resultSet.close();

            return rowSet;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Populates employee dropdown selection in work orders tab
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

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Populates customer dropdown selection in work orders tab
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

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Adds work order the database
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Deletes the selected work order
    public void deleteWorkOrder(int selectedWorkOrderID) {
        String query = "DELETE FROM workOrders WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedWorkOrderID);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Prepopulates update work order window with selected work order information
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
            }

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Updates selected work order in database
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Populates inventory table
    public CachedRowSet populateInventoryTable() {
        String query = "SELECT rowid AS 'ID', * FROM inventory";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            CachedRowSet rowSet = new CachedRowSetImpl();
            rowSet.populate(resultSet);

            statement.close();
            resultSet.close();

            return rowSet;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Removes inventory item from the database
    public void deleteInventoryItem(int selectedItemID) {
        String query = "DELETE FROM inventory WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedItemID);

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Prepopulates update inventory window with selected inventory item info
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
            }

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Updates inventory item in the database
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Returns URL of selected inventory item
    public URL getItemURL(int selectedInventoryItemID) {
        String query = "SELECT URL FROM inventory WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedInventoryItemID);
            ResultSet resultSet = statement.executeQuery();

            String temp = resultSet.getString("URL");
            URL url = new URL(temp);

            statement.close();
            resultSet.close();

            return url;

        } catch (SQLException | MalformedURLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Populates management tab employee table
    public CachedRowSet populateEmployeeTable() {
        String query = "SELECT rowid AS 'ID', [First Name], [Last Name], [Phone Number], " +
                "[Street Address], [City], [State], [Zipcode], [Email], [Hourly Pay], " +
                "[Username] FROM employees";

        try {

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            CachedRowSet rowSet = new CachedRowSetImpl();
            rowSet.populate(resultSet);

            statement.close();
            resultSet.close();

            return rowSet;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Adds new employee to the database
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

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Removes employee from the database
    public void deleteEmployee(int selectedEmployeeID) {
        String query = "DELETE FROM employees WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedEmployeeID);

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Prepopulates update employee window with selected employee information
    public String[] fillEmployeeUpdate(int selectedEmployeeID) {
        String query = "SELECT * FROM employees WHERE rowid = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, selectedEmployeeID);
            ResultSet resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] fieldResults = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                fieldResults[i] = resultSet.getString(i + 1);
            }

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Updates the selected employee in the database
    public void updateEmployee(String firstName, String lastName, String phoneNumber, String email,
                               String streetAddress, String city, String state, String zipcode,
                               String username, double hourlyRate, int isManager, int selectedEmployeeID) {
        String query = "UPDATE employees SET 'First Name' = ?, 'Last Name' = ?, 'Phone Number' = ?, " +
                "'Street Address' = ?, City = ?, State = ?, Zipcode = ?, Email = ?, 'Hourly Pay' = ?," +
                "Username = ?, isManager = ? WHERE rowid = ?";

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
            statement.setDouble(9, hourlyRate);
            statement.setString(10, username);
            statement.setInt(11, isManager);
            statement.setInt(12, selectedEmployeeID);

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Populates employee personal info in Employee portal tab
    public CachedRowSet populateEmployeeInfo(String activeUser) {
        String query = "SELECT rowid AS 'ID', [First Name], [Last Name], [Phone Number], " +
                "[Street Address], [City], [State], [Zipcode], [Email], [Hourly Pay], " +
                "[Username] FROM employees WHERE Username = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, activeUser);
            ResultSet resultSet = statement.executeQuery();

            CachedRowSet rowSet = new CachedRowSetImpl();
            rowSet.populate(resultSet);

            statement.close();
            resultSet.close();

            return rowSet;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Prepopulates employee personal info in the update personal info window
    public String[] fillPersonalInfoUpdate(String activeUser) {
        String query = "SELECT [First Name], [Last Name], [Phone Number], " +
                "[Street Address], [City], [State], [Zipcode], [Email], " +
                "[Username] FROM employees WHERE Username = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, activeUser);
            ResultSet resultSet = statement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] fieldResults = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                fieldResults[i] = resultSet.getString(i + 1);
            }

            statement.close();
            resultSet.close();

            return fieldResults;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    // Updates employee personal info from the employee portal in the database
    public void updatePersonalInfo(String firstName, String lastName, String phoneNumber, String email,
                                   String streetAddress, String city, String state, String zipcode,
                                   String username, String activeUser) {
        String query = "UPDATE employees SET 'First Name' = ?, 'Last Name' = ?, 'Phone Number' = ?, " +
                "'Street Address' = ?, City = ?, State = ?, Zipcode = ?, Email = ?," +
                "Username = ? WHERE username = ?";

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
            statement.setString(9, username);
            statement.setString(10, activeUser);

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Verifies current password when attempting to change password
    // Used by both employee portal and login window reset password
    public boolean verifyPassword(String user, String password) {

        String query = "SELECT Password FROM [employees] WHERE [Username] = ?";

        // Tries to execute query
        try {// Prepares query statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user);
            // Gets results of query
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // If given username and password matches a row in the database
                if (resultSet.getString("Password").equals(password)) {

                    statement.close();
                    resultSet.close();
                    return true;
                }
            }

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return false;
    }

    // Updates password in the database for the given employee
    // Used by both employee portal and login window reset password
    public void changePassword(String activeUser, String newPassword) {
        String query = "UPDATE employees SET Password = ? WHERE username = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassword);
            statement.setString(2, activeUser);

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}