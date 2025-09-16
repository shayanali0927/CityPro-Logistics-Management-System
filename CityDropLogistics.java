import java.util.*;
public class CityDropLogistics {
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        LogisticsHandler handler = new LogisticsHandler();
        int choice = -1;

        do{
            try {
                System.out.println("\n====== CityDrop Logistics Test Menu ======");
                System.out.println("1. Graph-Based Route Planning");
                System.out.println("2. Hash-Based Customer Lookup");
                System.out.println("3. Heap Based Parcel Scheduling");
                System.out.println("4. Sorting Delivery Records");
                System.out.println("5. Testing Delivery Records");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1: {
                        handler.GraphHandler();
                        break;
                    }
                    case 2: {
                        handler.HashingHandler();
                        break; 
                    }
                    case 3: {
                        handler.HeapHandler(handler.getCustomerTable(), handler.getGraph());
                        break;
                    }
                    case 4: {
                        handler.SortHandler();
                        break;
                    }
                    case 5: {
                        testDeliveryRecords();
                        break;
                    }
                    case 0: {
                        System.out.println("Exiting program.");
                        break;
                    }
                    default: {
                        System.out.println("Invalid choice. Try again.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear scanner buffer
            }
        
        } while (choice != 0);
    }
    
    public static void testDeliveryRecords(){
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Testing Menu =====");

        System.out.print("Enter full file name (e.g., delivery_records.csv): ");
        String fileName = sc.next();

        // Load data separately for each sort
        DeliveryRecord[] recordsMerge = DeliveryRecordFileIO.loadFromCSV(fileName);
        DeliveryRecord[] recordsQuick = DeliveryRecordFileIO.loadFromCSV(fileName);

        // validation to check if the file is written right or not
        if (recordsMerge == null || recordsQuick == null) {
            System.out.println("Error loading file. Please check the file name.");
            return;
        }

         // Merge Sort
        long startMerge = System.nanoTime();
        DeliverySorter.mergeSort(recordsMerge);
        long endMerge = System.nanoTime();
        double mergeTime = (endMerge - startMerge) / 1_000_000.0;

        // Quick Sort
        long startQuick = System.nanoTime();
        DeliverySorter.quickSort(recordsQuick);
        long endQuick = System.nanoTime();
        double quickTime = (endQuick - startQuick) / 1_000_000.0;
    

        // Output both times
        System.out.printf("Merge Sort on %s took %.2f ms\n", fileName, mergeTime);
        System.out.printf("Quick Sort on %s took %.2f ms\n", fileName, quickTime);

        
    }
    
}

    
        

