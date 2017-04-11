public class LaunchApplication {
    public static void main(String[] args) {
        Login login = new Login();
        javax.swing.SwingUtilities.invokeLater(() -> {
            login.openLogin();
        });
    }
}
