//TODO: (Caroline) Add field names above text box

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
    
    //This method creates the employee portal table.
    public JTable createEmployeePortalTable() {
        portalTable = new JTable();

        return portalTable;
    }
        
    //This method creates the refresh button.
    public JButton createRefreshButton() {
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
        //TODO: (Colten) actually refresh the table
    }
    
    //This method creates the update info button.
    public JButton createUpdateInfoButton() {
        JButton updateInfoButton = new JButton("Update Info");
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
        JButton changePasswordButton = new JButton("Change Password");
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
        //This initializes the frame.
        JFrame updateInfoFrame = new JFrame();
        updateInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateInfoFrame.setResizable(false);
        updateInfoFrame.setTitle("Update Info");
        updateInfoFrame.setBounds(100, 100, 475, 315);
        updateInfoFrame.getContentPane().setLayout(null);

        //This initializes the first name text field.
        firstNameText = new JTextField();
        firstNameText.setText("First Name");
        firstNameText.setBounds(10, 11, 203, 32);
        updateInfoFrame.getContentPane().add(firstNameText);

        //This initializes the last name text field.
        lastNameText = new JTextField();
        lastNameText.setText("Last Name");
        lastNameText.setBounds(233, 11, 203, 32);
        updateInfoFrame.getContentPane().add(lastNameText);

        //This initializes the phone number text field.
        phoneNumberText = new JTextField();
        phoneNumberText.setText("Phone Number");
        phoneNumberText.setBounds(10, 53, 203, 32);
        updateInfoFrame.getContentPane().add(phoneNumberText);

        //This initializes the email text field.
        emailText = new JTextField();
        emailText.setText("Email");
        emailText.setBounds(233, 53, 203, 32);
        updateInfoFrame.getContentPane().add(emailText);

        //This initializes the street address text field.
        streetAddressText = new JTextField();
        streetAddressText.setText("Street Address");
        streetAddressText.setBounds(10, 95, 426, 32);
        updateInfoFrame.getContentPane().add(streetAddressText);

        //This initializes the city text field.
        cityText = new JTextField();
        cityText.setText("City");
        cityText.setBounds(10, 137, 203, 32);
        updateInfoFrame.getContentPane().add(cityText);

        //This initializes the state text field.
        stateText = new JTextField();
        stateText.setText("State");
        stateText.setBounds(233, 137, 203, 32);
        updateInfoFrame.getContentPane().add(stateText);

        //This initializes the zipcode text field.
        zipcodeText = new JTextField();
        zipcodeText.setText("Zipcode");
        zipcodeText.setBounds(10, 179, 203, 32);
        updateInfoFrame.getContentPane().add(zipcodeText);
        
        //This initializes the username text field.
        usernameText = new JTextField();
        usernameText.setText("Username");
        usernameText.setBounds(233, 179, 203, 32);
        updateInfoFrame.getContentPane().add(usernameText);
        
        //This initializes the cancel button.
        JButton updateInfoCancelButton = new JButton("Cancel");
        updateInfoCancelButton.setBounds(10, 221, 203, 32);
        updateInfoFrame.getContentPane().add(updateInfoCancelButton);

        //Pressing this button will close the update info window.
        updateInfoCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateInfoFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton updateInfoSubmitButton = new JButton("Update Info");
        updateInfoSubmitButton.setBounds(233, 221, 203, 32);
        updateInfoFrame.getContentPane().add(updateInfoSubmitButton);

        //Pressing this button will attempt to update the employee in the database.
        updateInfoSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present before attempting to
                //update the database. If they are present, update the employee and 
                //close the window. Otherwise display an error message.
                if (employeeHasRequiredFields()) {
                    updateEmployeeInfo();
                    updateInfoFrame.dispose();   
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
        String phoneNumber = phoneNumberText.getText();
        String email = emailText.getText();
        String streetAddress = streetAddressText.getText();
        String city = cityText.getText();
        String state = stateText.getText();
        String zipcode = zipcodeText.getText();
        String username = usernameText.getText();
        
        //TODO: (Colten) update database
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
        JLabel currentPasswordLabel = new JLabel("Enter Current Password");
        currentPasswordLabel.setBounds(10, 10, 203, 24);
        changePasswordFrame.getContentPane().add(currentPasswordLabel);
        
        //Create current password text field.
        currentPasswordText = new JTextField();
        currentPasswordText.setBounds(10, 39, 203, 24);
        changePasswordFrame.getContentPane().add(currentPasswordText);
        
        //Create current password label.
        JLabel newPasswordLabel = new JLabel("Enter New Password");
        newPasswordLabel.setBounds(10, 83, 203, 24);
        changePasswordFrame.getContentPane().add(newPasswordLabel);
        
        //Create new password text field.
        newPasswordText = new JTextField();
        newPasswordText.setBounds(10, 112, 203, 24);
        changePasswordFrame.getContentPane().add(newPasswordText);
        
        //Create confirm password label.
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password");
        confirmPasswordLabel.setBounds(10, 156, 203, 24);
        changePasswordFrame.getContentPane().add(confirmPasswordLabel);
        
        //Create confirm password text field.
        confirmPasswordText = new JTextField();
        confirmPasswordText.setBounds(10, 185, 203, 24);
        changePasswordFrame.getContentPane().add(confirmPasswordText);
        
        //This initializes the cancel button.
        JButton changePasswordCancelButton = new JButton("Cancel");
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
        JButton changePasswordSubmitButton = new JButton("Change Password");
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
        //TODO: (Colten) validate current password
        return true;
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
        //TODO: (Colten) make change password work
        String newPassword = newPasswordText.getText();
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
        JLabel errorMessage = new JLabel("There is a mismatch with your new password.");
        errorMessage.setBounds(40, 50, 270, 32);
        newPasswordErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton("OK");
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
        JLabel errorMessage = new JLabel("Your current password is incorrect.");
        errorMessage.setBounds(60, 50, 250, 32);
        currentPasswordErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton("OK");
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
