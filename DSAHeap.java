/*
 * DSAHeap.java
 * Module 3 – Heap-Based Parcel Scheduling
 * Author: Shayan Ali / 22715218
 * 
 * Description:
 * This class implements a max heap structure used to schedule deliveries
 * based on calculated priority values.
 * It supports:
 *   - Inserting new delivery requests into the heap,
 *   - Extracting the highest-priority delivery,
 *   - Displaying the current heap state after operations.
 * 
 * Priority is calculated using customer level and estimated delivery time
 * from Modules 1 and 2.
 * 
 * This code was adapted from my DSAHeap from Prac 8 and modified to the assignment 
 */

public class DSAHeap {
    private DSAHeapEntry[] heap;  // Array to store heap entries
    private int count;            // to record number of items in the heap

    // Constructor to initialize Heap with maximum size
    public DSAHeap(int maxSize) {
        heap = new DSAHeapEntry[maxSize];
        count = 0;
    }

    // Add a new entry to the heap with the given priority and value
    public void add(double priority, Object value) {
        DSAHeapEntry newEntry = new DSAHeapEntry(priority, value); // Create new heap entry
        heap[count] = newEntry; // Place it at the end of the array
        trickleUp(count);       // Restore heap condition by moving it up
        count++;                // Increase item count
    }

    // Adds a new delivery request to the heap using priority formula
    public void addDelivery(String customerID, int customerPriority, int deliveryTime) {
        // Calculate priority: (6 - P) + 1000 / T
        double priorityscore = (6 - customerPriority) + (1000.0 / deliveryTime);

        // Build a string to represent the delivery info usign value
        String value = "Customer " + customerID + " | P=" + customerPriority + ", T=" + deliveryTime;

        // Print the calculation for priority
        System.out.println("--------------------------------------------------");
        System.out.println("Adding to heap: " + value);
        System.out.println("Priority = (6 - " + customerPriority + ") + 1000 / " + deliveryTime +" = " + priorityscore);

        // Insert into heap using existing method of add
        add(priorityscore, value); 
    }

    // Removes and returns the highest-priority delivery from the heap
    public Object dispatchNextParcel() {
        //Condition to check if heap is empty or not
        if (count == 0) {
            System.out.println("Heap is empty. No parcels to dispatch.");
            return null;
        }

        // Stores the root value which is the highest priority
        Object dispatchedParcel = heap[0].getValue();

        // ReplaceS root with last item
        heap[0] = heap[count - 1];

        // Removes reference to old last node 
        heap[count - 1] = null;

        // Decreases the count
        count--;

        // Fixs the heap from the root Restores heap order
        trickleDown(0);

        // Returns the removed value
        return dispatchedParcel;
    }

    // Remove and return the highest priority entry from the heap
    public DSAHeapEntry remove() {
        DSAHeapEntry removedEntry = heap[0]; // Save the root to return
        count--;                             // Reduce item count
        heap[0] = heap[count];               // Move last item to root
        heap[count] = null;                  // Clear the last slot
        trickleDown(0);               // Restore heap condition by moving it down
        return removedEntry;
    }

    // Display all elements of the heap 
    public void printHeap() {
        System.out.println("---- Current Heap State ----");
        for (int i = 0; i < count; i++) {
            System.out.println("[" + i + "] Priority: " + heap[i].getPriority() + " -> " + heap[i].getValue());
        }
        System.out.println("----------------------------");
    }
    
    // Trickle-up 
    private void trickleUp(int curIdx) {
        int parentIdx = (curIdx - 1) / 2; // Calculate parent index
        // Keep swapping as long as parent is smaller than current
        while (curIdx > 0 && heap[curIdx].getPriority() > heap[parentIdx].getPriority()) {
            DSAHeapEntry temp = heap[parentIdx];
            heap[parentIdx] = heap[curIdx];
            heap[curIdx] = temp;
            // Move up to parent and repeat
            curIdx = parentIdx;
            parentIdx = (curIdx - 1) / 2;
        }
    }

    // Trickle-down 
    private void trickleDown(int curIdx) {
        int leftChildIdx = 2 * curIdx + 1;   // Calculate left child index
        int rightChildIdx = 2 * curIdx + 2;  // Calculate right child index

        // Continue trickling down while there is at least a left child
        while (leftChildIdx < count) {
            int largestIdx = leftChildIdx; // Start by assuming left child is larger

            // Check if right child exists and has a higher priority than left child
            if (rightChildIdx < count && heap[rightChildIdx].getPriority() > heap[leftChildIdx].getPriority()) {
                largestIdx = rightChildIdx; // Right child is larger
            }

            // If the current node has lower priority than the largest child, swap
            if (heap[curIdx].getPriority() < heap[largestIdx].getPriority()) {
                DSAHeapEntry temp = heap[curIdx];
                heap[curIdx] = heap[largestIdx];
                heap[largestIdx] = temp;

                // Move down to the child's position
                curIdx = largestIdx;
                leftChildIdx = 2 * curIdx + 1;
                rightChildIdx = 2 * curIdx + 2;
            } else {
                break;
            }
        }
    }

     // Heapify
     public static void heapify(DSAHeapEntry[] array, int numItems) {
        for (int ii = (numItems / 2) - 1; ii >= 0; ii--) {
            trickleDownStatic(array, ii, numItems);
        }
    }

    // Helper method for trickleDown during heapify and heapsort
    private static void trickleDownStatic(DSAHeapEntry[] array, int curIdx, int numItems) {
        int leftChildIdx = 2 * curIdx + 1;
        int rightChildIdx = 2 * curIdx + 2;

        while (leftChildIdx < numItems) {
            int largestIdx = leftChildIdx;

            if (rightChildIdx < numItems && array[rightChildIdx].getPriority() > array[leftChildIdx].getPriority()) {
                largestIdx = rightChildIdx;
            }

            if (array[curIdx].getPriority() < array[largestIdx].getPriority()) {
                DSAHeapEntry temp = array[curIdx];
                array[curIdx] = array[largestIdx];
                array[largestIdx] = temp;

                curIdx = largestIdx;
                leftChildIdx = 2 * curIdx + 1;
                rightChildIdx = 2 * curIdx + 2;
            } else {
                break;
            }
        }
    }

    // HeapSort implementation using the heapify and trickleDown
    public static void heapSort(DSAHeapEntry[] array) {
        int numItems = array.length;
        heapify(array, numItems);

        for (int ii = numItems - 1; ii > 0; ii--) {
            DSAHeapEntry temp = array[0];
            array[0] = array[ii];
            array[ii] = temp;

            trickleDownStatic(array, 0, ii); 
        }
    }
}

