public class l001 {
    
    // LC 925
    // Two pointers logic => TC O(n+m) SC O(1)
    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0, n = name.length(), m = typed.length();
        while (i < n && j < m) {
            char ch1 = name.charAt(i), ch2 = typed.charAt(j);
            if (ch1 == ch2) { // Property 1 => Both equal
                i++;
                j++;
            }
            else if (j-1 >= 0 && ch2 == typed.charAt(j-1)) // Property 2 => Prev equal (repetition)
                j++;
            else // wrong char
                return false;
        }
        
        while (j < m) { // i alone exhausted
            char ch2 = typed.charAt(j);
            if (ch2 == typed.charAt(j-1)) // Property 2 => Prev equal (repetition)
                j++;
            else // "abc", "abccd"
                return false;
        }
        
        if (i < n) // j alone exhausted
            return false;
        
        return true;
    }

    /****************************************************************************************************/

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

    // LC 169
    // Moore's Voting Algorithm => TC O(n) SC O(1)
    public int majorityElement_01(int[] nums) {
        int i = 1, n = nums.length;
        
        int val = nums[0];
        int count = 1;
        
        while (i < n) {
            if (nums[i] == val) {
                count++;
            }
            else { // Distinct elem got
                if (count > 0) {
                    count--; // Pair distinct elems
                }
                else { // Create next valid candidate for majority elem
                    val = nums[i];
                    count = 1;
                }
            }
            i++;
        }
        
        return val; // Ques. says maj. always exist
        
        
//         If maj. might not exist then below steps [Try this on Pep portal]
        
//         int freq = 0;
//         for (int j = 0; j < n; j++) {
//             if (nums[j] == val)
//                 freq++;
//         }
        
//         if (freq > n/2)
//             return true; // Majority elem exist
//         else 
//             return false; // Majority elem doesn't exist
    }

    /****************************************************************************************************/

    // LC 229
    public List<Integer> majorityElement_02(int[] nums) {
        int n = nums.length;
        
        int val1 = nums[0];
        int count1 = 1;
        
        int val2 = -(int) 1e9; // assume -(int) 1e9 just for temp purpose, since count2 will handle initialization
        int count2 = 0; // we need to yet initialize, so 0
        
        for (int i = 1; i < n; i++) {
            if (nums[i] == val1)
                count1++;
            else if (nums[i] == val2)
                count2++;
            else { // distinct elem
                if (count1 == 0) { // val1 initialization
                    val1 = nums[i];
                    count1 = 1;
                }
                else if (count2 == 0) { // val2 initialization
                    val2 = nums[i];
                    count2 = 1;
                }
                else { // pair up distinct elems
                    count1--;
                    count2--;
                }
            }
        }
        
        // check for val1 & val2, freq > n/3
        int freq1 = 0, freq2 = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == val1)
                freq1++;
            else if (nums[i] == val2)
                freq2++;
        }

        List<Integer> ans = new ArrayList<>();
        if (freq1 > n/3)
            ans.add(val1);
        if (freq2 > n/3)
            ans.add(val2);
        
        return ans;
    }

    /****************************************************************************************************/

    // Majority Element General [Pepcoding Portal]
    public static ArrayList<Integer> majorityElement_03(int[] arr, int k) {
        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        
        // freq map for elems
        for (int i = 0; i < n; i++) {
            if (map.containsKey(arr[i])) {
                map.put(arr[i], map.get(arr[i]) + 1);
            }
            else {
                map.put(arr[i], 1);
            }
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        for (int val : map.keySet()) {
            int freq = map.get(val);
            if (freq > n/k)
                ans.add(val);
        }
        
        Collections.sort(ans); // This is just for output matching
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
        
        swap(num, dipIndex, ceilIndex);
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

    public static void swap(char[] num, int i, int j) {
        char temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    public static void reverse(char[] num, int left, int right) {
        while (left < right) {
            swap(num, left, right);
            left++;
            right--;
        }
    }

    /****************************************************************************************************/

    // LC 905
    public int[] sortArrayByParity(int[] nums) {
        int i = 0, j = 0;
        while (j < nums.length) {
            if (nums[j]%2 == 1) { // odd
                j++; // increase odd segment
            }
            else { // even
                swap_00(nums, i, j);
                i++;
                j++;
            }
        }
        
        return nums;
    }
    
    public static void swap_00(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /****************************************************************************************************/

    // LC 628
    public int maximumProduct(int[] nums) {
        int max1 = -(int) 1e9, max2 = -(int) 1e9, max3 = -(int) 1e9;
        int min1 = (int) 1e9, min2 = (int) 1e9; // why only 2 min, bcoz mins will make again -ve on xply
        
        for (int val : nums) {
            // For max
            if (val > max1) {
                max3 = max2;
                max2 = max1;
                max1 = val;
            }
            else if (val > max2) {
                max3 = max2;
                max2 = val;
            }
            else if (val > max3) {
                max3 = val;
            }
            
            // For min
            if (val < min1) {
                min2 = min1;
                min1 = val;
            }
            else if (val < min2) {
                min2 = val;
            }
        }
        
        int maxProduct = Math.max((max1 * max2 * max3), (min1 * min2 * max1));
        return maxProduct;
    }

    /****************************************************************************************************/

    // LC 769
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        
        int chunks = 0;
        int maxVal = -(int) 1e9;
        for (int idx = 0; idx < n; idx++) {
            maxVal = Math.max(maxVal, arr[idx]);
            
            if (idx == maxVal) // max impact range the maxVal can have is upto the idx, (0 <= val <= n-1)
                chunks++;
        }
        
        return chunks;
    }

    /****************************************************************************************************/

    // LC 768
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        
        // No need for seperate leftMax, since we can make & manage leftMax along traversal itself
        int[] rightMin = new int[n+1]; // why n+1, just to handle 
        rightMin[n] = (int) 1e9; // for last edge case to incr final chunk
        
        // Prepare rightMin
        for (int i = n-1; i >= 0; i--) {
            rightMin[i] = Math.min(rightMin[i+1], arr[i]);
        }
        
        int chunks = 0;
        int leftMax = -(int) 1e9;
        for (int idx = 0; idx < n; idx++) {
            leftMax = Math.max(leftMax, arr[idx]);
            
            if (leftMax <= rightMin[idx+1])
                chunks++;
        }
        
        return chunks;
    }

    /****************************************************************************************************/

    // LC 747
    public int dominantIndex(int[] nums) {
        if (nums.length == 1) // Edge case; one elem is obviously atleast twice than every other elem
            return 0;
        
        int largestIdx = -1;
        int max1 = -(int) 1e9, max2 = -(int) 1e9;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max1) {
                max2 = max1;
                max1 = nums[i];
                largestIdx = i;
            }
            else if (nums[i] > max2) {
                max2 = nums[i];
            }
        }
        
        return (max1 >= 2*max2) ? largestIdx : -1;
    }

    /****************************************************************************************************/

    // LC 345
    public String reverseVowels(String s) {
        char[] arr = s.toCharArray();
        
        int n = s.length();
        int i = 0, j = n-1;
        while (i < j) {
            while (i < j && !isVowel(arr[i]))
                i++;
            while (i < j && !isVowel(arr[j]))
                j--;
            
            swap(arr, i, j);
            i++;
            j--;
        }
        
        return String.valueOf(arr);
    }
    
    public static boolean isVowel(char ch) {
        String vowels = "AEIOUaeiou";
        return vowels.contains(ch + "");
    }
    
    public static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /****************************************************************************************************/

    // LC 795
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int n = nums.length;

        int i = 0, j = 0;
        int prevCount = 0; // indicates last subarrs count having max in-range
        int count = 0; // indicates total no. of subarrs having max in-range
        while (i < n) {
            if (nums[i] >= left && nums[i] <= right) { // max in-range
                prevCount = i-j+1;
                count += i-j+1;
            }
            else if (nums[i] < left) { // less than range,but has no impact on max of subarr, so consider
                count += prevCount; //combine nums[i] in prev subarrs in-range forming new unique subarrs
            }
            else { // greater than range, impacts max of any subarr formed which will include nums[i]
                prevCount = 0; // Break point, so reset
                j = i+1; // move j, since can't subarr from j, since max has been impacted
            }
            i++;
        }
        
        return count;
    }

    /****************************************************************************************************/

}
