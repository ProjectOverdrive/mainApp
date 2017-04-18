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

        //Creates username field.
        usernameText = new JTextField();
        usernameText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        usernameText.setHorizontalAlignment(SwingConstants.CENTER);
        usernameText.setToolTipText("Enter username here");
        usernameText.setText("Username");
        usernameText.setBounds(332, 156, 174, 31);
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

        //Creates password field.
        passwordText = new JPasswordField();
        passwordText.setToolTipText("Enter password here");
        passwordText.setHorizontalAlignment(SwingConstants.CENTER);
        passwordText.setText("1234");
        passwordText.setBounds(332, 198, 174, 31);
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
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnLogin.setBounds(332, 270, 174, 31);
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
        JButton forgotPassword = new JButton("Forgot Password");
        forgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        forgotPassword.setBounds(332, 310, 174, 31);
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
        JLabel overdriveTitle = new JLabel("Overdrive");
        overdriveTitle.setFont(new Font("Corbel", Font.PLAIN, 40));
        overdriveTitle.setHorizontalAlignment(SwingConstants.CENTER);
        overdriveTitle.setBounds(10, 23, 574, 83);
        frame.getContentPane().add(overdriveTitle);

        //Creates gear icon.
        JLabel gearIcon = new JLabel("");
        gearIcon.setIcon(new ImageIcon("resources/gearImage.png"));
        gearIcon.setBounds(52, 117, 200, 200);
        frame.getContentPane().add(gearIcon);

        //Display the frame.
        frame.setVisible(true);
    }
    
    //This method will send the reset password link.
    private void sendResetPasswordEmail() {
        //TODO: Colten you got dis
    }

    private void createSendEmailConfirmationWindow() {
        //This initializes the window.
        JFrame sendEmailConfirmationFrame = new JFrame();
        sendEmailConfirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sendEmailConfirmationFrame.setResizable(false);
        sendEmailConfirmationFrame.setTitle("Forgot Password");
        sendEmailConfirmationFrame.setBounds(100, 100, 300, 300);
        sendEmailConfirmationFrame.getContentPane().setLayout(null);

        //This label displays the message.
        JLabel message = new JLabel("Send forgot password email?");
        message.setBounds(50, 50, 200, 32);
        sendEmailConfirmationFrame.getContentPane().add(message);
        
        //This label displays the username label.
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 100, 200, 32);
        sendEmailConfirmationFrame.getContentPane().add(usernameLabel);
        
        //TODO: add fields and validation for username and password

        //This button sends the email.
        JButton confirmButton = new JButton("OK");
        confirmButton.setBounds(105, 150, 90, 28);
        sendEmailConfirmationFrame.getContentPane().add(confirmButton);

        //If the ok button is pressed, the window should be closed and the email
        //will be sent. The enter code window will open.
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                sendResetPasswordEmail();
                createEnterCodeWindow();
                sendEmailConfirmationFrame.dispose();
            }
        });

        sendEmailConfirmationFrame.setVisible(true);
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
        JLabel message = new JLabel("Enter the code you were emailed.");
        message.setBounds(50, 30, 200, 32);
        enterEmailCodeFrame.getContentPane().add(message);
        
        //This is the field for the user to enter the code.
        resetPasswordCodeText = new JTextField();
        resetPasswordCodeText.setBounds(50, 70, 200, 32);
        enterEmailCodeFrame.getContentPane().add(resetPasswordCodeText);

        //TODO: add button to retry sending email
        
        //This button submits the code.
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(105, 112, 90, 28);
        enterEmailCodeFrame.getContentPane().add(submitButton);

        //If the submit button is pressed and the code matches what was emailed, 
        //the window should be closed and the enter new password window will open.
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (codeMatchesEmail()) {
                    createEnterPasswordWindow();
                    enterEmailCodeFrame.dispose();
                } else {
                    createCodeErrorWindow();
                }
            }
        });

        enterEmailCodeFrame.setVisible(true);
    }
    
    //This method ensures that the entered code matches that of the emailed one.
    private boolean codeMatchesEmail() {
        String enteredCode = resetPasswordCodeText.getText();
        //TODO: pull down emailed code to verify match
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
        JLabel newMessage = new JLabel("New Password");
        newMessage.setBounds(50, 30, 200, 32);
        enterNewPasswordFrame.getContentPane().add(newMessage);
        
        //This is the field for the user to enter the password.
        newPasswordText = new JTextField();
        newPasswordText.setBounds(50, 70, 200, 32);
        enterNewPasswordFrame.getContentPane().add(newPasswordText);

        //This label displays the confirm password message.
        JLabel confirmMessage = new JLabel("Confirm Password");
        confirmMessage.setBounds(50, 120, 200, 32);
        enterNewPasswordFrame.getContentPane().add(confirmMessage);
        
        //This is the field for the user to confirm the password.
        confirmPasswordText = new JTextField();
        confirmPasswordText.setBounds(50, 160, 200, 32);
        enterNewPasswordFrame.getContentPane().add(confirmPasswordText);
        
        //This button submits the code.
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(105, 210, 90, 28);
        enterNewPasswordFrame.getContentPane().add(submitButton);

        //TODO: validate against empty fields
        
        //If the submit button is pressed and the passwords match, update the 
        //passwords in the database.
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (passwordsMatch()) {
                    changePassword();
                    enterNewPasswordFrame.dispose();
                } else {
                    createPasswordMismatchErrorWindow();
                }
            }
        });

        enterNewPasswordFrame.setVisible(true);
    }
    
    //This method confirms that the new passwords match
    private boolean passwordsMatch() {
        //TODO: make sure passwords match
        return true;
    }
    
    //This method changes the password in the database.
    private void changePassword() {
        //TODO: make thsi work
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
    
    //TODO: verify that entered code field is not empty
    
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
        JLabel errorMessage = new JLabel("You entered the wrong code.");
        errorMessage.setBounds(50, 50, 200, 32);
        errorCodeErrorFrame.getContentPane().add(errorMessage);

        //This button closes the error message.
        JButton closeErrorMessageButton = new JButton("OK");
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

        //TODO: Add max input size for text fields

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