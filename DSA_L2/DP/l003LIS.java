import java.util.*;

public class l003LIS {

    // LC 300 => Approach 1 => LIS [Longest Increasing Subsequence]
    public int lengthOfLIS_001(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
            
        int lisLen = 0;
        // for (int i = 0; i < nums.length; i++) {
        //     lisLen = Math.max(lisLen, lis_mem(nums, i, dp));
        // }
        
        lisLen = lis_tab(nums, dp);
        return lisLen;
    }

    public static int lis_mem(int[] nums, int ei, int[] dp) {
        if (dp[ei] != 0)  // Why 0, bcoz one len LIS always exists, "ei" itself
            return dp[ei];
        
        int maxLen = 1; // There will be atleast one length LIS, "ei" itself
        for (int i = ei-1; i >= 0; i--) { // Get LIS before endIndex, so ei-1
            if (nums[i] < nums[ei]) {
                maxLen = Math.max(maxLen, lis_mem(nums, i, dp) + 1); // +1 for including current ith elem in LIS
            }
        }
        
        return dp[ei] = maxLen;
    }
    
    public static int lis_tab(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = 0; ei < n; ei++) {
            int maxLen = 1;
            for (int i = ei-1; i >= 0; i--) {
                if (nums[i] < nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + 1);
                }
            }
            
