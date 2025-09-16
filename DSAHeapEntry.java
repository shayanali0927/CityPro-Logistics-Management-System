/*Used DSAHeapEntry from prac 8 
Author: Shayan Ali / 22715218 */
public class DSAHeapEntry {
    private double priority;  // The priority value of the heap entry
    private Object value;  // The actual value stored in the heap entry

    // Constructor to DSAHeapEntry
    public DSAHeapEntry(double priority, Object value) {
        this.priority = priority;
        this.value = value;
    }

    // Getter for priority
    public double getPriority() {
        return priority;
    }

    // Setter for priority
    public void setPriority(int curIdx) {
        this.priority = curIdx;
    }

    // Getter for value
    public Object getValue() {
        return value;
    }

    // Setter for value
    public void setValue(Object value) {
        this.value = value;
    }
}