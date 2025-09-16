/*Used DSAEdge from prac 6
Author: Shayan Ali / 22715218 */
public class DSAEdge {
    private String destination;
    private int travelTime;

    // Constructor for DSAEdge
    public DSAEdge(String dest, int time) {
        destination = dest;
        travelTime = time;
    }

    // Getters
    public String getDestination() {
        return destination;
    }

    public int getTravelTime() {
        return travelTime;
    }

    // Setter
    public String toString() {
        return destination + " (" + travelTime + " mins)";
    }
}
