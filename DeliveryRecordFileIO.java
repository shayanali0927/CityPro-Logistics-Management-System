import java.io.*;

public class DeliveryRecordFileIO {

    public static DeliveryRecord[] loadFromCSV(String fileName) {
        FileInputStream fileStream = null;
        BufferedReader bufRdr = null;
        int lineCount = 0;
    
        try {
          
            fileStream = new FileInputStream(fileName);
            bufRdr = new BufferedReader(new InputStreamReader(fileStream));
            while (bufRdr.readLine() != null) {
                lineCount++;
            }
            fileStream.close();
    
            
            DeliveryRecord[] records = new DeliveryRecord[lineCount - 1];
    
           
            fileStream = new FileInputStream(fileName);
            bufRdr = new BufferedReader(new InputStreamReader(fileStream));
            String line = bufRdr.readLine(); // Skip header
            int index = 0;
    
            while ((line = bufRdr.readLine()) != null) {
                String[] fields = line.split(",");
                String customerID = fields[0];
                String customerName = fields[1];
                int estimatedTime = Integer.parseInt(fields[2]);

                records[index] = new DeliveryRecord(customerID, customerName, estimatedTime);

                index++;
            }
    
            fileStream.close();
            return records;
    
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
    

    public static void exportSortedToCSV(String outputFileName, DeliveryRecord[] sortedRecords, int recordCount) {
        FileOutputStream fileStrm = null;
        PrintWriter pw;

        try {
            fileStrm = new FileOutputStream(outputFileName);
            pw = new PrintWriter(fileStrm);

            
            pw.println("CustomerID,,CustomerName,EstimatedTime");

            for (int i = 0; i < recordCount; i++) {
                DeliveryRecord record = sortedRecords[i];
                pw.println(record.getCustomerID() + "," + record.getCustomerName() + "," + record.getEstimatedTime());
            }

            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
