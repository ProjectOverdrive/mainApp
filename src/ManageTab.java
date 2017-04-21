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
        this.connection = SQLConnections.getConnectionInstance();
    }

    //This method creates the employee table.
    public JTable createEmployeeTable() {
        employeeTable = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        connection.connect();
        employeeTable.setModel(DbUtils.resultSetToTableModel(connection.populateEmployeeTable()));
        connection.disconnect();

        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(5);

        return employeeTable;
    }

    //This method creates the add employee button.
    public JButton createAddEmployeeButton() {
        JButton addEmployeeButton = new JButton();
        addEmployeeButton.setText("Add Employee");
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
        JButton deleteEmployeeButton = new JButton();
        deleteEmployeeButton.setText("Delete Employee");
        deleteEmployeeButton.setBounds(1133, 50, 167, 28);
        
        //The customer should be deleted when this is pressed.
        deleteEmployeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = employeeTable.getSelectedRow();
                if (row == -1) {
                    createDeleteEmployeeErrorWindow();
                } else {
                    int selectedEmployeeID = (int) employeeTable.getValueAt(row, 0);
                    connection.connect();
                    connection.deleteEmployee(selectedEmployeeID);
                    connection.disconnect();
                    JOptionPane.showMessageDialog(null, "Employee Removed");
                }
            }
        });
        return deleteEmployeeButton;
    }
    
    //This method creates the error window for deleting w/o selecting.
    private void createDeleteEmployeeErrorWindow() {
        //This creates the error window
        JFrame deleteErrorFrame = new JFrame();
        deleteErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteErrorFrame.setResizable(false);
        deleteErrorFrame.setTitle("Error");
        deleteErrorFrame.setBounds(100, 100, 350, 200);
        deleteErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Select an employee to delete.");
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

    //This method creates the update employee button.
    public JButton createUpdateEmployeeButton() {
        JButton updateEmployeeButton = new JButton();
        updateEmployeeButton.setText("Update Employee");
        updateEmployeeButton.setBounds(1133, 89, 167, 28);

        updateEmployeeButton.addMouseListener(new MouseAdapter() {
            //When this button is pressed the udpate employee window
            //will open.
            @Override
            public void mousePressed(MouseEvent e) {
                int row = employeeTable.getSelectedRow();
                if (row == -1) {
                    createUpdateEmployeeErrorWindow();
                } else {
                    buildUpdateEmployeeFrame();
                }
            }
        });
        return updateEmployeeButton;
    }
    
    //This method creates the error window for updating w/o selecting.
    private void createUpdateEmployeeErrorWindow() {
        //This creates the error window
        JFrame updateErrorFrame = new JFrame();
        updateErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateErrorFrame.setResizable(false);
        updateErrorFrame.setTitle("Error");
        updateErrorFrame.setBounds(100, 100, 350, 200);
        updateErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Select an employee to update.");
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
    
    //This method creates and controls the add employee window.
    public void buildAddEmployeeFrame() {
        //This initializes the frame.
        JFrame addEmployeeFrame = new JFrame();
        addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addEmployeeFrame.setResizable(false);
        addEmployeeFrame.setTitle("Add Employee");
        addEmployeeFrame.setBounds(100, 100, 475, 655);
        addEmployeeFrame.getContentPane().setLayout(null);

        //Creates first name label.
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name");
        firstNameLabel.setBounds(10, 11, 203, 32);
        addEmployeeFrame.getContentPane().add(firstNameLabel);
        
        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setBounds(10, 48, 203, 32);
        addEmployeeFrame.getContentPane().add(firstNameText);
        
        //Creates last name label.
        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name");
        lastNameLabel.setBounds(233, 11, 203, 32);
        addEmployeeFrame.getContentPane().add(lastNameLabel);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setBounds(233, 48, 203, 32);
        addEmployeeFrame.getContentPane().add(lastNameText);
        
        //Creates phone number label.
        JLabel phoneNumberLabel = new JLabel();
        phoneNumberLabel.setText("Phone Number");
        phoneNumberLabel.setBounds(10, 90, 203, 32);
        addEmployeeFrame.getContentPane().add(phoneNumberLabel);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setBounds(10, 127, 203, 32);
        addEmployeeFrame.getContentPane().add(phoneNumberText);
        
        //Creates email label.
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email");
        emailLabel.setBounds(233, 90, 203, 32);
        addEmployeeFrame.getContentPane().add(emailLabel);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setBounds(233, 127, 203, 32);
        addEmployeeFrame.getContentPane().add(emailText);
        
        //Creates street address label.
        JLabel streetAddressLabel = new JLabel();
        streetAddressLabel.setText("Street Address");
        streetAddressLabel.setBounds(10, 169, 203, 32);
        addEmployeeFrame.getContentPane().add(streetAddressLabel);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setBounds(10, 206, 426, 32);
        addEmployeeFrame.getContentPane().add(streetAddressText);
        
        //Creates city label.
        JLabel cityLabel = new JLabel();
        cityLabel.setText("City");
        cityLabel.setBounds(10, 248, 203, 32);
        addEmployeeFrame.getContentPane().add(cityLabel);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setBounds(10, 285, 203, 32);
        addEmployeeFrame.getContentPane().add(cityText);
        
        //Creates state label.
        JLabel stateLabel = new JLabel();
        stateLabel.setText("State");
        stateLabel.setBounds(233, 248, 203, 32);
        addEmployeeFrame.getContentPane().add(stateLabel);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setBounds(233, 285, 203, 32);
        addEmployeeFrame.getContentPane().add(stateText);
        
        //Creates zipcode label.
        JLabel zipcodeLabel = new JLabel();
        zipcodeLabel.setText("Zipcode");
        zipcodeLabel.setBounds(10, 327, 203, 32);
        addEmployeeFrame.getContentPane().add(zipcodeLabel);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setBounds(10, 364, 203, 32);
        addEmployeeFrame.getContentPane().add(zipcodeText);
        
        //Creates username label.
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        usernameLabel.setBounds(10, 406, 203, 32);
        addEmployeeFrame.getContentPane().add(usernameLabel);
        
        //This initializes the username text field.
        usernameText = new JTextField();
        usernameText.setBounds(10, 443, 203, 32);
        addEmployeeFrame.getContentPane().add(usernameText);
        
        //Creates password label.
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        passwordLabel.setBounds(233, 406, 203, 32);
        addEmployeeFrame.getContentPane().add(passwordLabel);
        
        //This initializes the password text field.
        passwordText = new JTextField();
        passwordText.setBounds(233, 443, 203, 32);
        addEmployeeFrame.getContentPane().add(passwordText);
        
        //Creates hourly pay label.
        JLabel hourlyPayLabel = new JLabel();
        hourlyPayLabel.setText("Hourly Pay");
        hourlyPayLabel.setBounds(10, 485, 203, 32);
        addEmployeeFrame.getContentPane().add(hourlyPayLabel);
        
        //This initializes the hourly pay text field.
        hourlyPayText = new JTextField();
        hourlyPayText.setBounds(10, 522, 203, 32);
        addEmployeeFrame.getContentPane().add(hourlyPayText);
        
        //Creates manager label.
        JLabel managerLabel = new JLabel();
        managerLabel.setText("Manager");
        managerLabel.setBounds(233, 485, 203, 32);
        addEmployeeFrame.getContentPane().add(managerLabel);
        
        //This initializes the is manager combo box.
        String[] isManagerChoices = {"", "Is Manager", "Not Manager"};
        isManagerSelection = new JComboBox(isManagerChoices);
        isManagerSelection.setBounds(233, 522, 203, 32);
        addEmployeeFrame.getContentPane().add(isManagerSelection);
        
        //This initializes the cancel button.
        JButton addEmployeeCancelButton = new JButton();
        addEmployeeCancelButton.setText("Cancel");
        addEmployeeCancelButton.setBounds(10, 572, 203, 32);
        addEmployeeFrame.getContentPane().add(addEmployeeCancelButton);

        //Pressing this button will close the add customer window.
        addEmployeeCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                addEmployeeFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton addEmployeeSubmitButton = new JButton();
        addEmployeeSubmitButton.setText("Add Employee");
        addEmployeeSubmitButton.setBounds(233, 572, 203, 32);
        addEmployeeFrame.getContentPane().add(addEmployeeSubmitButton);

        //Pressing this button will attempt to add the employee to the database.
        addEmployeeSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //add to the database. If they are present, add the employee and 
                //close the window. Otherwise display an error message.
                if (addEmployeeHasRequiredFields()) {
                    //Check that hourly pay is a numeric value.
                    if (numberParsesCorrectly(hourlyPayText.getText())) {
                        if (phoneNumberParsesCorrectly()) {
                            addNewEmployee();
                            addEmployeeFrame.dispose();      
                        } else {
                            createPhoneNumberErrorWindow();
                        }
                    } else {
                        createHourlyPayErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        addEmployeeFrame.setVisible(true);
    }
    
    //This method validates that all required fields are present.
    private boolean addEmployeeHasRequiredFields() {
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
        String phoneNumber = parsePhoneNumber(phoneNumberText.getText());
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();
        String email = emailText.getText();
        Double hourlyRate = Double.parseDouble(hourlyPayText.getText());
        String username = usernameText.getText();
        String password = passwordText.getText();

        int isManager = 0;
        if (isManagerSelection.getSelectedItem().toString().equals("Is Manager")) {
            isManager = 1;
        }

        connection.connect();
        connection.addNewEmployee(firstName, lastName, phoneNumber, streetAddress, city,
                state, zipcode, email, hourlyRate, username, password, isManager);
        connection.disconnect();
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
     
    //This method controls the update employee window
    public void buildUpdateEmployeeFrame() {
        int row = employeeTable.getSelectedRow();
        int selectedEmployeeID = (int) employeeTable.getValueAt(row, 0);

        connection.connect();
        String[] fieldValues = connection.fillEmployeeUpdate(selectedEmployeeID);
        connection.disconnect();

        //This initializes the frame.
        JFrame updateEmployeeFrame = new JFrame();
        updateEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateEmployeeFrame.setResizable(false);
        updateEmployeeFrame.setTitle("Update Employee");
        updateEmployeeFrame.setBounds(100, 100, 475, 655);
        updateEmployeeFrame.getContentPane().setLayout(null);

        //Creates first name label.
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name");
        firstNameLabel.setBounds(10, 11, 203, 32);
        updateEmployeeFrame.getContentPane().add(firstNameLabel);
        
        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText(fieldValues[0]);
        firstNameText.setBounds(10, 48, 203, 32);
        updateEmployeeFrame.getContentPane().add(firstNameText);
        
        //Creates last name label.
        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name");
        lastNameLabel.setBounds(233, 11, 203, 32);
        updateEmployeeFrame.getContentPane().add(lastNameLabel);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText(fieldValues[1]);
        lastNameText.setBounds(233, 48, 203, 32);
        updateEmployeeFrame.getContentPane().add(lastNameText);
        
        //Creates phone number label.
        JLabel phoneNumberLabel = new JLabel();
        phoneNumberLabel.setText("Phone Number");
        phoneNumberLabel.setBounds(10, 90, 203, 32);
        updateEmployeeFrame.getContentPane().add(phoneNumberLabel);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText(fieldValues[2]);
        phoneNumberText.setBounds(10, 127, 203, 32);
        updateEmployeeFrame.getContentPane().add(phoneNumberText);
        
        //Creates email label.
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email");
        emailLabel.setBounds(233, 90, 203, 32);
        updateEmployeeFrame.getContentPane().add(emailLabel);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText(fieldValues[7]);
        emailText.setBounds(233, 127, 203, 32);
        updateEmployeeFrame.getContentPane().add(emailText);
        
        //Creates street address label.
        JLabel streetAddressLabel = new JLabel();
        streetAddressLabel.setText("Street Address");
        streetAddressLabel.setBounds(10, 169, 203, 32);
        updateEmployeeFrame.getContentPane().add(streetAddressLabel);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText(fieldValues[3]);
        streetAddressText.setBounds(10, 206, 426, 32);
        updateEmployeeFrame.getContentPane().add(streetAddressText);
        
        //Creates city label.
        JLabel cityLabel = new JLabel();
        cityLabel.setText("City");
        cityLabel.setBounds(10, 248, 203, 32);
        updateEmployeeFrame.getContentPane().add(cityLabel);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText(fieldValues[4]);
        cityText.setBounds(10, 285, 203, 32);
        updateEmployeeFrame.getContentPane().add(cityText);
        
        //Creates state label.
        JLabel stateLabel = new JLabel();
        stateLabel.setText("State");
        stateLabel.setBounds(233, 248, 203, 32);
        updateEmployeeFrame.getContentPane().add(stateLabel);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText(fieldValues[5]);
        stateText.setBounds(233, 285, 203, 32);
        updateEmployeeFrame.getContentPane().add(stateText);
        
        //Creates zipcode label.
        JLabel zipcodeLabel = new JLabel();
        zipcodeLabel.setText("Zipcode");
        zipcodeLabel.setBounds(10, 327, 203, 32);
        updateEmployeeFrame.getContentPane().add(zipcodeLabel);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText(fieldValues[6]);
        zipcodeText.setBounds(10, 364, 203, 32);
        updateEmployeeFrame.getContentPane().add(zipcodeText);
        
        //Creates username label.
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        usernameLabel.setBounds(10, 406, 203, 32);
        updateEmployeeFrame.getContentPane().add(usernameLabel);
        
        //This initializes the username text field.
        usernameText = new JTextField();
        usernameText.setText(fieldValues[9]);
        usernameText.setBounds(10, 443, 203, 32);
        updateEmployeeFrame.getContentPane().add(usernameText);
        
        //Managers are not allowed to update passwords
        
        //Creates hourly pay label.
        JLabel hourlyPayLabel = new JLabel();
        hourlyPayLabel.setText("Hourly Pay");
        hourlyPayLabel.setBounds(10, 485, 203, 32);
        updateEmployeeFrame.getContentPane().add(hourlyPayLabel);
        
        //This initializes the hourly pay text field.
        hourlyPayText = new JTextField();
        hourlyPayText.setText(fieldValues[8]);
        hourlyPayText.setBounds(10, 522, 203, 32);
        updateEmployeeFrame.getContentPane().add(hourlyPayText);
        
        //Creates manager label.
        JLabel managerLabel = new JLabel();
        managerLabel.setText("Manager");
        managerLabel.setBounds(233, 485, 203, 32);
        updateEmployeeFrame.getContentPane().add(managerLabel);
        
        //This initializes the is manager combo box.
        String[] isManagerChoices = {"", "Is Manager", "Not Manager"};
        isManagerSelection = new JComboBox(isManagerChoices);
        //Auto select manager if employee is a manager
        if(Integer.parseInt(fieldValues[11]) == 1) {
            isManagerSelection.setSelectedIndex(1);
        } else {
            isManagerSelection.setSelectedIndex(2);
        }
        isManagerSelection.setBounds(233, 522, 203, 32);
        updateEmployeeFrame.getContentPane().add(isManagerSelection);
        
        //This initializes the cancel button.
        JButton updateEmployeeCancelButton = new JButton();
        updateEmployeeCancelButton.setText("Cancel");
        updateEmployeeCancelButton.setBounds(10, 572, 203, 32);
        updateEmployeeFrame.getContentPane().add(updateEmployeeCancelButton);

        //Pressing this button will close the update customer window.
        updateEmployeeCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateEmployeeFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton updateEmployeeSubmitButton = new JButton();
        updateEmployeeSubmitButton.setText("Update Employee");
        updateEmployeeSubmitButton.setBounds(233, 572, 203, 32);
        updateEmployeeFrame.getContentPane().add(updateEmployeeSubmitButton);

        //Pressing this button will attempt to update the employee in the database.
        updateEmployeeSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //update the database. If they are present, update the employee and 
                //close the window. Otherwise display an error message.
                if (updateEmployeeHasRequiredFields()) {
                    //Check that hourly pay is a numeric value.
                    if (numberParsesCorrectly(hourlyPayText.getText())) {
                        if (phoneNumberParsesCorrectly()) {
                            updateEmployee();
                            updateEmployeeFrame.dispose();     
                        } else {
                            createPhoneNumberErrorWindow();
                        }
                    } else {
                        createHourlyPayErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        updateEmployeeFrame.setVisible(true);
    }
    
    //This method validates that all required fields are present.
    private boolean updateEmployeeHasRequiredFields() {
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
    
    //This method updates the employee to the database.
    private void updateEmployee() {
        int row = employeeTable.getSelectedRow();
        int selectedEmployeeID = (int) employeeTable.getValueAt(row, 0);

        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = parsePhoneNumber(phoneNumberText.getText());
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();
        String username = usernameText.getText();
        Double hourlyRate = Double.parseDouble(hourlyPayText.getText());
        int isManager = 0;
        if (isManagerSelection.getSelectedItem().toString().equals("Is Manager")) {
            isManager = 1;
        }

        connection.connect();
        connection.updateEmployee(firstName, lastName, phoneNumber, email, streetAddress,
                city, state, zipcode, username, hourlyRate, isManager, selectedEmployeeID);
        connection.disconnect();
    }
    
    //This number ensures that fields that are supposed to be numeric are so.
    private boolean numberParsesCorrectly(String numericalText) {
        int count = 0;
        //Loop through the string and increment count when a numeric character
        //is found.
        for (char c : numericalText.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        //If a numeric character is not found, count will be zero and we return
        //false becuase the number is not numeric.
        if (count == 0) {
            return false;
        }
        //Otherwise return true.
        return true;
    }
    
    //This method creates the error window for hourly pay not being numeric.
    private void createHourlyPayErrorWindow() {
        //This creates the error window
        JFrame hourlyPayErrorFrame = new JFrame();
        hourlyPayErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hourlyPayErrorFrame.setResizable(false);
        hourlyPayErrorFrame.setTitle("Error");
        hourlyPayErrorFrame.setBounds(100, 100, 350, 200);
        hourlyPayErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Hourly Pay must be a numerical value.");
        errorMessage.setBounds(60, 50, 250, 32);
        hourlyPayErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        hourlyPayErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                hourlyPayErrorFrame.dispose();
            }
        });

        hourlyPayErrorFrame.setVisible(true);
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
}