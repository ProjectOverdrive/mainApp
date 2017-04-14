public class WorkOrder {
    private Employee employee;
    private String status;
    private String details;
    private Customer customer;
    private String priority;
    
    public WorkOrder(Employee employee, String status, String details, 
            Customer customer, String priority) {
        this.employee = employee;
        this.status = status;
        this.details = details;
        this.customer = customer;
        this.priority = priority;
    }
    
    public Employee getEmployee() {
        return this.employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDetails() {
        return this.details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getPriority() {
        return this.priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
}
