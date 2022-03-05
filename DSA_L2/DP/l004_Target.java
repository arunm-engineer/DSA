import java.util.*;

public class l004_Target {

    public static void display(int[] dp) {
        for (int ele : dp)
            System.out.print(ele + " ");
        System.out.println();
    }

    public static void display2D(int[][] dp) {
        for (int[] arr : dp)
            display(arr);
    }

    // ===================================================

    // LC 377
    public static int infinitePerm_mem(int[] arr, int tar, int[] dp) {
        if (tar == 0)
            return dp[tar] = 1;

        if (dp[tar] != -1)
            return dp[tar];

        int count = 0;
        for (int elem : arr) {
            if (tar - elem >= 0)
                count += infinitePerm_mem(arr, tar - elem, dp);
        }

        return dp[tar] = count;
    }

    public static int infinitePerm_tab(int[] arr, int TAR, int[] dp) {
        for (int tar = 0; tar <= TAR; tar++) {
            if (tar == 0) {
                dp[tar] = 1;
                continue;
            }

            int count = 0;
            for (int elem : arr) {
                if (tar - elem >= 0)
                    count += dp[tar - elem];
            }

            dp[tar] = count;
        }

        return dp[TAR];
    }

    // ===================================================

    public static int infiniteComb_mem(int[] arr, int n, int tar, int[][] dp) {
        if (tar == 0)
            return dp[n][tar] = 1;
        
        if (dp[n][tar] != -1)
            return dp[n][tar];

        int count = 0;
        for (int i = n; i >= 0; i--) {
            if (tar - arr[i] >= 0)
                count += infiniteComb_mem(arr, i, tar - arr[i], dp);
        }

        return dp[n][tar] = count;
    }

    public static int infiniteComb_tab(int[] arr, int N, int TAR, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar == 0) {
                    dp[n][tar] = 1;
                    continue;
                }
                
                int count = 0;
                for (int i = n; i >= 0; i--) {
                    if (tar - arr[i] >= 0)
                        count += dp[i][tar - arr[i]];
                }

                dp[n][tar] = count;
            }
        }

        return dp[N][TAR];
    }

    // DP Space Optimized => 1D DP => SC O(n)
    public static int infiniteComb_opt(int[] arr, int N, int TAR, int[] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar == 0) {
                    dp[tar] = 1;
                    continue;
                }
                
                // Here we don't have to run loop for (n-1) coins since they are already pre-calculated, so just calculate for nth coin
                if (tar - arr[n] >= 0)
                    dp[tar] += dp[tar - arr[n]];
            }
        }

        return dp[TAR];
    }

    // ===================================================

    // LC 322 
    // Here we used combination, but we can also use permutation, either will not have any effect on the problem
    // tar = 7, comb => "223" len=3 ; perm => "322","232","223" len=3 only so no effect
    //  Used combination bcoz comb saves a few recursive calls compared to perm
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, (int) 1e9);
        
        int ans = infiniteComb_opt(coins, n-1, amount, dp);
        return ans == (int) 1e9 ? -1 : ans;
    }
    
    public static int infiniteComb_mem_00(int[] arr, int n, int tar, int[][] dp) {
        if (tar == 0)
            return dp[n][tar] = 0;
        
        if (dp[n][tar] != 0)
            return dp[n][tar];

        int minCoins = (int) 1e9;
        for (int i = n; i >= 0; i--) {
            if (tar - arr[i] >= 0) {
                int recAns = infiniteComb_mem_00(arr, i, tar - arr[i], dp);
                minCoins = Math.min(minCoins, recAns + 1);
            }
        }

        return dp[n][tar] = minCoins;
    }
    
    public static int infiniteComb_tab_00(int[] arr, int N, int TAR, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar == 0) {
                    dp[n][tar] = 0;
                    continue;
                }
                
                int minCoins = (int) 1e9;
                for (int i = n; i >= 0; i--) {
                    if (tar - arr[i] >= 0) {
                        int recAns = dp[i][tar - arr[i]];
                        minCoins = Math.min(minCoins, recAns + 1);
                    }
                }

                dp[n][tar] = minCoins;
            }
        }

        return dp[N][TAR];
    }
    
    public static int infiniteComb_opt_00(int[] arr, int N, int TAR, int[] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar == 0) {
                    dp[tar] = 0;
                    continue;
                }
                
                // Here we don't have to run loop for (n-1) coins since they are already pre-calculated, so just calculate for nth coin
                if (tar - arr[n] >= 0)
                    dp[tar] = Math.min(dp[tar], dp[tar - arr[n]] + 1);
            }
        }

        return dp[TAR];
    }

    // ===================================================



    public static void Target() {
        int[] arr = {2, 3, 5, 7};
        int n = arr.length, tar = 10;

        // Permutations
        // int[] dp = new int[tar+1];
        // Arrays.fill(dp, -1);

        // System.out.println(infinitePerm_mem(arr, tar, dp));
        // System.out.println(infinitePerm_tab(arr, tar, dp));

        // Combinations
        // int[][] dp = new int[n][tar+1];
        // for (int[] a : dp)
            // Arrays.fill(a, -1);

        // System.out.println(infiniteComb_mem(arr, n-1, tar, dp));
        // System.out.println(infiniteComb_tab(arr, n-1, tar, dp));
        // int[] dp = new int[tar+1];
        // Arrays.fill(dp, -1);
        // System.out.println(infiniteComb_opt(arr, n-1, tar, dp));
        // display(dp);
    }

    // ===================================================
    
    public static void main(String[] args) {
        Target();
    }
}
