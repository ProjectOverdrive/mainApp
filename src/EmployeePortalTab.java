import net.proteanit.sql.DbUtils;

import java.awt.event.*;
import javax.swing.*;

public class EmployeePortalTab {
    
    //These fields are for the employee portal data.
    private JTable portalTable;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField phoneNumberText;
    private JTextField streetAddressText;
    private JTextField cityText;
    private JTextField stateText;
    private JTextField zipcodeText;
    private JTextField emailText;
    private JTextField usernameText;
    
    private JTextField currentPasswordText;
    private JTextField newPasswordText;
    private JTextField confirmPasswordText;
    private String activeUser;
    private SQLConnections connection;

    public EmployeePortalTab(String activeUser) {
        this.activeUser = activeUser;
        this.connection = SQLConnections.getConnectionInstance();
    }
    
    //This method creates the employee portal table.
    public JTable createEmployeePortalTable() {
        portalTable = new JTable();

        portalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        connection.connect();
        portalTable.setModel(DbUtils.resultSetToTableModel(connection.populateEmployeeInfo(activeUser)));
        connection.disconnect();

        portalTable.getTableHeader().setReorderingAllowed(false);
        portalTable.getColumnModel().getColumn(0).setPreferredWidth(5);

        return portalTable;
    }
        
