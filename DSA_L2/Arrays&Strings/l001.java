import java.util.*;

public class l001 {

    // LC 11
    // TC O(n) SC O(1)
    public int maxArea(int[] height) {
        int i = 0, j = height.length-1;
        int maxWater = -(int) 1e9;
        
        while (i < j) {
            int l = j - i; // len
            int h = Math.min(height[i], height[j]); // since min decides how much it can store ht. wise
            int water = l * h; // curr area of water stored
            maxWater = Math.max(maxWater, water);
            
            if (height[i] < height[j]) { // neglect min & make a move to make maxWater
                i++;
            }
            else {
                j--;                
            }
        }
        
        return maxWater;
    }

    /****************************************************************************************************/

    // LC 977
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
            
        int i = 0, j = n-1;
        for (int k = n-1; k >= 0; k--) {
            int val1 = nums[i]*nums[i];
            int val2 = nums[j]*nums[j];
            
            if (val1 > val2) {
                ans[k] = val1;
                i++;
            }
            else {
                ans[k] = val2;
                j--;
            }
        }
        
        return ans;
    }

    /****************************************************************************************************/
    
    // LC 556
    public int nextGreaterElement(int n) {
        if (n < 10) // Bcoz already in max possibility form
            return -1;

        String str = nextGreaterElement_("" + n);
        
        long ans = Long.parseLong(str);
        if (ans <= Integer.MAX_VALUE) // With in integer range, Leetcode check
            return (int) ans;
        else 
            return -1;
    }

    public static String nextGreaterElement_(String n) {
        char[] num = n.toCharArray();
        int dipIndex = findDipIndex(num);
        if (dipIndex == -1)
            return "" + -1; // Cannot form next greater, since already in maxPossibility
        
        int ceilIndex = findCeilIndex(num, num[dipIndex], dipIndex+1, num.length-1);
        
        swap_00(num, dipIndex, ceilIndex);
        reverse(num, dipIndex+1, num.length-1);

        return String.valueOf(num);
    }

    public static int findDipIndex(char[] num) {
        int index = -1;

        int n = num.length;
        for (int i = n-1; i > 0; i--) {
            if (num[i-1] < num[i]) {
                index = i-1;
                break;
            }
        }

        return index;
    }

    public static int findCeilIndex(char[] num, int dipVal, int left, int right) {
        int ceilIndex = -1; // ceil => Just greater than dipVal, also ceil index will exist for sure
        // LSD to MSD first ceil
        for (int i = left; i <= right; i++) {
            if (num[i] > dipVal) 
                ceilIndex = i;
        }

        return ceilIndex;
    }

    public static void swap_00(char[] num, int i, int j) {
        char temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    public static void reverse(char[] num, int left, int right) {
        while (left < right) {
            swap_00(num, left, right);
            left++;
            right--;
        }
    }

    /****************************************************************************************************/

    // LC 169
    // Moore's Voting Algorithm => TC O(n) SC O(1)
    public int majorityElement(int[] nums) {
        int n = nums.length;
        Integer majElem = null;
        int count = 0, index = 0;
        
        while (index < n) {
            if (count == 0) { // means a new set is to be processed
                majElem = nums[index];
                count = 1;
            }
            else if (nums[index] == majElem) {
                count++;
            }
            else {
                count--;
            }
            
            index++;
        }
        
        return majElem; // just bcoz ques says maj Elem will exist
        
        // if majElem doesn't exist
        // int freq = 0;
        // for (int i = 0; i < n; i++) {
        //     if (nums[i] == majElem)
        //         freq++;
        // }

        // return (freq > n/2) ? majElem : Integer.MIN_VALUE;
        
    }

    /****************************************************************************************************/

    // LC 769 (July 18)
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length, max = -(int) 1e9;
        
        int chunks = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, arr[i]);
            
            if (max == i) { // all elems are from 0 to (n-1), so at max the value can only fluctuate upto it's index
                chunks++;
            }
        }
        
        return chunks;
    }

    /****************************************************************************************************/

    // LC 768 (July 18)
    public int maxChunksToSorted_(int[] arr) {
        int n = arr.length;
        int rightMin[] = new int[n]; // stores rightKaMin from i to n-1
        
        for (int i = n-1; i >= 0; i--) {
            if (i == n-1) {
                rightMin[i] = arr[n-1];
                continue;
            }
            
            rightMin[i] = Math.min(rightMin[i+1], arr[i]);
        }
        
        int chunks = 0, leftMax = 0; // Basically maxSoFar
        for (int i = 0; i < n-1; i++) {
            leftMax = Math.max(leftMax, arr[i]);
            
            // leftSideKaMax < rightSideKaMin (means ek chunk mil gaya)
            if (leftMax <= rightMin[i+1]) {
                chunks++;
            }
        }
        
        return chunks+1;
    }

    /****************************************************************************************************/

    // LC 795 (July 18)
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int si = -1, ei = -1;
        
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) { // in range
                ei = i;
            }
            else if (nums[i] > right) {
                si = ei = i;
            }
            else if (nums[i] < left) {
                // do nothing
            }
            
            res += (ei - si);
        }
        
        return res;
    }

    /****************************************************************************************************/

    // LC 925 (July 18)
    public boolean isLongPressedName(String name, String typed) {
        String s1 = name, s2 = typed;
        int i = 0, j = 0, n = s1.length(), m = s2.length();
        
        while (j < m) {
            if (i < n && s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            }
            else if (j > 0 && s2.charAt(j) == s2.charAt(j-1)) { // currChar == prevChar
                j++;
            }
            else {
                return false; // aleex, alex
            }
        }
        
        return i == n; // alexa, aalex
    }

    /****************************************************************************************************/

    // LC 747 (July 18)
    public int dominantIndex(int[] nums) {
        // intuition: if max is > than 2*secondMax, it is enough to confirm is twice than others for sure
        int max = -1, secondMax = -1; // stores indexes of max
        
        for (int i = 0; i < nums.length; i++) {
            if (max == -1 || nums[i] > nums[max]) {
                secondMax = max;
                max = i;
            }
            else if (secondMax == -1 || nums[i] > nums[secondMax]) {
                secondMax = i;
            }
        }
        
        return nums[max] >= 2*nums[secondMax] ? max : -1;
    }

    /****************************************************************************************************/

    // LC 905 (July 20)
    // Segregate regions (like 01, 012 regions) - (0 to j-1 -> even), (j to i-1 -> odd), (i to n -> unknown)
    public int[] sortArrayByParity(int[] nums) {
        int i = 0, j = 0;
        while (i < nums.length) {
            if (nums[i] % 2 == 1) { // odd - just move
                i++;
            }
            else { // even - swap
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j++;
            }
        }
        
        return nums;
    }

    /****************************************************************************************************/

    // LC 345 (July 20)
    public String reverseVowels(String s) {
        int i = 0, j = s.length()-1;
        String check = "aeiouAEIOU";
        StringBuilder sb = new StringBuilder(s);

        while (i < j) {
            // findd vowel chars to swap
            while (i < j && check.indexOf(s.charAt(i)) == -1) {
                i++;
            }
            while (i < j && check.indexOf(s.charAt(j)) == -1) {
                j--;
            }
            
            // swap
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
                
            i++;
            j--;
        }
        
        return sb.toString();
    }

    /****************************************************************************************************/

    // NOTE:
    // median -> middle elem present in an sorted arr
    // avg -> middle value after summation of all elems of arr (totalSum/totalElems)
    // avg val elem may or may not be present in arr

    // LC 462 (July 20)
    // Approach 1 - Using sort TC O(NlogN)
    // Insightful mathematical concept
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        
        int moves = 0, median = nums[n/2];
        
        for (int i = 0; i < n; i++) 
            moves += Math.abs(nums[i] - median);
        
        return moves;
    }

    // Approach 2 - Using Quick Select TC O(N) avg case, TC O(N^2) worst case
    public int minMoves2_(int[] nums) {
        int n = nums.length;
        
        // Quick select ot find median elem in an unsorted arr, TC O(N) avg case, O(N^2) worst case
        int median = quickSelect(nums, 0, n-1, n/2);
        
        int moves = 0;
        for (int i = 0; i < n; i++)
            moves += Math.abs(nums[i] - median);
        
        return moves;
    }
    
    private int quickSelect(int[] nums, int lo, int hi, int medianIdx) {
        int pivotIdx = partition(nums, lo, hi);
        if (pivotIdx < medianIdx)
            return quickSelect(nums, pivotIdx+1, hi, medianIdx);
        else if (pivotIdx > medianIdx)
            return quickSelect(nums, lo, pivotIdx-1, medianIdx);
        else 
            return nums[pivotIdx]; // found medianElem
    }
    
    // segregate or partition regions (like sort 01, 012 regions problems)
    private int partition(int[] nums, int lo, int hi) {
        int pivotElem = nums[hi];
        int i = 0, j = 0;
        while (i <= hi) {
            if (nums[i] > pivotElem) {
                i++;
            }
            else {
                // swap
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j++;
            }
        }
        
        int pivotIdx = j-1;
        return pivotIdx;
    }

    /****************************************************************************************************/

    // LC 238 (July 20)
    // TC O(N)
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
            
        int[] suffixProduct = new int[n+1]; // stores product from right side
        for (int i = n; i >= 0; i--) {
            if (i == n) {
                suffixProduct[i] = 1;
                continue;
            }
            
            suffixProduct[i] = nums[i] * suffixProduct[i+1];
        }
        
        int prefixProduct = 1; // stores product from left side
        for (int i = 0; i < n; i++) {
            ans[i] = prefixProduct * suffixProduct[i+1];
            prefixProduct *= nums[i];
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 670 (July 20)
    // TC O(N)
    public int maximumSwap(int num) {
        char[] nums = (num + "").toCharArray();
        int n = nums.length;
        
        int[] rightMax = new int[n]; // stores indexes of maxValue from right side
        for (int i = n-1; i >= 0; i--) {
            int digit1 = nums[i] - '0';
            
            if (i == n-1) {
                rightMax[i] = i;
                continue;
            }

            int j = rightMax[i+1], digit2 = nums[j] - '0';
            
            rightMax[i] = (digit1 > digit2) ? i : j;
        }
        
        for (int i = 0; i < n-1; i++) {
            int digit1 = nums[i] - '0';
            int j = rightMax[i+1], digit2 = nums[j] - '0';
            
            if (digit1 < digit2) {
                // swap
                char temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                break;
            }
        }
        
        return Integer.parseInt(String.valueOf(nums));
    }

    /****************************************************************************************************/

    // LC 849 (July 20) Ad-hoc problem
    // Testcase - 00010000010000100
    // edge cases for first & last occurence of 1, we need to calculate gap by trick
    public int maxDistToClosest(int[] seats) {
        int n = seats.length;
        
        int j = -1; // prevIdx for 1
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (seats[i] == 1) {
                int val = 0;
                
                if (j == -1)
                    val = i; // first occurence 1, gap=idx
                else 
                    val = (i-j)/2; // why (gap/2)? - since only best spot to maximize distance
                
                ans = Math.max(ans, val);
                j = i;
            }
        }
        
        // gap for last occurence of 1
        ans = Math.max(ans, (n-1) - j); // assume last spot as 1 to calc gap
        
        return ans;
    }

    /****************************************************************************************************/

    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/arrays-and-strings/range_addition/ojquestion
    // TC O(m + n) => m = q.len
    public int[] rangeAddition(int[][] queries, int n) {
        int[] ans = new int[n];
        
        for (int[] q : queries) {
            int st = q[0], end = q[1], val = q[2];
            ans[st] += val; // create impact of +val from ith index
            if (end+1 < n)
                ans[end+1] += -val; // after j+1 index need to nullify impact of val by -val
        }

        // find prefix sum which will calc by creating and nullifying impact on ranges
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += ans[i];
            ans[i] = sum;
        }

        return ans;        
    }

    /****************************************************************************************************/

    // LC 763
    // Idea of Max chunks to sort an array 2 problem
    public List<Integer> partitionLabels(String s) {
        int n = s.length();
        int[] last = new int[26]; // stores last occurence idx of a char
        Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            int ch = s.charAt(i)-'a';
            last[ch] = Math.max(i, last[ch]);
        }
        
        List<Integer> ans = new ArrayList<>();
        int max = -1; // max range for last occurence of a char
        int prev = -1; // just to track gap i.e. len of chunk
        for (int i = 0; i < n; i++) {
            int ch = s.charAt(i)-'a';
            max = Math.max(max, last[ch]);
            if (i == max) {
                ans.add(i-prev); // len of chunk
                prev = i;
            }
        }
        
        return ans;
    }

    /****************************************************************************************************/

}
