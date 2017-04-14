import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomerTab {
    
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField phoneNumberText;
    private JTextField emailText;
    private JTextField streetAddressText;
    private JTextField cityText;
    private JTextField stateText;
    private JTextField zipcodeText;
    
    public JTable createCustomerTable() {
        JTable custTable = new JTable();
        custTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        custTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        return custTable;
    }
    
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
    }
    
    public JButton createAddCustomerButton() {
        JButton addCustomerButton = new JButton("Add New Customer");
        addCustomerButton.setBounds(1133, 11, 167, 28);
        addCustomerButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddCustomerFrame();
            }
	});
        return addCustomerButton;
    }
    
    public JButton createDeleteCustomerButton() {
        JButton deleteCustomerButton = new JButton("Delete Customer");
        deleteCustomerButton.setBounds(1133, 50, 167, 28);
        return deleteCustomerButton;
    }
    
    public JButton createUpdateCustomerButton() {
        JButton updateCustomerButton = new JButton("Update Customer");
        updateCustomerButton.setBounds(1133, 89, 167, 28);
        return updateCustomerButton;
    }
    
    public void buildAddCustomerFrame() {
        JFrame addCustomerFrame = new JFrame();
        addCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCustomerFrame.setResizable(false);
        addCustomerFrame.setTitle("Add New Customer");
        addCustomerFrame.setBounds(100, 100, 475, 315);
        addCustomerFrame.getContentPane().setLayout(null);
        
        firstNameText = new JTextField();
	firstNameText.setText("First Name");
	firstNameText.setBounds(10, 11, 203, 32);
	addCustomerFrame.getContentPane().add(firstNameText);
        
        lastNameText = new JTextField();
	lastNameText.setText("Last Name");
	lastNameText.setBounds(233, 11, 203, 32);
	addCustomerFrame.getContentPane().add(lastNameText);
	
        phoneNumberText = new JTextField();
        phoneNumberText.setText("Phone Number");
        phoneNumberText.setBounds(10, 53, 203, 32);
        addCustomerFrame.getContentPane().add(phoneNumberText);
        
        emailText = new JTextField();
	emailText.setText("Email");
	emailText.setBounds(233, 53, 203, 32);
	addCustomerFrame.getContentPane().add(emailText);
        
        streetAddressText = new JTextField();
	streetAddressText.setText("Street Address");
	streetAddressText.setBounds(10, 95, 426, 32);
	addCustomerFrame.getContentPane().add(streetAddressText);
        
        cityText = new JTextField();
        cityText.setText("City");
        cityText.setBounds(10, 137, 203, 32);
        addCustomerFrame.getContentPane().add(cityText);
        
        stateText = new JTextField();
	stateText.setText("State");
	stateText.setBounds(233, 137, 203, 32);
	addCustomerFrame.getContentPane().add(stateText);
        
        zipcodeText = new JTextField();
        zipcodeText.setText("Zipcode");
        zipcodeText.setBounds(10, 179, 203, 32);
        addCustomerFrame.getContentPane().add(zipcodeText);
        
        JButton addCustomerSubmitButton = new JButton("Add New Customer");
        addCustomerSubmitButton.setBounds(233, 221, 203, 32);
        addCustomerFrame.getContentPane().add(addCustomerSubmitButton);
        
        addCustomerSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if(addNewCustomerHasRequiredFields()) {
                    addNewCustomer();
                    addCustomerFrame.dispose();
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
	});
                
        addCustomerFrame.setVisible(true);
    }
    
    private boolean addNewCustomerHasRequiredFields() {
        if (firstNameText.getText().equals("First Name") || 
                firstNameText.getText().equals("")) {
            return false;
        }
        if (lastNameText.getText().equals("Last Name") || 
                lastNameText.getText().equals("")) {
            return false;
        }
        if (phoneNumberText.getText().equals("Phone Number") || 
                phoneNumberText.getText().equals("")) {
            return false;
        }
        if (emailText.getText().equals("Email") || 
                emailText.getText().equals("")) {
            return false;
        }
        if (streetAddressText.getText().equals("Street Address") || 
                streetAddressText.getText().equals("")) {
            return false;
        }
        if (cityText.getText().equals("City") || 
                cityText.getText().equals("")) {
            return false;
        }
        if (stateText.getText().equals("State") || 
                stateText.getText().equals("")) {
            return false;
        }
        if (zipcodeText.getText().equals("Zipcode") || 
                zipcodeText.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    private void addNewCustomer() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = phoneNumberText.getText();
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();
        
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
