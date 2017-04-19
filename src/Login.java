import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login {
    
    SQLConnections connection;

    //These are the main fields for the login window.
    JFrame frame;
    JTextField usernameText;
    JTextField passwordText;
    
    JTextField resetPasswordCodeText;
    JTextField newPasswordText;
    JTextField confirmPasswordText;
    JTextField emailText;

    //This is the wrapper method for the UI initialization.
    public void openLogin() {
        this.connection = new SQLConnections();
        constructUi();
    }

    //This method creates the Login UI.
    private void constructUi() {
        //Create the primary frame.
        frame = new JFrame("Overdrive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Overdrive");
        frame.setBounds(100, 100, 595, 443);
        frame.getContentPane().setLayout(null);
        
        //Creates the username label.
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        usernameLabel.setBounds(332, 110, 175, 32);
        frame.getContentPane().add(usernameLabel);

        //Creates username field.
        usernameText = new JTextField();
        usernameText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        usernameText.setHorizontalAlignment(SwingConstants.CENTER);
        usernameText.setToolTipText("Enter username here");
        usernameText.setText("Username");
        usernameText.setBounds(332, 147, 174, 31);
        frame.getContentPane().add(usernameText);
        usernameText.setColumns(10);

        //Username Methods (3)
        //Auto-fills username field.
        usernameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameText.getText().trim().equals("")) {
                    usernameText.setText("Username");
                }
            }
        });

        //Binds enter to the username field.
        usernameText.addKeyListener(new KeyAdapter() {
            int keyPress = 0;

            @Override
            public void keyPressed(KeyEvent e) {
                if (keyPress < 1) {
                    usernameText.selectAll();
                    keyPress++;
                }
            }
        });

        //Calls to validate the entered user.
        usernameText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateUser();
                }
            }
        });
        
        //Creates the password label.
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        passwordLabel.setBounds(332, 189, 175, 32);
        frame.getContentPane().add(passwordLabel);

        //Creates password field.
        passwordText = new JPasswordField();
        passwordText.setToolTipText("Enter password here");
        passwordText.setHorizontalAlignment(SwingConstants.CENTER);
        passwordText.setText("1234");
        passwordText.setBounds(332, 226, 174, 31);
        frame.getContentPane().add(passwordText);

        //Password Methods (2)
        //Auto-fills password field.
        passwordText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if (passwordText.getText().trim().equals("1234")) {
                    passwordText.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordText.getText().trim().equals("")) {
                    passwordText.setText("1234");
                }
            }
        });

        //Binds enter to the password field, calls to validate the entered user.
        passwordText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateUser();
                }
            }
        });

        //Creates login button.        
        JButton btnLogin = new JButton();
        btnLogin.setText("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnLogin.setBounds(332, 280, 174, 31);
        frame.getContentPane().add(btnLogin);

        //Login Button Methods (1)
        //Mouse listener for login button.
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validateUser();
            }
        });
        
        //Creates forgot password button.        
        JButton forgotPassword = new JButton();
        forgotPassword.setText("Forgot Password");
        forgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        forgotPassword.setBounds(332, 320, 174, 31);
        frame.getContentPane().add(forgotPassword);

        //Forgot Password Button Methods (1)
        //Mouse listener for forgot password button.
        forgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createSendEmailConfirmationWindow();
            }
        });

        //Creates "Overdrive" title.
        JLabel overdriveTitle = new JLabel();
        overdriveTitle.setText("Overdrive");
        overdriveTitle.setFont(new Font("Corbel", Font.PLAIN, 40));
        overdriveTitle.setHorizontalAlignment(SwingConstants.CENTER);
        overdriveTitle.setBounds(10, 23, 574, 83);
        frame.getContentPane().add(overdriveTitle);

        //Creates gear icon.
        JLabel gearIcon = new JLabel();
        gearIcon.setIcon(new ImageIcon("resources/gearImage.png"));
        gearIcon.setBounds(52, 117, 200, 200);
        frame.getContentPane().add(gearIcon);

        //Display the frame.
        frame.setVisible(true);
    }
    
    //This method will send the reset password link.
    private void sendResetPasswordEmail() {
        //TODO: (Colten) send reset password link
    }

    private void createSendEmailConfirmationWindow() {
        //This initializes the window.
        JFrame sendEmailConfirmationFrame = new JFrame();
        sendEmailConfirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sendEmailConfirmationFrame.setResizable(false);
        sendEmailConfirmationFrame.setTitle("Forgot Password");
        sendEmailConfirmationFrame.setBounds(100, 100, 300, 310);
        sendEmailConfirmationFrame.getContentPane().setLayout(null);

        //This label displays the message.
        JLabel message = new JLabel();
        message.setText("Send forgot password email?");
        message.setBounds(50, 30, 200, 32);
        sendEmailConfirmationFrame.getContentPane().add(message);
        
        //This label displays the username label.
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        usernameLabel.setBounds(50, 70, 200, 32);
        sendEmailConfirmationFrame.getContentPane().add(usernameLabel);
        
        //This text field is for the username.
        usernameText = new JTextField();
        usernameText.setBounds(50, 105, 174, 32);
        sendEmailConfirmationFrame.getContentPane().add(usernameText);
        
        //This label displays the email label.
        JLabel emailLabel = new JLabel();
        emailLabel.setText("Email");
        emailLabel.setBounds(50, 145, 200, 32);
        sendEmailConfirmationFrame.getContentPane().add(emailLabel);
        
        //This text field is for the email.
        emailText = new JTextField();
        emailText.setBounds(50, 180, 174, 31);
        sendEmailConfirmationFrame.getContentPane().add(emailText);

        //This button sends the email.
        JButton confirmButton = new JButton();
        confirmButton.setText("OK");
        confirmButton.setBounds(105, 230, 90, 28);
        sendEmailConfirmationFrame.getContentPane().add(confirmButton);

        //If the ok button is pressed, the window should be closed and the email
        //will be sent. The enter code window will open.
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (windowOneHasRequiredFields()) {
                    if (usernameExists()) {
                        sendResetPasswordEmail();
                        createEnterCodeWindow();
                        sendEmailConfirmationFrame.dispose();    
                    } else {
                        createUsernameErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        sendEmailConfirmationFrame.setVisible(true);
    }
    
    //This method ensures that the user enters their username and email.
    private boolean windowOneHasRequiredFields() {
        if (usernameText.getText().equals("")) {
            return false;
        }
        if (emailText.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    //This method ensures that the entered username matches one in the database.
    private boolean usernameExists() {
        String username = usernameText.getText();
        //TODO: (Colten) actually make a username check here
        return true;
    }
    
    //This method displays an error for an incorrect username.
    private void createUsernameErrorWindow() {
        //This initializes the error window.
        JFrame usernameErrorFrame = new JFrame();
        usernameErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        usernameErrorFrame.setResizable(false);
        usernameErrorFrame.setTitle("Error");
        usernameErrorFrame.setBounds(100, 100, 350, 200);
        usernameErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("Invalid username.");
        errorMessage.setBounds(120, 50, 100, 32);
        usernameErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(130, 100, 90, 28);
        usernameErrorFrame.getContentPane().add(closeErrorMessageButton);

        //When this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                usernameErrorFrame.dispose();
            }
        });

        usernameErrorFrame.setVisible(true);
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
        JLabel errorMessage = new JLabel();
        errorMessage.setText("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);

        //This button closes the error message.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
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
    
    //This method creates the window for the user to enter the code they were
    //emailed to be able to reset their password.
    private void createEnterCodeWindow() {
        //This initializes the window.
        JFrame enterEmailCodeFrame = new JFrame();
        enterEmailCodeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enterEmailCodeFrame.setResizable(false);
        enterEmailCodeFrame.setTitle("Forgot Password");
        enterEmailCodeFrame.setBounds(100, 100, 300, 200);
        enterEmailCodeFrame.getContentPane().setLayout(null);

        //This label displays the message.
        JLabel message = new JLabel();
        message.setText("Enter the code you were emailed.");
        message.setBounds(50, 30, 200, 32);
        enterEmailCodeFrame.getContentPane().add(message);
        
        //This is the field for the user to enter the code.
        resetPasswordCodeText = new JTextField();
        resetPasswordCodeText.setBounds(50, 70, 200, 32);
        enterEmailCodeFrame.getContentPane().add(resetPasswordCodeText);

        //This button lets the user retry sending the email
        JButton resendButton = new JButton();
        resendButton.setText("Re-Email");
        resendButton.setBounds(50, 112, 90, 28);
        enterEmailCodeFrame.getContentPane().add(resendButton);
        
        //If the user tries to resend the email, they will return to the first 
        //window to re-enter their username and email.
        resendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                createSendEmailConfirmationWindow();
                enterEmailCodeFrame.dispose();
            }
        });
        
        //This button submits the code.
        JButton submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setBounds(150, 112, 90, 28);
        enterEmailCodeFrame.getContentPane().add(submitButton);

        //If the submit button is pressed and the code matches what was emailed, 
        //the window should be closed and the enter new password window will open.
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (windowTwoHasRequiredFields()) {
                    if (codeMatchesEmail()) {
                        createEnterPasswordWindow();
                        enterEmailCodeFrame.dispose();
                    } else {
                        createCodeErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        enterEmailCodeFrame.setVisible(true);
    }
    
    //This method ensures that the user enters a code.
    private boolean windowTwoHasRequiredFields() {
        if (resetPasswordCodeText.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    //This method ensures that the entered code matches that of the emailed one.
    private boolean codeMatchesEmail() {
        String enteredCode = resetPasswordCodeText.getText();
        //TODO: (Colten) pull down emailed code to verify match
        return true;
    }
    
    //This method creates the window for the user to enter the new password.
    private void createEnterPasswordWindow() {
        //This initializes the window.
        JFrame enterNewPasswordFrame = new JFrame();
        enterNewPasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enterNewPasswordFrame.setResizable(false);
        enterNewPasswordFrame.setTitle("Forgot Password");
        enterNewPasswordFrame.setBounds(100, 100, 300, 300);
        enterNewPasswordFrame.getContentPane().setLayout(null);

        //This label displays the new password message.
        JLabel newMessage = new JLabel();
        newMessage.setText("New Password");
        newMessage.setBounds(50, 30, 200, 32);
        enterNewPasswordFrame.getContentPane().add(newMessage);
        
        //This is the field for the user to enter the password.
        newPasswordText = new JTextField();
        newPasswordText.setBounds(50, 70, 200, 32);
        enterNewPasswordFrame.getContentPane().add(newPasswordText);

        //This label displays the confirm password message.
        JLabel confirmMessage = new JLabel();
        confirmMessage.setText("Confirm Password");
        confirmMessage.setBounds(50, 120, 200, 32);
        enterNewPasswordFrame.getContentPane().add(confirmMessage);
        
        //This is the field for the user to confirm the password.
        confirmPasswordText = new JTextField();
        confirmPasswordText.setBounds(50, 160, 200, 32);
        enterNewPasswordFrame.getContentPane().add(confirmPasswordText);
        
        //This button submits the code.
        JButton submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setBounds(105, 210, 90, 28);
        enterNewPasswordFrame.getContentPane().add(submitButton);
        
        //If the submit button is pressed and the passwords match, update the 
        //passwords in the database.
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (windowThreeHasRequiredFields()) {
                    if (passwordsMatch()) {
                        changePassword();
                        enterNewPasswordFrame.dispose();
                    } else {
                        createPasswordMismatchErrorWindow();
                    }
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        enterNewPasswordFrame.setVisible(true);
    }
    
    //This method ensures that the user enters a new password and a confirmation
    //password.
    private boolean windowThreeHasRequiredFields() {
        if (newPasswordText.getText().equals("")) {
            return false;
        }
        if (confirmPasswordText.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    //This method confirms that the new passwords match
    private boolean passwordsMatch() {
        if (!newPasswordText.getText().equals(confirmPasswordText.getText())) {
            return false;
        }
        return true;
    }
    
    //This method changes the password in the database.
    private void changePassword() {
        //TODO: (Colten) make change password work
    }
    
    //This method creates an error window if there is a password mismatch.
    private void createPasswordMismatchErrorWindow() {
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
    
    //This method creates the error window if the entered code is wrong.
    private void createCodeErrorWindow() {
        //This initializes the error window.
        JFrame errorCodeErrorFrame = new JFrame();
        errorCodeErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorCodeErrorFrame.setResizable(false);
        errorCodeErrorFrame.setTitle("Error");
        errorCodeErrorFrame.setBounds(100, 100, 300, 200);
        errorCodeErrorFrame.getContentPane().setLayout(null);

        //This label displays the error message.
        JLabel errorMessage = new JLabel();
        errorMessage.setText("You entered the wrong code.");
        errorMessage.setBounds(50, 50, 200, 32);
        errorCodeErrorFrame.getContentPane().add(errorMessage);

        //This button closes the error message.
        JButton closeErrorMessageButton = new JButton();
        closeErrorMessageButton.setText("OK");
        closeErrorMessageButton.setBounds(105, 100, 90, 28);
        errorCodeErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If the close button is pressed, the window should be closed.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                errorCodeErrorFrame.dispose();
            }
        });

        errorCodeErrorFrame.setVisible(true);
    }
    
    /*This method will check the entered values against the users stored in the 
    database.*/
    public void validateUser() {
        String username = usernameText.getText();
        String password = passwordText.getText();
        boolean valid = connection.validateUser(username, password);

        //TODO: (Caroline) Add max input size for text fields

        if (valid) {
            enterApp(username);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username/password");
        }
    }

    //Moves the user from the login window to the full application.
    private void enterApp(String username) {
        frame.setVisible(false);
        //Open up the Customer Page
        MainMenu mainMenu = new MainMenu();
        mainMenu.open(username);
    }
}