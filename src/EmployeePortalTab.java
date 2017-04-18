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
        //TODO: actually refresh the table
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
        
        //TODO: update database
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
        //TODO: build this
    }
}
