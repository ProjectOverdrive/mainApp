import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WorkOrderTab {
    
    private JComboBox employeeSelection;
    private JComboBox statusSelection;
    private JTextField detailsText;
    private JComboBox customerSelection;
    private JComboBox prioritySelection;
    
    public JTable createWorkOrderTable() {
        JTable workOrderTable = new JTable();
        workOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        workOrderTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        return workOrderTable;
    }
    
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
    }
    
    public JButton createAddWorkOrderButton() {
        JButton addWorkOrderButton = new JButton("Add Work Order");
        addWorkOrderButton.setBounds(1133, 11, 167, 28);
        addWorkOrderButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddWorkOrderFrame();
            }
	});
        return addWorkOrderButton;
    }
    
    public JButton createDeleteWorkOrderButton() {
        JButton deleteWorkOrderButton = new JButton("Delete Work Order");
        deleteWorkOrderButton.setBounds(1133, 50, 167, 28);
        return deleteWorkOrderButton;
    }
    
    public JButton createUpdateWorkOrderButton() {
        JButton updateWorkOrderButton = new JButton("Update Work Order");
        updateWorkOrderButton.setBounds(1133, 89, 167, 28);
        updateWorkOrderButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO: create update work order window with prepopulated data
                //buildUpdateWorkOrderFrame();
            }
	});
        return updateWorkOrderButton;
    }
    
    public void buildAddWorkOrderFrame() {
        JFrame addWorkOrderFrame = new JFrame();
        addWorkOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWorkOrderFrame.setResizable(false);
        addWorkOrderFrame.setTitle("Add New Work Order");
        addWorkOrderFrame.setBounds(100, 100, 475, 335);
        addWorkOrderFrame.getContentPane().setLayout(null);
        
        JLabel employeeLabel = new JLabel("Employee");
        employeeLabel.setBounds(10, 11, 203, 24);
        addWorkOrderFrame.getContentPane().add(employeeLabel);
        
        //TODO: Create an actual array of employees names from the database.
        String[] employees = {"Jim", "Mary"};
        employeeSelection = new JComboBox(employees);
	employeeSelection.setBounds(10, 40, 150, 32);
	addWorkOrderFrame.getContentPane().add(employeeSelection);
        
        JLabel customerLabel = new JLabel("Customer");
        customerLabel.setBounds(233, 11, 203, 24);
        addWorkOrderFrame.getContentPane().add(customerLabel);
        
        //TODO: Create an actual array of customer names from the database.
        String[] customers = {"Matt", "Leah"};
        customerSelection = new JComboBox(customers);
	customerSelection.setBounds(233, 40, 150, 32);
	addWorkOrderFrame.getContentPane().add(customerSelection);
        
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(10, 82, 203, 24);
        addWorkOrderFrame.getContentPane().add(statusLabel);
        
        String[] statuses = {"New", "In Progress", "Completed"};
        statusSelection = new JComboBox(statuses);
        statusSelection.setBounds(10, 111, 150, 32);
	addWorkOrderFrame.getContentPane().add(statusSelection);
        
        JLabel priorityLabel = new JLabel("Priority");
        priorityLabel.setBounds(233, 82, 203, 24);
        addWorkOrderFrame.getContentPane().add(priorityLabel);
        
        String[] priorities = {"High", "Medium", "Low"};
        prioritySelection = new JComboBox(priorities);
	prioritySelection.setBounds(233, 111, 150, 32);
	addWorkOrderFrame.getContentPane().add(prioritySelection);
      
        detailsText = new JTextField();
	detailsText.setText("Details");
	detailsText.setBounds(10, 173, 383, 32);
	addWorkOrderFrame.getContentPane().add(detailsText);
                
        JButton addWorkOrderSubmitButton = new JButton("Add Work Order");
        addWorkOrderSubmitButton.setBounds(233, 241, 203, 32);
        addWorkOrderFrame.getContentPane().add(addWorkOrderSubmitButton);
        
        addWorkOrderSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if(addWorkOrderHasRequiredFields()) {
                    addWorkOrder();
                    addWorkOrderFrame.dispose();
                } else {
                   createRequiredFieldsErrorWindow();
                }
            }
	});
                
        addWorkOrderFrame.setVisible(true);
    }
    
    private boolean addWorkOrderHasRequiredFields() {
        if (detailsText.getText().equals("Details") || 
                detailsText.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    private void addWorkOrder() {
        String employeeName = employeeSelection.getSelectedItem().toString();
        String customerName = customerSelection.getSelectedItem().toString();
        String status = statusSelection.getSelectedItem().toString();
        String details = detailsText.getText();
        String priority = prioritySelection.getSelectedItem().toString();
        
        //TODO: Add these fields as an object to the database.
    }
    
    private void createRequiredFieldsErrorWindow() {
        JFrame requiredFieldsErrorFrame = new JFrame();
        requiredFieldsErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requiredFieldsErrorFrame.setResizable(false);
        requiredFieldsErrorFrame.setTitle("Error");
        requiredFieldsErrorFrame.setBounds(100, 100, 300, 200);
        requiredFieldsErrorFrame.getContentPane().setLayout(null);
        
        JLabel errorMessage = new JLabel("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);
        
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(105, 100, 90, 28);
        requiredFieldsErrorFrame.getContentPane().add(closeErrorMessageButton);
        
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                requiredFieldsErrorFrame.dispose();
            }
	});
        
        requiredFieldsErrorFrame.setVisible(true);
    }
}
