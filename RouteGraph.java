
/*
 * RouteGraph.java
 * Module 1 – Graph-Based Route Planning
 * Author: [Shayan Ali / 22715218
 * Description:
 * This class implements the core graph data structure using adjacency lists.
 * It supports:
 *   - Adding/removing hubs and road segments,
 *   - BFS for hoping,
 *   - DFS for cycle detection,
 *   - Dijkstra’s algorithm for shortest path estimation.
 * 
 * This code was adapted from my DSAGraph from Prac 6 and modified to the assignment 
 */

public class RouteGraph {
    private DSALinkedList[] adjList; //Holds the adjacency list
    private String[] labels;    // name of the hubs A,B,c
    private boolean[] visited; // Keeps track of visited hubs
    private int vertexCount; // counts how many hubs are added
    private int maxVertices; //max number of hubs allowed
    private int[] dist;          // Stores shortest path times from warehouse 
    

    // Constructor
    public RouteGraph(int n) {
        maxVertices = n;
        adjList = new DSALinkedList[n];
        labels = new String[n];
        visited = new boolean[n];
        vertexCount = 0;

        // Initialize each adjacency list slot 
        for (int i = 0; i < n; i++) {
            adjList[i] = new DSALinkedList();
        }
    }

    // Adds vertex (hub) to the graph
    public void addVertex(String label) {
        if (vertexCount < maxVertices) {
            boolean exists = false;
            for (int i = 0; i < vertexCount; i++) {
                if (labels[i].equals(label)) {
                    exists = true;
                    break;
                }
            }
            // If not added already, add the hub
            if (!exists) {
                labels[vertexCount] = label;
                vertexCount++;
            } else {
                System.out.println("Vertex with label '" + label + "' already exists.");
            }
        } else {
            System.out.println("Graph is full. Cannot add more vertices.");
        }
    }

    //Deletes a vertex (hub)
    public void deleteVertex(String label) {
        int index = getIndex(label);
        if (index != -1) {
            //Removes this hub from adjacency lists
            for (int i = 0; i < vertexCount; i++) {
                adjList[i].remove(label);
            }
            // Shift everything left to fill the gap
            for (int i = index; i < vertexCount - 1; i++) {
                labels[i] = labels[i + 1];
                adjList[i] = adjList[i + 1];
            }
            // Clear the last slot
            labels[vertexCount - 1] = null;
            adjList[vertexCount - 1] = new DSALinkedList();
            vertexCount--;
            System.out.println("Vertex '" + label + "' deleted.");
        } else {
            System.out.println("Vertex not found.");
        }
    }

    //Adds edges (travel time) between each node
    public void addEdge(String label1, String label2, int travelTime) {
        int index1 = getIndex(label1);
        int index2 = getIndex(label2);
    
        // Checks if both vertices exist in the grapgh or no
        if (index1 != -1 && index2 != -1) {
            // if theres no edge for label 1 it will add it
            if (!edgeExists(index1, label2))
                adjList[index1].insertLast(new DSAEdge(label2, travelTime)); // using DSAEdge to store the travel time
            // if theres no edge for label 2 it will add it
            if (!edgeExists(index2, label1))
                adjList[index2].insertLast(new DSAEdge(label1, travelTime));
        }
    }

    // checks to see if a specific edge already exists betwen a given vertex and destination label
    private boolean edgeExists(int index, String destLabel) {
        DSAListNode curr = adjList[index].getHead(); // starts at the head of the adjacency list
        while (curr != null) {
            DSAEdge edge = (DSAEdge) curr.getValue(); // Gets the valud of the current node and converts it in to DSAEdge
            if (edge.getDestination().equals(destLabel)) {
                return true; // If edge already exists
            }
            curr = curr.getNext(); //Moves to the next node
        }
        return false; // No same edge found 
    }

    public boolean hasVertex(String label) {
        return getIndex(label) != -1;
    }

    public int getVertexCount() {
        return vertexCount;
    }

