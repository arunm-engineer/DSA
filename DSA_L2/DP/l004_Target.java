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

    // LC 322 
    // Here we used combination, but we can also use permutation, either will not have any effect on the problem
    // tar = 7, comb => "223" len=3 ; perm => "322","232","223" len=3 only so no effect
    //  Used combination bcoz comb saves a few recursive calls compared to perm
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, (int) 1e9);
        
        int ans = infiniteComb_opt_00(coins, n-1, amount, dp);
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

    // https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
    public static Boolean isSubsetSum(int N, int arr[], int sum) {
        // Since T, F both are part of answer we use int DP and not boolean DP
        int[][] dp = new int[N+1][sum+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
            
        // int ans = subsetSum_mem(arr, N, sum, dp);
        int ans = subsetSum_tab(arr, N, sum, dp);
        // System.out.println(subsetSum_BackEng(dp, N, sum, arr, "")); // manipulate according to the type of DP you pass => int DP or boolean DP

        return ans == 1;
    }
    
    // 1 => indicates subset exists, 0 => indicates subset not exists
    public static int subsetSum_mem(int[] arr, int n, int tar, int[][] dp) {
        if (tar == 0 || n == 0)
            return dp[n][tar] = tar == 0 ? 1 : 0;
            
        if (dp[n][tar] != -1)
            return dp[n][tar];
            
        boolean res = false;
        
        if (tar - arr[n-1] >= 0)
            res = res || subsetSum_mem(arr, n-1, tar - arr[n-1], dp) == 1; // yes
        res = res || subsetSum_mem(arr, n-1, tar, dp) == 1; // no
        
        return dp[n][tar] = res ? 1 : 0;
    }
    
    public static int subsetSum_tab(int[] arr, int N, int TAR, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar == 0 || n == 0) {
                    dp[n][tar] = tar == 0 ? 1 : 0;
                    continue;
                }
                
                boolean res = false;
                
                if (tar - arr[n-1] >= 0)
                    res = res || dp[n-1][tar - arr[n-1]] == 1; // yes
                res = res || dp[n-1][tar] == 1; // no
                
                dp[n][tar] = res ? 1 : 0;
            }
        }
        
        return dp[N][TAR];
    }

    // Back Engineering => To print the coins used
    // For subset we used int DP, here we don't have as such need of int DP, we can work with boolean DP as well
    // If want to use int DP can be done just manipulate 1 => T, 0 => F
    // Manipulate accordingly for boolean DP or int DP
    public static int subsetSum_BackEng(boolean[][] dp, int n, int tar, int[] arr, String path) {
        if (tar == 0) {
            System.out.println(path);
            return 1;
        }

        if (dp[n][tar] == false) // False indicates => not found a combination to form "w"
            return 0;

        int count = 0;
        if (tar - arr[n-1] >= 0)
            count += subsetSum_BackEng(dp, n-1, tar - arr[n-1], arr, arr[n-1] + " " + path);
        count += subsetSum_BackEng(dp, n-1, tar, arr, path);

        return count;
    }

    // ===================================================

    // Knapsack 01 => https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
    // Function to return max value that can be put in knapsack of capacity W.
    public static int knapSack(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n+1][W+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
            
        // return knapsack_01_mem(W, n, wt, val, dp);
        return knapsack_01_tab(W, n, wt, val, dp);
    }
    
    public static int knapsack_01_mem(int w, int n, int[] wt, int[] val, int[][] dp) {
        if (w == 0 || n == 0) 
            return dp[n][w] = 0;
        
        if (dp[n][w] != -1)
            return dp[n][w];
            
        int max = 0;
        if (w - wt[n-1] >= 0) 
            max = Math.max(max, knapsack_01_mem(w - wt[n-1], n-1, wt, val, dp) + val[n-1]);
        max = Math.max(max, knapsack_01_mem(w, n-1, wt, val, dp));
        
        return dp[n][w] = max;
    }
    
    public static int knapsack_01_tab(int W, int N, int[] wt, int[] val, int[][] dp) {
        
        for (int n = 0; n <= N; n++) {
            for (int w = 0; w <= W; w++) {
                if (w == 0 || n == 0) {
                    dp[n][w] = 0;
                    continue;
                }
                
                int max = 0;
                if (w - wt[n-1] >= 0) 
                    max = Math.max(max, dp[n-1][w - wt[n-1]] + val[n-1]);
                max = Math.max(max, dp[n-1][w]);
                
                dp[n][w] = max;
            }
        }
        
        return dp[N][W];
    }

    // ===================================================

    // https://www.geeksforgeeks.org/unbounded-knapsack-repetition-items-allowed/
    // Unbounded Knapsack repetition allowed => infinite coins
    // Same Classical knapsack 01 problem just single change in "yes" (incl) calls => "n" can appear again
    public static int knapSack(int N, int W, int val[], int wt[]) {
        // Subsq method => infinite comb => Correct but uses 2D DP space
        // int[][] dp = new int[N+1][W+1];
        // for (int[] a : dp)
        //     Arrays.fill(a, -1);
            
        // return unboundedKnapsack_mem_01(W, N, wt, val, dp);
        // return unboundedKnapsack_tab_01(W, N, wt, val, dp);

        // nCr method => infinite perm (can also do comb also, no change in ans) => Space Optimized 1D DP
        int[] dp = new int[W+1];
        Arrays.fill(dp, -1);
        
        // return unboundedKnapsack_mem_02(W, wt, val, dp);
        return unboundedKnapsack_tab_02(W, wt, val, dp);
        
    }
    
    public static int unboundedKnapsack_mem_01(int w, int n, int[] wt, int[] val, int[][] dp) {
        if (w == 0 || n == 0) 
            return dp[n][w] = 0;
        
        if (dp[n][w] != -1)
            return dp[n][w];
            
        int max = 0;
        if (w - wt[n-1] >= 0) 
            max = Math.max(max, unboundedKnapsack_mem_01(w - wt[n-1], n, wt, val, dp) + val[n-1]);
        max = Math.max(max, unboundedKnapsack_mem_01(w, n-1, wt, val, dp));
        
        return dp[n][w] = max;
    }
    
    public static int unboundedKnapsack_tab_01(int W, int N, int[] wt, int[] val, int[][] dp) {
        
        for (int n = 0; n <= N; n++) {
            for (int w = 0; w <= W; w++) {
                if (w == 0 || n == 0) {
                    dp[n][w] = 0;
                    continue;
                }
                
                int max = 0;
                if (w - wt[n-1] >= 0) 
                    max = Math.max(max, dp[n][w - wt[n-1]] + val[n-1]);
                max = Math.max(max, dp[n-1][w]);
                
                dp[n][w] = max;
            }
        }
        
        return dp[N][W];
    }

    // Space Optimized 1D DP used => Infinite perm
    public static int unboundedKnapsack_mem_02(int w, int[] wt, int[] val, int[] dp) {
        if (w == 0)
            return dp[w] = 0;
        
        if (dp[w] != -1)
            return dp[w];
            
        int max = 0;
        for (int i = 0; i < wt.length; i++) {
            if (w - wt[i] >= 0) {
                int recAns = unboundedKnapsack_mem_02(w - wt[i], wt, val, dp) + val[i];
                max = Math.max(max, recAns);
            }
        }
        
        return dp[w] = max;
    }
    
    public static int unboundedKnapsack_tab_02(int W, int[] wt, int[] val, int[] dp) {
        for (int w = 0; w <= W; w++) {
            if (w == 0) {
                dp[w] = 0;
                continue;
            }
                
            int max = 0;
            for (int i = 0; i < wt.length; i++) {
                if (w - wt[i] >= 0) {
                    int recAns = unboundedKnapsack_mem_02(w - wt[i], wt, val, dp) + val[i];
                    max = Math.max(max, recAns);
                }
            }
            
            dp[w] = max;
        }
        
        return dp[W];
    }

    // ===================================================
    
    // https://www.geeksforgeeks.org/find-number-of-solutions-of-a-linear-equation-of-n-variables/
    // Same infinite comb => just try to relate it with infinite comb
    public static int infiniteComb_opt_01(int[] coeff, int N, int RHS, int[] dp) {
        int TAR = RHS;
        int[] arr = coeff;
        
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

    // LC 416
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums)
            sum += num;
        
        // We cant find a subset for "odd" sum, since half of odd sum will be decimal val, for which subset cannot exist
        if (sum%2 != 0) // Why %2, because we are finding 2-partitions, if k-partitions, then %k
            return false;
        
        // We now only need to find the subset for next half to equal sum/2 which together forms "sum"
        int n = nums.length, tar = sum/2;
        boolean[][] dp = new boolean[n+1][tar+1];
        
        return subsetSum_tab_00(nums, n, tar, dp);
    }
    
    // Same target sum i.e. Subset Sum approach
    public static boolean subsetSum_tab_00(int[] arr, int N, int TAR, boolean[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar <= TAR; tar++) {
                if (tar == 0 || n == 0) {
                    dp[n][tar] = tar == 0 ? true : false;
                    continue;
                }
                
                boolean res = false;
                
                if (tar - arr[n-1] >= 0)
                    res = res || dp[n-1][tar - arr[n-1]]; // yes
                res = res || dp[n-1][tar]; // no
                
                dp[n][tar] = res;
            }
        }
        
        return dp[N][TAR];
    }

    // ===================================================

    // LC 494
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int ele : nums)
            sum += ele;
        
        if (target > sum || target < -sum) 
            return 0;
        
        int n = nums.length;
        return targetSum_rec(nums, n, target);
    }
    
    public static int targetSum_rec(int[] nums, int n, int tar) {
        if (n == 0) // Since every values of nums has to be used either +ve or -ve
            return tar == 0 ? 1 : 0;
        
        int count = 0;
        count += targetSum_rec(nums, n - 1, tar - nums[n-1]); // +ve
        count += targetSum_rec(nums, n - 1, tar - (-nums[n-1])); // -ve
        
        return count;
    }

    public int findTargetSumWays_01(int[] nums, int target) {
        int sum = 0;
        for (int ele : nums)
            sum += ele;
        
        if (target > sum || target < -sum) 
            return 0;
        
        int n = nums.length;
        int[][] dp = new int[n+1][2*sum+1];
        for (int[] a : dp)
                Arrays.fill(a, -1);
        
        // return targetSum_mem_01(nums, n, target+sum, sum, dp);
        return targetSum_tab_01(nums, n, target+sum, sum, dp);
    }
    
    // From tar to 0(here 0 acts as sum because of shifted indexes)
    public static int targetSum_mem_01(int[] nums, int n, int tar, int sum, int[][] dp) {
        if (n == 0) // Since every values of nums has to be used either +ve or -ve
            return dp[n][tar] = tar == sum ? 1 : 0;
        
        if (dp[n][tar] != -1)
            return dp[n][tar];
        
        int count = 0;
        if (tar - nums[n-1] >= 0) count += targetSum_mem_01(nums, n - 1, tar - nums[n-1], sum, dp); // +ve
        if (tar - (-nums[n-1]) <= 2*sum) count += targetSum_mem_01(nums, n - 1, tar - (-nums[n-1]), sum, dp); // -ve
        
        return dp[n][tar] = count;
    }

    public static int targetSum_tab_01(int[] nums, int N, int TAR, int SUM, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int tar = 0; tar < dp[0].length; tar++) {
                if (n == 0) {
                    dp[n][tar] = tar == SUM ? 1 : 0;                    
                    continue;
                }

                int count = 0;
                if (tar - nums[n-1] >= 0) count += dp[n-1][tar - nums[n-1]];
                if (tar - (-nums[n-1]) <= 2*SUM) count += dp[n-1][tar - (-nums[n-1])];
                dp[n][tar] = count;
            }
        }
        
        return dp[N][TAR];
    }

    public static int findTargetSumWays_02(int[] nums, int target) {
        int sum = 0;
        for (int ele : nums) sum += ele;
        if (target > sum || target < -sum) return 0;
        
        int n = nums.length;
        int[][] dp = new int[n+1][2*sum+1];
        for (int[] a : dp)
                Arrays.fill(a, -1);
        
        int ans = targetSum_mem_02(nums, n, sum, sum+target, dp);
        display2D(dp);
        return ans;
    }
    
    // From 0(here 0 acts as the sum because of shifted indexes) to tar
    public static int targetSum_mem_02(int[] nums, int n, int sum, int tar, int[][] dp) {
        if (n == 0) // Since every values of nums has to be used either +ve or -ve
            return dp[n][sum] = tar == sum ? 1 : 0;
        
        if (dp[n][sum] != -1)
            return dp[n][sum];
        
        int count = 0;
        count += targetSum_mem_02(nums, n - 1, sum + nums[n-1], tar, dp); // +ve
        count += targetSum_mem_02(nums, n - 1, sum - nums[n-1], tar, dp); // -ve
        
        return dp[n][sum] = count;
    }

    // ===================================================

    // LC 698 
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0, maxEle = 0;
        for (int ele : nums) {
            sum += ele;
            maxEle = Math.max(maxEle, ele);
        }
        
        // %k => for k-partitions subset && /k => maxelem should not be > than sum/k
        // Think on this Testcase for k-partitions => nums = [2,2,2,2,1,3], k = 6
        if (sum%k != 0 || maxEle > sum/k) return false;
        
        int n = nums.length, tar = sum/k;
        boolean[] vis = new boolean[n];
        return kEqualSumSubset(nums, 0, k, 0, tar, vis);
    }
    
    public static boolean kEqualSumSubset(int[] nums, int idx, int k, int sumSF, int tar, boolean[] vis) {
        if (k == 0) return true; // We have found all k equal sum subsets
        if (sumSF > tar) return false;
        
        // Found one subset of equal sum now we further call for k-1 equal sum subset
        if (tar == sumSF) 
            return kEqualSumSubset(nums, 0, k-1, 0, tar, vis);
        
        boolean res = false;
        for (int i = idx; i < nums.length; i++) {
            if (vis[i] == true) continue;
            
            vis[i] = true;
            res = res || kEqualSumSubset(nums, i+1, k, sumSF + nums[i], tar, vis);
            vis[i] = false;
        }
        
        return res;
    }

    // Correct approach but TLE
    public boolean canPartitionKSubsets_01(int[] nums, int k) {
        int n = nums.length;
        int[] KSumArr = new int[k];
        
        return KEqualSumSubset_01(nums, n, KSumArr, k);
    }
    
    public static boolean KEqualSumSubset_01(int[] nums, int n, int[] KSumArr, int k) {
        if (n == 0) {
            for (int j = 1; j < k; j++) {
                if (KSumArr[j] != KSumArr[j-1])
                    return false;
            }
            
            return true;
        }
        
        boolean res = false;
        for (int i = 0; i < k; i++) {
            KSumArr[i] += nums[n-1];
            res = res || KEqualSumSubset_01(nums, n-1, KSumArr, k);
            KSumArr[i] -= nums[n-1];
        }
        
        return res;
    }

    // ===================================================

    // LC 688
    public double knightProbability(int n, int k, int row, int column) {
        int dx[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int dy[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
        
        double[][][] dp = new double[k+1][n+1][n+1];
        for (double[][] a : dp)
            for (double[] b : a)
                Arrays.fill(b, -1.0);
        
        // return knightProbability_mem(n, k, row, column, dx, dy, dp);
        return knightProbability_tab(n, k, row, column, dx, dy, dp);
    }
    
    public static double knightProbability_mem(int n, int k, int sr, int sc, int[] dx, int[] dy, double[][][] dp) {
        if (k == 0)
            return dp[k][sr][sc] = 1.0;
        
        if (dp[k][sr][sc] != -1.0)
            return dp[k][sr][sc];
        
        double count = 0.0;
        for (int d = 0; d < 8; d++) {
            int r = sr + dx[d];
            int c = sc + dy[d];
            
            if (r >= 0 && c >= 0 && r < n && c < n) 
                count += knightProbability_mem(n, k-1, r, c, dx, dy, dp); // Safe move within board, so k-1
        }
        
        // Why /8.0 => bcoz we explore 8 directions and for every 8-paths we have to find probability => /8.0
        return dp[k][sr][sc] = count/8.0;
    }

    public static double knightProbability_tab(int n, int K, int SR, int SC, int[] dx, int[] dy, double[][][] dp) {
        for (int k = 0; k <= K; k++) {
            for (int sr = 0; sr < n; sr++) { // Here in this case, we have to get sr, sc start from 0 to n
                for (int sc = 0; sc < n; sc++) {
                    if (k == 0) {
                        dp[k][sr][sc] = 1.0;                        
                        continue;
                    }

                    double count = 0.0;
                    for (int d = 0; d < 8; d++) {
                        int r = sr + dx[d];
                        int c = sc + dy[d];

                        if (r >= 0 && c >= 0 && r < n && c < n) 
                            count += dp[k-1][r][c];
                    }

                    dp[k][sr][sc] = count/8.0;
                }
            }
        }
        
        return dp[K][SR][SC];
    }

    // ===================================================
    
    
    public static void main(String[] args) {
        // Target();
        
        // int N = 3, sum = 4;
        // int[] arr = {2, 2, 3};
        // isSubsetSum(N, arr, sum);
        // int[] dp = new int[sum+1];
        // System.out.println(infiniteComb_opt_01(arr, N-1, sum, dp));

        System.out.println(findTargetSumWays_02(new int[]{1,1,1,1,1}, 3));
    }
}
