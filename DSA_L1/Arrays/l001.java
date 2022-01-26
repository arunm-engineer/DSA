/**
 * l001
 */

import java.util.*;
public class l001 {
    static Scanner sc = new Scanner(System.in);

    public static void display(int[] arr) {
        for (int i = 0;i < arr.length;i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }

    public static void input1(int[] arr) {
        int n = arr.length;
        for (int i = 0;i < n;i++) {
            arr[i] = sc.nextInt();
        }
    }

    public static int[] input2(int n) {
        int[] arr = new int[n];
        for (int i = 0;i < n;i++) {
            arr[i] = sc.nextInt();
        }
        return arr;
    }

    public static int maximum(int[] arr) {
        int max = -(int) 1e9; // Will not be using Integer.MIN_VALUE
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] > max) max = arr[i];
        }

        return max;
    }

    // public static int maximum(int[] arr) {
    //     if (arr.length == 0) return -(int) 1e9;

    //     int max = arr[0];
    //     for (int i = 0;i < arr.length;i++) {
    //         if (arr[i] > max) max = arr[i];
    //     }

    //     return max;
    // }

    public static int minimum(int[] arr) {
        int min = (int)1e9;
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] < min) min = arr[i];
        }

        return min;
    }

    // If found, return index, else -1
    public static int find(int[] arr, int data) {
        int found = -1;
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] == data) {
                found = i;
                break;
            }
        }

        return found;
    }

    public static int spanOfArray(int[] arr) {
        int min = minimum(arr);
        int max = maximum(arr);

        int span = max-min;
        return span;
    }

    public static void main(String[] args) {
        // int n = 5;

        // int[] arr1 = new int[n];
        // input1(arr1);
        // display(arr1);

        // int[] arr2 = input2(n);

        /*************************************************************************************/

        int[] arr3 = { 2, 5, -1, -5, 100, 1808, -100 };
        int max = maximum(arr3);
        int min = minimum(arr3);
        int found = find(arr3, 100);

        System.out.println(max + " " + min + " " + found);
    }
}