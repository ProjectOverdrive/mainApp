import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//TODO: (Caroline) add JDatePicker 
public class WorkOrderTab {
    
    //These are the fields/combo boxes for adding and updating work orders.
    private JComboBox employeeSelection;
    private JComboBox statusSelection;
    private JTextField detailsText;
    private JComboBox customerSelection;
    private JComboBox prioritySelection;
    private SQLConnections connection;
    private JTable workOrderTable;

    public WorkOrderTab() {
        this.connection = SQLConnections.getConnectionInstance();
    }

    //This method creates the table for displaying work orders.
    public JTable createWorkOrderTable() {
        workOrderTable = new JTable();
        workOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        connection.connect();
        workOrderTable.setModel(DbUtils.resultSetToTableModel(connection.populateWorkOrderTable()));
        connection.disconnect();
        workOrderTable.getTableHeader().setReorderingAllowed(false);
        workOrderTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        workOrderTable.setFont(new Font("Tahoma", Font.PLAIN, 12));

        return workOrderTable;
    }

    //This method creates the button for refreshing the list from the database.
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
        //TODO: (Colten) make this work
    }

    //This method creates the add work order button.
    public JButton createAddWorkOrderButton() {
        JButton addWorkOrderButton = new JButton();
        addWorkOrderButton.setText("Add Work Order");
        addWorkOrderButton.setBounds(1133, 11, 167, 28);

        addWorkOrderButton.addMouseListener(new MouseAdapter() {
            //When the button is pressed, the add work order window should open.
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddWorkOrderFrame();
            }
        });
        return addWorkOrderButton;
    }

    //This method creates the delete work order button.
    public JButton createDeleteWorkOrderButton() {
        JButton deleteWorkOrderButton = new JButton();
        deleteWorkOrderButton.setText("Delete Work Order");
        deleteWorkOrderButton.setBounds(1133, 50, 167, 28);

        deleteWorkOrderButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int row = workOrderTable.getSelectedRow();
                if (row == -1) {
                    createDeleteWorkOrderErrorWindow();
                } else {
                    System.out.println(row);
                    int selectedWorkOrderID = (int) workOrderTable.getValueAt(row, 0);
                    connection.connect();
                    connection.deleteWorkOrder(selectedWorkOrderID);
                    connection.disconnect();
                    JOptionPane.showMessageDialog(null, "Work Order Removed");
                }
            }
        });

        return deleteWorkOrderButton;
    }

    //This method creates the update work order button.
    public JButton createUpdateWorkOrderButton() {
        JButton updateWorkOrderButton = new JButton();
        updateWorkOrderButton.setText("Update Work Order");
        updateWorkOrderButton.setBounds(1133, 89, 167, 28);

        updateWorkOrderButton.addMouseListener(new MouseAdapter() {
            //When this button is pressed, the update work order window should 
            //open.
            @Override
            public void mousePressed(MouseEvent e) {
                int row = workOrderTable.getSelectedRow();
                if (row == -1) {
                    createUpdateWorkOrderErrorWindow();
                } else {
                    buildUpdateWorkOrderFrame();
                }
            }
        });

        return updateWorkOrderButton;
    }
    
    //This method creates the error window for deleting w/o selecting.
    private void createDeleteWorkOrderErrorWindow() {
        //This creates the error window
        JFrame deleteErrorFrame = new JFrame();
        deleteErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteErrorFrame.setResizable(false);
        deleteErrorFrame.setTitle("Error");
        deleteErrorFrame.setBounds(100, 100, 350, 200);
        deleteErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Select a work order to delete.");
        errorMessage.setBounds(80, 50, 250, 32);
        deleteErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        deleteErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                deleteErrorFrame.dispose();
            }
        });

        deleteErrorFrame.setVisible(true);
    }
    
    //This method creates the error window for updating w/o selecting.
    private void createUpdateWorkOrderErrorWindow() {
        //This creates the error window
        JFrame updateErrorFrame = new JFrame();
        updateErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateErrorFrame.setResizable(false);
        updateErrorFrame.setTitle("Error");
        updateErrorFrame.setBounds(100, 100, 350, 200);
        updateErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Select a work order to update.");
        errorMessage.setBounds(80, 50, 250, 32);
        updateErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        updateErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateErrorFrame.dispose();
            }
        });

        updateErrorFrame.setVisible(true);
    }
    
    //This method creates and controls the add work order window.
    public void buildAddWorkOrderFrame() {
        //This initializes the add work order window.
        JFrame addWorkOrderFrame = new JFrame();
        addWorkOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWorkOrderFrame.setResizable(false);
        addWorkOrderFrame.setTitle("Add New Work Order");
        addWorkOrderFrame.setBounds(100, 100, 475, 335);
        addWorkOrderFrame.getContentPane().setLayout(null);

        //This is the label for the employee combo box.
        JLabel employeeLabel = new JLabel();
        employeeLabel.setText("Employee");
        employeeLabel.setBounds(10, 11, 203, 24);
        addWorkOrderFrame.getContentPane().add(employeeLabel);

        //This is the combo box for selecting the employee on the work order.
        //The user can select from the names of all employees currently in the
        //database.
        connection.connect();
        String[] employees = connection.populateEmployeeDropdown();
        connection.disconnect();

        employeeSelection = new JComboBox(employees);
        employeeSelection.setBounds(10, 40, 150, 32);
        addWorkOrderFrame.getContentPane().add(employeeSelection);

        //This is the label for the customer combo box.
        JLabel customerLabel = new JLabel();
        customerLabel.setText("Customer");
        customerLabel.setBounds(233, 11, 203, 24);
        addWorkOrderFrame.getContentPane().add(customerLabel);

        //This is the combo box for selecting the customer on the work order.
        //The user can select from the names of all customers currently in the 
        //database.
        connection.connect();
        String[] customers = connection.populateCustomerDropdown();
        connection.disconnect();

        customerSelection = new JComboBox(customers);
        customerSelection.setBounds(233, 40, 150, 32);
        addWorkOrderFrame.getContentPane().add(customerSelection);

        //This is the label for the status combo box.
        JLabel statusLabel = new JLabel();
        statusLabel.setText("Status");
        statusLabel.setBounds(10, 82, 203, 24);
        addWorkOrderFrame.getContentPane().add(statusLabel);

        //This is the combo box for selecting the status on the work order.
        //The user can select the status as "new", "in progress", or "completed"
        String[] statuses = {"", "New", "In Progress", "Completed"};
        statusSelection = new JComboBox(statuses);
        statusSelection.setBounds(10, 111, 150, 32);
        addWorkOrderFrame.getContentPane().add(statusSelection);

        //This is the label for the priority combo box. 
        JLabel priorityLabel = new JLabel();
        priorityLabel.setText("Priority");
        priorityLabel.setBounds(233, 82, 203, 24);
        addWorkOrderFrame.getContentPane().add(priorityLabel);

        //This is the combo box for selecting the priority of the work order.
        //The user can select from high, meduim, or low priority.
        String[] priorities = {"", "High", "Medium", "Low"};
        prioritySelection = new JComboBox(priorities);
        prioritySelection.setBounds(233, 111, 150, 32);
        addWorkOrderFrame.getContentPane().add(prioritySelection);
        
        //Creates label for details.
        JLabel detailsLabel = new JLabel();
        detailsLabel.setText("Details");
        detailsLabel.setBounds(10, 160, 203, 24);
        addWorkOrderFrame.getContentPane().add(detailsLabel);
        
        //This is the text field for the work order details.
        detailsText = new JTextField();
        detailsText.setBounds(10, 190, 383, 32);
        addWorkOrderFrame.getContentPane().add(detailsText);

        //This is the cancel button.
        JButton addWorkOrderCancelButton = new JButton();
        addWorkOrderCancelButton.setText("Cancel");
        addWorkOrderCancelButton.setBounds(10, 241, 203, 32);
        addWorkOrderFrame.getContentPane().add(addWorkOrderCancelButton);

        addWorkOrderCancelButton.addMouseListener(new MouseAdapter() {
            //When this button is pressed, the add work order window should close.
            @Override
            public void mousePressed(MouseEvent arg0) {
                addWorkOrderFrame.dispose();
            }
        });

        //This is the submit button.
        JButton addWorkOrderSubmitButton = new JButton();
        addWorkOrderSubmitButton.setText("Add Work Order");
        addWorkOrderSubmitButton.setBounds(233, 241, 203, 32);
        addWorkOrderFrame.getContentPane().add(addWorkOrderSubmitButton);

        addWorkOrderSubmitButton.addMouseListener(new MouseAdapter() {
            //When the submit button is pushed, attempt to add the work order
            //to the database.
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that the work order has all required fields.
                if (workOrderHasRequiredFields()) {
                    //If all fields are present, add the work order to the 
                    //database and close the add work order window.
                    addWorkOrder();
                    addWorkOrderFrame.dispose();
                } else {
                    //Display an error about the required fields being gone.
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        addWorkOrderFrame.setVisible(true);
    }

    //This method verifies that the required fields are present.
    private boolean workOrderHasRequiredFields() {
        //If the text field is the default value or empty, return false.
        if (detailsText.getText().equals("Details") ||
                detailsText.getText().equals("")) {
            return false;
        }
        if (employeeSelection.getSelectedIndex() == 0) {
            return false;
        }
        if (customerSelection.getSelectedIndex() == 0) {
            return false;
        }
        if (statusSelection.getSelectedIndex() == 0) {
            return false;
        }
        if (prioritySelection.getSelectedIndex() == 0) {
            return false;
        }
        //Otherwise, return true.
        return true;
    }

    //This method adds the work order to the database.
    private void addWorkOrder() {
        String employeeName = employeeSelection.getSelectedItem().toString();
        String customerName = customerSelection.getSelectedItem().toString();
        String status = statusSelection.getSelectedItem().toString();
        String details = detailsText.getText();
        String priority = prioritySelection.getSelectedItem().toString();

        connection.connect();
        connection.addWorkOrder(employeeName, customerName, status, priority, details);
        connection.disconnect();
    }

    //This method creates and controls the update work order window.
    public void buildUpdateWorkOrderFrame() {
        // Populates fields with data from selected row
        int row = workOrderTable.getSelectedRow();
        int selectedWorkOrderID = (int) workOrderTable.getValueAt(row, 0);
        connection.connect();
        String[] updateWorkOrderFields = connection.fillWorkOrderUpdate(selectedWorkOrderID);
        connection.disconnect();

        //This initializes the update work order window.
        JFrame updateWorkOrderFrame = new JFrame();
        updateWorkOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateWorkOrderFrame.setResizable(false);
        updateWorkOrderFrame.setTitle("Update Work Order");
        updateWorkOrderFrame.setBounds(100, 100, 475, 335);
        updateWorkOrderFrame.getContentPane().setLayout(null);

        //This is the label for the employee combo box.
        JLabel employeeLabel = new JLabel();
        employeeLabel.setText("Employee");
        employeeLabel.setBounds(10, 11, 203, 24);
        updateWorkOrderFrame.getContentPane().add(employeeLabel);

        //This is the combo box for selecting the employee on the work order.
        //The user can select from the names of all employees currently in the
        //database.
        connection.connect();
        String[] employees = connection.populateEmployeeDropdown();
        connection.disconnect();

        employeeSelection = new JComboBox(employees);
        employeeSelection.setSelectedItem(updateWorkOrderFields[0]);
        employeeSelection.setBounds(10, 40, 150, 32);
        updateWorkOrderFrame.getContentPane().add(employeeSelection);

        //This is the label for the customer combo box.
        JLabel customerLabel = new JLabel();
        customerLabel.setText("Customer");
        customerLabel.setBounds(233, 11, 203, 24);
        updateWorkOrderFrame.getContentPane().add(customerLabel);

        //This is the combo box for selecting the customer on the work order.
        //The user can select from the names of all customers currently in the 
        //database.
        connection.connect();
        String[] customers = connection.populateCustomerDropdown();
        connection.disconnect();

        customerSelection = new JComboBox(customers);
        customerSelection.setSelectedItem(updateWorkOrderFields[1]);
        customerSelection.setBounds(233, 40, 150, 32);
        updateWorkOrderFrame.getContentPane().add(customerSelection);

        //This is the label for the status combo box.
        JLabel statusLabel = new JLabel();
        statusLabel.setText("Status");
        statusLabel.setBounds(10, 82, 203, 24);
        updateWorkOrderFrame.getContentPane().add(statusLabel);

        //This is the combo box for selecting the status on the work order.
        //The user can select the status as "new", "in progress", or "completed"
        String[] statuses = {"New", "In Progress", "Completed"};
        statusSelection = new JComboBox(statuses);
        statusSelection.setSelectedItem(updateWorkOrderFields[2]);
        statusSelection.setBounds(10, 111, 150, 32);
        updateWorkOrderFrame.getContentPane().add(statusSelection);

        //This is the label for the priority combo box.
        JLabel priorityLabel = new JLabel();
        priorityLabel.setText("Priority");
        priorityLabel.setBounds(233, 82, 203, 24);
        updateWorkOrderFrame.getContentPane().add(priorityLabel);

        //This is the combo box for selecting the priority of the work order.
        //The user can select from high, medium, or low priority.
        String[] priorities = {"High", "Medium", "Low"};
        prioritySelection = new JComboBox(priorities);
        prioritySelection.setSelectedItem(updateWorkOrderFields[3]);
        prioritySelection.setBounds(233, 111, 150, 32);
        updateWorkOrderFrame.getContentPane().add(prioritySelection);
        
        //Creates label for details.
        JLabel detailsLabel = new JLabel();
        detailsLabel.setText("Details");
        detailsLabel.setBounds(10, 160, 203, 24);
        updateWorkOrderFrame.getContentPane().add(detailsLabel);

        //This is the text field for the work order details.
        detailsText = new JTextField();
        detailsText.setText(updateWorkOrderFields[4]);
        detailsText.setBounds(10, 190, 383, 32);
        updateWorkOrderFrame.getContentPane().add(detailsText);

        //This is the cancel button.
        JButton updateWorkOrderCancelButton = new JButton();
        updateWorkOrderCancelButton.setText("Cancel");
        updateWorkOrderCancelButton.setBounds(10, 241, 203, 32);
        updateWorkOrderFrame.getContentPane().add(updateWorkOrderCancelButton);

        //When this button is pressed, the update work order window will close.
        updateWorkOrderCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateWorkOrderFrame.dispose();
            }
        });

        //This is the submit button.
        JButton updateWorkOrderSubmitButton = new JButton();
        updateWorkOrderSubmitButton.setText("Update Work Order");
        updateWorkOrderSubmitButton.setBounds(233, 241, 203, 32);
        updateWorkOrderFrame.getContentPane().add(updateWorkOrderSubmitButton);

        //When the submit button is pressed, attempt to update the work order 
        //in the database.
        updateWorkOrderSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present.
                if (workOrderHasRequiredFields()) {
                    //Update the work order in the database and close the 
                    //update work order window.
                    updateWorkOrder(selectedWorkOrderID);
                    updateWorkOrderFrame.dispose();
                } else {
                    //If a required field is missing, display the error window.
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        updateWorkOrderFrame.setVisible(true);
    }

    //This method updates the work order in the database.
    private void updateWorkOrder(int selectedWorkOrderID) {

        String employeeName = employeeSelection.getSelectedItem().toString();
        String customerName = customerSelection.getSelectedItem().toString();
        String status = statusSelection.getSelectedItem().toString();
        String priority = prioritySelection.getSelectedItem().toString();
        String details = detailsText.getText();

        connection.connect();
        connection.updateWorkOrder(employeeName, customerName, status,
                priority, details, selectedWorkOrderID);
        connection.disconnect();
    }

    //This method creates the required fields error window.
    private void createRequiredFieldsErrorWindow() {
        //This initializes the error window.
        JFrame requiredFieldsErrorFrame = new JFrame();
        requiredFieldsErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requiredFieldsErrorFrame.setResizable(false);
        requiredFieldsErrorFrame.setTitle("Error");
        requiredFieldsErrorFrame.setBounds(100, 100, 300, 200);
        requiredFieldsErrorFrame.getContentPane().setLayout(null);

        //This label displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);

        //This button closes the error message.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(105, 100, 90, 28);
        requiredFieldsErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If the close button is pressed, the window should be closed.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                requiredFieldsErrorFrame.dispose();
            }
        });

        requiredFieldsErrorFrame.setVisible(true);
    }
}