    //This method creates the refresh button.
    public JButton createRefreshButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
        //TODO: (Colten) actually refresh the table
    }
    
    //This method creates the update info button.
    public JButton createUpdateInfoButton() {
        JButton updateInfoButton = new JButton();
        updateInfoButton.setText("Update Info");
        updateInfoButton.setBounds(1133, 11, 167, 28);

        updateInfoButton.addMouseListener(new MouseAdapter() {
            //When the button is pressed, the update info window will open.
            @Override
            public void mousePressed(MouseEvent e) {
                buildUpdateInfoWindow();
            }
        });
        return updateInfoButton;
    }

    //This method creates the change password button.
    public JButton createChangePasswordButton() {
        JButton changePasswordButton = new JButton();
        changePasswordButton.setText("Change Password");
        changePasswordButton.setBounds(1133, 50, 167, 28);

        changePasswordButton.addMouseListener(new MouseAdapter() {
            //When the button is pressed, the change password window will open.
            @Override
            public void mousePressed(MouseEvent e) {
                createUpdatePasswordWindow();
            }
        });
        return changePasswordButton;
    }
    
    //This method controls the update info window.
    private void buildUpdateInfoWindow() {
        connection.connect();
        String[] fieldValues = connection.fillPersonalInfoUpdate(activeUser);
        connection.disconnect();

        //This initializes the frame.
        JFrame updateInfoFrame = new JFrame();
        updateInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateInfoFrame.setResizable(false);
        updateInfoFrame.setTitle("Update Info");
        updateInfoFrame.setBounds(100, 100, 475, 500);
        updateInfoFrame.getContentPane().setLayout(null);
        
        //This is the first name label.
        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name");
        firstNameLabel.setBounds(10, 11, 203, 32);
        updateInfoFrame.getContentPane().add(firstNameLabel);
        
        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText(fieldValues[0]);
        firstNameText.setBounds(10, 48, 203, 32);
        updateInfoFrame.getContentPane().add(firstNameText);
        
        //This is the last name label.
        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name");
        lastNameLabel.setBounds(233, 11, 203, 32);
        updateInfoFrame.getContentPane().add(lastNameLabel);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText(fieldValues[1]);
        lastNameText.setBounds(233, 48, 203, 32);
        updateInfoFrame.getContentPane().add(lastNameText);
        
        //This is the phone number label.
        JLabel phoneNumberLabel = new JLabel();
        phoneNumberLabel.setText("Phone Number");
        phoneNumberLabel.setBounds(10, 90, 203, 32);
        updateInfoFrame.getContentPane().add(phoneNumberLabel);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText(fieldValues[2]);
        phoneNumberText.setBounds(10, 127, 203, 32);
        updateInfoFrame.getContentPane().add(phoneNumberText);
        
        //This is the email label.
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email");
        emailLabel.setBounds(233, 90, 203, 32);
        updateInfoFrame.getContentPane().add(emailLabel);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText(fieldValues[7]);
        emailText.setBounds(233, 127, 203, 32);
        updateInfoFrame.getContentPane().add(emailText);
        
        //This is the street address label.
        JLabel streetAddressLabel = new JLabel();
        streetAddressLabel.setText("Street Address");
        streetAddressLabel.setBounds(10, 169, 203, 32);
        updateInfoFrame.getContentPane().add(streetAddressLabel);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText(fieldValues[3]);
        streetAddressText.setBounds(10, 206, 426, 32);
        updateInfoFrame.getContentPane().add(streetAddressText);
        
        //This is the city label.
        JLabel cityLabel = new JLabel();
        cityLabel.setText("City");
        cityLabel.setBounds(10, 248, 203, 32);
        updateInfoFrame.getContentPane().add(cityLabel);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText(fieldValues[4]);
        cityText.setBounds(10, 285, 203, 32);
        updateInfoFrame.getContentPane().add(cityText);
        
        //This is the state label.
        JLabel stateLabel = new JLabel();
        stateLabel.setText("State");
        stateLabel.setBounds(233, 248, 203, 32);
        updateInfoFrame.getContentPane().add(stateLabel);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText(fieldValues[5]);
        stateText.setBounds(233, 285, 203, 32);
        updateInfoFrame.getContentPane().add(stateText);
        
        //This is the zipcode label.
        JLabel zipcodeLabel = new JLabel();
        zipcodeLabel.setText("Zipcode");
        zipcodeLabel.setBounds(10, 327, 203, 32);
        updateInfoFrame.getContentPane().add(zipcodeLabel);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText(fieldValues[6]);
        zipcodeText.setBounds(10, 364, 203, 32);
        updateInfoFrame.getContentPane().add(zipcodeText);
        
        //This is the username label.
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        usernameLabel.setBounds(233, 327, 203, 32);
        updateInfoFrame.getContentPane().add(usernameLabel);
        
        //This initializes the username text field.
        usernameText = new JTextField();
        usernameText.setText(fieldValues[8]);
        usernameText.setBounds(233, 364, 203, 32);
        updateInfoFrame.getContentPane().add(usernameText);
        
        //This initializes the cancel button.
        JButton updateInfoCancelButton = new JButton();
        updateInfoCancelButton.setText("Cancel");
        updateInfoCancelButton.setBounds(10, 406, 203, 32);
        updateInfoFrame.getContentPane().add(updateInfoCancelButton);

        //Pressing this button will close the update info window.
        updateInfoCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateInfoFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton updateInfoSubmitButton = new JButton();
        updateInfoSubmitButton.setText("Update Info");
        updateInfoSubmitButton.setBounds(233, 406, 203, 32);
        updateInfoFrame.getContentPane().add(updateInfoSubmitButton);

        //Pressing this button will attempt to update the employee in the database.
        updateInfoSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //update the database. If they are present, update the employee and 
                //close the window. Otherwise display an error message.
                if (employeeHasRequiredFields()) {
                    if (phoneNumberParsesCorrectly()) {
                        updateEmployeeInfo();
                        updateInfoFrame.dispose();      
                    } else {
                        createPhoneNumberErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        updateInfoFrame.setVisible(true);
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
        
        //Otherwise return true. (All fields are present)
        return true;
    }
    
    //This method updates the info in the database.
    private void updateEmployeeInfo() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phoneNumber = parsePhoneNumber(phoneNumberText.getText());
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();
        String username = usernameText.getText();

        connection.connect();
        connection.updatePersonalInfo(firstName, lastName, phoneNumber, email, streetAddress,
                city, state, zipcode, username, activeUser);
        connection.disconnect();

        //TODO: (Colten) update database
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
    
    
    //This method controls the update password window.
    private void createUpdatePasswordWindow() {
        //This initializes the frame.
        JFrame changePasswordFrame = new JFrame();
        changePasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changePasswordFrame.setResizable(false);
        changePasswordFrame.setTitle("Change Password");
        changePasswordFrame.setBounds(100, 100, 475, 315);
        changePasswordFrame.getContentPane().setLayout(null);

        //Create current password label.
        JLabel currentPasswordLabel = new JLabel();
        currentPasswordLabel.setText("Enter Current Password");
        currentPasswordLabel.setBounds(10, 10, 203, 24);
        changePasswordFrame.getContentPane().add(currentPasswordLabel);
        
        //Create current password text field.
        currentPasswordText = new JTextField();
        currentPasswordText.setBounds(10, 39, 203, 24);
        changePasswordFrame.getContentPane().add(currentPasswordText);
        
        //Create current password label.
        JLabel newPasswordLabel = new JLabel();
        newPasswordLabel.setText("Enter New Password");
        newPasswordLabel.setBounds(10, 83, 203, 24);
        changePasswordFrame.getContentPane().add(newPasswordLabel);
        
        //Create new password text field.
        newPasswordText = new JTextField();
        newPasswordText.setBounds(10, 112, 203, 24);
        changePasswordFrame.getContentPane().add(newPasswordText);
        
        //Create confirm password label.
        JLabel confirmPasswordLabel = new JLabel();
        confirmPasswordLabel.setText("Confirm New Password");
        confirmPasswordLabel.setBounds(10, 156, 203, 24);
        changePasswordFrame.getContentPane().add(confirmPasswordLabel);
        
        //Create confirm password text field.
        confirmPasswordText = new JTextField();
        confirmPasswordText.setBounds(10, 185, 203, 24);
        changePasswordFrame.getContentPane().add(confirmPasswordText);
        
        //This initializes the cancel button.
        JButton changePasswordCancelButton = new JButton();
        changePasswordCancelButton.setText("Cancel");
        changePasswordCancelButton.setBounds(10, 226, 203, 32);
        changePasswordFrame.getContentPane().add(changePasswordCancelButton);

        //Pressing this button will close the update info window.
        changePasswordCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                changePasswordFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton changePasswordSubmitButton = new JButton();
        changePasswordSubmitButton.setText("Change Password");
        changePasswordSubmitButton.setBounds(233, 226, 203, 32);
        changePasswordFrame.getContentPane().add(changePasswordSubmitButton);

        //Pressing this button will attempt to update the password in the database.
        changePasswordSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //update the database. If they are present, update the password and 
                //close the window. Otherwise display an error message.
                if (passwordBoxHasRequiredFields()) {
                    if (currentPasswordIsValid()) {
                        if (newPasswordFieldsMatch()) {
                            changePassword();
                            changePasswordFrame.dispose();
                        } else {
                            createNewPasswordErrorWindow();
                        }
                    } else {
                        createCurrentPasswordErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        changePasswordFrame.setVisible(true);
    }
    
    //This method ensures that no fields are empty.
    private boolean passwordBoxHasRequiredFields() {
        if (currentPasswordText.getText().equals("")) {
            return false;
        }
        if (newPasswordText.getText().equals("")) {
            return false;
        }
        if (confirmPasswordText.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    //This method validates the current password against the database.
    private boolean currentPasswordIsValid() {
        String enteredCurrentPassword = currentPasswordText.getText();

        connection.connect();
        if(connection.verifyPassword(activeUser, enteredCurrentPassword)) {
            connection.disconnect();
            return true;
        } else {
            connection.disconnect();
            return false;
        }

    }
    
    //This method ensures that the new password fields match. 
    private boolean newPasswordFieldsMatch() {
        if (!newPasswordText.getText().equals(confirmPasswordText.getText())) {
            return false;
        }
        return true;
    }
    
    //This method will change the password in the database.
    private void changePassword() {
        String newPassword = newPasswordText.getText();

        connection.connect();
        connection.changePassword(activeUser, newPassword);
        connection.disconnect();
    }
    
    //This creates the error window if the new passwords don't match.
    private void createNewPasswordErrorWindow() {
        //This creates the error window
        JFrame newPasswordErrorFrame = new JFrame();
        newPasswordErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newPasswordErrorFrame.setResizable(false);
        newPasswordErrorFrame.setTitle("Error");
        newPasswordErrorFrame.setBounds(100, 100, 360, 200);
        newPasswordErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("There is a mismatch with your new password.");
        errorMessage.setBounds(40, 50, 270, 32);
        newPasswordErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(125, 100, 90, 28);
        newPasswordErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                newPasswordErrorFrame.dispose();
            }
        });

        newPasswordErrorFrame.setVisible(true);
    }
    
    //This creates the error window if the current password is wrong.
    private void createCurrentPasswordErrorWindow() {
        //This creates the error window
        JFrame currentPasswordErrorFrame = new JFrame();
        currentPasswordErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        currentPasswordErrorFrame.setResizable(false);
        currentPasswordErrorFrame.setTitle("Error");
        currentPasswordErrorFrame.setBounds(100, 100, 350, 200);
        currentPasswordErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Your current password is incorrect.");
        errorMessage.setBounds(60, 50, 250, 32);
        currentPasswordErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        currentPasswordErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                currentPasswordErrorFrame.dispose();
            }
        });

        currentPasswordErrorFrame.setVisible(true);
    }
}