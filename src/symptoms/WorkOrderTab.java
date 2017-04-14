import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//TODO: add JDatePicker 
public class WorkOrderTab {
    
    //These are the fields/combo boxes for adding and updating work orders.
    private JComboBox employeeSelection;
    private JComboBox statusSelection;
    private JTextField detailsText;
    private JComboBox customerSelection;
    private JComboBox prioritySelection;
    
    //This method creates the table for displaying work orders.
    public JTable createWorkOrderTable() {
        JTable workOrderTable = new JTable();
        workOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        workOrderTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        return workOrderTable;
    }
    
    //This method creates the button for refreshing the list from the database.
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
        //TODO: make this work
    }
    
    //This method creates the add work order button.
    public JButton createAddWorkOrderButton() {
        JButton addWorkOrderButton = new JButton("Add Work Order");
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
        JButton deleteWorkOrderButton = new JButton("Delete Work Order");
        deleteWorkOrderButton.setBounds(1133, 50, 167, 28);
        return deleteWorkOrderButton;
        //TODO: make this work
    }
    
    //This method creates the update work order button.
    public JButton createUpdateWorkOrderButton() {
        JButton updateWorkOrderButton = new JButton("Update Work Order");
        updateWorkOrderButton.setBounds(1133, 89, 167, 28);
        
        updateWorkOrderButton.addMouseListener(new MouseAdapter() {
            //When this button is pressed, the update work order window should 
            //open.
            @Override
            public void mousePressed(MouseEvent e) {
                buildUpdateWorkOrderFrame();
            }
	});
        return updateWorkOrderButton;
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
        JLabel employeeLabel = new JLabel("Employee");
        employeeLabel.setBounds(10, 11, 203, 24);
        addWorkOrderFrame.getContentPane().add(employeeLabel);
        
        //This is the combo box for selecting the employee on the work order.
        //The user can select from the names of all employees currently in the
        //database.
        //TODO: Create an actual array of employees names from the database.
        String[] employees = {"Jim", "Mary"};
        employeeSelection = new JComboBox(employees);
	employeeSelection.setBounds(10, 40, 150, 32);
	addWorkOrderFrame.getContentPane().add(employeeSelection);
        
        //This is the label for the customer combo box.
        JLabel customerLabel = new JLabel("Customer");
        customerLabel.setBounds(233, 11, 203, 24);
        addWorkOrderFrame.getContentPane().add(customerLabel);
        
        //This is the combo box for selecting the customer on the work order.
        //The user can select from the names of all customers currently in the 
        //database.
        //TODO: Create an actual array of customer names from the database.
        String[] customers = {"Matt", "Leah"};
        customerSelection = new JComboBox(customers);
	customerSelection.setBounds(233, 40, 150, 32);
	addWorkOrderFrame.getContentPane().add(customerSelection);
        
        //This is the label for the status combo box.
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(10, 82, 203, 24);
        addWorkOrderFrame.getContentPane().add(statusLabel);
        
        //This is the combo box for selecting the status on the work order.
        //The user can select the status as "new", "in progress", or "completed"
        String[] statuses = {"New", "In Progress", "Completed"};
        statusSelection = new JComboBox(statuses);
        statusSelection.setBounds(10, 111, 150, 32);
	addWorkOrderFrame.getContentPane().add(statusSelection);
        
        //This is the label for the priority combo box. 
        JLabel priorityLabel = new JLabel("Priority");
        priorityLabel.setBounds(233, 82, 203, 24);
        addWorkOrderFrame.getContentPane().add(priorityLabel);
        
        //This is the combo box for selecting the priority of the work order.
        //The user can select from high, meduim, or low priority.
        String[] priorities = {"High", "Medium", "Low"};
        prioritySelection = new JComboBox(priorities);
	prioritySelection.setBounds(233, 111, 150, 32);
	addWorkOrderFrame.getContentPane().add(prioritySelection);
      
        //This is the text field for the work order details.
        detailsText = new JTextField();
	detailsText.setText("Details");
	detailsText.setBounds(10, 173, 383, 32);
	addWorkOrderFrame.getContentPane().add(detailsText);
        
        //This is the cancel button.
        JButton addWorkOrderCancelButton = new JButton("Cancel");
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
        JButton addWorkOrderSubmitButton = new JButton("Add Work Order");
        addWorkOrderSubmitButton.setBounds(233, 241, 203, 32);
        addWorkOrderFrame.getContentPane().add(addWorkOrderSubmitButton);
        
        addWorkOrderSubmitButton.addMouseListener(new MouseAdapter() {
            //When the submit button is pushed, attempt to add the work order
            //to the database.
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that the work order has all required fields.
                if(workOrderHasRequiredFields()) {
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
        
        //TODO: Add these fields as an object to the database.
    }
    
    //This method creates and controls the update work order window.
    public void buildUpdateWorkOrderFrame() {
        //TODO: make this have prepopulated data from the db
        //This initializes the update work order window.
        JFrame updateWorkOrderFrame = new JFrame();
        updateWorkOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateWorkOrderFrame.setResizable(false);
        updateWorkOrderFrame.setTitle("Update Work Order");
        updateWorkOrderFrame.setBounds(100, 100, 475, 335);
        updateWorkOrderFrame.getContentPane().setLayout(null);
        
        //This is the label for the employee combo box.
        JLabel employeeLabel = new JLabel("Employee");
        employeeLabel.setBounds(10, 11, 203, 24);
        updateWorkOrderFrame.getContentPane().add(employeeLabel);
        
        //This is the combo box for selecting the employee on the work order.
        //The user can select from the names of all employees currently in the
        //database.
        //TODO: Create an actual array of employees names from the database.
        String[] employees = {"Jim", "Mary"};
        employeeSelection = new JComboBox(employees);
	employeeSelection.setBounds(10, 40, 150, 32);
	updateWorkOrderFrame.getContentPane().add(employeeSelection);
        
        //This is the label for the customer combo box.
        JLabel customerLabel = new JLabel("Customer");
        customerLabel.setBounds(233, 11, 203, 24);
        updateWorkOrderFrame.getContentPane().add(customerLabel);
        
        //This is the combo box for selecting the customer on the work order.
        //The user can select from the names of all customers currently in the 
        //database.
        //TODO: Create an actual array of customer names from the database.
        String[] customers = {"Matt", "Leah"};
        customerSelection = new JComboBox(customers);
	customerSelection.setBounds(233, 40, 150, 32);
	updateWorkOrderFrame.getContentPane().add(customerSelection);
        
        //This is the label for the status combo box.
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(10, 82, 203, 24);
        updateWorkOrderFrame.getContentPane().add(statusLabel);
        
        //This is the combo box for selecting the status on the work order.
        //The user can select the status as "new", "in progress", or "completed"
        String[] statuses = {"New", "In Progress", "Completed"};
        statusSelection = new JComboBox(statuses);
        statusSelection.setBounds(10, 111, 150, 32);
	updateWorkOrderFrame.getContentPane().add(statusSelection);
        
        //This is the label for the priority combo box.
        JLabel priorityLabel = new JLabel("Priority");
        priorityLabel.setBounds(233, 82, 203, 24);
        updateWorkOrderFrame.getContentPane().add(priorityLabel);
        
        //This is the combo box for selecting the priority of the work order.
        //The user can select from high, meduim, or low priority.
        String[] priorities = {"High", "Medium", "Low"};
        prioritySelection = new JComboBox(priorities);
	prioritySelection.setBounds(233, 111, 150, 32);
	updateWorkOrderFrame.getContentPane().add(prioritySelection);
      
        //This is the text field for the work order details.
        detailsText = new JTextField();
	detailsText.setText("Details");
	detailsText.setBounds(10, 173, 383, 32);
	updateWorkOrderFrame.getContentPane().add(detailsText);
        
        //This is the cancel button.
        JButton updateWorkOrderCancelButton = new JButton("Cancel");
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
        JButton updateWorkOrderSubmitButton = new JButton("Update Work Order");
        updateWorkOrderSubmitButton.setBounds(233, 241, 203, 32);
        updateWorkOrderFrame.getContentPane().add(updateWorkOrderSubmitButton);
        
        //When the submit button is pressed, attempt to update the work order 
        //in the database.
        updateWorkOrderSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present.
                if(workOrderHasRequiredFields()) {
                    //Update the work order in the database and close the 
                    //update work order window.
                    updateWorkOrder();
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
    private void updateWorkOrder() {
        String employeeName = employeeSelection.getSelectedItem().toString();
        String customerName = customerSelection.getSelectedItem().toString();
        String status = statusSelection.getSelectedItem().toString();
        String details = detailsText.getText();
        String priority = prioritySelection.getSelectedItem().toString();
        
        //TODO: update the db with these fields
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
        JLabel errorMessage = new JLabel("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);
        
        //This button closes the error message.
        JButton closeErrorMessageButton = new JButton("OK");
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