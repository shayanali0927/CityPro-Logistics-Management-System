/*Used DSAQueue from prac 3 
Author: Shayan Ali / 22715218 */ 
public class DSAQueue {
    private DSALinkedList queue;

    // Constructor
    public DSAQueue() {
        queue = new DSALinkedList();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Peek = get value of front-most element
    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.peekFirst();
    }

    // Enqueue = add to the end of the queue (FIFO)
    public void enqueue(Object value) {
        queue.insertLast(value);
    }

    // Dequeue = remove from the front of the queue (FIFO)
    public Object dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.removeFirst();
    }

    // Display queue contents
    public void display() {
        System.out.print("Delivery traversal from hub: ");
        queue.traverseForward();
        System.out.println(); // For spacing
    }

}
//Modified from previous Prac 3 DSAQueue to now using LinkedList for Prac 4
//work of: Shayan Ali (22715218)