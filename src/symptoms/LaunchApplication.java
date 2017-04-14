public class LaunchApplication {
    public static void main(String[] args) {
        //Initializes the login window to launch the application.
        Login login = new Login();
        javax.swing.SwingUtilities.invokeLater(() -> {
            login.openLogin();
        });
    }
}
