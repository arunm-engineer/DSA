import java.util.*;

public class l006_cutType {

    // https://www.geeksforgeeks.org/matrix-chain-multiplication-dp-8/
    // 0 1 2 3 4
    // Testcase => arr = [40, 20, 30, 10, 30]
    public static int matrixMultiplication(int N, int arr[]) {
        int[][] dp = new int[N][N];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        // return mcm_mem(arr, N, 0, N-1, dp);
        return mcm_tab(arr, N, 0, N - 1, dp);
    }

    public static int mcm_mem(int[] arr, int n, int si, int ei, int[][] dp) {
        if (ei - si == 1)
            return dp[si][ei] = 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int minCost = (int) 1e9;
        // ABCDE => (A)(BCDE) ; (AB)(CDE) ; (ABC)(DE) ; (ABCD)(E)
        for (int cut = si + 1; cut < ei; cut++) {
            int lc = mcm_mem(arr, n, si, cut, dp); // left cost / left cut's cost
            int rc = mcm_mem(arr, n, cut, ei, dp); // right cost / right cut's cost
            int cc = arr[si] * arr[cut] * arr[ei]; // curr cost / curr matrices multiplication cost
            // For cur cost (1,4) => (1,3) x (3,4) ; [20 x 10] x [10 x 30] => 20 * 10 * 30
            // steps => st * cut * end
            int totalCost = lc + cc + rc;
            minCost = Math.min(minCost, totalCost);
        }

        return dp[si][ei] = minCost;
    }

    public static int mcm_tab(int[] arr, int N, int SI, int EI, int[][] dp) {
        // Gap Strategy
        for (int gap = 1; gap < N; gap++) { // Starting from gap 0 is of no-use, since not matrix is formed (0,0) or
                                            // (1,1)
            for (int si = 0, ei = gap; ei < N; si++, ei++) {
                if (ei - si == 1) {
                    dp[si][ei] = 0;
                    continue;
                }

                int minCost = (int) 1e9;
                for (int cut = si + 1; cut < ei; cut++) {
                    int lc = dp[si][cut];
                    int rc = dp[cut][ei];
                    int cc = arr[si] * arr[cut] * arr[ei];
                    int totalCost = lc + cc + rc;
                    minCost = Math.min(minCost, totalCost);
                }

                dp[si][ei] = minCost;
            }
        }

        return dp[SI][EI];
    }

    // ===================================================

    // https://www.geeksforgeeks.org/printing-brackets-matrix-chain-multiplication-problem/
    // Same above ques. MCM, just added String DP to print mincost MCM string
    public static String matrixChainOrder(int p[], int n) {
        int[][] dp = new int[n][n];

        return mcm_tab_01(p, n, 0, n - 1, dp);
    }

    public static String mcm_tab_01(int[] arr, int N, int SI, int EI, int[][] dp) {
        String[][] sdp = new String[N][N]; // DP for mincost MCM string
        // Gap Strategy
        for (int gap = 1; gap < N; gap++) { // Starting from gap 0 is of no-use, since not matrix is formed with (0,0)
                                            // or (1,1)
            for (int si = 0, ei = gap; ei < N; si++, ei++) {
                if (ei - si == 1) {
                    dp[si][ei] = 0;
                    sdp[si][ei] = (char) (si + 'A') + "";
                    continue;
                }

                int minCost = (int) 1e9;
                for (int cut = si + 1; cut < ei; cut++) {
                    int lc = dp[si][cut];
                    int rc = dp[cut][ei];
                    int cc = arr[si] * arr[cut] * arr[ei];
                    int totalCost = lc + cc + rc;

                    if (totalCost < minCost) {
                        minCost = totalCost;
                        sdp[si][ei] = "(" + sdp[si][cut] + sdp[cut][ei] + ")"; // (left cut string + right cut string)
                                                                               // => ( (AB)(CD) )
                    }
                }

                dp[si][ei] = minCost;
            }
        }

        return sdp[SI][EI];
    }

    // ===================================================

