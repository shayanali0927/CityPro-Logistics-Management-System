import java.util.NoSuchElementException;
import java.util.Scanner;

public class LogisticsHandler {
    private RouteGraph graph;
    private DSAHashTable customerTable;
    private DSAHeap heap;

    public LogisticsHandler() {
        this.graph = new RouteGraph(8);
        this.customerTable = new DSAHashTable(50);
        this.heap = new DSAHeap(50);
    }

    public RouteGraph getGraph() {
        return this.graph;
    }
    
    public DSAHashTable getCustomerTable() {
        return this.customerTable;
    }
    
    public DSAHeap getHeap() {
        return this.heap;
    }

    public void GraphHandler() {
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n========== Graph-Based Route Planning Optimization ==========");
            System.out.println("1. Insert Hub");
            System.out.println("2. Insert road segment");
            System.out.println("3. Insert Hubs and Road Segment Automatically"); // for demo testing automatically fills the graph with nodes and edges
            System.out.println("4. Display Adjacency List");
            System.out.println("5. Run Breath-First Search");
            System.out.println("6. Run Depth-First Search");
            System.out.println("7. Find Shortest Paths between Hubs");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Enter vertex label: ");
                    String v = sc.nextLine();
                    graph.addVertex(v);
                    break;
                }

                case 2: {
                    System.out.print("Enter the start vertex: ");
                    String start = sc.nextLine();
                    System.out.print("Enter the destination vertex: ");
                    String dest = sc.nextLine();
                    System.out.print("Enter the travel time: ");
                    int time = sc.nextInt();
                    sc.nextLine();
                    graph.addEdge(start, dest, time);
                    break;
                }
                case 3: {
                     // Add all 8 nodes 
                    graph.addVertex("A");
                    graph.addVertex("B");
                    graph.addVertex("C");
                    graph.addVertex("D");
                    graph.addVertex("E");
                    graph.addVertex("F");
                    graph.addVertex("G");
                    graph.addVertex("H"); //disconnected

                    // 12 edges with weights
                    graph.addEdge("A", "B", 5);  
                    graph.addEdge("B", "C", 4);  
                    graph.addEdge("C", "A", 6); // Cycle: A → B → C → A 
                    graph.addEdge("A", "D", 7);
                    graph.addEdge("C", "D", 3);
                    graph.addEdge("D", "E", 2);
                    graph.addEdge("D", "F", 5);
                    graph.addEdge("F", "G", 4);
                    graph.addEdge("E", "G", 6);
                    graph.addEdge("B", "E", 3);
                    graph.addEdge("A", "F", 8);
                    graph.addEdge("G", "B", 5);  

                    System.out.println("Graph loaded with 8 nodes and 12 edges.");
                    break;
                }

                case 4: {
                    System.out.println("\n--- Adjacency List of Hubs ---");
                    graph.displayAsList();
                    break;
                }

                case 5: {
                    System.out.println("\n--- Breath-First Search ---");
                    graph.breadthFirstSearch("A"); // hardcoded, always starts from A
                    break;
                }

                case 6: {
                    System.out.println("\n--- Depth-First Search ---");
                    graph.depthFirstSearch("A");
                    if (graph.hasCycle("A")) {
                        System.out.println("Cycle detected! This may cause delivery delays.");
                    } else {
                        System.out.println("No cycles found. Delivery routes are efficient.");
                    }
                    break;
                }

                case 7: {
                    System.out.println("\n--- Shortest Paths ---");
                    graph.findShortestPaths("A");
                    break;
                }

                case 0: {
                    System.out.println("Returning to Main Menu...");
                    break;
                }

