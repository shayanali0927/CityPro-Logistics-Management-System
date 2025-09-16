/*
 * DSAHashTable.java
 * Module 2 – Graph-Based Route Planning
 * Author: Shayan Ali / 22715218
 * Description:
 * This class implements a hash table using linear probing for collision handling.
 * It supports:
 *   - Inserting customer records (ID, name, address, priority, status),
 *   - Searching for a customer by ID,
 *   - Deleting a customer from the table,
 *   - Viewing the Hash table
 * 
 * This code was adapted from my DSAHashTable from Prac 7 and modified to the assignment 
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class DSAHashTable {
    private DSAHashEntry[] hashArray;
    private int count; //items stored
    private int capacity; //total size of the table


    // Constructor
    public DSAHashTable(int tableSize) {
        int actualSize = nextPrime(tableSize);
        hashArray = new DSAHashEntry[actualSize];
        this.capacity = actualSize;

        for (int i = 0; i < actualSize; i++) {
            hashArray[i] = new DSAHashEntry();
        }
        count = 0;
    }

    // Polynomial rolling hash function for string like "C01"
    private int hash(String key) {
        int hashVal = 0;
        for (int i = 0; i < key.length(); i++) {
            hashVal = (31 * hashVal + key.charAt(i)) % capacity;
        }
        return hashVal;
    }

    // Public put method
    public void put(String inKey, CustomerRec inValue) {
        int hashIdx = hash(inKey);
        int origIdx = hashIdx;

        boolean found = false;
        boolean giveUp = false;

        while (!found && !giveUp) {
            DSAHashEntry entry = hashArray[hashIdx];

            // If slot is empty or deleted, insert new entry
            if (entry.getState() == 0 || entry.getState() == -1) {
                hashArray[hashIdx] = new DSAHashEntry(inKey, inValue);
                found = true;
                count++;
            
            // If the key exists, update the value 
            } else if (entry.getKey().equals(inKey)) {
                entry.setValue(inValue); // Overwrite
                found = true;
            // Else try next index, linear probing
            } else {
                // Output the collisions
                System.out.println("Collision at index " + hashIdx + ", probing next...");
                hashIdx = (hashIdx + 1) % hashArray.length;

                // If looped back to the original index, table is full
                if (hashIdx == origIdx) {
                    giveUp = true;
                    throw new RuntimeException("Hash table full, cannot insert " + inKey);
                }
            }
        }
        
    }
    // Public get method for the customer record
    public CustomerRec get(String inKey) {
        int index = find(inKey);
        if (index == -1 || hashArray[index].getState() != 1) {
            throw new NoSuchElementException("Key not found: " + inKey);
        }
        return (CustomerRec) hashArray[index].getValue();
    }

    // Public hasKey method
    public boolean hasKey(String inKey) {
        return find(inKey) != -1;
    }

    // Public remove method
    public void remove(String inKey) {
        int index = find(inKey);
        if (index == -1) {
            throw new NoSuchElementException("Key not found: " + inKey);
        }
        hashArray[index].setState(-1); 
        count--;

    }

    // find method for linear probing
    private int find(String inKey) {
        int hashIdx = hash(inKey); // Get the starting index using the hash function
        int origIdx = hashIdx; // Remember from where we started the orginal index
        boolean giveUp = false; // to see when we haev searched everything

        while (!giveUp) { 
            DSAHashEntry entry = hashArray[hashIdx];

            // checks if the slot is empty or null then stops searching as key isnt in the table
            if (entry == null || entry.getState() == 0) {
                giveUp = true;

            // checks if the slot is is used or the key matches if it does returns the index
            } else if (entry.getState() == 1 && entry.getKey().equals(inKey)) {
                return hashIdx;

            // else will move to the next index     
            } else {
                hashIdx = (hashIdx + 1) % hashArray.length;
                if (hashIdx == origIdx) {
                    giveUp = true;
                }
            }
        }
        return -1;
    }


    // Find next prime
    private int nextPrime(int startVal) {
        if (startVal % 2 == 0) startVal++; //makes it odd if even
        while (!isPrime(startVal)) {
            startVal += 2;
        }
        return startVal;
    }
    // Checks to see if the number is prime
    private boolean isPrime(int val) {
        if (val <= 1) return false;
        for (int i = 3; i <= Math.sqrt(val); i += 2) {
            if (val % i == 0) return false;
        }
        return true;
    }

    // Loads 50 customers from CSV into the hash table
    public void loadFromCSV(String filename) {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        String line;
        int count = 0;

        try {
            fileStream = new FileInputStream(filename);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);

            line = bufRdr.readLine(); // Skip header
            line = bufRdr.readLine(); // First data line

            while (line != null && count < 50) {
                String[] fields = line.split(",");

                String id = fields[0];
                String name = fields[1];
                String address = fields[2];
                int priority = Integer.parseInt(fields[3]);
                String status = fields[4];

                CustomerRec c = new CustomerRec(id, name, address, priority, status);
                put(id, c);
                count++;

                line = bufRdr.readLine();
            }

            fileStream.close();
            System.out.println("Successfully loaded 50 customers from CSV.");
        } catch (IOException e) {
            System.out.println(" Error loading customer_record.csv: " + e.getMessage());
        }
    }

    // Displays the contents of the HashTable
    public void displayTable() {
        for (int i = 0; i < capacity; i++) {
            System.out.print("[" + i + "] ");
            DSAHashEntry entry = hashArray[i];
    
            if (entry == null || entry.getState() == 0) {
                System.out.println("Empty");
            } else if (entry.getState() == -1) {
                System.out.println("Deleted");
            } else {
                System.out.println(entry.getValue());
            }
        }
    }

    // returns the array to be accessed by any other class
    public DSAHashEntry[] getAllEntries() {
        return hashArray;
    }
    
}

 