            dp[ei] = maxLen;
        }
        
        int lisLen = 0;
        for (int i = 0; i < n; i++) {
            lisLen = Math.max(lisLen, dp[i]);
        }
        
        return lisLen;
    }

    // LC 300 => Approach 2 => LIS [Longest Increasing Subsequence]
    public int lengthOfLIS_002(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n+1][n+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
            
        return lis_mem(nums, 0, -1, dp);
    }

    public static int lis_mem(int[] nums, int idx, int prevIdx, int[][] dp) {
        int n = nums.length;
        if (idx == n)
            return dp[prevIdx+1][idx] = 0;
        
        if (dp[prevIdx+1][idx] != -1)
            return dp[prevIdx+1][idx];
        
        int ans = -1;
        if (prevIdx == -1 || nums[idx] > nums[prevIdx]) {
            int incl = 1 + lis_mem(nums, idx+1, idx, dp); // Since want to include so +1
            int excl = lis_mem(nums, idx+1, prevIdx, dp); // excluding might form a subsq of greater len
            ans = Math.max(incl, excl);
        }
        else {
            ans = lis_mem(nums, idx+1, prevIdx, dp);
        }
        
        return dp[prevIdx+1][idx] = ans; // prevIdx+1, makes sure to not hit -ve index (-1)
    }

    // ===========================================

    // LDS => Longest Decreasing Subsequence
    public static int lengthOfLDS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
            
        int ldsLen = 0;
        // for (int i = n-1; i >= 0; i--) {
        //     ldsLen = Math.max(ldsLen, lds_mem(nums, i, dp));
        // }

        ldsLen = lds_tab(nums, dp);
        return ldsLen;
    }

    public static int lds_mem(int[] nums, int ei, int[] dp) {
        if (dp[ei] != 0)  // Why 0, bcoz one len LIS always exists, "ei" itself
            return dp[ei];
        
        int maxLen = 1; // There will be atleast one length LIS, "ei" itself
        for (int i = ei-1; i >= 0; i--) { // Get LIS before endIndex, so ei-1
            if (nums[i] > nums[ei]) {
                maxLen = Math.max(maxLen, lis_mem(nums, i, dp) + 1); // +1 for including current ith elem in LIS
            }
        }
        
        return dp[ei] = maxLen;
    }
    
    public static int lds_tab(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = 0; ei < n; ei++) {
            int maxLen = 1;
            for (int i = ei-1; i >= 0; i--) {
                if (nums[i] > nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + 1);
                }
            }
            
            dp[ei] = maxLen;
        }
        
        int lisLen = 0;
        for (int i = 0; i < n; i++) {
            lisLen = Math.max(lisLen, dp[i]);
        }
        
        return lisLen;
    }
    
    // ===========================================

    // https://www.geeksforgeeks.org/longest-bitonic-subsequence-dp-15/
    // LBS => Longest Bitonic Sequence 
    
    public static int LongestBitonicSequence(int[] nums) {
        int n = nums.length;
        // First pre-process LIS and LDS to calculate LBS further
        int[] lis_dp = new int[n];
        int[] lds_dp = new int[n];
         
        //      /\
        //     /  \
        // lis_tab_end(nums, lis_dp); // LIS calculation
        // lds_tab_start(nums, lds_dp); // LDS calculation => This LDS is performed as LIS in opposite direction
        
        //     \  /
        //      \/
        lds_tab_end(nums, lds_dp);
        lis_tab_start(nums, lis_dp);

        int lbsLen = 0; // Longest Bitonic Subsequence Length
        for (int i = 0; i < n; i++) {
            // -1, since 2pts from lis & lds meet at one common peak point (11 here) lis=(1,2,11) ; lds=(11,4,3)
            int len = lis_dp[i] + lds_dp[i] - 1; 
            lbsLen = Math.max(lbsLen, len);
        }

        return lbsLen;
    }
    
    // LIS ending ith
    public static void lis_tab_end(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = 0; ei < n; ei++) {
            int maxLen = 1;
            for (int i = ei-1; i >= 0; i--) {
                if (nums[i] < nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + 1);
                }
            }
            
            dp[ei] = maxLen;
        }
    }
    
    // This LDS is performed as LIS in opposite direction
    // LDS starting ith
    public static void lds_tab_start(int[] nums, int[] dp) { 
        int n = nums.length;
        for (int ei = n-1; ei >= 0; ei--) {
            int maxLen = 1;
            for (int i = ei+1; i < n; i++) {
                if (nums[i] < nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + 1);
                }
            }
            
            dp[ei] = maxLen;
        }
    }

    // LDS ending ith
    public static void lds_tab_end(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = 0; ei < n; ei++) {
            int maxLen = 1;
            for (int i = ei-1; i >= 0; i--) {
                if (nums[i] > nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + 1);
                }
            }
            
            dp[ei] = maxLen;
        }
    }

    // LIS starting ith
    public static void lis_tab_start(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = n-1; ei >= 0; ei--) {
            int maxLen = 1;
            for (int i = ei+1; i < n; i++) {
                if (nums[i] > nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + 1);
                }
            }
            
            dp[ei] = maxLen;
        }
    }

    // ===========================================

    // https://www.geeksforgeeks.org/maximum-sum-increasing-subsequence-dp-14/
    // Same LIS concept, find LIS having maxSum, dp stores the LIS sequence's maxSum
    // ith ending LIS's maxSum
    public int maxSumIS(int nums[], int n) {  
        int[] dp = new int[n];
            
        int maxSum = lis_tab_001(nums, dp);
        return maxSum;
	}
	
	public static int lis_tab_001(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = 0; ei < n; ei++) {
            int maxSum = nums[ei];
            for (int i = ei-1; i >= 0; i--) {
                if (nums[i] < nums[ei]) {
                    maxSum = Math.max(maxSum, dp[i] + nums[ei]);
                }
            }
            
            dp[ei] = maxSum;
        }
        
        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            maxSum = Math.max(maxSum, dp[i]);
        }
        
        return maxSum;
    }

    // ===========================================

    // https://www.geeksforgeeks.org/maximum-sum-bi-tonic-sub-sequence/
    public static int maxSumBS(int nums[], int n) {
        // First pre-process LIS and LDS with maxSum to calculate LBS further
        int[] lis_dp = new int[n];
        int[] lds_dp = new int[n];
         
        //      /\
        //     /  \
        lis_tab_end_001(nums, lis_dp); // LIS calculation
        lds_tab_start_001(nums, lds_dp); // LDS calculation => This LDS is performed as LIS in opposite direction

        int maxSum = 0; // Longest Bitonic Subsequence Length
        for (int i = 0; i < n; i++) {
            // -1, since 2pts from lis & lds meet at one common peak point (11 here) lis=(1,2,11) ; lds=(11,4,3)
            int len = lis_dp[i] + lds_dp[i] - nums[i]; 
            maxSum = Math.max(maxSum, len);
        }

        return maxSum;
    }
    
    public static void lis_tab_end_001(int[] nums, int[] dp) {
        int n = nums.length;
        for (int ei = 0; ei < n; ei++) {
            int maxLen = nums[ei];
            for (int i = ei-1; i >= 0; i--) {
                if (nums[i] < nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + nums[ei]);
                }
            }
            
            dp[ei] = maxLen;
        }
    }
    
    // This LDS is performed as LIS in opposite direction
    // LDS starting ith
    public static void lds_tab_start_001(int[] nums, int[] dp) { 
        int n = nums.length;
        for (int ei = n-1; ei >= 0; ei--) {
            int maxLen = nums[ei];
            for (int i = ei+1; i < n; i++) {
                if (nums[i] < nums[ei]) {
                    maxLen = Math.max(maxLen, dp[i] + nums[ei]);
                }
            }
            
            dp[ei] = maxLen;
        }
    }

    // ===========================================
    

    public static void main(String[] args) {
        int[] nums = {1, 11, 2, 10, 4, 5, 2, 1, 20};
        // lengthOfLDS(nums);
        // LongestBitonicSequence(nums);
    }
}
