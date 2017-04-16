public class InventoryItem {
    private String number;
    private String description;
    private String vendor;
    private String location;
    private int quantity;
    private double unitCost;
    private String url;

    public InventoryItem(String number, String description, String vendor,
                         String location, int quantity, double unitCost, String url) {
        this.number = number;
        this.description = description;
        this.vendor = vendor;
        this.location = location;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.url = url;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return this.unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
