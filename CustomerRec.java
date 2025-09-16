public class CustomerRec {
    private String customerID;
    private String name;
    private String address;
    private int priorityLevel;
    private String deliveryStatus;

    public CustomerRec(String customerID, String name, String address, int priorityLevel, String deliveryStatus) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.priorityLevel = priorityLevel;
        this.deliveryStatus = deliveryStatus;
    }
    

    // Getters
    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    // Setter
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    
    // Print method for logs
    public String toString() {
        return "Customer ID: " + customerID + "\n"
             + "  Name: " + name + "\n"
             + "  Address: " + address + "\n"
             + "  Priority: " + priorityLevel + "\n"
             + "  Status: " + deliveryStatus;
    }
    
}
