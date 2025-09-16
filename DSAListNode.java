/*Used DSAListNode from prac 4 
Author: Shayan Ali / 22715218 */
public class DSAListNode {
    // Data, next and previous pointer
    public Object value;             // Stores the data value
    public DSAListNode next;  // Points to the next node
    public DSAListNode prev;  // Points to the previous node

    // Constructor to initialize value, next and prev as null
    public DSAListNode(Object inValue) {
        if (inValue == null) {
            throw new IllegalArgumentException("Node value cannot be null");
        }
        value = inValue;
        next = null;
        prev = null;
    }

    // Setter for next
    public void setNext(DSAListNode newNext) {
        next = newNext;
    }

    // Setter for prev
    public void setPrev(DSAListNode newPrev) {
        prev = newPrev;
    }

    // Setter for value
    public void setValue(Object inValue) {
        if (inValue == null) {
            throw new IllegalArgumentException("Node value cannot be null");
        }
        value = inValue;
    }

    // Getter for next
    public DSAListNode getNext() {
        return next;
    }

    // Getter for prev
    public DSAListNode getPrev() {
        return prev;
    }

    // Getter for value
    public Object getValue() {
        return value;
    }
}