    // https://www.geeksforgeeks.org/minimum-maximum-values-expression/
    static class MinMaxPair {
        int min = (int) 1e9;
        int max = -(int) 1e9;

        public MinMaxPair() {

        }

        public MinMaxPair(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return "min = " + this.min + " , max = " + this.max;
        }
    }

    public static MinMaxPair evaluateExpr(MinMaxPair lcp, MinMaxPair rcp, char opr) {
        MinMaxPair p = new MinMaxPair();
        if (opr == '+') {
            p.min = lcp.min + rcp.min;
            p.max = lcp.max + rcp.max;
        } else { // '*'
            p.min = lcp.min * rcp.min;
            p.max = lcp.max * rcp.max;
        }

        return p;
    }

    public static MinMaxPair minMaxInExpression_mem(String expr, int si, int ei, MinMaxPair[][] dp) {
        if (si == ei) {
            MinMaxPair base = new MinMaxPair(expr.charAt(si) - '0', expr.charAt(si) - '0');
            return dp[si][ei] = base;
        }

        if (dp[si][ei] != null)
            return dp[si][ei];

        MinMaxPair res = new MinMaxPair();
        for (int cut = si + 1; cut < ei; cut += 2) {
            char opr = expr.charAt(cut);

            MinMaxPair lcp = minMaxInExpression_mem(expr, si, cut - 1, dp); // Left cut MinMaxPair
            MinMaxPair rcp = minMaxInExpression_mem(expr, cut + 1, ei, dp); // Right cut MinMaxPair

            MinMaxPair p = evaluateExpr(lcp, rcp, opr);

            res.min = Math.min(res.min, p.min);
            res.max = Math.max(res.max, p.max);
        }

        return dp[si][ei] = res;
    }

    // FOLLOW UP Questions for above problem
    // 1. If operators give as (+, *, -)
    // 2. If there are more digit numbers ("1112+234*346")

    // ===================================================

