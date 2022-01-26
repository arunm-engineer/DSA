public class l001 {

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void sort01(int[] arr) {
        int p = -1;
        int i = 0;

        // Here i is the iterator
        while (i < arr.length) {
            if (arr[i] == 0) {
                swap(arr, ++p, i);
            }
            i++;
        }
    }

    public static void sort012(int[] arr) {
        int i = -1;
        int j = 0;
        int k = arr.length - 1;

        // Here j is the iterator
        while (j <= k) {
            if (arr[j] == 0)
                swap(arr, ++i, j++);
            else if (arr[j] == 1)
                j++;
            else if (arr[j] == 2)
                swap(arr, j, k--);
        }
    }

    public static void main(String[] args) {

    }
}
