import javax.swing.*;
public class MainMenu {
    
    JFrame frame;
    
    //This is the wrapper method for the construction of the UI.
    public void open() {
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
        
        //TODO: add Overdrive label
        
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
        CustomerTab customerTab = new CustomerTab();
        customerPanel.add(customerTab.createCustomerTable());
        customerPanel.add(customerTab.createRefreshListButton());
        customerPanel.add(customerTab.createAddCustomerButton());
        customerPanel.add(customerTab.createDeleteCustomerButton());
        customerPanel.add(customerTab.createUpdateCustomerButton());
        
        //Create the panel for work orders.
	JPanel workOrderPanel = new JPanel();
	mainWindowPanel.addTab("Work Orders", null, workOrderPanel, 
                "All Work Orders");
	workOrderPanel.setLayout(null);
        
        //Initialize the work order tab and add its components.
        WorkOrderTab workOrderTab = new WorkOrderTab();
        workOrderPanel.add(workOrderTab.createWorkOrderTable());
        workOrderPanel.add(workOrderTab.createRefreshListButton());
        workOrderPanel.add(workOrderTab.createAddWorkOrderButton());
        workOrderPanel.add(workOrderTab.createDeleteWorkOrderButton());
        workOrderPanel.add(workOrderTab.createUpdateWorkOrderButton());
        
        //Create the panel for inventory.
	JPanel inventoryPanel = new JPanel();
	mainWindowPanel.addTab("Inventory", null, inventoryPanel, "Store "
                + "Inventory");
	inventoryPanel.setLayout(null);
        
        //Initialize the inventory tab and add its components.
        InventoryTab inventoryTab = new InventoryTab();
        inventoryPanel.add(inventoryTab.createInventoryTable());
        inventoryPanel.add(inventoryTab.createRefreshListButton());
        inventoryPanel.add(inventoryTab.createAddInventoryItemButton());
        inventoryPanel.add(inventoryTab.createDeleteInventoryItemButton());
        inventoryPanel.add(inventoryTab.createUpdateInventoryItemButton());
        inventoryPanel.add(inventoryTab.createOrderInventoryItemButton());
        
        //Create the panel for symptoms.
	JPanel symptomsPanel = new JPanel();
	mainWindowPanel.addTab("Symptoms", null, symptomsPanel, "Symptoms "
                + "Checker");
	symptomsPanel.setLayout(null);
        
        //Create the panel for manage.
        JPanel managePanel = new JPanel();
	mainWindowPanel.addTab("Manage", null, managePanel, null);
	managePanel.setLayout(null);
        
        //Display the frame.
        frame.setVisible(true);
    }
}