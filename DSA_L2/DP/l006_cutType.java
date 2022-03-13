import java.util.*;

public class l006_cutType {

    // https://www.geeksforgeeks.org/matrix-chain-multiplication-dp-8/
    //                     0   1   2   3   4
    // Testcase => arr = [40, 20, 30, 10, 30]
    public static int matrixMultiplication(int N, int arr[]) {
        int[][] dp = new int[N][N];
        for (int[] a : dp) 
            Arrays.fill(a, -1);
            
        // return mcm_mem(arr, N, 0, N-1, dp);
        return mcm_tab(arr, N, 0, N-1, dp);
    }
    
    public static int mcm_mem(int[] arr, int n, int si, int ei, int[][] dp) {
        if (ei-si == 1)
            return dp[si][ei] = 0;
        
        if (dp[si][ei] != -1)
            return dp[si][ei];
            
        int minCost = (int) 1e9;
        // ABCDE => (A)(BCDE) ; (AB)(CDE) ; (ABC)(DE) ; (ABCD)(E)
        for (int cut = si+1; cut < ei; cut++) {
            int lc = mcm_mem(arr, n, si, cut, dp); // left cost / left cut's cost
            int rc = mcm_mem(arr, n, cut, ei, dp); // right cost / right cut's cost
            int cc = arr[si] * arr[cut] * arr[ei]; // curr cost / curr matrices multiplication cost
            // For cur cost (1,4) => (1,3) x (3,4) ; [20 x 10] x [10 x 30] => 20 * 10 * 30 steps => st * cut * end
            int totalCost = lc + cc + rc;
            minCost = Math.min(minCost, totalCost);
        }
        
        return dp[si][ei] = minCost;
    }

    public static int mcm_tab(int[] arr, int N, int SI, int EI, int[][] dp) {
        // Gap Strategy
        for (int gap = 1; gap < N; gap++) { // Starting from gap 0 is of no-use
            for (int si = 0, ei = gap; si < N && ei < N; si++, ei++) {
                if (ei-si == 1) {
                    dp[si][ei] = 0;
                    continue;
                }
                   
                int minCost = (int) 1e9;
                for (int cut = si+1; cut < ei; cut++) {
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

    public static void main(String[] args) {
        
    }
}
