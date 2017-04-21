import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {

    JFrame frame;
    private SQLConnections connection;
    private String activeUser;
    private CustomerTab customerTab;
    private JScrollPane custTablePanel;
    private WorkOrderTab workOrderTab;
    private JScrollPane workOrderTablePanel;
    private InventoryTab inventoryTab;
    private JScrollPane inventoryTablePanel;
    private ManageTab manageTab;
    private JScrollPane employeeTablePanel;
    private EmployeePortalTab employeePortalTab;
    private JScrollPane employeeInfoPanel;

    //This is the wrapper method for the construction of the UI.
    public void open(String username) {
        activeUser = username;
        this.connection = SQLConnections.getConnectionInstance();
        constructUi();
    }

    //This creates the UI.
    private void constructUi() {
        //Create the primary frame.
        frame = new JFrame("Overdrive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Overdrive");
        frame.setBounds(100, 100, 1356, 928);
        frame.getContentPane().setLayout(null);

        //Create Overdrive Label
        JLabel overdriveLabel = new JLabel();
        overdriveLabel.setText("Overdrive");
        overdriveLabel.setBounds(620, 5, 300, 60);
        overdriveLabel.setFont(new Font(overdriveLabel.getFont().getName(), Font.PLAIN, 32));
        frame.getContentPane().add(overdriveLabel);
        
        //Create Logout Button
        JButton logoutButton = new JButton();
        logoutButton.setText("Logout");
        logoutButton.setBounds(1145, 25, 167, 28);
        frame.getContentPane().add(logoutButton);
        
        logoutButton.addMouseListener(new MouseAdapter() {
            //The window should close and login should open.
            @Override
            public void mousePressed(MouseEvent e) {
                Login login = new Login();
                login.openLogin();
                frame.dispose();
            }
        });

        //Creates the main window panel.
        JTabbedPane mainWindowPanel = new JTabbedPane(JTabbedPane.TOP);
        mainWindowPanel.setBorder(null);
        mainWindowPanel.setBounds(10, 72, 1330, 795);
        frame.getContentPane().add(mainWindowPanel);

        //Creates the customer panel.
        JPanel customerPanel = new JPanel();
        mainWindowPanel.addTab("Customers", null, customerPanel, "Customer "
                + "Database");
        customerPanel.setLayout(null);

        //Initialize the customer tab and add its components.
        customerTab = new CustomerTab();
        custTablePanel = new JScrollPane();
        custTablePanel.setBounds(0, 11, 927, 817);
        customerPanel.add(custTablePanel);
        createCustomerTable();

        customerPanel.add(createCustomerRefreshListButton());
        customerPanel.add(customerTab.createAddCustomerButton());
        customerPanel.add(customerTab.createDeleteCustomerButton());
        customerPanel.add(customerTab.createUpdateCustomerButton());

        //Create the panel for work orders.
        JPanel workOrderPanel = new JPanel();
        mainWindowPanel.addTab("Work Orders", null, workOrderPanel,
                "All Work Orders");
        workOrderPanel.setLayout(null);

        //Initialize the work order tab and add its components.
        workOrderTab = new WorkOrderTab();
        workOrderTablePanel = new JScrollPane();
        workOrderTablePanel.setBounds(0, 11, 927, 817);
        workOrderPanel.add(workOrderTablePanel);
        createWorkOrderTable();

        workOrderPanel.add(createWorkOrderRefreshListButton());
        workOrderPanel.add(workOrderTab.createAddWorkOrderButton());
        workOrderPanel.add(workOrderTab.createDeleteWorkOrderButton());
        workOrderPanel.add(workOrderTab.createUpdateWorkOrderButton());

        //Create the panel for inventory.
        JPanel inventoryPanel = new JPanel();
        mainWindowPanel.addTab("Inventory", null, inventoryPanel, "Store "
                + "Inventory");
        inventoryPanel.setLayout(null);

        //Initialize the inventory tab and add its components.
        inventoryTab = new InventoryTab();
        inventoryTablePanel = new JScrollPane();
        inventoryTablePanel.setBounds(0, 11, 927, 817);
        inventoryPanel.add(inventoryTablePanel);
        createInventoryTable();

        inventoryPanel.add(createInventoryRefreshListButton());
        inventoryPanel.add(inventoryTab.createAddInventoryItemButton());
        inventoryPanel.add(inventoryTab.createDeleteInventoryItemButton());
        inventoryPanel.add(inventoryTab.createUpdateInventoryItemButton());
        inventoryPanel.add(inventoryTab.createOrderInventoryItemButton());

        //Create the panel for symptoms.
        JPanel symptomsPanel = new JPanel();
        mainWindowPanel.addTab("Symptoms", null, symptomsPanel, "Symptoms "
                + "Checker");
        symptomsPanel.setLayout(null);

        //Initialize the symptoms tab and its components.
        Symptoms symptoms = new Symptoms();
        symptomsPanel.add(symptoms.createSymptomsLabel());
        symptomsPanel.add(symptoms.createInputBox1());
        symptomsPanel.add(symptoms.createInputBox2());
        symptomsPanel.add(symptoms.createInputBox3());
        symptomsPanel.add(symptoms.createMileageLabel());
        symptomsPanel.add(symptoms.createMileageInput());
        symptomsPanel.add(symptoms.createSubmitButton());

        //Create the panel for manage.
        //If active user is a manager
        connection.connect();
        if (connection.isAdmin(activeUser)) {
            JPanel managePanel = new JPanel();
            mainWindowPanel.addTab("Manage", null, managePanel, null);
            managePanel.setLayout(null);

            //Initialize the manage tab and its components.
            manageTab = new ManageTab();
            employeeTablePanel = new JScrollPane();
            employeeTablePanel.setBounds(0, 11, 927, 817);
            managePanel.add(employeeTablePanel);
            createManageTable();

            managePanel.add(createManageRefreshListButton());
            managePanel.add(manageTab.createAddEmployeeButton());
            managePanel.add(manageTab.createDeleteEmployeeButton());
            managePanel.add(manageTab.createUpdateEmployeeButton());
            connection.disconnect();
        }

        //Create the panel for the employee portal.
        JPanel employeePortalPanel = new JPanel();
        mainWindowPanel.addTab("Employee Portal", null, employeePortalPanel, null);
        employeePortalPanel.setLayout(null);
        
        //Initialize the employee portal tab and its components.
        //Initialize the symptoms tab and its components.
        employeePortalTab = new EmployeePortalTab(activeUser);
        employeeInfoPanel = new JScrollPane();
        employeeInfoPanel.setBounds(0, 11, 927, 817);
        employeePortalPanel.add(employeeInfoPanel);
        createEmployeePortalTable();

        employeePortalPanel.add(createEmployeePortalRefreshButton());
        employeePortalPanel.add(employeePortalTab.createUpdateInfoButton());
        employeePortalPanel.add(employeePortalTab.createChangePasswordButton());

        //Display the frame.
        frame.setVisible(true);
    }

    //This method creates the button for refreshing the customer list from the database.
    public JButton createCustomerRefreshListButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        refreshButton.addMouseListener(new MouseAdapter() {
            //The table should be refreshed.
            @Override
            public void mousePressed(MouseEvent e) {
                createCustomerTable();
                System.out.println("I fired");
            }
        });

        return refreshButton;
    }

    //This method creates the work order table refresh list button.
    public JButton createWorkOrderRefreshListButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        refreshButton.addMouseListener(new MouseAdapter() {
            //The table should be refreshed.
            @Override
            public void mousePressed(MouseEvent e) {
                createWorkOrderTable();
            }
        });
        return refreshButton;
    }

    //This method creates the inventory table refresh list button.
    public JButton createInventoryRefreshListButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        refreshButton.addMouseListener(new MouseAdapter() {
            //The table should be refreshed.
            @Override
            public void mousePressed(MouseEvent e) {
                createInventoryTable();
            }
        });
        return refreshButton;
    }

    //This method creates the management table refresh list button.
    public JButton createManageRefreshListButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        refreshButton.addMouseListener(new MouseAdapter() {
            //The table should be refreshed.
            @Override
            public void mousePressed(MouseEvent e) {
                createManageTable();
            }
        });
        return refreshButton;
    }

    //This method creates the employee info refresh button.
    public JButton createEmployeePortalRefreshButton() {
        JButton refreshButton = new JButton();
        refreshButton.setText("Refresh");
        refreshButton.setBounds(956, 11, 167, 28);
        refreshButton.addMouseListener(new MouseAdapter() {
            //The table should be refreshed.
            @Override
            public void mousePressed(MouseEvent e) {
                createEmployeePortalTable();
            }
        });
        return refreshButton;
    }

    //Creates customer table and adds to customer panel
    public void createCustomerTable() {
        custTablePanel.setViewportView(customerTab.createCustomerTable());
    }

    //Creates work order table and adds to work orders panel
    public void createWorkOrderTable() {
        workOrderTablePanel.setViewportView(workOrderTab.createWorkOrderTable());
    }

    //Creates inventory table and adds to work orders panel
    public void createInventoryTable() {
        inventoryTablePanel.setViewportView(inventoryTab.createInventoryTable());
    }

    //Creates manage table and adds to manage panel
    public void createManageTable() {
        employeeTablePanel.setViewportView(manageTab.createEmployeeTable());
    }

    //Creates employee info table and adds to employee portal panel
    public void createEmployeePortalTable() {
        employeeInfoPanel.setViewportView(employeePortalTab.createEmployeePortalTable());
    }
}