     // Returns number of roads in the graph
    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < vertexCount; i++) {
            DSAListNode curr = adjList[i].getHead();  
            while (curr != null) {
                count++;
                curr = curr.getNext();
            }
        }
        return count / 2;
    }

    // Checks if two hubs are directly connected
    public boolean isAdjacent(String label1, String label2) {
        int index1 = getIndex(label1);
        return index1 != -1 && adjList[index1].find(label2);
    }
    // Returns the list of all hubs connected to the given hub
    public String[] getAdjacent(String label) {
        int index = getIndex(label);
        if (index != -1) {
            int count = 0;
            DSALinkedList neighbors = new DSALinkedList();
            DSAListNode curr = adjList[index].getHead();
            // First pass: count how many neighbors
            while (curr != null) {
                DSAEdge edge = (DSAEdge) curr.getValue();
                neighbors.insertLast(edge.getDestination());  // Save destination only
                curr = curr.getNext();
            }
            // Second pass: fill the array
            String[] result = new String[neighbors.getSize()];
            curr = neighbors.getHead();
            int i = 0;

            while (curr != null) {
                result[i++] = (String) curr.getValue();
                curr = curr.getNext();         
            }
    
            return result;
        }
        return new String[0];  // if no hub found, return empty
    }
        
    // Displays graph as list
    public void displayAsList() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print(labels[i] + ": ");
            adjList[i].traverseForward();
        }
    }

    private int getIndex(String label) {
        for (int i = 0; i < vertexCount; i++) {
            if (labels[i].equals(label)) {
                return i;
            }
        }
        return -1;
    }

    public void clearVisited() {
        for (int i = 0; i < vertexCount; i++) {
            visited[i] = false;
        }
    }

    public void depthFirstSearch(String sourceLabel) {
        DSAStack S = new DSAStack();
        DSAQueue T = new DSAQueue();

        clearVisited();

        int startIndex = getIndex(sourceLabel);      // Get the index of source
        if (startIndex == -1) {
            System.out.println("Source hub not found.");
            return;
        }

        visited[0] = true;
        S.push(labels[startIndex]);                  // Push starting label to stack
        T.enqueue(labels[startIndex]);               // Store in visit order

        while (!S.isEmpty()) {
            String v = (String) S.top();
            
            boolean found = false;

            String[] neighbors = getAdjacent(v);
            sortAlphabetically(neighbors);  
            
            for (int i = 0; i < neighbors.length; i++) {
                String w = neighbors[i];
                int wIndex = getIndex(w);
            
                if (!visited[wIndex]) {
                    visited[wIndex] = true;
                    S.push(w);
                    T.enqueue(w);
                    found = true;
                    break;
                }
            }

            if (!found) {
                S.pop();
            }
        }
        T.display();
    }

    public void breadthFirstSearch(String sourceLabel) {
        DSAQueue Q = new DSAQueue();
        DSAQueue T = new DSAQueue();
        int[] level = new int[maxVertices];

        for (int i = 0; i < maxVertices; i++) {
            level[i] = -1;                      // -1 means unvisited
        }

        clearVisited();
        int startIndex = getIndex(sourceLabel);  // Get index of source vertex
        if (startIndex == -1) {
            System.out.println("Source hub not found.");
            return;
        }

        visited[startIndex] = true;
        level[startIndex] = 0;  // start hub is level 0
        Q.enqueue(labels[startIndex]);               // Enqueue starting label
        T.enqueue(labels[startIndex]);               // Track visit order

        while (!Q.isEmpty()) {
            String v = (String) Q.dequeue();
            int vIndex = getIndex(v);

            String[] neighbors = getAdjacent(v);
            sortAlphabetically(neighbors);  
            for (int i = 0; i < neighbors.length; i++) {
                String w = neighbors[i];
                int wIndex = getIndex(w);
                if (!visited[wIndex]) {
                    visited[wIndex] = true;
                    Q.enqueue(w);
                    T.enqueue(w);
                    level[wIndex] = level[vIndex] + 1; 
                }
            }
        }

        T.display();

        System.out.println("Reachable delivery zones:");
        for (int i = 0; i < maxVertices; i++) {
            if (visited[i]) {
                if (level[i] == 0) {
                    System.out.println("Started at hub " + labels[i]);
                } else {
                    System.out.println("Reached hub " + labels[i] + " in " + level[i] + " hop(s)");
                }
            }
        }
    }
    //Implemented using Dijkstra’s Algorithm from Geek for geek and modified to be used in my code
    public void findShortestPaths(String label) {
        int numVertices = vertexCount;
    
        dist = new int[numVertices];              // Stores shortest travel times
        boolean[] visited = new boolean[numVertices];   // Tracks if a node is finalized
    
        // Initialize all distances to infinity and mark all unvisited
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
    
        int sourceIndex = getIndex(label);              // Find the index of the source hub
        if (sourceIndex == -1) {
            System.out.println("Source hub not found.");
            return;
        }
    
        dist[sourceIndex] = 0;                          // Distance from source to itself is 0
    
        // go through all vertices
        for (int count = 0; count < numVertices - 1; count++) {
            int u = findMinDistance(dist, visited);     // Find unvisited node with the smallest distance
            if (u == -1) {
                break; // No reachable unvisited nodes are left
            }                        
    
            visited[u] = true;                          // Mark this node as visited
    
            DSAListNode curr = adjList[u].getHead();
            while (curr != null) {
                DSAEdge edge = (DSAEdge) curr.getValue();
                int v = getIndex(edge.getDestination());
    
                if (!visited[v] && dist[u] != Integer.MAX_VALUE &&
                    dist[u] + edge.getTravelTime() < dist[v]) {
                    dist[v] = dist[u] + edge.getTravelTime();  // Update the distance if a better path is found
                }
    
                curr = curr.getNext();
            }
        }
    
        // Output the results
        for (int i = 0; i < numVertices; i++) {
            System.out.println(label + " -> " + labels[i] + ": " + (dist[i] == Integer.MAX_VALUE ? "Unreachable" : dist[i] + " mins"));
        }
    }

    private int findMinDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
    
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] <= min) {
                min = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Gets the shortest time of each hub and then gives it to heap (estimated time)
    public int getShortestTime(String hub) {
        int index = getIndex(hub);
    
        // Skip if hub not found, dist array not ready, or distance is 0 (A → A case)
        if (index == -1 || dist == null || dist[index] == 0) {
            return Integer.MAX_VALUE;
        }
    
        return dist[index];
    }
    
    

    public boolean hasCycle(String label) {
        clearVisited(); // Reset all visited flags of DFS before starting it 
        int startIndex = getIndex(label); //Finds the index of the starting hub
    
        if (startIndex == -1) {
            System.out.println("Source hub not found.");
            return false;
        }
    
        return detectCycle(label, null); // Start DFS with no parent
    }

    private boolean detectCycle(String current, String parent) {
        int currIndex = getIndex(current); //gets index of the current node
        visited[currIndex] = true; //used to mark any node as visited
    
        String[] neighbors = getAdjacent(current); //gets all the adjacent neighbors of the current node
    
        // Loops through each neighbour
        for (int i = 0; i < neighbors.length; i++) {
            String neighbor = neighbors[i]; // gets the neighbors label
            int neighborIndex = getIndex(neighbor); //converts the label to index
    
            if (!visited[neighborIndex]) {
                if (detectCycle(neighbor, current)) {
                    return true; // Found cycle
                }
              // If the neighbour is visited but its NOT the node through which we came through then its a back edge meaning a cycle  
            } else if (!neighbor.equals(parent)) {
                return true; // Found back edge → cycle
            }
        }
        
        return false; // No cycle found
    }
    
    // Sorts the hubs Alphabetically
    public static void sortAlphabetically(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            String temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}
