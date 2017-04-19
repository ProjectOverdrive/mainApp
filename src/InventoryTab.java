import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//TODO: (Colten) Table row backgrounds
//TODO: (Caroline) Add field names above text box

public class InventoryTab {

    private SQLConnections connection;

    //These are the text fields for the add/update inventory item windows.
    private JTextField numberText;
    private JTextField descriptionText;
    private JTextField vendorText;
    private JTextField locationText;
    private JTextField quantityText;
    private JTextField unitCostText;
    private JTextField urlText;
    private JTable inventoryTable;

    public InventoryTab(SQLConnections connection) {
        this.connection = connection;
    }

    //This method creates the inventory table.
    public JTable createInventoryTable() {
        inventoryTable = new JTable();
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setModel(DbUtils.resultSetToTableModel(connection.populateInventoryTable()));
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(5);

        return inventoryTable;
    }

    //This method creates the refresh list button.
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
        //TODO: (Colten) actually refresh the table
    }

    //This method creates the add inventory item button.
    public JButton createAddInventoryItemButton() {
        JButton addInventoryItemButton = new JButton("Add Inventory Item");
        addInventoryItemButton.setBounds(1133, 11, 167, 28);

        addInventoryItemButton.addMouseListener(new MouseAdapter() {
            //When the button is pressed, the add inventory item window will open.
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddInventoryItemFrame();
            }
        });
        return addInventoryItemButton;
    }

    //This method creates the delete inventory item button.
    public JButton createDeleteInventoryItemButton() {
        JButton deleteInventoryItemButton = new JButton("Delete Inventory Item");
        deleteInventoryItemButton.setBounds(1133, 50, 167, 28);

        deleteInventoryItemButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int row = inventoryTable.getSelectedRow();
                System.out.println(row);
                int selectedItemID = (int) inventoryTable.getValueAt(row, 0);
                System.out.println(selectedItemID);
                connection.deleteInventoryItem(selectedItemID);
                JOptionPane.showMessageDialog(null, "Item Removed");
            }
        });
        return deleteInventoryItemButton;
    }

    //This method creates the update inventory item button.
    public JButton createUpdateInventoryItemButton() {
        JButton updateInventoryItemButton = new JButton("Update Inventory Item");
        updateInventoryItemButton.setBounds(1133, 89, 167, 28);

        updateInventoryItemButton.addMouseListener(new MouseAdapter() {
            //When this button is pressed the upate inventory item window
            //will open.
            @Override
            public void mousePressed(MouseEvent e) {
                buildUpdateInventoryItemFrame();
            }
        });
        return updateInventoryItemButton;
    }

    //This method creates the order inventory item button.
    public JButton createOrderInventoryItemButton() {
        JButton orderInventoryItemButton = new JButton("Order Inventory Item");
        orderInventoryItemButton.setBounds(956, 50, 167, 28);
        return orderInventoryItemButton;
        //TODO: make order item work
    }

    //This method creates and controls the add inventory item window.
    public void buildAddInventoryItemFrame() {
        //This initializes the add inventory item window.
        JFrame addInventoryItemFrame = new JFrame();
        addInventoryItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addInventoryItemFrame.setResizable(false);
        addInventoryItemFrame.setTitle("Add Inventory Item");
        addInventoryItemFrame.setBounds(100, 100, 475, 315);
        addInventoryItemFrame.getContentPane().setLayout(null);

        //This is the part number text field.
        numberText = new JTextField();
        numberText.setText("Part Number");
        numberText.setBounds(10, 11, 426, 32);
        addInventoryItemFrame.getContentPane().add(numberText);

        //This is the part description text field.
        descriptionText = new JTextField();
        descriptionText.setText("Part Description");
        descriptionText.setBounds(10, 53, 426, 32);
        addInventoryItemFrame.getContentPane().add(descriptionText);

        //This is the part vendor text field.
        vendorText = new JTextField();
        vendorText.setText("Vendor");
        vendorText.setBounds(10, 95, 203, 32);
        addInventoryItemFrame.getContentPane().add(vendorText);

        //This is the part location text field.
        locationText = new JTextField();
        locationText.setText("Location in Store");
        locationText.setBounds(233, 95, 203, 32);
        addInventoryItemFrame.getContentPane().add(locationText);

        //This is the quantity in stock text field.
        quantityText = new JTextField();
        quantityText.setText("Quantity");
        quantityText.setBounds(10, 137, 203, 32);
        addInventoryItemFrame.getContentPane().add(quantityText);

        //This is the unit cost text field.
        unitCostText = new JTextField();
        unitCostText.setText("Unit Cost");
        unitCostText.setBounds(233, 137, 203, 32);
        addInventoryItemFrame.getContentPane().add(unitCostText);

        //This is the part url text field.
        urlText = new JTextField();
        urlText.setText("URL");
        urlText.setBounds(10, 179, 426, 32);
        addInventoryItemFrame.getContentPane().add(urlText);

        //This is the cancel button.
        JButton addInventoryItemCancelButton = new JButton("Cancel");
        addInventoryItemCancelButton.setBounds(10, 221, 203, 32);
        addInventoryItemFrame.getContentPane().add(addInventoryItemCancelButton);

        //Pressing this button will close the add inventory window.
        addInventoryItemCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                addInventoryItemFrame.dispose();
            }
        });

        //This is the submit button.
        JButton addInventoryItemSubmitButton = new JButton("Add Inventory Item");
        addInventoryItemSubmitButton.setBounds(233, 221, 203, 32);
        addInventoryItemFrame.getContentPane().add(addInventoryItemSubmitButton);

        //This button will attempt to add the new item to the database.
        addInventoryItemSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present.
                if (inventoryItemHasRequiredFields()) {
                    //Check that quantity is a numeric value.
                    if (numberParsesCorrectly(quantityText.getText())) {
                        //Check that unit cost is a numeric value.
                        if (numberParsesCorrectly(unitCostText.getText())) {
                            //Add the new item to the database and close the 
                            //add item window.
                            addNewInventoryItem();
                            addInventoryItemFrame.dispose();
                        } else {
                            //Display error if unit cost is not numeric.
                            createUnitCostErrorWindow();
                        }
                    } else {
                        //Display error if quantity is not numeric.
                        createQuantityErrorWindow();
                    }
                    //Display error if required fields are missing.
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        addInventoryItemFrame.setVisible(true);
    }

    //This method verifies that all required fields are present.
    private boolean inventoryItemHasRequiredFields() {
        //If a required field is the default value or is empty, return false.
        if (numberText.getText().equals("Part Number") ||
                numberText.getText().equals("")) {
            return false;
        }
        if (descriptionText.getText().equals("Part Description") ||
                descriptionText.getText().equals("")) {
            return false;
        }
        if (vendorText.getText().equals("Vendor") ||
                vendorText.getText().equals("")) {
            return false;
        }
        if (locationText.getText().equals("Location in Store") ||
                locationText.getText().equals("")) {
            return false;
        }
        if (quantityText.getText().equals("Quantity") ||
                quantityText.getText().equals("")) {
            return false;
        }
        if (unitCostText.getText().equals("Unit Cost") ||
                unitCostText.getText().equals("")) {
            return false;
        }
        if (urlText.getText().equals("URL") ||
                urlText.getText().equals("")) {
            return false;
        }
        //Return true if all required values are present.
        return true;
    }

    //This method adds a new inventory item to the database.
    private void addNewInventoryItem() {
        String number = numberText.getText();
        String description = descriptionText.getText();
        String vendor = vendorText.getText();
        String location = locationText.getText();
        int quantity = Integer.parseInt(quantityText.getText());
        double unitCost = Double.parseDouble(unitCostText.getText());
        String url = urlText.getText();

        connection.addNewInventoryItem(number, description, vendor, location,
                quantity, unitCost, url);
    }

    //This number ensures that fields that are supposed to be numeric are so.
    private boolean numberParsesCorrectly(String numericalText) {
        int count = 0;
        //Loop through the string and increment count when a numeric character
        //is found.
        for (char c : numericalText.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        //If a numeric character is not found, count will be zero and we return
        //false becuase the number is not numeric.
        if (count == 0) {
            return false;
        }
        //Otherwise return true.
        return true;
    }

    //This method creates and controls the update item window.
    public void buildUpdateInventoryItemFrame() {
        //Gets the currently selected row
        int row = inventoryTable.getSelectedRow();
        int selectedInventoryItemID = (int) inventoryTable.getValueAt(row, 0);

        //This initializes the update item window.
        JFrame updateInventoryItemFrame = new JFrame();
        updateInventoryItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateInventoryItemFrame.setResizable(false);
        updateInventoryItemFrame.setTitle("Update Inventory Item");
        updateInventoryItemFrame.setBounds(100, 100, 475, 315);
        updateInventoryItemFrame.getContentPane().setLayout(null);

        //This initializes the part number text field.
        numberText = new JTextField();
        numberText.setText("Part Number");
        numberText.setBounds(10, 11, 426, 32);
        updateInventoryItemFrame.getContentPane().add(numberText);

        //This initializes the part description text field.
        descriptionText = new JTextField();
        descriptionText.setText("Part Description");
        descriptionText.setBounds(10, 53, 426, 32);
        updateInventoryItemFrame.getContentPane().add(descriptionText);

        //This initializes the part vendor text field.
        vendorText = new JTextField();
        vendorText.setText("Vendor");
        vendorText.setBounds(10, 95, 203, 32);
        updateInventoryItemFrame.getContentPane().add(vendorText);

        //This initializes the part location text field.
        locationText = new JTextField();
        locationText.setText("Location in Store");
        locationText.setBounds(233, 95, 203, 32);
        updateInventoryItemFrame.getContentPane().add(locationText);

        //This initializes the quantity in stock text field.
        quantityText = new JTextField();
        quantityText.setText("Quantity");
        quantityText.setBounds(10, 137, 203, 32);
        updateInventoryItemFrame.getContentPane().add(quantityText);

        //This initializes the unit cost text field.
        unitCostText = new JTextField();
        unitCostText.setText("Unit Cost");
        unitCostText.setBounds(233, 137, 203, 32);
        updateInventoryItemFrame.getContentPane().add(unitCostText);

        //This initializes the url text field.
        urlText = new JTextField();
        urlText.setText("URL");
        urlText.setBounds(10, 179, 426, 32);
        updateInventoryItemFrame.getContentPane().add(urlText);

        //This initializes the cancel button.
        JButton updateInventoryCancelButton = new JButton("Cancel");
        updateInventoryCancelButton.setBounds(10, 221, 203, 32);
        updateInventoryItemFrame.getContentPane().add(updateInventoryCancelButton);

        //If the cancel button is pressed, close the update item window.
        updateInventoryCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateInventoryItemFrame.dispose();
            }
        });

        //This initializes the submit button.
        JButton updateInventoryItemSubmitButton = new JButton("Update Inventory Item");
        updateInventoryItemSubmitButton.setBounds(233, 221, 203, 32);
        updateInventoryItemFrame.getContentPane().add(updateInventoryItemSubmitButton);

        //When this button is pressed, attempt to update the item in the database.
        updateInventoryItemSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                //Check that all required fields are present.
                if (inventoryItemHasRequiredFields()) {
                    //Check that quantity is numeric.
                    if (numberParsesCorrectly(quantityText.getText())) {
                        //Check that unit cost is numeric.
                        if (numberParsesCorrectly(unitCostText.getText())) {
                            //If all fields are correct, update the item in the
                            //database and close the update item window.
                            updateInventoryItem(selectedInventoryItemID);
                            updateInventoryItemFrame.dispose();
                        } else {
                            //Display an error if unit cost is not numeric.
                            createUnitCostErrorWindow();
                        }
                    } else {
                        //Display an error if quantity is not numeric.
                        createQuantityErrorWindow();
                    }
                    //Display an error if a required field is missing.
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
        });

        updateInventoryItemFrame.setVisible(true);
    }

    //This method updates an inventory item in the database.
    private void updateInventoryItem(int selectedInventoryItemID) {
        String number = numberText.getText();
        String description = descriptionText.getText();
        String vendor = vendorText.getText();
        String location = locationText.getText();
        int quantity = Integer.parseInt(quantityText.getText());
        double unitCost = Double.parseDouble(unitCostText.getText());
        String url = urlText.getText();

        connection.updateInventoryItem(number, description, vendor, location,
                quantity, unitCost, url, selectedInventoryItemID);
    }

    //This method creates the error window for quantity not being numeric.
    private void createQuantityErrorWindow() {
        //This creates the error window
        JFrame quantityErrorFrame = new JFrame();
        quantityErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quantityErrorFrame.setResizable(false);
        quantityErrorFrame.setTitle("Error");
        quantityErrorFrame.setBounds(100, 100, 350, 200);
        quantityErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel("Quantity must be a numerical value.");
        errorMessage.setBounds(60, 50, 250, 32);
        quantityErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        quantityErrorFrame.getContentPane().add(closeErrorMessageButton);

        //If this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                quantityErrorFrame.dispose();
            }
        });

        quantityErrorFrame.setVisible(true);
    }

    //This method creates the error window if unit cost is not numeric.
    private void createUnitCostErrorWindow() {
        //This initializes the error window.
        JFrame unitCostErrorFrame = new JFrame();
        unitCostErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        unitCostErrorFrame.setResizable(false);
        unitCostErrorFrame.setTitle("Error");
        unitCostErrorFrame.setBounds(100, 100, 350, 200);
        unitCostErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel("Unit Cost must be a numerical value.");
        errorMessage.setBounds(60, 50, 250, 32);
        unitCostErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        unitCostErrorFrame.getContentPane().add(closeErrorMessageButton);

        //When this button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                unitCostErrorFrame.dispose();
            }
        });

        unitCostErrorFrame.setVisible(true);
    }

    //This method creates an error window if a required field is missing.
    private void createRequiredFieldsErrorWindow() {
        //This initializes the error window.
        JFrame requiredFieldsErrorFrame = new JFrame();
        requiredFieldsErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requiredFieldsErrorFrame.setResizable(false);
        requiredFieldsErrorFrame.setTitle("Error");
        requiredFieldsErrorFrame.setBounds(100, 100, 300, 200);
        requiredFieldsErrorFrame.getContentPane().setLayout(null);

        //This displays the error message.
        JLabel errorMessage = new JLabel("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);

        //This creates the okay button.
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(105, 100, 90, 28);
        requiredFieldsErrorFrame.getContentPane().add(closeErrorMessageButton);

        //When the button is pressed, close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                requiredFieldsErrorFrame.dispose();
            }
        });

        requiredFieldsErrorFrame.setVisible(true);
    }

}