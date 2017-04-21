import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerTab {

    //These are the text fields for the add/update customer UI.
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField phoneNumberText;
    private JTextField emailText;
    private JTextField streetAddressText;
    private JTextField cityText;
    private JTextField stateText;
    private JTextField zipcodeText;
    private JTable custTable;
    private SQLConnections connection;

    public CustomerTab() {
        this.connection = SQLConnections.getConnectionInstance();
    }

    //This method creates the customer table.
    public JTable createCustomerTable() {
        custTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        custTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        connection.connect();
        TableModel tableModel = DbUtils.resultSetToTableModel(connection.populateCustomerTable());
        custTable.setModel(tableModel);
        connection.disconnect();

        custTable.getTableHeader().setReorderingAllowed(false);
        custTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        custTable.setFont(new Font("Tahoma", Font.PLAIN, 12));

        return custTable;
    }

    //This method creates the add customer button.
    public JButton createAddCustomerButton() {
        JButton addCustomerButton = new JButton();
        addCustomerButton.setText("Add New Customer");
        addCustomerButton.setBounds(1133, 11, 167, 28);

        addCustomerButton.addMouseListener(new MouseAdapter() {
            //The add customer window should open when button is pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddCustomerFrame();
            }
        });
        
        return addCustomerButton;
    }
    
    //Ths method creates the delete customer button.
    public JButton createDeleteCustomerButton() {
        JButton deleteCustomerButton = new JButton();
        deleteCustomerButton.setText("Delete Customer");
        deleteCustomerButton.setBounds(1133, 50, 167, 28);

        //The customer should be deleted when this is pressed.
        deleteCustomerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = custTable.getSelectedRow();
                if (row == -1) {
                    createDeleteCustomerErrorWindow();
                } else {
                    int selectedCusID = (int) custTable.getValueAt(row, 0);
                    connection.connect();
                    connection.deleteCustomer(selectedCusID);
                    connection.disconnect();
                    JOptionPane.showMessageDialog(null, "Customer Removed");  
                }
            }
        });
        
        return deleteCustomerButton;
    }
    
    //This method creates the error window for deleting w/o selecting.
    private void createDeleteCustomerErrorWindow() {
        //This creates the error window
        JFrame deleteErrorFrame = new JFrame();
        deleteErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteErrorFrame.setResizable(false);
        deleteErrorFrame.setTitle("Error");
        deleteErrorFrame.setBounds(100, 100, 350, 200);
        deleteErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Select a customer to delete.");
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

    //This method creates the update customer button.
    public JButton createUpdateCustomerButton() {
        JButton updateCustomerButton = new JButton();
        updateCustomerButton.setText("Update Customer");
        updateCustomerButton.setBounds(1133, 89, 167, 28);

        updateCustomerButton.addMouseListener(new MouseAdapter() {
            //The update customer window should open when this button is pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                int row = custTable.getSelectedRow();
                if (row == -1) {
                    createUpdateCustomerErrorWindow();
                } else {
                    buildUpdateCustomerFrame();    
                }
            }
        });
        
        return updateCustomerButton;
    }
    
    //This method creates the error window for updating w/o selecting.
    private void createUpdateCustomerErrorWindow() {
        //This creates the error window
        JFrame updateErrorFrame = new JFrame();
        updateErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateErrorFrame.setResizable(false);
        updateErrorFrame.setTitle("Error");
        updateErrorFrame.setBounds(100, 100, 350, 200);
        updateErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Select a customer to update.");
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

    //This method creates and controls the add customer window.
    public void buildAddCustomerFrame() {
        //This initializes the frame.
        JFrame addCustomerFrame = new JFrame();
        addCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCustomerFrame.setResizable(false);
        addCustomerFrame.setTitle("Add New Customer");
        addCustomerFrame.setBounds(100, 100, 475, 500);
        addCustomerFrame.getContentPane().setLayout(null);

        //Creates first name label.
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name");
        firstNameLabel.setBounds(10, 11, 203, 32);
        addCustomerFrame.getContentPane().add(firstNameLabel);
        
        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setBounds(10, 48, 203, 32);
        addCustomerFrame.getContentPane().add(firstNameText);

        //Creates last name label.
        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name");
        lastNameLabel.setBounds(233, 11, 203, 32);
        addCustomerFrame.getContentPane().add(lastNameLabel);
        
        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setBounds(233, 48, 203, 32);
        addCustomerFrame.getContentPane().add(lastNameText);
        
        //Creates phone number name label.
        JLabel phoneNumberLabel = new JLabel();
        phoneNumberLabel.setText("Phone Number");
        phoneNumberLabel.setBounds(10, 90, 203, 32);
        addCustomerFrame.getContentPane().add(phoneNumberLabel);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setBounds(10, 127, 203, 32);
        addCustomerFrame.getContentPane().add(phoneNumberText);

        //Creates email label.
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email");
        emailLabel.setBounds(233, 90, 203, 32);
        addCustomerFrame.getContentPane().add(emailLabel);
        
        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setBounds(233, 127, 203, 32);
        addCustomerFrame.getContentPane().add(emailText);
        
        //Creates street address label.
        JLabel streetAddressLabel = new JLabel();
        streetAddressLabel.setText("Street Address");
        streetAddressLabel.setBounds(10, 169, 203, 32);
        addCustomerFrame.getContentPane().add(streetAddressLabel);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setBounds(10, 206, 426, 32);
        addCustomerFrame.getContentPane().add(streetAddressText);
        
        //Creates city label.
        JLabel cityLabel = new JLabel();
        cityLabel.setText("City");
        cityLabel.setBounds(10, 248, 203, 32);
        addCustomerFrame.getContentPane().add(cityLabel);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setBounds(10, 285, 203, 32);
        addCustomerFrame.getContentPane().add(cityText);
        
        //Creates state label.
        JLabel stateLabel = new JLabel();
        stateLabel.setText("State");
        stateLabel.setBounds(233, 248, 203, 32);
        addCustomerFrame.getContentPane().add(stateLabel);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setBounds(233, 285, 203, 32);
        addCustomerFrame.getContentPane().add(stateText);
        
        //Creates zipcode label.
        JLabel zipcodeLabel = new JLabel();
        zipcodeLabel.setText("Zipcode");
        zipcodeLabel.setBounds(10, 327, 203, 32);
        addCustomerFrame.getContentPane().add(zipcodeLabel);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setBounds(10, 364, 203, 32);
        addCustomerFrame.getContentPane().add(zipcodeText);

        //This initializes the cancel button.
        JButton addCustomerCancelButton = new JButton();
        addCustomerCancelButton.setText("Cancel");
        addCustomerCancelButton.setBounds(10, 406, 203, 32);
        addCustomerFrame.getContentPane().add(addCustomerCancelButton);

        //Pressing this button will close the add customer window.
        addCustomerCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                addCustomerFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton addCustomerSubmitButton = new JButton();
        addCustomerSubmitButton.setText("Add New Customer");
        addCustomerSubmitButton.setBounds(233, 406, 203, 32);
        addCustomerFrame.getContentPane().add(addCustomerSubmitButton);

        //Pressing this button will attempt to add the customer to the database.
        addCustomerSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //add to the database. If they are present, add the customer and 
                //close the window. Otherwise display an error message.
                if (customerHasRequiredFields()) {
                    if (phoneNumberParsesCorrectly()) {
                        addNewCustomer();
                        addCustomerFrame.dispose();    
                    } else {
                        createPhoneNumberErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        addCustomerFrame.setVisible(true);
    }

    //This method validates that all required fields are present.
    private boolean customerHasRequiredFields() {
        //If the value is the default value or is empty, return false.
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
        //Otherwise return true. (All fields are present)
        return true;
    }

    //This method adds the new customer to the database.
    private void addNewCustomer() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = parsePhoneNumber(phoneNumberText.getText());
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();

        connection.connect();
        connection.addNewCustomer(firstName, lastName, phoneNumber,
                streetAddress, city, state, zipcode, email);
        connection.disconnect();
    }

    //This method creates the update customer window.
    public void buildUpdateCustomerFrame() {
        // Gets selected row
        int row = custTable.getSelectedRow();
        int selectedCusID = (int) custTable.getValueAt(row, 0);

        connection.connect();
        String[] fieldValues = connection.fillCustomerUpdate(selectedCusID);
        connection.disconnect();

        //This initializes the update customer window.
        JFrame updateCustomerFrame = new JFrame();
        updateCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateCustomerFrame.setResizable(false);
        updateCustomerFrame.setTitle("Update Customer");
        updateCustomerFrame.setBounds(100, 100, 475, 500);
        updateCustomerFrame.getContentPane().setLayout(null);

        //Creates first name label.
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name");
        firstNameLabel.setBounds(10, 11, 203, 32);
        updateCustomerFrame.getContentPane().add(firstNameLabel);
        
        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText(fieldValues[0]);
        firstNameText.setBounds(10, 48, 203, 32);
        updateCustomerFrame.getContentPane().add(firstNameText);

        //Creates last name label.
        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name");
        lastNameLabel.setBounds(233, 11, 203, 32);
        updateCustomerFrame.getContentPane().add(lastNameLabel);
        
        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText(fieldValues[1]);
        lastNameText.setBounds(233, 48, 203, 32);
        updateCustomerFrame.getContentPane().add(lastNameText);
        
        //Creates phone number name label.
        JLabel phoneNumberLabel = new JLabel();
        phoneNumberLabel.setText("Phone Number");
        phoneNumberLabel.setBounds(10, 90, 203, 32);
        updateCustomerFrame.getContentPane().add(phoneNumberLabel);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText(fieldValues[2]);
        phoneNumberText.setBounds(10, 127, 203, 32);
        updateCustomerFrame.getContentPane().add(phoneNumberText);

        //Creates email label.
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email");
        emailLabel.setBounds(233, 90, 203, 32);
        updateCustomerFrame.getContentPane().add(emailLabel);
        
        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText(fieldValues[7]);
        emailText.setBounds(233, 127, 203, 32);
        updateCustomerFrame.getContentPane().add(emailText);
        
        //Creates street address label.
        JLabel streetAddressLabel = new JLabel();
        streetAddressLabel.setText("Street Address");
        streetAddressLabel.setBounds(10, 169, 203, 32);
        updateCustomerFrame.getContentPane().add(streetAddressLabel);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText(fieldValues[3]);
        streetAddressText.setBounds(10, 206, 426, 32);
        updateCustomerFrame.getContentPane().add(streetAddressText);
        
        //Creates city label.
        JLabel cityLabel = new JLabel();
        cityLabel.setText("City");
        cityLabel.setBounds(10, 248, 203, 32);
        updateCustomerFrame.getContentPane().add(cityLabel);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText(fieldValues[4]);
        cityText.setBounds(10, 285, 203, 32);
        updateCustomerFrame.getContentPane().add(cityText);
        
        //Creates state label.
        JLabel stateLabel = new JLabel();
        stateLabel.setText("State");
        stateLabel.setBounds(233, 248, 203, 32);
        updateCustomerFrame.getContentPane().add(stateLabel);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText(fieldValues[5]);
        stateText.setBounds(233, 285, 203, 32);
        updateCustomerFrame.getContentPane().add(stateText);
        
        //Creates zipcode label.
        JLabel zipcodeLabel = new JLabel();
        zipcodeLabel.setText("Zipcode");
        zipcodeLabel.setBounds(10, 327, 203, 32);
        updateCustomerFrame.getContentPane().add(zipcodeLabel);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText(fieldValues[6]);
        zipcodeText.setBounds(10, 364, 203, 32);
        updateCustomerFrame.getContentPane().add(zipcodeText);

        //This initializes the cancel button.
        JButton updateCustomerCancelButton = new JButton();
        updateCustomerCancelButton.setText("Cancel");
        updateCustomerCancelButton.setBounds(10, 406, 203, 32);
        updateCustomerFrame.getContentPane().add(updateCustomerCancelButton);

        //Clicking this button will close the update customer window.
        updateCustomerCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateCustomerFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton updateCustomerSubmitButton = new JButton();
        updateCustomerSubmitButton.setText("Update Customer");
        updateCustomerSubmitButton.setBounds(233, 406, 203, 32);
        updateCustomerFrame.getContentPane().add(updateCustomerSubmitButton);

        //Clicking this button will update the customer in the database.
        updateCustomerSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //If all the required fields are present, update the customer in
                //the database and close the update customer window. Otherwise
                //display an error.
                if (customerHasRequiredFields()) {
                    if (phoneNumberParsesCorrectly()) {
                        updateCustomer(selectedCusID);
                        updateCustomerFrame.dispose();    
                    } else {
                        createPhoneNumberErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        updateCustomerFrame.setVisible(true);
    }

    //This method updates the customer in the database.
    private void updateCustomer(int selectedCusID) {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = parsePhoneNumber(phoneNumberText.getText());
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();

        connection.connect();
        connection.updateCustomer(firstName, lastName, phoneNumber, email,
                streetAddress, city, state, zipcode, selectedCusID);
        connection.disconnect();
    }
    
    //This method parses the phone number.
    private String parsePhoneNumber(String inputNumber) {
        String outputNumber = "";
        for (int i = 0; i < inputNumber.length(); i++) {
            char c = inputNumber.charAt(i);
            if (Character.isDigit(c)) {
                outputNumber += c;
            }
        }
        return outputNumber;
    }
    
    //This method ensures that the phone number is correctly parsed.
    private boolean phoneNumberParsesCorrectly() {
        String phoneNumber = parsePhoneNumber(phoneNumberText.getText());
        if (phoneNumber.length() != 10) {
            return false;
        }
        return true;
    }
    
    //This method creates an error window for an invalid phone number.
    private void createPhoneNumberErrorWindow() {
        //This creates the error window
        JFrame phoneNumberErrorFrame = new JFrame();
        phoneNumberErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        phoneNumberErrorFrame.setResizable(false);
        phoneNumberErrorFrame.setTitle("Error");
        phoneNumberErrorFrame.setBounds(100, 100, 350, 200);
        phoneNumberErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("The phone number must be 10 digits.");
        errorMessage.setBounds(60, 50, 250, 32);
        phoneNumberErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        phoneNumberErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                phoneNumberErrorFrame.dispose();
            }
        });

        phoneNumberErrorFrame.setVisible(true);
    }

    //This method creates a new window to display the required fields error 
    //message.
    private void createRequiredFieldsErrorWindow() {
        //This initializes the required fields error window.
        JFrame requiredFieldsErrorFrame = new JFrame();
        requiredFieldsErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requiredFieldsErrorFrame.setResizable(false);
        requiredFieldsErrorFrame.setTitle("Error");
        requiredFieldsErrorFrame.setBounds(100, 100, 300, 200);
        requiredFieldsErrorFrame.getContentPane().setLayout(null);

        //This label displays the text of the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);

        //This initializes the ok button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(105, 100, 90, 28);
        requiredFieldsErrorFrame.getContentPane().add(closeErrorMessageButton);

        //Pressing this button will close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                requiredFieldsErrorFrame.dispose();
            }
        });

        requiredFieldsErrorFrame.setVisible(true);
    }
}