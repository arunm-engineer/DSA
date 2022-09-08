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
    // Need to focus on impact range a char can have
    // TC O(N) SC constant space
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

    // LC 915
    // Idea of Max chunks to sort an array 2 problem
    // TC O(N) SC O(N)
    public int partitionDisjoint(int[] nums) {
        int n = nums.length;
        int[] right = new int[n]; // stores min from right
        for (int i = n-1; i >= 0; i--) {
            if (i == n-1) {
                right[i] = nums[i];
                continue;
            }
            
            right[i] = Math.min(nums[i], right[i+1]);
        }
        
        int left = nums[0]; // stores max from left
        for (int i = 0; i < n; i++) {
            left = Math.max(left, nums[i]);
            if (i+1 < n && left <= right[i+1]) {
                return i+1;
            }
        }
        
        return -1;
    }

    /****************************************************************************************************/

    // LC 1329
    // TC O(mnlog(m))
    // Why logm, at max PQ can hold min(m,n) elems i.e. total elems in a diagonal
    public int[][] diagonalSort(int[][] mat) {
        HashMap<Integer, PriorityQueue<Integer>> map = new HashMap<>(); // <gap, diaElemsOfGap>
        // Why key is gap, since gap is common & same for the whole diagonal
        
        int n = mat.length, m = mat[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int gap = i - j;
                map.putIfAbsent(gap, new PriorityQueue<>());
                
                map.get(gap).add(mat[i][j]);
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int gap = i - j;
                mat[i][j] = map.get(gap).remove();
            }`
        }
        
        return mat;
    }

    /****************************************************************************************************/

    // LC 41
    // Testcase - [0,2,2,1,1]
    // TC O(nlogn) SC O(1)
    public int firstMissingPositive(int[] nums) {
        Arrays.sort(nums);
        
        int val = 1;
        int i = 0, n = nums.length;
        while (i < n) {
            if (nums[i] <= 0) { // ignore -ve, since we only want first +ve
                i++;
                continue;
            }
            
            if (val != nums[i])
                return val;
            val++;
            
            // Remove duplicates
            int prev = nums[i];
            while (i < n && nums[i] == prev)
                i++;
        }
        
        return val;
    }

    // TC O(N) SC O(1)
    // nums to contains values in range of [1,n]
    // mark and find strategy
    // Testcase - [4,-8,12,2,-6,6,43,11,0,1,9,3,2,5,8]
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // 1. convert out of range nums to any +ve common out of range num
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0 || nums[i] > n) 
                nums[i] = n+1; // mark with out of range +ve num
        }
        
        // 2. Mark all in-range nums [1,n] as visited in -ve
        for (int i = 0; i < n; i++) {
            int val = Math.abs(nums[i]); // -1 for 0-indexed
            if (val <= n && nums[val-1] > 0) { // in range & not visited
                nums[val-1] = -1 * nums[val-1];
            }
        }
        
        // 3. if any +ve num found, means found 1st +ve
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) // if not visited, means we got 1st +ve
                return i+1; // +1 for 1-indexed num
        }
        
        return n+1; // all in range nums, next +ve is n+1
    }

    /****************************************************************************************************/

    // LC 1010
    // Approach 1
    // Concept - Sum of pairs divisible by K
    public int numPairsDivisibleBy60(int[] time) {
        int k = 60;
        long[] map = new long[k]; // rem can range from [0, k-1], stores cnt of elems of rem
        
        for (int num : time) {
            int rem = num % k;
            map[rem]++;
        }
        
        long ans = 0;
        for (int i = 1; i <= (k-1)/2; i++) { // 'x' can pair with 'k-x' to form % by k
            ans += (map[i] * map[k-i]);
        }
        
        // '0' rem can pair only with '0' rem
        long n = map[0];
        ans += n * (n-1)/2; 
        
        if (k%2 == 0) { // k is even, spl case the middle also can only pair with self
            n = map[k/2];
            ans += n * (n-1)/2; 
        }
        
        return (int) ans;
    }

    // Approach 2 - Same technique, just way to calc changes
    // Same Concept - Sum of pairs divisible by K
    public int numPairsDivisibleBy60(int[] time) {
        int k = 60;
        int[] map = new int[k]; // rem can range from [0, k-1], stores cnt of elems of rem
        
        int ans = 0;
        for (int num : time) {
            int rem = num % k;
            
            if (rem == 0) {
                ans += map[0]; // since 0 rem can only pair with 0 rem elems to form % by k
            }
            else { // at every step we pair up k-x, eventually x will also pair up
                ans += map[k-rem];
            }
            
            map[rem]++;
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // https://www.geeksforgeeks.org/minimum-number-platforms-required-railwaybus-station/
    public static int findPlatform(int arr[], int dep[], int n) {
        Arrays.sort(arr);
        Arrays.sort(dep);
        
        int trainCount = 0, maxPlatforms = 0; // max trains count will be platforms req.
        int i = 0, j = 0;
        while (i < n && j < n) {
            if (arr[i] <= dep[j]) { // for == case, we req. a platorm at the very moment as well
                trainCount++; // any of the train arrived
                i++;
            }
            else {
                trainCount--; // any of the train departed
                j++;
            }
            
            maxPlatforms = Math.max(maxPlatforms, trainCount);
        }
        
        return maxPlatforms;
    }

    /****************************************************************************************************/

    // LC 1094
    public boolean carPooling(int[][] trips, int capacity) {
        // size as per ques constraint
        int[] map = new int[1001]; // stores impact of from, to on stops
        TreeSet<Integer> stops = new TreeSet<>(); // no duplicates, also gives in sorted order
        
        for (int[] trip : trips) {
            int passenger = trip[0], from = trip[1], to = trip[2];
            
            // put impact of passengers on the stops
            map[from] += passenger; // passenger boards
            stops.add(from);
            
            map[to] -= passenger; // passenger leaves
            stops.add(to);
        }
        
        int currCapacity = 0;
        for (int stop : stops) {
            currCapacity += map[stop];
            if (currCapacity > capacity) {
                return false;
            }
            
            if (currCapacity < 0) // reset, although ques has valid input
                currCapacity = 0;
        }
        
        return true;
    }

    /****************************************************************************************************/

    // LC 453
    public int minMoves(int[] nums) {
        int min = (int) 1e9; // to which elem we want to equal
        for (int num : nums) {
            min = Math.min(min, num);
        }
        
        int ans = 0;
        for (int num : nums) {
            int gap = num - min; // the gap has to be decr at each step for one elem
            ans += gap;
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 881
    // Intuition: The best person to fit with most weighted person will be most lightest person
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        
        int n = people.length;
        int i = 0, j = n-1, boatCount = 0;
        while (i < j) {
            if (people[i] + people[j] <= limit) {
                i++;
                j--;
            }
            else {
                j--;
            }
            boatCount++;
        }
        
        if (i == j) // for that one single person when our condition breaks
            boatCount++;
        
        return boatCount;
    }

    /****************************************************************************************************/

    // https://practice.geeksforgeeks.org/problems/key-pair5616/1
    // Target Sum pair approach
    // Another approach: Using HashMap technique a+b=x, then put elem-x (x-b) in map,
    // if with another elem (x-a) found in map, means got a pair
    public boolean hasArrayTwoCandidates(int arr[], int n, int x) {
        Arrays.sort(arr);
        
        int i = 0, j = n-1;
        while (i < j) {
            if (arr[i] + arr[j] > x) {
                j--;
            }
            else if (arr[i] + arr[j] < x) {
                i++;
            }
            else {
                return true;
            }
        }
        
        return false;
    }

    /****************************************************************************************************/

    // https://practice.geeksforgeeks.org/problems/find-pair-given-difference1559/1
    // Target Difference Pair
    // To get All pairs, this approach won't work, use HashMap technique to find all pairs or count of all pairs
    public boolean findPair(int arr[], int n, int x)
    {
        Arrays.sort(arr);
        
        int i = 0, j = 1;
        while (j < n) {
            if (arr[j] - arr[i] > x) {
                i++;
            }
            else if (arr[j] - arr[i] < x) {
                j++;
            }
            else {
                return true;
            }
            
            if (i == j) // beacuse if both at same point, then it's not a pair
                j++;
        }
        
        return false;
    }

    /****************************************************************************************************/

    // LC Permium - Best Meeting Point
    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/arrays-and-strings/best-meeting-point/ojquestion
    // Median will always be the best meeting point because of the 'Delta' factor for all the persons
    // So we reduce horizontal motion and vertical motion towards median point
    public static int minTotalDistance(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        List<Integer> cols = new ArrayList<>(); // Collect elems column wise
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (grid[i][j] == 1) 
                    cols.add(j);
            }
        }

        List<Integer> rows = new ArrayList<>(); // Collect elems row wise
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) 
                    rows.add(i);
            }
        }

        int medianCol = cols.get(cols.size()/2);
        int medianRow = rows.get(rows.size()/2);

        int distanceCol = 0, distanceRow = 0; // find distance from median points for all persons

        for (int i = 0; i < cols.size(); i++) {
            int gap = Math.abs(cols.get(i) - medianCol);
            distanceCol += gap;
        }
        for (int i = 0; i < rows.size(); i++) {
            int gap = Math.abs(rows.get(i) - medianRow);
            distanceRow += gap;
        }

        int totalDistance = distanceRow + distanceCol; // total distance all 1s have to travel towards median (best meeting point)
        return totalDistance;
    }

    /****************************************************************************************************/

    // LC 763
    public List<Integer> partitionLabels(String s) {
        int n = s.length();
        int[] last = new int[26]; // stores last occurence idx of a char
        Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            int ch = s.charAt(i)-'a';
            last[ch] = Math.max(i, last[ch]);
        }
        
        List<Integer> ans = new ArrayList<>();
        
        // max keeps the farthest buffer range idx, any char can go (basically range)
        int max = -1;
        int prev = -1; // just to find gap i.e. len of chunk
        for (int i = 0; i < n; i++) {
            int ch = s.charAt(i)-'a';
            max = Math.max(max, last[ch]);
            if (i == max) { // if we've reached a boundary of a range part
                ans.add(i-prev);
                prev = i;
            }
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 915
    // TC O(N) SC O(N)
    public int partitionDisjoint(int[] nums) {
        int n = nums.length;
        int[] right = new int[n]; // stores min from right
        for (int i = n-1; i >= 0; i--) {
            if (i == n-1) {
                right[i] = nums[i];
                continue;
            }
            
            right[i] = Math.min(nums[i], right[i+1]);
        }
        
        int left = nums[0]; // stores max from left
        for (int i = 0; i < n; i++) {
            left = Math.max(left, nums[i]);
            if (i+1 < n && left <= right[i+1]) {
                return i+1;
            }
        }
        
        return -1;
    }

    // TC O(N) SC O(1)
    public int partitionDisjoint(int[] nums) {
        int n = nums.length;
        
        int partitionIndex = 0; // assume initial partition point
        int maxTillPartition = nums[0];
        int maxSoFar = nums[0]; // will help set next maxTillPartition, if new partition found
        
        for (int i = 0; i < n; i++) {
            maxSoFar = Math.max(maxSoFar, nums[i]);
            
            if (nums[i] < maxTillPartition) { // means new partition point found, extend partition
                partitionIndex = i;
                maxTillPartition = maxSoFar;
            }
        }
        
        return partitionIndex+1;
    }

    /****************************************************************************************************/

    // LC 754
    // https://www.geeksforgeeks.org/find-the-number-of-jumps-to-reach-x-in-the-number-line-from-zero/
    public int reachNumber(int target) {
        target = Math.abs(target);
        
        int n = 1; // moves

        // use n*(n+1)/2 formula to find range
        while (true) {
            int range = n*(n+1) / 2;
            // target within range and target, range should be same odd based or even based
            if (range >= target && target%2 == range%2) { 
                break;
            }
            n++;
        }
        
        return n;
    }

    /****************************************************************************************************/

    // LC 838
    // TC O(n)
    // Correct code and approach, but TLE on Leetcode, although this is the optimised approach
    // Go with this approach in interview also, no issues
    public String pushDominoes(String dominoes) {
        // add L, R at both ends to use prev, curr logic easily
        dominoes = "L" + dominoes + "R";
        
        char[] arr = dominoes.toCharArray();
        
        // evaluate
        int prev = 0, curr = -1;
        for (int i = 1; i < dominoes.length(); i++) {
            if (arr[i] == '.')
                continue;
            
            curr = i;
            // cases for motion of dominoes
            if (arr[prev] == 'L' && arr[curr] == 'L') { // do left
                for (int j = prev+1; j < curr; j++) 
                    arr[j] = 'L';
            }
            else if (arr[prev] == 'L' && arr[curr] == 'R') { // do nothing
                
            }
            else if (arr[prev] == 'R' && arr[curr] == 'R') { // do right
                for (int j = prev+1; j < curr; j++) 
                    arr[j] = 'R';
            }
            else if (arr[prev] == 'R' && arr[curr] == 'L') { // meet in the middle
                int st = prev+1, end = curr-1;
                while (st < end) {
                    arr[st++] = 'R';
                    arr[end--] = 'L';
                }
            }
            
            prev = curr;
        }
        
        String ans = "";
        for (int i = 1; i < arr.length-1; i++) // since first & last L,R are just reference pts
            ans += arr[i];
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 53
    // Kadane's Algorithm
    public int maxSubArray(int[] nums) {
        int max = -(int) 1e9;
        +
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum < 0) // kyunki ab nayi train start karna chaiye, warna mujhe bhi kam kardega
                sum = 0;
            
            sum += nums[i]; // agar pichla train +ve tha, toh ussi mein chadna behter hai
            max = Math.max(max, sum);
        }
        
        return max;
    }

    /****************************************************************************************************/

    // LC 152
    // Approach 1 - Thought process based
    // TC O(n)
    // The maxProduct will will never exist in center subarr, it can be either covering a prefix Subarr or a suffix subbarr. But never a middle subarr. Refer notes for such cases.
    // Basically it is bcoz of the cases of +ve & -ve combinations
    // Everytime we can combine middle subarr on either sides or incl both sides to get max product
    public int maxProduct(int[] nums) {
        int max = -(int) 1e9;
        
        // Get max product from "Prefix" direction
        int product = 1;
        for (int i = 0; i < nums.length; i++) {
            product *= nums[i];
            max = Math.max(max, product);
            
            // reset product, basically incases of 0 we split subarr as left & right across 0s
            if (nums[i] == 0) 
                product = 1;
        }
        
        // Get max product from "Suffix" direction
        product = 1;
        for (int i = nums.length-1; i >= 0; i--) {
            product *= nums[i];
            max = Math.max(max, product);
            
            // reset product, basically incases of 0 we split subarr as left & right across 0s
            if (nums[i] == 0) 
                product = 1;
        }
        
        return max;
    }

    // Approach 2 - Kadane's Algo based
    // TC O(n)
    public int maxProduct(int[] nums) {
        // We need to maintain min as well, since we've -ve nums, we have few cases
        // (min * -ve) can become max, also (max * -ve) can become min
        // Thereby we have possibilities so need to consider all cases, eg: [-2, 5, -3]
        int max = nums[0];
        int min = nums[0];

        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= 0) { // +ve case, then obviously max*+ve for max & min*+ve for min
                max = Math.max(max * nums[i], nums[i]);
                min = Math.min(min * nums[i], nums[i]);
            }
            else { // -ve case, -ve*max can be next min & -ve*min can be next max (possibilities)
                int temp = max; // store max, since max gets updated
                max = Math.max(min * nums[i], nums[i]);
                min = Math.min(temp * nums[i], nums[i]);
            }
            
            ans = Math.max(ans, max);
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 204
    // Sieve of Eratosthenes - TC O(nlog(logn)) - No proof, by observation TC O(square_root(n)*n)
    public int countPrimes(int n) {
        if (n <= 1) 
            return 0;
        
        boolean[] primes = new boolean[n+1];
        Arrays.fill(primes, true);
        
        primes[0] = primes[1] = false; // 0, 1 are not primes
        for (int i = 2; i * i <= n; i++) {
            if (primes[i] == false)
                continue;
            
            // start j from i, since before i values are already used in prev i values
            // if i=3, then j=3, since 3x2 is already handled in 2x3, so j=i (3 in this case)
            // Why not 3x1, since any num only divisible by itself or 1 is prime, so 3x1 is prime
            for (int j = i; i*j <= n; j++) { 
                primes[i*j] = false; // marking all factors of i, since they can't be prime
            }
        }
        
        int count = 0;
        for (int i = 0; i < n; i++)
            if (primes[i] == true) count++;
        
        return count;
    }

    /****************************************************************************************************/

    // LC 1031
    // TC O(n)
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int max1 = getMaxWindowSum(nums, firstLen, secondLen);
        int max2 = getMaxWindowSum(nums, secondLen, firstLen); // also possible len2+len1 window sum
        int ans = Math.max(max1, max2);
        return ans;
    }
    
    private int getMaxWindowSum(int[] nums, int len1, int len2) {
        int n = nums.length;
        int[] left = new int[n]; // stores max window sum of len1 size ending within 'i'
        int[] right = new int[n]; // stores max window sum of len2 size starting after 'i+1'
        
        // to calc window sum & to store max window sum
        int sum = 0, max = 0;
        for (int i = 0; i < n; i++) {
            if (i <= len1 - 1)
                sum += nums[i];
            else  // reduce elem impact before window in curr window sum
                sum = sum + nums[i] - nums[i-len1];
            
            max = Math.max(max, sum);
            left[i] = max;
        }
        
        sum = 0;
        max = 0;
        for (int i = n-1; i >= 0; i--) {
            if (i >= n - len2)
                sum += nums[i];
            else  // reduce elem impact before window in curr window sum
                sum = sum + nums[i] - nums[i + len2];
            
            max = Math.max(max, sum);
            right[i] = max;
        }
        
        int maxSum = 0;
        for (int i = len1 - 1; i < n - len2; i++) {
            maxSum = Math.max(maxSum, left[i] + right[i+1]); // try all non-overlapping combinations
        }
        
        return maxSum;
    }

    /****************************************************************************************************/

    // LC 42
    // TC O(n)
    public int trap(int[] height) {
        int n = height.length;
        // stores tallest on left & right side (not immediate left, right, but among all left, right)
        int[] left = new int[n]; 
        int[] right = new int[n];
        
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                left[i] = height[i];
                continue;
            }
            
            left[i] = Math.max(left[i-1], height[i]);
        }
        
        for (int i = n-1; i >= 0; i--) {
            if (i == n-1) {
                right[i] = height[i];
                continue;
            }
            
            right[i] = Math.max(right[i+1], height[i]);
        }
        
        int water = 0;
        for (int i = 0; i < n; i++) {
            int gap = Math.min(left[i], right[i]) - height[i];
            water += gap;
        }
        
        return water;
    }

    /****************************************************************************************************/

    // LC 56
    // TC O(nlogn)
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> {
            return a[0] - b[0];
        });
        
        Stack<int[]> st = new Stack<>();
        
        for (int[] interval : intervals) {
            int start = interval[0], end = interval[1];
            if (st.isEmpty()) {
                st.push(interval);
            }
            else {
                int[] top = st.peek();
                if (start > top[1]) 
                    st.push(interval);
                else 
                    top[1] = Math.max(end, top[1]);
            }
        }
        
        int[][] ans = new int[st.size()][2];
        int i = st.size()-1;
        while (!st.isEmpty()) {
            ans[i--] = st.pop();
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 57
    // TC O(n)
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        List<int[]> list = new ArrayList<>();
        
        // 1. add intervals which are not part of overlapping inserting interval 
        int i = 0;
        while (i < n && intervals[i][1] < newInterval[0]) { // (end < st of inserting)
            list.add(intervals[i]);
            i++;
        }
        
        // 2. merge overlapping interval with inserting interval
        int[] merge = newInterval; // this arr will accumulate all overlapping intervals
        while (i < n && intervals[i][0] <= merge[1]) { // end of inseting >= start
            merge[0] = Math.min(intervals[i][0], merge[0]);
            merge[1] = Math.max(intervals[i][1], merge[1]);
            i++;
        }
        list.add(merge);
        
        // 3. add intervals which are not part of overlapping inserting interval 
        while (i < n) { // (start > end of inserting)
            list.add(intervals[i]);
            i++;
        }
        
        int[][] ans = new int[list.size()][2];
        for (int j = 0; j < list.size(); j++)
            ans[j] = list.get(j);
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 986
    // TC O(n+m)
    // Two-pointer approach
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int n = firstList.length, m = secondList.length;

        List<int[]> list = new ArrayList<>();
        int i = 0, j = 0;
        while (i < n && j < m) {
            int st1 = firstList[i][0], end1 = firstList[i][1];
            int st2 = secondList[j][0], end2 = secondList[j][1];
            
            int latestSt = Math.max(st1, st2);
            int earliestEnd = Math.min(end1, end2);
            
            if (latestSt <= earliestEnd) { // means latestSt occurs even before earliestEnd ends
                list.add(new int[]{latestSt, earliestEnd}); // intersection gap [st,end]
            }
            
            if (end1 < end2)  // incr i, so that to cover intersection within end2, if at all occurs
                i++;
            else // vice-versa
                j++;
        }
        
        int[][] ans = new int[list.size()][2];
        for (int k = 0; k < list.size(); k++) 
            ans[k] = list.get(k);
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 891
    // TC O(n)
    public int sumSubseqWidths(int[] nums) {
        int m = (int) 1e9+7;
        int n = nums.length;
        
        Arrays.sort(nums); // so that min & max are taken easily for subsq width calc
        
        long ans = 0; // adding overall width
        long x = 1; // represents subsq count
        for (int i = 0, j = n-1; i < n; i++, j--) {
            ans = (ans + x*nums[i] - x*nums[j]) % m;
            x = (x*2) % m;
        }
        
        return (int) ans;
    }

    /****************************************************************************************************/

    // LC 452
    // TC O(nlogn)
    public int findMinArrowShots(int[][] points) {
        int n = points.length;
        
        // Use this type of sort incases of Interger overflow problem, a[0] - b[0] might overflow
        Arrays.sort(points, (a, b) -> {
            if (a[0] > b[0]) 
                return 1;
            else if (a[0] == b[0])
                return 0;
            else 
                return -1;
        });
        
        int arrows = 1; // represents arrows count, intialize 1 to burst very first balloon
        int end = points[0][1]; // range within which we can burst more using same arrow (greedy)
        for (int i = 1; i < n; i++) {
            int st = points[i][0];
            if (st > end) {
                arrows++;
                end = points[i][1];
            }
            else { // min - so that we can burst more using same arrow in min end range
                end = Math.min(end, points[i][1]); 
            }
        }
        
        return arrows;
    }

    /****************************************************************************************************/

    // LC 134
    // TC O(n)
    // Greedy - Refer notes for intuition
    // Overall delta = sum of delta at all stations
    // Note: No need to loop remaining journey, since overall delta sum is +ve & remaining journey is also part of the same overall delta sum which will be +ve
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (sum(gas) < sum(cost)) // no enough gas itself overall to complete journey
            return -1;
        
        int n = gas.length;
        int total = 0; // this the 'delta' from stations, delta = gas - cost
        int start = 0; // represents the station to start from to complete journey
        for (int i = 0; i < n; i++) {
            total += gas[i] - cost[i];
            
            if (total < 0) { // -ve means not enough gas, so start from new station fresh
                total = 0;
                start = i+1;
            }
        }
        
        return start;
    }
    
    private int sum (int[] arr) {
        int sum = 0;
        for (int val : arr)
            sum += val;
        return sum;
    }

    /****************************************************************************************************/

    // LC 239
    // Approach 1 - Using Next Greater Element Technique
    // TC O(n) 
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        
        // 1. Find Next Greater Element
        int[] nge = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                st.push(i);
            }
            else {
                while (!st.isEmpty() && nums[i] > nums[st.peek()]) { // curr > top => curr is NGE
                    nge[st.pop()] = i;
                }
                st.push(i);
            }
        }
        
        while (!st.isEmpty()) {
            nge[st.pop()] = n; // no NGE for these so assign some out of range number
        }
        
        // 2. Find Max in windows
        int[] ans = new int[n-k+1]; // stores max in windows
        int j = 0; // indicates max in window
        for (int i = 0; i < ans.length; i++) {
            if (j < i) // j cannot be behind (happens when i shifts to a new window)
                j = i;
            
            while (nge[j] <= i+k-1) { // keep moving to NGEs until doesn't get out of window
                j = nge[j];
            }
            
            // Finally j will stand at point of max in window
            ans[i] = nums[j];
        }
        
        return ans;
    }

    // Approach 2 - Using Doubly Ended Queue
    // TC O(n)
    // If you notice the Queue always tries to maintain increasing order of elems from reverse 
    // Thereby first elem will be max in window
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n-k+1]; // stores max in each window of k-size
        
        // Deque - Add/remove from both ends of Queue
        Deque<Integer> q = new LinkedList<>(); // stores indexes to evaluate of max in window
        // initial fill of k size
        for (int i = 0; i < k; i++) {
            while (!q.isEmpty() && nums[i] > nums[q.getLast()]) {
                q.removeLast();
            }
            q.addLast(i);
        }
        
        int j = 0;
        ans[j++] = nums[q.getFirst()]; // for the first window
            
        // for remaining windows
        for (int i = k; i < n; i++) {
            // 1. remove prev window elems first
            while (!q.isEmpty() && q.getFirst() <= i-k) { 
                q.removeFirst();
            }
            
            // 2. now evaluate for curr window
            while (!q.isEmpty() && nums[i] > nums[q.getLast()]) { 
                q.removeLast();
            }
            
            q.addLast(i);
            ans[j++] = nums[q.getFirst()]; // at first of Queue we'll have the max in window
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 628
    // TC O(n)
    public int maximumProduct(int[] nums) {
        int min1 = (int) 1e9, min2 = (int) 1e9;
        int max1 = -(int) 1e9, max2 = -(int) 1e9, max3 = -(int) 1e9;
        
        for (int num : nums) {
            // For min
            if (num < min1) {
                min2 = min1;
                min1 = num;
            }
            else if (num < min2) {
                min2 = num;
            }
            
            // For max
            if (num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            }
            else if (num > max2) {
                max3 = max2;
                max2 = num;
            }
            else if (num > max3) {
                max3 = num;
            }
        }
        
        int case1 = min1 * min2 * max1; // If -ve present, min1*min2 becomes +ve, then with max1
        int case2 = max1 * max2 * max3; // Obvious case xply all max nums
        
        int ans = Math.max(case1, case2); // Probability of max in both cases
        return ans;
    }

    /****************************************************************************************************/

    // LC 1004
    // TC O(n)
    public int longestOnes(int[] nums, int k) {
        int n = nums.length;
        
        int ans = -(int) 1e9;
        
        int zeroes = 0; // 0's count
        int j = 0; // points to start of window, where 0s count is atmost k, to calc gap, flipping 0s
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0)
                zeroes++;
            
            while (zeroes > k) { // (Invalid zone) 0s count > k, then get 0s count within k
                if (nums[j] == 0)
                    zeroes--;
                j++;
            }
            
            // coming at this line means, (Valid zone) 0s count <= k, so calc gap, assuming you flipped 0s
            // Even if 0s count is less we can get ans, Testcase - arr=[0,0,0,1], k=4
            ans = Math.max(ans, i-j+1);
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // https://www.geeksforgeeks.org/find-smallest-number-whose-digits-multiply-given-number-n/
    // https://practice.geeksforgeeks.org/problems/digit-multiplier3000/1
    // TC O(logn)
    public static String getSmallest(Long N) {
        if (N < 10) // if single digit it itself is smallest number divisible by self
            return N + "";
        
        String ans = "";
        
        // Starting from big divisor (9,8,7,..2) since division by big number has big impact contributing to smallest number in ans
        for (int div = 9; div >= 2; div--) {  
            while (N % div == 0) { // while N is divisible by divisor
                N = N/div;
                ans = div + ans;
            }
        }
        
        if (N >= 10) // means N is double digit & couldn't divide that N by any divisor, so no ans
            return "-1";
            
        return ans;
    }

    /****************************************************************************************************/

    // LC 153
    // TC O(logn)
    // Observation of pattern needed, refer notes
    public int findMin(int[] nums) {
        int n = nums.length;
        
        // We use this type of binary serach method, since we want to make only 2 partitions, i.e. "including" mid in any one partition and not excluding like we do in mid+1 or mid-2
        int lo = 0, hi = n-1;
        while (lo < hi) {
            int mid = (hi-lo)/2 + lo;
            
            // all big on right, then go left to find smallest elem
            if (nums[hi] > nums[mid]) 
                hi = mid;
            else // right side has small elems, so go right
                lo = mid+1;
        }
        
        return nums[lo];
    }

    /****************************************************************************************************/

    // LC 1283
    // TC O(nlogn)
    public int smallestDivisor(int[] nums, int threshold) {
        // since a divisor will lie btw min & max of arr to get close to threshold value
        int lo = 1, hi = 1; // default values from constraint
        for (int num : nums) {
            lo = Math.min(lo, num);
            hi = Math.max(hi, num);
        }
        
        // 2 partition based binary search
        while (lo < hi) { // TC O(logn)
            int mid = (hi-lo)/2 + lo;
            
            int sum = 0;
            int div = mid; // mid is your divisor in the range of lo & hi of arr
            for (int num : nums) { // TC O(n)
                // for ceil value of quotient (or) Math.ceil(num*1.0/div)
                int quo = (num + div - 1) / div;
                sum += quo;
            }
            
            if (sum > threshold) 
                lo = mid+1; // when divisor incr, thereby quotient decr, sum will also decr
            else // when divisor decr, thereby quotient will incr, sum will also incr
                hi = mid;
        }
        
        return lo;
    }

    /****************************************************************************************************/

    // https://practice.geeksforgeeks.org/problems/chocolate-distribution-problem3825/1
    // TC O(nlogn)
    public long findMinDiff (ArrayList<Integer> a, int n, int m) {
        Collections.sort(a);
        
        int minDiff = (int) 1e9;
        for (int i = 0; i <= n-m; i++) {
            minDiff = Math.min(minDiff, a.get(i+m-1) - a.get(i)); // Get min diff from each window's start, end
        }
        
        return minDiff;
    }

    /****************************************************************************************************/

    // LC 410
    // TC O(nlogn)
    public int splitArray(int[] nums, int m) {
        int hi = 0; // max sum a partition is allowed (sum of all elems)
        int lo = 0; // min sum a partition is allowed 
        // (why max for lo? let's say a single partition of one elem & that one elem itself might be so big than the limit, max sum allowed in a partition itself, max ensures that all partitions will have atleast min sum in a partition allowed) [Testcase: nums=[1,4,4] m=3]
        
        for (int num : nums) {
            hi += num;
            lo = Math.max(lo, num);
        }
        
        // Binary search - 2 partitions logic
        while (lo < hi) { // O(logn)
            // this mid(limit) is nothing but max sum a partition is allowed to have
            int mid = (hi-lo)/2 + lo; 
            int limit = mid;
            
            int countOfPartitions = 1; // By default all elems are a single partition
            int currPartitionSum = 0; // sum of the curr partition
            
            for (int num : nums) { // O(n)
                if (currPartitionSum + num <= limit) { // sum within max sum allowed, accumulate
                    currPartitionSum += num;
                }
                else { // sum overflow max sum allowed in a partition, so make a parition
                    currPartitionSum = num; // starting a new partition
                    countOfPartitions++;
                }
            }
            
            // (if) partitions more than 'm', limit sum is small, obvious to increase limit sum to accumulate more partitions within 'm'
            // (else) partitions within 'm', means good, let's minizme limit sum little more to see if we get much better partitions
            if (countOfPartitions > m) {
                lo = mid + 1; // go right
            }
            else { 
                hi = mid; // go left
            }
        }
        
        return lo; // eventually lo, hi will be at same point doesn't matter return anything
    }

    /****************************************************************************************************/

    // LC 1011
    // Same Ditto copy of above problem approach [LC 410]
    // TC O(nlogn)
    public int shipWithinDays(int[] weights, int days) {
        int lo = 0;
        int hi = 0;
        
        for (int weight : weights) {
            hi += weight;
            lo = Math.max(lo, weight);
        }
        
        // Binary Search - 2 partitions logic
        while (lo < hi) { // O(logn)
            int mid = (hi-lo)/2 + lo;
            int limit = mid;
            
            int countOfDays = 1;
            int currPackagesWeight = 0;
            
            for (int weight : weights) { // O(n)
                if (currPackagesWeight + weight <= limit) {
                    currPackagesWeight += weight;
                }
                else {
                    currPackagesWeight = weight;
                    countOfDays++;
                }
            }
            
            if (countOfDays > days) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        
        return lo;
    }

    /****************************************************************************************************/

    // LC 875
    // Same Ditto copy of above problem approach [LC 1283]
    // TC O(nlogn)
    public int minEatingSpeed(int[] piles, int h) {
        int lo = 1;
        int hi = 1;
        
        for (int pile : piles) {
            hi = Math.max(hi, pile);
        }
        
        // Binary Search - 2 partitions logic
        while (lo < hi) {
            int mid = (hi-lo)/2 + lo;
            int speed = mid; // k
            
            int totalHours = 0;
            for (int pile : piles) {
                int time = (pile+speed-1) / speed; // Time(hrs) to eat that pile in k-speed
                totalHours += time;
            }
            
            if (totalHours > h) { // time high, speed low, increase speed
                lo = mid + 1;
            }
            else { // time low(good), try to minimize for much better if much better k-speed
                hi = mid;
            }
        }
        
        return lo;
    }

    /****************************************************************************************************/

}
