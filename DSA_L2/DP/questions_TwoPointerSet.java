import java.util.Arrays;

public class questions_TwoPointerSet {

    // LC 62
    public int uniquePaths(int m, int n) {
        int sr = 0, sc = 0, er = m - 1, ec = n - 1;
        int[][] dp = new int[er + 1][ec + 1];
        int[][] dir2 = { { 0, 1 }, { 1, 0 } };

        return mazePath1Jump_mem(sr, sc, er, ec, dir2, dp);
    }

    public static int mazePath1Jump_mem(int sr, int sc, int er, int ec, int[][] dir, int[][] dp) {
        if (sr == er && sc == ec)
            return dp[sr][sc] = 1;

        if (dp[sr][sc] != 0)
            return dp[sr][sc];

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if (r >= 0 && c >= 0 && r <= er && c <= ec) {
                count += mazePath1Jump_mem(r, c, er, ec, dir, dp);
            }
        }

        return dp[sr][sc] = count;
    }

    // TC : O(3n^3)
    public static int mazePath1Jump_tab(int SR, int SC, int ER, int EC, int[][] dir, int[][] dp) {

        for (int sr = ER; sr >= SR; sr--) {
            for (int sc = EC; sc >= SC; sc--) {
                if (sr == ER && sc == EC) {
                    dp[sr][sc] = 1;
                    continue;
                }

                int count = 0;
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    if (r >= 0 && c >= 0 && r <= ER && c <= EC)
                        count += dp[r][c];
                }
                dp[sr][sc] = count;
            }
        }

        return dp[SR][SC];
    }

    // ==============================================

    // LC 63
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int sr = 0, sc = 0, er = m - 1, ec = n - 1;
        int[][] dp = new int[er + 1][ec + 1];
        int[][] dir2 = { { 0, 1 }, { 1, 0 } };

        if (obstacleGrid[sr][sc] == 1 || obstacleGrid[er][ec] == 1)
            return 0;
        return mazePath1Jump_mem(obstacleGrid, sr, sc, er, ec, dir2, dp);
    }

    public static int mazePath1Jump_mem(int[][] grid, int sr, int sc, int er, int ec, int[][] dir, int[][] dp) {
        if (sr == er && sc == ec)
            return dp[sr][sc] = 1;

        if (dp[sr][sc] != 0)
            return dp[sr][sc];

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if (r >= 0 && c >= 0 && r <= er && c <= ec && grid[r][c] != 1) {
                count += mazePath1Jump_mem(grid, r, c, er, ec, dir, dp);
            }
        }

        return dp[sr][sc] = count;
    }

    // TC : O(3n^3)
    public static int mazePath1Jump_tab(int[][] grid, int SR, int SC, int ER, int EC, int[][] dir, int[][] dp) {

        for (int sr = ER; sr >= SR; sr--) {
            for (int sc = EC; sc >= SC; sc--) {
                if (sr == ER && sc == EC) {
                    dp[sr][sc] = 1;
                    continue;
                }

                int count = 0;
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    if (r >= 0 && c >= 0 && r <= ER && c <= EC && grid[r][c] != 1)
                        count += dp[r][c];
                }
                dp[sr][sc] = count;
            }
        }

        return dp[SR][SC];
    }

    // ==============================================

    // LC 70
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];

        return climbStairs_mem(n, dp);
    }

    public static int climbStairs_mem(int n, int[] dp) {
        if (n == 0)
            return dp[n] = 1;

        if (dp[n] != 0)
            return dp[n];

        int count = 0;
        for (int jump = 1; jump <= 2; jump++) {
            if (n - jump >= 0)
                count += climbStairs_mem(n - jump, dp);
        }

        return dp[n] = count;
    }

    public static int climbStairs_tab(int N, int[] dp) {
        for (int n = 0; n <= N; n++) {
            if (n == 0) {
                dp[n] = 1;
                continue;
            }

            int count = 0;
            for (int jump = 1; jump <= 2; jump++) {
                if (n - jump >= 0)
                    count += dp[n - jump];
            }

            dp[n] = count;
        }

        return dp[N];
    }

    // ==============================================

    // LC 746
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        return minCost_mem(n, dp, cost);
        // return minCost_tab(n, dp, cost);
    }

    public static int minCost_mem(int n, int[] dp, int[] cost) {
        if (n <= 1)
            return dp[n] = cost[n];

        if (dp[n] != -1)
            return dp[n];

        int curCost = n < cost.length ? cost[n] : 0;
        int ans = Math.min(minCost_mem(n - 1, dp, cost), minCost_mem(n - 2, dp, cost)) + curCost;

        return dp[n] = ans;
    }

    public static int minCost_tab(int N, int[] dp, int[] cost) {
        for (int n = 0; n <= N; n++) {
            if (n <= 1) {
                dp[n] = cost[n];
                continue;
            }

            int curCost = n < cost.length ? cost[n] : 0;
            int ans = Math.min(dp[n - 1], dp[n - 2]) + curCost;
            dp[n] = ans;
        }

        return dp[N];
    }

    // ==============================================

    // LC 91
    public int numDecodings_01(String s) {
        int[] dp = new int[s.length() + 1];
        Arrays.fill(dp, -1);

        // return decodeWays_mem(s, 0, dp);
        // return decodeWays_tab(s, 0, dp);
        return decodeWays_opt(s);
    }

    // T O(n), S O(n)
    public static int decodeWays_mem(String str, int i, int[] dp) {
        int n = str.length();
        if (i == n)
            return dp[i] = 1;

        if (dp[i] != -1)
            return dp[i];

        int ch1 = str.charAt(i) - '0';
        if (ch1 == 0)
            return dp[i] = 0;
        int count = decodeWays_mem(str, i + 1, dp);

        if (i < n - 1) {
            int ch2 = (str.charAt(i) - '0') * 10 + str.charAt(i + 1) - '0';
            if (ch2 >= 1 && ch2 <= 26)
                count += decodeWays_mem(str, i + 2, dp);
        }

        return dp[i] = count;
    }

    public static int decodeWays_tab(String str, int I, int[] dp) {
        int N = str.length();
        for (int i = N; i >= 0; i--) {
            if (i == N) {
                dp[i] = 1;
                continue;
            }

            int ch1 = str.charAt(i) - '0';
            if (ch1 == 0) {
                dp[i] = 0;
                continue;
            }
            int count = dp[i + 1]; // decodeWays_mem(str, i+1, dp);

            if (i < N - 1) {
                int ch2 = (str.charAt(i) - '0') * 10 + str.charAt(i + 1) - '0';
                if (ch2 >= 1 && ch2 <= 26)
                    count += dp[i + 2]; // decodeWays_mem(str, i+2, dp);
            }

            dp[i] = count;
        }

        return dp[I];
    }

    public static int decodeWays_opt(String str) {
        int N = str.length();
        int a = 1, b = 0; // a = 1 since one character always forms one;
        for (int i = N - 1; i >= 0; i--) {
            int sum = 0;

            int ch1 = str.charAt(i) - '0';
            if (ch1 != 0) {
                sum += a;

                if (i < N - 1) {
                    int ch2 = (str.charAt(i) - '0') * 10 + str.charAt(i + 1) - '0';
                    if (ch2 >= 1 && ch2 <= 26)
                        sum += b;
                }
            }

            b = a;
            a = sum;
        }

        return a;
    }

    // ==============================================

    // LC 639
    static int mod = (int) 1e9 + 7;

    public int numDecodings(String s) {
        // Why long? => Since answer will overflow first, then bring in int range by mod
        int n = s.length();
        long[] dp = new long[n + 1];
        Arrays.fill(dp, -1);

        // int ans = (int) decodeWays2_mem(s, 0, dp);
        // int ans = (int) decodeWays2_tab(s, 0, dp);
        int ans = (int) decodeWays2_opt(s);

        return ans;
    }

    public static long decodeWays2_mem(String str, int i, long[] dp) {
        int n = str.length();
        if (i == n)
            return dp[i] = 1;

        if (dp[i] != -1)
            return dp[i];

        long count = 0;
        char ch1 = str.charAt(i);

        if (ch1 == '0')
            return dp[i] = 0;

        // * => 1-9
        if (ch1 == '*') {
            count = (count + 9 * decodeWays2_mem(str, i + 1, dp)) % mod; // One char case
            if (i < n - 1) { // Two chars case
                char ch2 = str.charAt(i + 1);
                if (ch2 >= '0' && ch2 <= '6') // 2 possibility => 10-16, 20-26
                    count = (count + 2 * decodeWays2_mem(str, i + 2, dp)) % mod;
                else if (ch2 >= '7' && ch2 <= '9') // 1 poss => 17-19; (27,28 => not valid)
                    count = (count + 1 * decodeWays2_mem(str, i + 2, dp)) % mod;
                else // * => 15 poss => 11-26
                    count = (count + 15 * decodeWays2_mem(str, i + 2, dp)) % mod;
            }
        } else { // ch1 == c => any digit char
            count = (count + 1 * decodeWays2_mem(str, i + 1, dp)) % mod; // One char case
            if (i < n - 1) { // Two chars case
                char ch2 = str.charAt(i + 1);
                if (ch1 == '1' && ch2 == '*') // 11-19
                    count = (count + 9 * decodeWays2_mem(str, i + 2, dp)) % mod;
                else if (ch1 == '2' && ch2 == '*')
                    count = (count + 6 * decodeWays2_mem(str, i + 2, dp)) % mod;
                else if (ch2 != '*') { // any fixed 2 digits, eg:
                    int num = (ch1 - '0') * 10 + (ch2 - '0');
                    if (num <= 26)
                        count = (count + 1 * decodeWays2_mem(str, i + 2, dp)) % mod;
                }
            }
        }

        return dp[i] = count;
    }

    public static long decodeWays2_tab(String str, int I, long[] dp) {
        int N = str.length();
        for (int i = N; i >= 0; i--) {
            if (i == N) {
                dp[i] = 1;
                continue;
            }

            long count = 0;
            char ch1 = str.charAt(i);

            if (ch1 == '0') {
                dp[i] = 0;
                continue;
            }

            // * => 1-9
            if (ch1 == '*') {
                count = (count + 9 * dp[i + 1]) % mod; // One char case
                if (i < N - 1) { // Two chars case
                    char ch2 = str.charAt(i + 1);
                    if (ch2 >= '0' && ch2 <= '6') // 2 possibility => 10-16, 20-26
                        count = (count + 2 * dp[i + 2]) % mod;
                    else if (ch2 >= '7' && ch2 <= '9') // 1 poss => 17-19; (27,28 => not valid)
                        count = (count + 1 * dp[i + 2]) % mod;
                    else // * => 15 poss => 11-26
                        count = (count + 15 * dp[i + 2]) % mod;
                }
            } else { // ch1 == c => any digit char
                count = (count + 1 * dp[i + 1]) % mod; // One char case
                if (i < N - 1) { // Two chars case
                    char ch2 = str.charAt(i + 1);
                    if (ch1 == '1' && ch2 == '*') // 11-19
                        count = (count + 9 * dp[i + 2]) % mod;
                    else if (ch1 == '2' && ch2 == '*')
                        count = (count + 6 * dp[i + 2]) % mod;
                    else if (ch2 != '*') { // any fixed 2 digits, eg:
                        int num = (ch1 - '0') * 10 + (ch2 - '0');
                        if (num <= 26)
                            count = (count + 1 * dp[i + 2]) % mod;
                    }
                }
            }
            dp[i] = count;
        }

        return dp[I];
    }

    // SC O(1) TC O(n)
    public static long decodeWays2_opt(String str) {
        int N = str.length();
        long a = 1, b = 0;
        for (int i = N - 1; i >= 0; i--) {
            long count = 0;
            char ch1 = str.charAt(i);

            if (ch1 != '0') {
                // * => 1-9
                if (ch1 == '*') {
                    count = (count + 9 * a) % mod; // One char case
                    if (i < N - 1) { // Two chars case
                        char ch2 = str.charAt(i + 1);
                        if (ch2 >= '0' && ch2 <= '6') // 2 possibility => 10-16, 20-26
                            count = (count + 2 * b) % mod;
                        else if (ch2 >= '7' && ch2 <= '9') // 1 poss => 17-19; (27,28 => not valid)
                            count = (count + 1 * b) % mod;
                        else // * => 15 poss => 11-26
                            count = (count + 15 * b) % mod;
                    }
                } else { // ch1 == c => any digit char
                    count = (count + 1 * a) % mod; // One char case
                    if (i < N - 1) { // Two chars case
                        char ch2 = str.charAt(i + 1);
                        if (ch1 == '1' && ch2 == '*') // 11-19
                            count = (count + 9 * b) % mod;
                        else if (ch1 == '2' && ch2 == '*')
                            count = (count + 6 * b) % mod;
                        else if (ch2 != '*') { // any fixed 2 digits, eg:
                            int num = (ch1 - '0') * 10 + (ch2 - '0');
                            if (num <= 26)
                                count = (count + 1 * b) % mod;
                        }
                    }
                }
            }

            b = a;
            a = count;
        }

        return a;
    }

    // ==============================================

    // https://www.geeksforgeeks.org/gold-mine-problem/
    public static int maxGold(int n, int m, int grid[][]) {
        int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
        int[][] dp = new int[n][m];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);

        // for (int i = 0; i < grid.length; i++)
        // goldmine_mem(i, 0, grid, dir, dp);

        goldmine_tab(grid, dir, dp);

        int maxGold = -(int) 1e9;
        for (int i = 0; i < grid.length; i++)
            maxGold = Math.max(maxGold, dp[i][0]);

        return maxGold;
    }

    public static int goldmine_mem(int sr, int sc, int[][] grid, int[][] dir, int[][] dp) {
        int n = grid.length, m = grid[0].length;
        if (sc == m - 1)
            return dp[sr][sc] = grid[sr][sc];

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int maxGold = -(int) 1e9;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && r <= n - 1 && c >= 0 && c <= m - 1)
                maxGold = Math.max(maxGold, goldmine_mem(r, c, grid, dir, dp) + grid[sr][sc]);
        }

        return dp[sr][sc] = maxGold;
    }

    public static void goldmine_tab(int[][] grid, int[][] dir, int[][] dp) {
        int n = grid.length, m = grid[0].length;

        for (int sc = m - 1; sc >= 0; sc--) {
            for (int sr = 0; sr <= n - 1; sr++) {
                if (sc == m - 1) {
                    dp[sr][sc] = grid[sr][sc];
                    continue;
                }

                int maxGold = -(int) 1e9;
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && r <= n - 1 && c >= 0 && c <= m - 1)
                        maxGold = Math.max(maxGold, dp[r][c] + grid[sr][sc]);
                }

                dp[sr][sc] = maxGold;
            }
        }
    }

    // ==============================================

    // https://www.geeksforgeeks.org/min-cost-path-dp-6/
    public static void minCostPath(int n, int m, int[][] grid) {
        int[][] dir = { { 0, 1 }, { 1, 1 }, { 1, 0 } };
        int[][] dp = new int[n][m];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);

        // System.out.println(minCostPath_mem(0, 0, grid, dir, dp));
        System.out.println(minCostPath_tab(0, 0, grid, dir, dp));

    }

    public static int minCostPath_mem(int sr, int sc, int[][] grid, int[][] dir, int[][] dp) {
        int n = grid.length, m = grid[0].length;
        if (sr == n - 1 && sc == m - 1)
            return dp[sr][sc] = grid[sr][sc];

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int minCost = (int) 1e9;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && r <= n - 1 && c >= 0 && c <= m - 1)
                minCost = Math.min(minCost, minCostPath_mem(r, c, grid, dir, dp) + grid[sr][sc]);
        }

        return dp[sr][sc] = minCost;
    }

    public static int minCostPath_tab(int SR, int SC, int[][] grid, int[][] dir, int[][] dp) {
        int n = grid.length, m = grid[0].length;

        for (int sr = n - 1; sr >= 0; sr--) {
            for (int sc = m - 1; sc >= 0; sc--) {
                if (sr == n - 1 && sc == m - 1) {
                    dp[sr][sc] = grid[sr][sc];
                    continue;
                }

                int minCost = (int) 1e9;
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && r <= n - 1 && c >= 0 && c <= m - 1)
                        minCost = Math.min(minCost, dp[r][c] + grid[sr][sc]);
                }

                dp[sr][sc] = minCost;
            }
        }

        return dp[SR][SC];
    }

    // ==============================================

    // https://www.geeksforgeeks.org/friends-pairing-problem/
    // static int mod = (int)1e9 + 7;
    public static long countFriendsPairings(int n) { 
       //code here
       long[] dp = new long[n+1];
       Arrays.fill(dp, -1);
       
       long ans = 0;
    //   ans = friendsPair_mem(n, dp);
    //   ans = friendsPair_tab(n, dp);
      ans = friendsPair_opt(n);
       return ans;
    }
    
    public static long friendsPair_mem(int n, long[] dp) {
        if (n <= 1)
            return dp[n] = 1;
            
        if (dp[n] != -1)
            return dp[n];
        
        long count = 0; 
        long single = 1 * friendsPair_mem(n-1, dp); // Single 
        long pairUp = (n-1) * friendsPair_mem(n-2, dp); // Paired up, why n-1 since leaving himself and pairs with everyone 1-by-1

        count = (single%mod + pairUp%mod) % mod;
        
        return dp[n] = count;
    }
    
    public static long friendsPair_tab(int N, long[] dp) {
        for (int n = 0; n <= N; n++) {
            if (n <= 1) {
                dp[n] = 1;
                continue;
            }
            
            long count = 0;
            long single = 1 * dp[n-1];
            long pairUp = (n-1) * dp[n-2];

            count = (single%mod + pairUp%mod) % mod;
            
            dp[n] = count;
        }
        
        return dp[N];
    }
    
    public static long friendsPair_opt(int N) {
        long a = 0, b = 1;
        for (int n = 1; n <= N; n++) {
            long count = 0;
            long single = 1 * b;
            long pairUp = (n-1) * a;

            count = (single%mod + pairUp%mod) % mod;
            
            a = b;
            b = count;
        }
        
        return b;
    }

    // ==============================================

    public static void main(String[] args) {
        int[][] grid = { { 1, 2, 3 },
                        { 4, 8, 2 },
                        { 1, 5, 3 } };

        minCostPath(3, 3, grid);
    }
}