public class DeliveryRecord {
    private String customerID;
    private String customerName;
    private int estimatedTime;

    public DeliveryRecord(String customerID, String customerName, int estimatedTime) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.estimatedTime = estimatedTime;
        
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }
}
