/*Used DSAQueue from prac 3 
Author: Shayan Ali / 22715218 */ 
public class DSAStack {
    private DSALinkedList stack;

    // Constructor
    public DSAStack() {
        stack = new DSALinkedList();
    }

    // Push = add to top of stack (LIFO)
    public void push(Object value) {
        stack.insertFirst(value);
    }

    // Pop = remove top item
    public Object pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.removeFirst();
    }

    // Top = view top item
    public Object top() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.peekFirst();
    }

    // Check if empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Display stack contents
    public void display() {
        stack.traverseForward();
    }

    // Main method to test
    public static void main(String[] args) {
        DSAStack stack = new DSAStack();

        System.out.println("\n====== Stack Operations ======\n");
        System.out.println("Stack initialized.");

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Stack after pushes:");
        stack.display();

        System.out.println("\nTop element: " + stack.top());

        System.out.println("\nPopped element: " + stack.pop());
        System.out.println("Stack after popping:");
        stack.display();

        stack.pop();
        stack.pop();

        System.out.println("\nIs stack empty? " + stack.isEmpty());
        System.out.println("====== End of Stack Operations ======");
    }
}
//Modified from previous Prac 3 DSAStack to now using LinkedList for Prac 4
//work of: Shayan Ali (22715218)