import net.proteanit.sql.DbUtils;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

public class ManageTab {
    
    //These are the text fields for the add/update inventory item windows.
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField phoneNumberText;
    private JTextField streetAddressText;
    private JTextField cityText;
    private JTextField stateText;
    private JTextField zipcodeText;
    private JTextField emailText;
    private JTextField hourlyPayText;
    private JTextField usernameText;
    private JTextField passwordText;
    private JComboBox isManagerSelection;
    private JTable employeeTable;
    private SQLConnections connection;

    public ManageTab() {
        connection = new SQLConnections();
    }

    //This method creates the employee table.
    public JTable createEmployeeTable() {
        employeeTable = new JTable();
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setModel(DbUtils.resultSetToTableModel(connection.populateEmployeeTable()));
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(5);

        return employeeTable;
    }

    //This method creates the refresh list button.
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
        //TODO: actually refresh the table
    }

    //This method creates the add employee button.
    public JButton createAddEmployeeButton() {
        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setBounds(1133, 11, 167, 28);

        addEmployeeButton.addMouseListener(new MouseAdapter() {
            //When the button is pressed, the add employee window will open.
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddEmployeeFrame();
            }
        });
        return addEmployeeButton;
    }

    //This method creates the delete employee button.
    public JButton createDeleteEmployeeButton() {
        JButton deleteEmployeeButton = new JButton("Delete Employee");
        deleteEmployeeButton.setBounds(1133, 50, 167, 28);
        return deleteEmployeeButton;
        //TODO: make this work
    }

    //This method creates the update employee button.
    public JButton createUpdateEmployeeButton() {
        JButton updateEmployeeButton = new JButton("Update Employee");
        updateEmployeeButton.setBounds(1133, 89, 167, 28);

        updateEmployeeButton.addMouseListener(new MouseAdapter() {
            //When this button is pressed the udpate employee window
            //will open.
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO: create update window
                //buildUpdateEmployeeFrame();
            }
        });
        return updateEmployeeButton;
    }

    //This method creates the export payroll button.
    public JButton createExportPayrollButton() {
        JButton exportPayrollButton = new JButton("Export Payroll");
        exportPayrollButton.setBounds(956, 50, 167, 28);
        return exportPayrollButton;
        //TODO: make this work
    }   
    
    //This method creates and controls the add employee window.
    public void buildAddEmployeeFrame() {
        //This initializes the frame.
        JFrame addEmployeeFrame = new JFrame();
        addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addEmployeeFrame.setResizable(false);
        addEmployeeFrame.setTitle("Add Employee");
        addEmployeeFrame.setBounds(100, 100, 475, 385);
        addEmployeeFrame.getContentPane().setLayout(null);

        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText("First Name");
        firstNameText.setBounds(10, 11, 203, 32);
        addEmployeeFrame.getContentPane().add(firstNameText);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText("Last Name");
        lastNameText.setBounds(233, 11, 203, 32);
        addEmployeeFrame.getContentPane().add(lastNameText);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText("Phone Number");
        phoneNumberText.setBounds(10, 53, 203, 32);
        addEmployeeFrame.getContentPane().add(phoneNumberText);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText("Email");
        emailText.setBounds(233, 53, 203, 32);
        addEmployeeFrame.getContentPane().add(emailText);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText("Street Address");
        streetAddressText.setBounds(10, 95, 426, 32);
        addEmployeeFrame.getContentPane().add(streetAddressText);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText("City");
        cityText.setBounds(10, 137, 203, 32);
        addEmployeeFrame.getContentPane().add(cityText);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText("State");
        stateText.setBounds(233, 137, 203, 32);
        addEmployeeFrame.getContentPane().add(stateText);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText("Zipcode");
        zipcodeText.setBounds(10, 179, 203, 32);
        addEmployeeFrame.getContentPane().add(zipcodeText);
        
        //This initializes the username text field.
        usernameText = new JTextField();
        usernameText.setText("Username");
        usernameText.setBounds(10, 221, 203, 32);
        addEmployeeFrame.getContentPane().add(usernameText);
        
        //This initializes the password text field.
        passwordText = new JTextField();
        passwordText.setText("Password");
        passwordText.setBounds(233, 221, 203, 32);
        addEmployeeFrame.getContentPane().add(passwordText);
        
        //This initializes the hourly pay text field.
        hourlyPayText = new JTextField();
        hourlyPayText.setText("Hourly Pay");
        hourlyPayText.setBounds(10, 263, 203, 32);
        addEmployeeFrame.getContentPane().add(hourlyPayText);
        
        //This initializes the is manager combo box.
        String[] isManagerChoices = {"", "Is Manager", "Not Manager"};
        isManagerSelection = new JComboBox(isManagerChoices);
        isManagerSelection.setBounds(233, 263, 203, 32);
        addEmployeeFrame.getContentPane().add(isManagerSelection);
        
        //This initializes the cancel button.
        JButton addEmployeeCancelButton = new JButton("Cancel");
        addEmployeeCancelButton.setBounds(10, 305, 203, 32);
        addEmployeeFrame.getContentPane().add(addEmployeeCancelButton);

        //Pressing this button will close the add customer window.
        addEmployeeCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                addEmployeeFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton addEmployeeSubmitButton = new JButton("Add Employee");
        addEmployeeSubmitButton.setBounds(233, 305, 203, 32);
        addEmployeeFrame.getContentPane().add(addEmployeeSubmitButton);

        //TODO: ensure hourly pay is a number, show error window
        //Pressing this button will attempt to add the customer to the database.
        addEmployeeSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //add to the database. If they are present, add the employee and 
                //close the window. Otherwise display an error message.
                if (employeeHasRequiredFields()) {
                    addNewEmployee();
                    addEmployeeFrame.dispose();
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        addEmployeeFrame.setVisible(true);
    }
    
    //This method validates that all required fields are present.
    private boolean employeeHasRequiredFields() {
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
        if (usernameText.getText().equals("Username") ||
                usernameText.getText().equals("")) {
            return false;
        }
        if (passwordText.getText().equals("Password") ||
                passwordText.getText().equals("")) {
            return false;
        }
        if (hourlyPayText.getText().equals("Hourly Pay") ||
                hourlyPayText.getText().equals("")) {
            return false;
        }
        if (isManagerSelection.getSelectedItem().equals("")) {
            return false;
        }
        
        //Otherwise return true. (All fields are present)
        return true;
    }
    
    //This method adds the new employee to the database.
    private void addNewEmployee() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = phoneNumberText.getText();
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();
        String username = usernameText.getText();
        String password = usernameText.getText();
        Double hourlyRate = Double.parseDouble(hourlyPayText.getText());
        int isManager = 0;
        if (isManagerSelection.getSelectedItem().toString().equals("Is Manager")) {
            isManager = 1;
        }
        
        //TODO: add to database
    }
    
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
