/*Used DSALinkedList from prac 4 
Author: Shayan Ali / 22715218 */  
import java.util.*;

// DSALinkedList class for managing a Doubly-Ended Doubly-Linked List
public class DSALinkedList {
    private DSAListNode head; // First node of the list
    private DSAListNode tail; // Last node of the list
    private int count = 0;


    // Constructor
    public DSALinkedList() {
        head = null;
        tail = null;
    }

    public void insertFirst(Object newValue) {
        DSAListNode newNode = new DSAListNode(newValue);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        count++;
    }

    public void insertLast(Object newValue) {
        DSAListNode newNode = new DSAListNode(newValue);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        count++;
    }

    public int getSize() {
        return count;
    }

    public Object peekFirst() {
        if (isEmpty()) throw new NoSuchElementException("List is empty.");
        return head.getValue();
    }

    public Object peekLast() {
        if (isEmpty()) throw new NoSuchElementException("List is empty.");
        return tail.getValue();
    }

    public Object removeFirst() {
        if (isEmpty()) throw new IllegalStateException("List is empty");
        Object nodeValue = head.getValue();
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.setPrev(null);
        }
        count--;
        return nodeValue;
    }

    public Object removeLast() {
        if (isEmpty()) throw new IllegalStateException("List is empty");
        Object nodeValue = tail.getValue();
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        count--;
        return nodeValue;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void traverseForward() {
        DSAListNode current = head;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

    public boolean find(Object valueToFind) {
        DSAListNode current = head;
        while (current != null) {
            if (current.getValue().equals(valueToFind)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
    public DSAListNode getHead() {
        return head;
    }
    
    // Removes the first value from the list
    public boolean remove(Object valueToRemove) {
        DSAListNode current = head;
        while (current != null) {
            if (current.getValue().equals(valueToRemove)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                return true; // Value found and removed
            }
            current = current.getNext();
        }
        return false; // Value not found
    }
}