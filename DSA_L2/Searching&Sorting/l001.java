import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class l001 {
    
    // https://practice.geeksforgeeks.org/problems/marks-of-pcm2529/1
    public void customSort (int phy[], int chem[], int math[], int N) {
        int n = phy.length;
        
        Marks[] marks = new Marks[n];
        
        // 1. Fill marks arr
        for (int i = 0; i < n; i++) {
            marks[i] = new Marks(phy[i], chem[i], math[i]);
        }
        
        // 2. Sort marks arr
        Arrays.sort(marks);
        
        // 3. Fill sorted marks in arrays
        for (int i = 0; i < n; i++) {
            phy[i] = marks[i].phy;
            chem[i] = marks[i].chem;
            math[i] = marks[i].math;
        }
    }
    
    public static class Marks implements Comparable<Marks> {
        int phy;
        int chem;
        int math;
        
        public Marks() {}
        
        public Marks(int phy, int chem, int math) {
            this.phy = phy;
            this.chem = chem;
            this.math = math;
        }
        
        public int compareTo(Marks other) {
            if (this.phy == other.phy && this.chem == other.chem)
                return this.math - other.math; // a-b
            else if (this.phy == other.phy)
                return other.chem - this.chem; // b-a
            else 
                return this.phy - other.phy;
        }
    }

    /****************************************************************************************************/

    // https://practice.geeksforgeeks.org/problems/union-of-two-sorted-arrays/1
    public static ArrayList<Integer> findUnion(int arr1[], int arr2[], int n, int m) {
        ArrayList<Integer> ans = new ArrayList<>();
        
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (arr1[i] == arr2[j]) {
                if (ans.size() == 0 || ans.get(ans.size()-1) != arr1[i]) 
                    ans.add(arr1[i]);
                i++;
                j++;
            }
            else if (arr1[i] < arr2[j]) { // arr1[i] is smaller
                if (ans.size() == 0 || ans.get(ans.size()-1) != arr1[i]) 
                    ans.add(arr1[i]);
                i++;
            }
            else { // arr2[j] is smaller
                if (ans.size() == 0 || ans.get(ans.size()-1) != arr2[j]) 
                    ans.add(arr2[j]);
                j++;
            }
        }
        
        while (i < n) {
            if (ans.size() == 0 || ans.get(ans.size()-1) != arr1[i]) 
                ans.add(arr1[i]);
            i++;
        }
        
        while (j < m) {
            if (ans.size() == 0 || ans.get(ans.size()-1) != arr2[j]) 
                ans.add(arr2[j]);
            j++;
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 74
    public boolean searchMatrix_01(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int i = 0, j = n*m-1;
        
        // Binary search => assume 2d array converted into 1d array
        while (i <= j) {
            int mid = (i+j)/2;
            int row = mid/m, col = mid%m;
            int midElem = matrix[row][col];
            
            if (midElem == target)
                return true;
            else if (midElem < target)
                i = mid+1;
            else 
                j = mid-1;
        }
        
        return false;
    }

    /****************************************************************************************************/
    
    // LC 240
    public boolean searchMatrix_02(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        
        // Two pointer
        int row = 0, col = m-1;
        while (row < n && col >= 0) {
            int self = matrix[row][col];
            if (self > target)
                col--;
            else if (self < target)
                row++;
            else 
                return true;
        }
        
        return false;
    }
    
    /****************************************************************************************************/

    // LC 724
    public int pivotIndex(int[] nums) {
        int totalSum = 0;
        for (int val : nums)
            totalSum += val;
        
        int leftSum = 0, rightSum = 0, pivotIdx = -1;
        for (int i = 0; i < nums.length; i++) {
            rightSum = totalSum - nums[i] - leftSum; // calc by formula (leftSum + rightSum = totalSum)
            
            if (leftSum == rightSum) {
                pivotIdx = i;
                break;
            }
            
            leftSum += nums[i]; // calc along traversal
        }
        
        return pivotIdx;
    }

    /****************************************************************************************************/

    // LC 658
    // Approach 1 TC O(nlogk + klogk) SC O(k)
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        PriorityQueue<GapPair> pq = new PriorityQueue<>(Collections.reverseOrder()); // Max Heap
        
        for (int elem : arr) {
            GapPair curPair = new GapPair(elem, Math.abs(x-elem));
            
            if (pq.size() < k) {
                pq.add(curPair);                
            }
            else if (pq.size() == k) {
                GapPair peekPair = pq.peek();
                if (curPair.gap < peekPair.gap) {
                    pq.remove(); // remove worst candidate to put new best candidate
                    pq.add(curPair);                    
                }
            }
        }
        
        List<Integer> list = new ArrayList<>();
        while (!pq.isEmpty()) {
            list.add(pq.remove().elem);
        }
        
        Collections.sort(list); // Asked in question to sort
        return list;
        
    }
    
    public static class GapPair implements Comparable<GapPair> {
        int elem;
        int gap;
    
        public GapPair() {}
        
        public GapPair(int elem, int gap) {
            this.elem = elem;
            this.gap = gap;
        }
        
        public int compareTo(GapPair other) {
            if (this.gap != other.gap)
                return this.gap - other.gap;
            else // equal gap
                return this.elem - other.elem;
        }
    }

    // Approach 2 Optimised TC O(logn + k + klogk), SC O(1)
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int n = arr.length;
        List<Integer> list = new ArrayList<>();
        
        // 1. Binary search to reach best closest position to x
        int low = 0, high = n-1, index = 0; // index will take to best possible gap closest to x
        while (low <= high) {
            int mid = (low+high)/2;
            index = Math.abs(arr[index]-x) > Math.abs(arr[mid]-x) ? mid : index; // Get closest gap idx to x
            
            if (arr[mid] == x) 
                break;
            else if (arr[mid] < x)
                low = mid + 1;
            else 
                high = mid - 1;
        }
        
        // 2. Two pointers to find the best k-1 closest elems to x, since one elem is found arr[mid]
        // k-1 closest elems would be found either in left or right region from best pt. we found above
        int left = index-1, right = index;
        while (left >= 0 && right < n && list.size() < k) {
            int leftElem = arr[left], rightElem = arr[right];
            int leftElemGap = Math.abs(leftElem - x), rightElemGap = Math.abs(rightElem - x);
            
            if (leftElemGap <= rightElemGap) {
                list.add(leftElem);
                left--;
            }
            else {
                list.add(rightElem);
                right++;
            }
        }
        
        // Edge case: Might be the size of arr is exhausted, but there are still few k-y elems remaining
        while (list.size() < k && left >= 0) {
            int leftElem = arr[left];
            list.add(leftElem);
            left--;
        }
        while (list.size() < k && right < n) {
            int rightElem = arr[right];
            list.add(rightElem);
            right++;
        }
        
        Collections.sort(list); // Asked in question to sort o/p
        return list;
    }

    // Approach 3 => Avoided the sorting complexity at last
    // TC O(logn + k)
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int n = arr.length;
        List<Integer> list = new ArrayList<>();
        
        // 1. Binary search to reach best closest position to x
        int low = 0, high = n-1;
        int minGapVal = (int) 1e9;
        int minGapIndex = -1; // to find closest index to x
        
        while (low <= high) {
            int mid = low + (high-low)/2;
            
            int currGapVal = Math.abs(arr[mid] - x);
            if (currGapVal < minGapVal) {
                minGapVal = currGapVal;
                minGapIndex = mid;
            }
            
            if (arr[mid] == x) 
                break;
            else if (arr[mid] < x)
                low = mid + 1;
            else 
                high = mid - 1;
        }
        
        // 2. Two pointers to find the best k-1 window of closest elems to x
        int left = minGapIndex-1, right = minGapIndex+1;
        int count = 1; // tracks k size, one elem incl so 1 size default
        while (left >= 0 && right < n && count < k) {
            int leftElem = arr[left], rightElem = arr[right];
            int leftElemGap = Math.abs(leftElem - x), rightElemGap = Math.abs(rightElem - x);
            
            if (leftElemGap <= rightElemGap) 
                left--;
            else 
                right++;
            
            count++;
        }
        
        // Edge case: Might be the size of arr is exhausted, but there are still few k-y elems remaining
        while (count < k && left >= 0) {
            int leftElem = arr[left];
            left--;
            count++;
        }
        while (count < k && right < n) {
            int rightElem = arr[right];
            right++;
            count++;
        }
        
        for (int i = left+1; i <= right-1; i++) // dump k closest elems (already in sorted form)
            list.add(arr[i]);
        
        return list;
    }

    /****************************************************************************************************/
}
