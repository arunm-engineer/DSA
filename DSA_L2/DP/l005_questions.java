import java.util.*;

public class l005_questions {

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
    
}
