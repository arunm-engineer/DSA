import java.util.Arrays;

public class questions {

    // LC 213
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0]; // edge case
        int[] dp = new int[n + 1];

        Arrays.fill(dp, -1);
        int ans1 = rob_mem(nums, 0, n - 2, dp);

        Arrays.fill(dp, -1); // Why 2 DP calacualtions, since the boundary for both recurrsion varies
        int ans2 = rob_mem(nums, 1, n - 1, dp);

        int max = Math.max(ans1, ans2);
        return max;
    }

    public static int rob_mem(int[] nums, int si, int ei, int[] dp) {
        if (si > ei)
            return 0;

        if (dp[si] != -1)
            return dp[si];

        int curCoin = nums[si];
        int robCurrHouse = rob_mem(nums, si + 2, ei, dp) + curCoin;
        int notRobCurrHouse = rob_mem(nums, si + 1, ei, dp);
        int max = Math.max(robCurrHouse, notRobCurrHouse);

        return dp[si] = max;
    }

    // ===================================================

    // LC 198
    public int rob_00(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return nums[0]; // edge case
        int[] dp = new int[n + 1];

        Arrays.fill(dp, -1);
        int ans1 = rob_mem_00(nums, 0, n - 1, dp);
        int ans2 = rob_mem_00(nums, 1, n - 1, dp);

        int max = Math.max(ans1, ans2);
        return max;
    }

    public static int rob_mem_00(int[] nums, int si, int ei, int[] dp) {
        if (si > ei)
            return dp[si] = 0;

        if (dp[si] != -1)
            return dp[si];

        int curCoin = nums[si];
        int robCurrHouse = rob_mem(nums, si + 2, ei, dp) + curCoin;
        int notRobCurrHouse = rob_mem(nums, si + 1, ei, dp);
        int max = Math.max(robCurrHouse, notRobCurrHouse);

        return dp[si] = max;
    }

    // Going n to 0, rest no change in code
    public int rob_01(int[] nums) {
        int n = nums.length;
        if (n == 1) // edge case
            return nums[0];
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        return rob_mem_01(nums, n, dp);
    }

    public static int rob_mem_01(int[] nums, int n, int[] dp) {
        if (n <= 0)
            return 0;

        if (dp[n] != -1)
            return dp[n];

        int curCoin = nums[n - 1];
        int robCurrHouse = rob_mem_01(nums, n - 2, dp) + curCoin;
        int notRobCurrHouse = rob_mem_01(nums, n - 1, dp);
        int max = Math.max(robCurrHouse, notRobCurrHouse);

        return dp[n] = max;
    }

    // ===================================================

    // LC 1388
    public int maxSizeSlices(int[] slices) {
        int n = slices.length;
        int numOfSlices = n/3; // Since I can pick at max only n/3 slices, rest slices picked by Alice & Bob
        
        int[][] dp = new int[n][numOfSlices + 1];4y
        
        for (int[] a : dp) Arrays.fill(a, -1);
        int ans1 = maxSizeSlices_mem(slices, 0, n-2, numOfSlices, dp);
        
        for (int[] a : dp) Arrays.fill(a, -1);
        int ans2 = maxSizeSlices_mem(slices, 1, n-1, numOfSlices, dp);
        
        return Math.max(ans1, ans2);
    }
    
    // Same Ditto House Robber 2 problem concept (LC 213)
    public static int maxSizeSlices_mem(int[] slices, int si, int ei, int numOfSlices, int[][] dp) {
        if (si > ei || numOfSlices == 0)
            return 0;
        
        if (dp[ei][numOfSlices] != -1)
            return dp[ei][numOfSlices];
        
        int takeThisSlice = maxSizeSlices_mem(slices, si, ei-2, numOfSlices-1, dp) + slices[ei];
        int leaveThisSlice = maxSizeSlices_mem(slices, si, ei-1, numOfSlices, dp);
        
        return dp[ei][numOfSlices] = Math.max(takeThisSlice, leaveThisSlice);
    }

    // ===================================================

    public static void main(String[] args) {

    }
}
