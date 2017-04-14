import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class InventoryTab {
    private JTextField numberText;
    private JTextField descriptionText;
    private JTextField vendorText;
    private JTextField locationText;
    private JTextField quantityText;
    private JTextField unitCostText;
    private JTextField urlText;
    
    public JTable createInventoryTable() {
        JTable inventoryTable = new JTable();
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        return inventoryTable;
    }
    
    public JButton createRefreshListButton() {
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.setBounds(956, 11, 167, 28);
        return refreshButton;
    }
    
    public JButton createAddInventoryItemButton() {
        JButton addInventoryItemButton = new JButton("Add Inventory Item");
        addInventoryItemButton.setBounds(1133, 11, 167, 28);
        addInventoryItemButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                buildAddInventoryItemFrame();
            }
	});
        return addInventoryItemButton;
    }
    
    public JButton createDeleteInventoryItemButton() {
        JButton deleteInventoryItemButton = new JButton("Delete Inventory Item");
        deleteInventoryItemButton.setBounds(1133, 50, 167, 28);
        return deleteInventoryItemButton;
    }
    
    public JButton createUpdateInventoryItemButton() {
        JButton updateInventoryItemButton = new JButton("Update Inventory Item");
        updateInventoryItemButton.setBounds(1133, 89, 167, 28);
        updateInventoryItemButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                buildUpdateInventoryItemFrame();
            }
	});
        return updateInventoryItemButton;
    }
    
    public JButton createOrderInventoryItemButton() {
        JButton orderInventoryItemButton = new JButton("Order Inventory Item");
        orderInventoryItemButton.setBounds(956, 50, 167, 28);
        return orderInventoryItemButton;
    }
 
    public void buildAddInventoryItemFrame() {
        JFrame addInventoryItemFrame = new JFrame();
        addInventoryItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addInventoryItemFrame.setResizable(false);
        addInventoryItemFrame.setTitle("Add Inventory Item");
        addInventoryItemFrame.setBounds(100, 100, 475, 315);
        addInventoryItemFrame.getContentPane().setLayout(null);
        
        numberText = new JTextField();
	numberText.setText("Part Number");
	numberText.setBounds(10, 11, 426, 32);
	addInventoryItemFrame.getContentPane().add(numberText);
        
        descriptionText = new JTextField();
	descriptionText.setText("Part Description");
	descriptionText.setBounds(10, 53, 426, 32);
	addInventoryItemFrame.getContentPane().add(descriptionText);
	
        vendorText = new JTextField();
        vendorText.setText("Vendor");
        vendorText.setBounds(10, 95, 203, 32);
        addInventoryItemFrame.getContentPane().add(vendorText);
        
        locationText = new JTextField();
	locationText.setText("Location in Store");
	locationText.setBounds(233, 95, 203, 32);
	addInventoryItemFrame.getContentPane().add(locationText);
        
        quantityText = new JTextField();
	quantityText.setText("Quantity");
	quantityText.setBounds(10, 137, 203, 32);
	addInventoryItemFrame.getContentPane().add(quantityText);
        
        unitCostText = new JTextField();
        unitCostText.setText("Unit Cost");
        unitCostText.setBounds(233, 137, 203, 32);
        addInventoryItemFrame.getContentPane().add(unitCostText);
        
        urlText = new JTextField();
	urlText.setText("URL");
	urlText.setBounds(10, 179, 426, 32);
	addInventoryItemFrame.getContentPane().add(urlText);
        
        JButton addInventoryItemCancelButton = new JButton("Cancel");
        addInventoryItemCancelButton.setBounds(10, 221, 203, 32);
        addInventoryItemFrame.getContentPane().add(addInventoryItemCancelButton);
        
        addInventoryItemCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                addInventoryItemFrame.dispose();
            }
	});
        
        JButton addInventoryItemSubmitButton = new JButton("Add Inventory Item");
        addInventoryItemSubmitButton.setBounds(233, 221, 203, 32);
        addInventoryItemFrame.getContentPane().add(addInventoryItemSubmitButton);
        
        addInventoryItemSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if(inventoryItemHasRequiredFields()) {
                    if(numberParsesCorrectly(quantityText.getText())) {
                        if(numberParsesCorrectly(unitCostText.getText())) {
                            addNewInventoryItem();
                            addInventoryItemFrame.dispose();     
                        } else {
                            createUnitCostErrorWindow();
                        }
                    } else {
                        createQuantityErrorWindow();
                    }
                    
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
	});
                
        addInventoryItemFrame.setVisible(true);
    }
    
    private boolean inventoryItemHasRequiredFields() {
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
        return true;
    }
    
    private void addNewInventoryItem() {
        String number = numberText.getText();
        String description = descriptionText.getText();
        String vendor = vendorText.getText();
        String location = locationText.getText();
        int quantity = Integer.parseInt(quantityText.getText());
        double unitCost = Double.parseDouble(unitCostText.getText());
        String url = urlText.getText();
        
        //TODO: Add these fields as an object to the database.
    }
    
    private boolean numberParsesCorrectly(String numericalText) {
        int count = 0;
        for(char c : numericalText.toCharArray()) {
            if(Character.isDigit(c)) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        }
        return true;
    }
    
    //get it to prepopulate
    public void buildUpdateInventoryItemFrame() {
        JFrame updateInventoryItemFrame = new JFrame();
        updateInventoryItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateInventoryItemFrame.setResizable(false);
        updateInventoryItemFrame.setTitle("Update Inventory Item");
        updateInventoryItemFrame.setBounds(100, 100, 475, 315);
        updateInventoryItemFrame.getContentPane().setLayout(null);
        
        numberText = new JTextField();
	numberText.setText("Part Number");
	numberText.setBounds(10, 11, 426, 32);
	updateInventoryItemFrame.getContentPane().add(numberText);
        
        descriptionText = new JTextField();
	descriptionText.setText("Part Description");
	descriptionText.setBounds(10, 53, 426, 32);
	updateInventoryItemFrame.getContentPane().add(descriptionText);
	
        vendorText = new JTextField();
        vendorText.setText("Vendor");
        vendorText.setBounds(10, 95, 203, 32);
        updateInventoryItemFrame.getContentPane().add(vendorText);
        
        locationText = new JTextField();
	locationText.setText("Location in Store");
	locationText.setBounds(233, 95, 203, 32);
	updateInventoryItemFrame.getContentPane().add(locationText);
        
        quantityText = new JTextField();
	quantityText.setText("Quantity");
	quantityText.setBounds(10, 137, 203, 32);
	updateInventoryItemFrame.getContentPane().add(quantityText);
        
        unitCostText = new JTextField();
        unitCostText.setText("Unit Cost");
        unitCostText.setBounds(233, 137, 203, 32);
        updateInventoryItemFrame.getContentPane().add(unitCostText);
        
        urlText = new JTextField();
	urlText.setText("URL");
	urlText.setBounds(10, 179, 426, 32);
	updateInventoryItemFrame.getContentPane().add(urlText);
        
        JButton updateInventoryCancelButton = new JButton("Cancel");
        updateInventoryCancelButton.setBounds(10, 221, 203, 32);
        updateInventoryItemFrame.getContentPane().add(updateInventoryCancelButton);
        
        updateInventoryCancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                updateInventoryItemFrame.dispose();
            }
	});
        
        JButton updateInventoryItemSubmitButton = new JButton("Update Inventory Item");
        updateInventoryItemSubmitButton.setBounds(233, 221, 203, 32);
        updateInventoryItemFrame.getContentPane().add(updateInventoryItemSubmitButton);
        
        updateInventoryItemSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if(inventoryItemHasRequiredFields()) {
                    if(numberParsesCorrectly(quantityText.getText())) {
                        if(numberParsesCorrectly(unitCostText.getText())) {
                            updateInventoryItem();
                            updateInventoryItemFrame.dispose();     
                        } else {
                            createUnitCostErrorWindow();
                        }
                    } else {
                        createQuantityErrorWindow();
                    }
                    
                } else {
                    createRequiredFieldsErrorWindow();
                }
            }
	});
                
        updateInventoryItemFrame.setVisible(true);
    }
    
    private void updateInventoryItem() {
        String number = numberText.getText();
        String description = descriptionText.getText();
        String vendor = vendorText.getText();
        String location = locationText.getText();
        int quantity = Integer.parseInt(quantityText.getText());
        double unitCost = Double.parseDouble(unitCostText.getText());
        String url = urlText.getText();
        
        //TODO: update these fields as an object to the database.
    }
    
    private void createQuantityErrorWindow() {
        JFrame quantityErrorFrame = new JFrame();
        quantityErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quantityErrorFrame.setResizable(false);
        quantityErrorFrame.setTitle("Error");
        quantityErrorFrame.setBounds(100, 100, 350, 200);
        quantityErrorFrame.getContentPane().setLayout(null);
        
        JLabel errorMessage = new JLabel("Quantity must be a numerical value.");
        errorMessage.setBounds(60, 50, 250, 32);
        quantityErrorFrame.getContentPane().add(errorMessage);
        
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        quantityErrorFrame.getContentPane().add(closeErrorMessageButton);
        
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                quantityErrorFrame.dispose();
            }
	});
        
        quantityErrorFrame.setVisible(true);
    }
    
    private void createUnitCostErrorWindow() {
        JFrame unitCostErrorFrame = new JFrame();
        unitCostErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        unitCostErrorFrame.setResizable(false);
        unitCostErrorFrame.setTitle("Error");
        unitCostErrorFrame.setBounds(100, 100, 350, 200);
        unitCostErrorFrame.getContentPane().setLayout(null);
        
        JLabel errorMessage = new JLabel("Unit Cost must be a numerical value.");
        errorMessage.setBounds(60, 50, 250, 32);
        unitCostErrorFrame.getContentPane().add(errorMessage);
        
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        unitCostErrorFrame.getContentPane().add(closeErrorMessageButton);
        
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                unitCostErrorFrame.dispose();
            }
	});
        
        unitCostErrorFrame.setVisible(true);
    }
    
    private void createRequiredFieldsErrorWindow() {
        JFrame requiredFieldsErrorFrame = new JFrame();
        requiredFieldsErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requiredFieldsErrorFrame.setResizable(false);
        requiredFieldsErrorFrame.setTitle("Error");
        requiredFieldsErrorFrame.setBounds(100, 100, 300, 200);
        requiredFieldsErrorFrame.getContentPane().setLayout(null);
        
        JLabel errorMessage = new JLabel("You are missing a required field.");
        errorMessage.setBounds(50, 50, 200, 32);
        requiredFieldsErrorFrame.getContentPane().add(errorMessage);
        
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(105, 100, 90, 28);
        requiredFieldsErrorFrame.getContentPane().add(closeErrorMessageButton);
        
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                requiredFieldsErrorFrame.dispose();
            }
	});
        
        requiredFieldsErrorFrame.setVisible(true);
    }
}
