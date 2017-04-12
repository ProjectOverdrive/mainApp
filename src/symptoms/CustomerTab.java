import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomerTab {
    public static JTable createCustomerTable() {
        JTable custTable = new JTable();
        custTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        custTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        return custTable;
    }
    
    public static JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
    }
    
    public static JButton createAddCustomerButton() {
        JButton addCustomerButton = new JButton("Add New Customer");
        addCustomerButton.setBounds(1133, 11, 167, 28);
        return addCustomerButton;
    }
    
    public static JButton createDeleteCustomerButton() {
        JButton deleteCustomerButton = new JButton("Delete Customer");
        deleteCustomerButton.setBounds(1133, 50, 167, 28);
        return deleteCustomerButton;
    }
    
    public static JButton createUpdateCustomerButton() {
        JButton updateCustomerButton = new JButton("Update Customer");
        updateCustomerButton.setBounds(1133, 89, 167, 28);
        return updateCustomerButton;
    }
}
