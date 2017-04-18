//TODO: Input validation on address fields (zipcode 5 digits, etc)

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerTab {

    SQLConnections connection;

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

    public CustomerTab() {
        connection = new SQLConnections();
    }

    //This method creates the customer table.
    public JTable createCustomerTable() {
        custTable = new JTable();
        custTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        custTable.setModel(DbUtils.resultSetToTableModel(connection.populateCustomerTable()));
        custTable.getTableHeader().setReorderingAllowed(false);

        return custTable;
    }

    //This method creates the refresh list button.

    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        refreshButton.addMouseListener(new MouseAdapter() {
            //The add customer window should open when button is pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                createCustomerTable();
            }
        });
        return refreshButton;
    }

    //This method creates the add customer button.
    public JButton createAddCustomerButton() {
        JButton addCustomerButton = new JButton("Add New Customer");
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
        JButton deleteCustomerButton = new JButton("Delete Customer");
        deleteCustomerButton.setBounds(1133, 50, 167, 28);

        deleteCustomerButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int row = custTable.getSelectedRow();
                int selectedCusID = (int) custTable.getValueAt(row, 0);
                connection.deleteCustomer(selectedCusID);
                JOptionPane.showMessageDialog(null, "Customer Removed");
            }
        });
        return deleteCustomerButton;
    }

    //This method creates the update customer button.
    public JButton createUpdateCustomerButton() {
        JButton updateCustomerButton = new JButton("Update Customer");
        updateCustomerButton.setBounds(1133, 89, 167, 28);

        updateCustomerButton.addMouseListener(new MouseAdapter() {
            //The update customer window should open when this button is pressed.
            //TODO: enable row selection
            @Override
            public void mousePressed(MouseEvent e) {
                buildUpdateCustomerFrame();
            }
        });
        return updateCustomerButton;
    }

    //This method creates and controls the add customer window.
    public void buildAddCustomerFrame() {
        //This initializes the frame.
        JFrame addCustomerFrame = new JFrame();
        addCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addCustomerFrame.setResizable(false);
        addCustomerFrame.setTitle("Add New Customer");
        addCustomerFrame.setBounds(100, 100, 475, 315);
        addCustomerFrame.getContentPane().setLayout(null);

        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText("First Name");
        firstNameText.setBounds(10, 11, 203, 32);
        addCustomerFrame.getContentPane().add(firstNameText);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText("Last Name");
        lastNameText.setBounds(233, 11, 203, 32);
        addCustomerFrame.getContentPane().add(lastNameText);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText("Phone Number");
        phoneNumberText.setBounds(10, 53, 203, 32);
        addCustomerFrame.getContentPane().add(phoneNumberText);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText("Email");
        emailText.setBounds(233, 53, 203, 32);
        addCustomerFrame.getContentPane().add(emailText);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText("Street Address");
        streetAddressText.setBounds(10, 95, 426, 32);
        addCustomerFrame.getContentPane().add(streetAddressText);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText("City");
        cityText.setBounds(10, 137, 203, 32);
        addCustomerFrame.getContentPane().add(cityText);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText("State");
        stateText.setBounds(233, 137, 203, 32);
        addCustomerFrame.getContentPane().add(stateText);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText("Zipcode");
        zipcodeText.setBounds(10, 179, 203, 32);
        addCustomerFrame.getContentPane().add(zipcodeText);

        //This initializes the cancel button.
        JButton addCustomerCancelButton = new JButton("Cancel");
        addCustomerCancelButton.setBounds(10, 221, 203, 32);
        addCustomerFrame.getContentPane().add(addCustomerCancelButton);

        //Pressing this button will close the add customer window.
        addCustomerCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                addCustomerFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton addCustomerSubmitButton = new JButton("Add New Customer");
        addCustomerSubmitButton.setBounds(233, 221, 203, 32);
        addCustomerFrame.getContentPane().add(addCustomerSubmitButton);

        //Pressing this button will attempt to add the customer to the database.
        addCustomerSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //add to the database. If they are present, add the customer and 
                //close the window. Otherwise display an error message.
                if (customerHasRequiredFields()) {
                    addNewCustomer();
                    addCustomerFrame.dispose();
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
        String phoneNumber = phoneNumberText.getText();
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();

        connection.addNewCustomer(firstName, lastName, phoneNumber,
                streetAddress, city, state, zipcode, email);

        //TODO: Make sure not adding duplicate customer
    }

    //This method creates the update customer window.
    public void buildUpdateCustomerFrame() {
        //TODO: prepopulate these fields with the customer from the database
        //This initializes the update customer window.
        JFrame updateCustomerFrame = new JFrame();
        updateCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateCustomerFrame.setResizable(false);
        updateCustomerFrame.setTitle("Update Customer");
        updateCustomerFrame.setBounds(100, 100, 475, 315);
        updateCustomerFrame.getContentPane().setLayout(null);

        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText("First Name");
        firstNameText.setBounds(10, 11, 203, 32);
        updateCustomerFrame.getContentPane().add(firstNameText);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText("Last Name");
        lastNameText.setBounds(233, 11, 203, 32);
        updateCustomerFrame.getContentPane().add(lastNameText);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText("Phone Number");
        phoneNumberText.setBounds(10, 53, 203, 32);
        updateCustomerFrame.getContentPane().add(phoneNumberText);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText("Email");
        emailText.setBounds(233, 53, 203, 32);
        updateCustomerFrame.getContentPane().add(emailText);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText("Street Address");
        streetAddressText.setBounds(10, 95, 426, 32);
        updateCustomerFrame.getContentPane().add(streetAddressText);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText("City");
        cityText.setBounds(10, 137, 203, 32);
        updateCustomerFrame.getContentPane().add(cityText);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText("State");
        stateText.setBounds(233, 137, 203, 32);
        updateCustomerFrame.getContentPane().add(stateText);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText("Zipcode");
        zipcodeText.setBounds(10, 179, 203, 32);
        updateCustomerFrame.getContentPane().add(zipcodeText);

        //This initializes the cancel button.
        JButton updateCustomerCancelButton = new JButton("Cancel");
        updateCustomerCancelButton.setBounds(10, 221, 203, 32);
        updateCustomerFrame.getContentPane().add(updateCustomerCancelButton);

        //Clicking this button will close the update customer window.
        updateCustomerCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateCustomerFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton updateCustomerSubmitButton = new JButton("Update Customer");
        updateCustomerSubmitButton.setBounds(233, 221, 203, 32);
        updateCustomerFrame.getContentPane().add(updateCustomerSubmitButton);

        //Clicking this button will update the customer in the database.
        updateCustomerSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //If all the required fields are present, update the customer in
                //the database and close the update customer window. Otherwise
                //display an error.
                if (customerHasRequiredFields()) {
                    updateCustomer();
                    updateCustomerFrame.dispose();
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        updateCustomerFrame.setVisible(true);
    }

    //This method updates the customer in the database.
    private void updateCustomer() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = phoneNumberText.getText();
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();

        //TODO: Update these fields on the database.
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
        JLabel errorMessage = new JLabel("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);

        //This initializes the ok button.
        JButton closeErrorMessageButton = new JButton("OK");
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