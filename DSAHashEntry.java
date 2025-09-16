/*Used DSAHashEntry from prac 7 
Author: Shayan Ali / 22715218 */
public class DSAHashEntry {
    private String key;        // Key used for identifying  entry
    private CustomerRec value;      // Generic value 
    private int state;         // Entry state: 0 = never used, 1 = used, -1 =  (deleted)

    // Default constructor 
    public DSAHashEntry() {
        key = "";
        value = null;
        state = 0;
    }

    // Constructor with key and value 
    public DSAHashEntry(String inKey, CustomerRec inValue) {
        key = inKey;
        value = inValue;
        state = 1;
    }

    // getters
    public String getKey() { 
        return key; 
    }

    public CustomerRec getValue() { 
        return value; 
    }

    public int getState() {
        return state; 
    }
    
    //setters
    public void setKey(String inKey) { 
        key = inKey; 
    }

    public void setValue(CustomerRec inValue) { 
        value = inValue; 
    }
    public void setState(int inState) { 
        state = inState; 
    }
}