                default: {
                    System.out.println("Invalid choice.");
                }
            }

        } while (choice != 0);
    }

    public void HashingHandler() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n========== Hash-Based Customer Lookup ==========");
            System.out.println("1. Insert New Customer (Manual)");
            System.out.println("2. Insert Customers from File"); 
            System.out.println("3. Search Customer by ID");
            System.out.println("4. Delete Customer by ID");
            System.out.println("5. Display Hash Table");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1: {
                    System.out.print("Enter Customer ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter Priority (1 - 5): ");
                    int priority = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Delivery Status (Delivered/In Transit/Delayed): ");
                    String status = sc.nextLine();
    
                    CustomerRec customer = new CustomerRec(id, name, address, priority, status);
                    customerTable.put(id, customer);
                    System.out.println("Customer added successfully.");
                    break;
                }
    
                case 2: {
                    customerTable.loadFromCSV("customer_record.csv");
                    break;
                }
    
                case 3: {
                    System.out.print("Enter Customer ID to search: ");
                    String idKey = sc.nextLine();
                    try {
                        CustomerRec found = customerTable.get(idKey);
                        System.out.println("Found: " + found);
                    } catch (NoSuchElementException e) {
                        System.out.println("Customer not found.");
                    }
                    break;
                }
    
                case 4: {
                    System.out.print("Enter Customer ID to delete: ");
                    String id = sc.nextLine();
                    customerTable.remove(id);
                    System.out.println("Customer deleted.");
                    break;
                }
    
                case 5:
                    customerTable.displayTable();
                    break;
    
                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
    
                default:
                    System.out.println("Invalid choice.");
            }
    
        } while (choice != 0);
    }
    public void HeapHandler(DSAHashTable hashTable, RouteGraph graph){
        Scanner sc = new Scanner(System.in);
        int choice ;
    
        String[] hubs = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int customerIndex = 0;

        do { 
            System.out.println("\n========== Heap Based Parcel Scheduling ==========");
            System.out.println("1. Schedule all deliveries into heap");
            System.out.println("2. View current heap state");
            System.out.println("3. Dispatch next highest-priority parcel");
            System.out.println("4. View heap state after dispatch");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: {
                    System.out.println("\nScheduling deliveries into heap...");
                
                    DSAHashEntry[] entries = hashTable.getAllEntries(); //get all the entries from the hash table

                
                    for (int i = 0; i < entries.length; i++) {
                        DSAHashEntry entry = entries[i];
                
                        if (entry != null && entry.getState() == 1) { //checks if the slot is used
                            CustomerRec customer = entry.getValue(); //gets the customer record
                
                            if (customer.getDeliveryStatus().equals("In Transit")) { //checks to see if the customer records DeliveryStatus = "In Transit"
                                String custId = customer.getCustomerID(); //gets CustomerID
                                int priorityLevel = customer.getPriorityLevel(); //Gets priority level

                                //randomly assigns a hub to each customer using index 
                                String assignedHub = hubs[customerIndex % hubs.length];
                                customerIndex++;

                                // gets the estimated time from the routeGraph module
                                int estimatedTime = graph.getShortestTime(assignedHub);
                                if (estimatedTime == Integer.MAX_VALUE) { //if no valid path for the hub it will skip
                                    System.out.println("No path to " + assignedHub + " for " + custId);
                                } else {
                                    //Calculates the priority 
                                    double priority = (6 - priorityLevel) + (1000.0 / estimatedTime);
                
                                    // Create a string that holds customer ID and other info for storage
                                    String entryData = custId + " (Priority: " + priority + ")";
                                    heap.add(priority, entryData);// adds the priority and entry data into the heap
                
                                    System.out.println("Scheduled: " + custId + " -> " + assignedHub + ", Priority Level: " + priorityLevel + ", Time: " + estimatedTime + " mins" + ", Priority: (6 - " + priorityLevel + ") + (1000 / " + estimatedTime + ") = " + priority);
                                }
                            }
                        }
                    }
                    System.out.println("Scheduling complete.");
                    break;
                }
                case 2:{
                    System.out.println("Current Heap State: ");
                    heap.printHeap();
                    break;
                }
                case 3: {
                    System.out.println("\nDispatching highest-priority parcel");
                    Object dispatched = heap.dispatchNextParcel();
                    System.out.println("Dispatched: " + dispatched);
                    break;
                }
                case 4: {
                    System.out.println("\nCurrent Heap State (after dispatch):");
                    heap.printHeap();  
                    break;
                }
                case 0:{
                    System.out.println("Returning to Main Menu...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice.");
                }
                
            }
        } while (choice !=0);
    }
    public void SortHandler(){
        Scanner sc = new Scanner(System.in);
        int choice;

        do { 
            System.out.println("========== Sorting Delivery Records ==========");
            System.out.println("1. Run Merge Sort for 100 inputs");
            System.out.println("2. Run Merge Sort for 500 inputs");
            System.out.println("3. Run Merge Sort for 1000 inputs");
            System.out.println("4. Run Quick Sort for 100 inputs");
            System.out.println("5. Run Quick Sort for 500 inputs");
            System.out.println("6. Run Quick Sort for 1000 inputs");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch(choice){
                case 1: {
                    // Load from delivery_records.csv
                    DeliveryRecord[] deliveryDetails = DeliveryRecordFileIO.loadFromCSV("delivery_records_100_random.csv");
                
                    // Create separate copies for each sort
                    DeliveryRecord[] mergeCopy = new DeliveryRecord[deliveryDetails.length];
                    
                
                    for (int i = 0; i < deliveryDetails.length; i++) {
                        mergeCopy[i] = deliveryDetails[i];
                    }
                    
                    // Sort using merge sort
                    DeliverySorter.mergeSort(mergeCopy);

                    // Export sorted data to file
                    DeliveryRecordFileIO.exportSortedToCSV("sorted_100_merge.csv", mergeCopy, mergeCopy.length);
                    System.out.println("Sorted output written to sorted_100_merge.csv");
                    break;
                }
                case 2: {
                    // Load from delivery_records.csv
                    DeliveryRecord[] deliveryDetails = DeliveryRecordFileIO.loadFromCSV("delivery_records_500_random.csv");
                
                    // Create separate copies for each sort
                    DeliveryRecord[] mergeCopy = new DeliveryRecord[deliveryDetails.length];
                     
                 
                    for (int i = 0; i < deliveryDetails.length; i++) {
                        mergeCopy[i] = deliveryDetails[i];
                    }
                     
                    // Sort using merge sort
                    DeliverySorter.mergeSort(mergeCopy);
 
                    // Export sorted data to file
                    DeliveryRecordFileIO.exportSortedToCSV("sorted_500_merge.csv", mergeCopy, mergeCopy.length);
                    System.out.println("Sorted output written to sorted_500_merge.csv");
                    break;
                    
                }
                case 3: {
                    // Load from delivery_records.csv
                    DeliveryRecord[] deliveryDetails = DeliveryRecordFileIO.loadFromCSV("delivery_records_1000_random.csv");
                
                    // Create separate copies for each sort
                    DeliveryRecord[] mergeCopy = new DeliveryRecord[deliveryDetails.length];
                     
                 
                    for (int i = 0; i < deliveryDetails.length; i++) {
                        mergeCopy[i] = deliveryDetails[i];
                    }
                     
                    // Sort using merge sort
                    DeliverySorter.mergeSort(mergeCopy);
 
                    // Export sorted data to file
                    DeliveryRecordFileIO.exportSortedToCSV("sorted_1000_merge.csv", mergeCopy, mergeCopy.length);
                    System.out.println("Sorted output written to sorted_1000_merge.csv");
                    break;
                }
                case 4: {
                    // Load from delivery_records.csv
                    DeliveryRecord[] deliveryDetails = DeliveryRecordFileIO.loadFromCSV("delivery_records_100_random.csv");

                    DeliveryRecord[] quickCopy = new DeliveryRecord[deliveryDetails.length];

                    for (int i = 0; i < deliveryDetails.length; i++) {
                        quickCopy[i] = deliveryDetails[i];
                    }

                    // Sort using merge sort
                    DeliverySorter.quickSort(quickCopy);

                    // Export sorted data to file
                    DeliveryRecordFileIO.exportSortedToCSV("sorted_100_quick.csv", quickCopy, quickCopy.length);
                    System.out.println("Sorted output written to sorted_100_quick.csv");
                    break;
                }
                case 5: {
                    // Load from delivery_records.csv
                    DeliveryRecord[] deliveryDetails = DeliveryRecordFileIO.loadFromCSV("delivery_records_500_random.csv");

                    DeliveryRecord[] quickCopy = new DeliveryRecord[deliveryDetails.length];

                    for (int i = 0; i < deliveryDetails.length; i++) {
                        quickCopy[i] = deliveryDetails[i];
                    }

                    // Sort using merge sort
                    DeliverySorter.quickSort(quickCopy);

                    // Export sorted data to file
                    DeliveryRecordFileIO.exportSortedToCSV("sorted_500_quick.csv", quickCopy, quickCopy.length);
                    System.out.println("Sorted output written to sorted_500_quick.csv");
                    break;
                }
                case 6: {
                    // Load from delivery_records.csv
                    DeliveryRecord[] deliveryDetails = DeliveryRecordFileIO.loadFromCSV("delivery_records_1000_random.csv");

                    DeliveryRecord[] quickCopy = new DeliveryRecord[deliveryDetails.length];

                    for (int i = 0; i < deliveryDetails.length; i++) {
                        quickCopy[i] = deliveryDetails[i];
                    }

                    // Sort using merge sort
                    DeliverySorter.quickSort(quickCopy);

                    // Export sorted data to file
                    DeliveryRecordFileIO.exportSortedToCSV("sorted_1000_quick.csv", quickCopy, quickCopy.length);
                    System.out.println("Sorted output written to sorted_1000_quick.csv");
                    break;
                }
                case 0: {
                    System.out.println("Returning to Main Menu...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice.");
                }
            }
        } while (choice != 0);


    }
    
}