    // LC 312
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        return burstBalloons_mem(nums, 0, n - 1, dp);
        // For Tabulation for this problem, simply apply the gap strategy that's it. Why
        // gap strategy => si+ increasing, ei- decreasing ; then copy paste
    }

    public static int burstBalloons_mem(int[] nums, int si, int ei, int[][] dp) {
        int n = nums.length;
        if (dp[si][ei] != -1)
            return dp[si][ei];

        int lEle = (si == 0) ? 1 : nums[si - 1];
        int rEle = (ei == n - 1) ? 1 : nums[ei + 1];
        int maxCoins = 0;
        for (int cut = si; cut <= ei; cut++) {
            int lCost = (cut == si) ? 0 : burstBalloons_mem(nums, si, cut - 1, dp);
            int rCost = (cut == ei) ? 0 : burstBalloons_mem(nums, cut + 1, ei, dp);

            int cCost = lCost + (lEle * nums[cut] * rEle) + rCost;
            maxCoins = Math.max(maxCoins, cCost);
        }

        return dp[si][ei] = maxCoins;
    }

    // For Tabulation for this problem, simply apply the gap strategy that's it. Why
    // gap strategy => si+ increasing, ei- decreasing ; then copy paste

    // ===================================================

    // https://www.geeksforgeeks.org/boolean-parenthesization-problem-dp-37/
    public static int countWays(int N, String S) {
        BoolPair[][] dp = new BoolPair[N][N];

        BoolPair res = booleanParanthesization_mem(S, 0, N - 1, dp);
        return (int) res.totalTrue;
        // For Tabulation for this problem, simply apply the gap strategy that's it. Why
        // gap strategy => si+ increasing, ei- decreasing ; then copy paste
    }

    static class BoolPair {
        long totalTrue;
        long totalFalse;

        public BoolPair() {

        }

        public BoolPair(long totalTrue, long totalFalse) {
            this.totalTrue = totalTrue;
            this.totalFalse = totalFalse;
        }
    }

    public static BoolPair booleanParanthesization_mem(String expr, int si, int ei, BoolPair[][] dp) {
        if (si == ei) {
            char ch = expr.charAt(si);
            long totalTrue = ch == 'T' ? 1 : 0;
            long totalFalse = ch == 'F' ? 1 : 0;
            return dp[si][ei] = new BoolPair(totalTrue, totalFalse);
        }

        if (dp[si][ei] != null)
            return dp[si][ei];

        BoolPair res = new BoolPair();
        for (int cut = si + 1; cut < ei; cut += 2) {
            char opr = expr.charAt(cut);
            BoolPair lCut = booleanParanthesization_mem(expr, si, cut - 1, dp);
            BoolPair rCut = booleanParanthesization_mem(expr, cut + 1, ei, dp);

            evaluate(lCut, rCut, res, opr);
        }

        return dp[si][ei] = res;
    }

    public static void evaluate(BoolPair left, BoolPair right, BoolPair res, char opr) {
        long m = 1003; // MOD value given in question
        long totalTF = (left.totalTrue + left.totalFalse) * (right.totalTrue + right.totalFalse); // Total ways of True
                                                                                                  // & False (all
                                                                                                  // combinations
                                                                                                  // together)
        long totalTrue = 0, totalFalse = 0;
        if (opr == '&') {
            totalTrue = (left.totalTrue * right.totalTrue) % m;
            totalFalse = (totalTF - totalTrue + m) % m;
        } else if (opr == '|') {
            totalFalse = (left.totalFalse * right.totalFalse) % m;
            totalTrue = (totalTF - totalFalse + m) % m;
        } else if (opr == '^') {
            totalTrue = (left.totalTrue * right.totalFalse) % m + (left.totalFalse * right.totalTrue) % m;
            totalFalse = (totalTF - totalTrue + m) % m;
        }

        res.totalTrue = (res.totalTrue + totalTrue) % m; // (other cuts totalTrue ans + curr cut totalTrue ans)
        res.totalFalse = (res.totalFalse + totalFalse) % m;
    }

    // For Tabulation for this problem, simply apply the gap strategy that's it. Why
    // gap strategy => si+ increasing, ei- decreasing ; then copy paste

    // ===================================================

    // https://www.geeksforgeeks.org/optimal-binary-search-tree-dp-24/
    public static int optimalBST() {
        int[] keys = { 10, 12, 20 };
        int[] freq = { 34, 8, 50 };
        int n = keys.length;
        int[][] dp = new int[n][n];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        return optimalBST_mem(keys, freq, 0, n - 1, dp);
        // For Tabulation for this problem, simply apply the gap strategy that's it. Why
        // gap strategy => si+ increasing, ei- decreasing ; then copy paste
    }

    public static int optimalBST_mem(int[] keys, int[] freq, int si, int ei, int[][] dp) {
        if (dp[si][ei] != -1)
            return dp[si][ei];

        int minCost = (int) 1e9;
        int sum = 0; // For substree sum of range (si to ei)
        for (int cut = si; cut <= ei; cut++) {
            int lCost = (cut == si) ? 0 : optimalBST_mem(keys, freq, si, cut - 1, dp);
            int rCost = (cut == ei) ? 0 : optimalBST_mem(keys, freq, cut + 1, ei, dp);

            int cCost = lCost + (0) + rCost; // (0) in between of sum of subtree, since sum is not ready yet, will add
                                             // at last while returing. Using an extra loop outside is useless
                                             // complexity
            minCost = Math.min(minCost, cCost);
            sum += freq[cut]; // calculating the sum for range (si to ei) => sum of the substree in the same
                              // loop itself.
            // So sum is not prepared yet. Will add it at last while returning. ===>
            // min(A+K, B+K, C+K) = min(A,B,C) + K
        }

        return dp[si][ei] = minCost + sum; // Adding sum of subtree at last
    }

    // For Tabulation for this problem, simply apply the gap strategy that's it. Why
    // gap strategy => si+ increasing, ei- decreasing ; then copy paste

    // ===================================================

    // LC 1039
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        return minTriangle_mem(values, 0, n - 1, dp);
        // For Tabulation for this problem, simply apply the gap strategy that's it. Why
        // gap strategy => si+ increasing, ei- decreasing ; then copy paste
    }

    public static int minTriangle_mem(int[] values, int si, int ei, int[][] dp) {
        if (ei - si == 1)
            return dp[si][ei] = 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int minCost = (int) 1e9;
        for (int cut = si + 1; cut < ei; cut++) {
            int lCost = minTriangle_mem(values, si, cut, dp);
            int rCost = minTriangle_mem(values, cut, ei, dp);

            int cCost = lCost + (values[si] * values[cut] * values[ei]) + rCost;
            minCost = Math.min(minCost, cCost);
        }

        return dp[si][ei] = minCost;
    }

    // For Tabulation for this problem, simply apply the gap strategy that's it. Why
    // gap strategy => si+ increasing, ei- decreasing ; then copy paste

    // ===================================================

    // LC 95
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1, n);
    }
    
    public static List<TreeNode> generateTrees(int si, int ei) {
        List<TreeNode> list = new ArrayList<>();
        
        if (si > ei) {
            list.add(null);
            return list;
        }
        
        for (int cut = si; cut <= ei; cut++) { 
            List<TreeNode> leftList = generateTrees(si, cut-1);
            List<TreeNode> rightList = generateTrees(cut+1, ei);
            
            // Now creating "combinations" to create all forms of tree
            for (TreeNode lnode : leftList) {
                for (TreeNode rnode : rightList) {
                    TreeNode root = new TreeNode(cut);
                    root.left = lnode;
                    root.right = rnode;
                    
                    list.add(root);
                }
            }
        }
        
        return list;
    }

    public List<TreeNode> generateTrees_00(int n) {
        // Why n+2, since the n=4 i.e. (1234) ; Out of bounds can be 0 & 5. So we need to include both of these in the DP range
        List<TreeNode>[][] dp = new ArrayList[n+2][n+2]; 
        return generateTrees_mem_00(1, n, dp);
    }
    
    public static List<TreeNode> generateTrees_mem_00(int si, int ei, List<TreeNode>[][] dp) {
        List<TreeNode> list = new ArrayList<>();
        if (si > ei) {
            list.add(null);
            return dp[si][ei] = list;
        }
        
        if (dp[si][ei] != null)
            return dp[si][ei];
        
        for (int cut = si; cut <= ei; cut++) { 
            List<TreeNode> leftList = generateTrees_mem_00(si, cut-1, dp);
            List<TreeNode> rightList = generateTrees_mem_00(cut+1, ei, dp);
            
            for (TreeNode lnode : leftList) {
                for (TreeNode rnode : rightList) {
                    TreeNode root = new TreeNode(cut);
                    root.left = lnode;
                    root.right = rnode;
                    
                    list.add(root);
                }
            }
        }
        
        return dp[si][ei] = list;
    }

    // ===================================================

    // LC 576
    static int mod = (int)1e9 + 7;
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int[][] dir = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };
        int[][][] dp = new int[m][n][maxMove+1];
        for (int[][] a : dp) {
            for (int[] b : a)
                Arrays.fill(b, -1);
        }
        
        return findPaths_mem(m, n, startRow, startColumn, maxMove, dir, dp);
    }
    
    public static int findPaths_mem(int n, int m, int sr, int sc, int k, int[][] dir, int[][][] dp) {
        if (sr < 0 || sc < 0 || sr == n || sc == m)
            return 1; // Time will not matter a lot as we are not storing this state in DP, since this is just a base case, important are the intermediate states. If at all we want to store, we can manipulate with DP size.
        
        if (k == 0)
            return 0;
        
        if (dp[sr][sc][k] != -1)
            return dp[sr][sc][k];
        
        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            count = (count%mod + findPaths_mem(n, m, r, c, k-1, dir, dp)%mod) % mod;
        }
        
        return dp[sr][sc][k] = count;
    }

    // ===================================================

    public static void main(String[] args) {
        // String expr = "1+2*3+4*5";
        // int n = expr.length();
        // MinMaxPair[][] dp = new MinMaxPair[n][n];
        // System.out.println(minMaxInExpression_mem(expr, 0, n-1, dp));

        // System.out.println(optimalBST());
    }
}
