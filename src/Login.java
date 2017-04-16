import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login {
    SQLConnections connection;
    
    //These are the main fields for the login window.
    JFrame frame;
    JTextField usernameText;
    JTextField passwordText;
    
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
		if(usernameText.getText().trim().equals("")) {
                    usernameText.setText("Username");
                }
            }
        });
        
        //Binds enter to the username field.
	usernameText.addKeyListener(new KeyAdapter() {   
            int keyPress = 0;
	
            @Override
            public void keyPressed(KeyEvent e) {
		if(keyPress < 1){
                    usernameText.selectAll();
                    keyPress++;
		}
            }
	});
        
        //Calls to validate the entered user.
	usernameText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
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
		if(passwordText.getText().trim().equals("1234")) {
                    passwordText.setText(""); 
                }    
            }
		
            @Override
            public void focusLost(FocusEvent e) {
                if(passwordText.getText().trim().equals("")) {
                    passwordText.setText("1234");    
                }    
            }
	});

        //Binds enter to the password field, calls to validate the entered user.
	passwordText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
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
        
        //Creates "Overdrive" title.
        JLabel overdriveTitle = new JLabel("Overdrive");
	overdriveTitle.setFont(new Font("Corbel", Font.PLAIN, 40));
	overdriveTitle.setHorizontalAlignment(SwingConstants.CENTER);
	overdriveTitle.setBounds(10, 23, 574, 83);
	frame.getContentPane().add(overdriveTitle);
        
        //Creates gear icon.
        JLabel gearIcon = new JLabel("");
	gearIcon.setIcon(new ImageIcon("mainApp/resources/gearImage.png"));
	gearIcon.setBounds(52, 117, 200, 200);
	frame.getContentPane().add(gearIcon);
        
        //Display the frame.
        frame.setVisible(true);
    }
    
    /*This method will check the entered values against the users stored in the 
    database.*/
    public void validateUser() {
        String username = usernameText.getText();
        String password = passwordText.getText();
        boolean valid = connection.validateUser(username, password);
        
        if (valid) {
            enterApp();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username/password");
        }
    }
    
    //Moves the user from the login window to the full application.
    private void enterApp() {
        frame.setVisible(false);
        //Open up the Customer Page
        MainMenu mainMenu = new MainMenu();
        mainMenu.open();
    }
}