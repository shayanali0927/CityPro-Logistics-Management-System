/*
 * DeliverySorter.java
 * Module 4 – Sorting Delivery Records
 * Author: Shayan Ali / 22715218
 * 
 * Description:
 * This class implements two custom sorting algorithms for DeliveryRecord objects.
 * It supports:
 *   - Merge Sort: A stable divide-and-conquer algorithm,
 *   - Quick Sort: A faster in-place sorting method.
 * 
 * Both sorting algorithms sort delivery records by estimated delivery time
 * using the getEstimatedTime() method in DeliveryRecord.
 *
 * This code was adapted from DSA Prac 9 and  modified to the assignment 
 */

public class DeliverySorter {
    // Merge Sort
    public static void mergeSort(DeliveryRecord[] arr) {
        mergeSortRec(arr, 0, arr.length - 1);
    }
     // Recursive method to perform merge sort
    private static void mergeSortRec(DeliveryRecord[] arr, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            int midIdx = (leftIdx + rightIdx) / 2;

            // Sort left half 
            mergeSortRec(arr, leftIdx, midIdx);

            // Sort right half
            mergeSortRec(arr, midIdx + 1, rightIdx);

             // Merge both sorted halves
            merge(arr, leftIdx, midIdx, rightIdx);
        }
    }

    // Merges two sorted parts of the array into one 
    private static void merge(DeliveryRecord[] arr, int leftIdx, int midIdx, int rightIdx) {
        int size = rightIdx - leftIdx + 1;
        DeliveryRecord[] temp = new DeliveryRecord[size]; //temp array for merge values

        int i = leftIdx; // Index for left half
        int j = midIdx + 1; // Index for right half
        int k = 0;  // Index for temp array

        // Compare and insert smallest estimated time into temp
        while (i <= midIdx && j <= rightIdx) {
            if (arr[i].getEstimatedTime() <= arr[j].getEstimatedTime()) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= midIdx) temp[k++] = arr[i++]; //adds any remaining items from the left half
        while (j <= rightIdx) temp[k++] = arr[j++]; //adds any remaining items from the right half

        // Copys the sorted values from temp back to original array
        for (int m = 0; m < size; m++) {
            arr[leftIdx + m] = temp[m];
        }
    }

    // Quick Sort
    public static void quickSort(DeliveryRecord[] arr) {
        quickSortRec(arr, 0, arr.length - 1);
    }
    // Recursive method to perform quick sort
    private static void quickSortRec(DeliveryRecord[] arr, int leftIdx, int rightIdx) {
        if (leftIdx < rightIdx) {
            //Partition the array and get the pivot index
            int pivotIdx = partition(arr, leftIdx, rightIdx);

            //Recursively sort the left and right parts of the pivot
            quickSortRec(arr, leftIdx, pivotIdx - 1);
            quickSortRec(arr, pivotIdx + 1, rightIdx);
        }
    }
    //Method to put pivot back in its position
    private static int partition(DeliveryRecord[] arr, int leftIdx, int rightIdx) {
        DeliveryRecord pivot = arr[rightIdx]; //Picks the last element as pivot
        int i = leftIdx - 1; //tracks the smaller element

        // loop through the array to find the smallest elements befoe the pivot
        for (int j = leftIdx; j < rightIdx; j++) {
            if (arr[j].getEstimatedTime() <= pivot.getEstimatedTime()) {
                i++;
                //Swapping
                DeliveryRecord temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Put the pivot into the correct position
        DeliveryRecord temp = arr[i + 1];
        arr[i + 1] = arr[rightIdx];
        arr[rightIdx] = temp;

        return i + 1; //return the index of the pivot
    }

}
