import java.util.*;

public class questions_StringSet {

    // LC 516
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        
        // return lpss_mem(s, 0, n-1, dp);
        return lpss_tab(s, 0, n-1, dp);
    }
    
    public static int lpss_mem(String s, int i, int j, int[][] dp) {
        if (i >= j)
            return dp[i][j] = i == j ? 1 : 0;
        
        if (dp[i][j] != 0)
            return dp[i][j];
        
        int count = 0;
        if (s.charAt(i) == s.charAt(j))
            count = 2 + lpss_mem(s, i+1, j-1, dp);
        else
            count = Math.max(lpss_mem(s, i+1, j, dp), lpss_mem(s, i, j-1, dp));
        
        return dp[i][j] = count;
    }
    
    public static int lpss_tab(String s, int I, int J, int[][] dp) {
        int n = s.length();
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (i >= j) {
                    dp[i][j] = i == j ? 1 : 0;
                    continue;
                }

                int count = 0;
                if (s.charAt(i) == s.charAt(j))
                    count = 2 + dp[i+1][j-1];
                else
                    count = Math.max(dp[i+1][j], dp[i][j-1]);

                dp[i][j] = count;
            }
        }
        
        return dp[I][J];
    }

    // ===========================================

    // LC 1143
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n+1][m+1];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);
        
        // return lcss_mem(text1, text2, n, m, dp);
        return lcss_tab(text1, text2, n, m, dp);
    }
    
    // Can do interms of i, j as well solving interms of index
    // But solve interms of length => n, m to mke base case easy
    public static int lcss_mem(String s1, String s2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0)
            return dp[n][m] = 0;
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        int count = 0;
        if (s1.charAt(n-1) == s2.charAt(m-1))
            count = 1 + lcss_mem(s1, s2, n-1, m-1, dp);
        else 
            count = Math.max(lcss_mem(s1, s2, n-1, m, dp), lcss_mem(s1, s2, n, m-1, dp));
        
        return dp[n][m] = count;
    }
    
    public static int lcss_tab(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                int count = 0;
                if (s1.charAt(n-1) == s2.charAt(m-1))
                    count = 1 + dp[n-1][m-1];
                else 
                    count = Math.max(dp[n-1][m], dp[n][m-1]);

                dp[n][m] = count;
            }
        }
        
        return dp[N][M];
    }

    // ===========================================

    // LC 115
    public int numDistinct(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] dp = new int[n+1][m+1];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);
        
        // return distinctSs_mem(s, t, n, m, dp);
        return distinctSs_tab(s, t, n, m, dp);
    }
    
    public static int distinctSs_mem(String s1, String s2, int n, int m, int[][] dp) {
        if (m == 0)
            return dp[n][m] = 1;
        
        if (n < m)
            return dp[n][m] = 0;
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        int count = 0;
        if (s1.charAt(n-1) == s2.charAt(m-1)) 
            // Why (n-1, m) call, bcoz we may again find the occurence of s2 in s1
            count = distinctSs_mem(s1, s2, n-1, m-1, dp) + distinctSs_mem(s1, s2, n-1, m, dp);
        else 
            count = distinctSs_mem(s1, s2, n-1, m, dp);
        
        return dp[n][m] = count;
    }
    
    public static int distinctSs_tab(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (m == 0) {
                    dp[n][m] = 1;
                    continue;
                }

                if (n < m) {
                    dp[n][m] = 0;
                    continue;
                }

                int count = 0;
                if (s1.charAt(n-1) == s2.charAt(m-1)) 
                    count = dp[n-1][m-1] + dp[n-1][m];
                else 
                    count = dp[n-1][m];

                dp[n][m] = count;
            }
        }
        
        return dp[N][M];
    }

    // ===========================================

    // LC 72
    public int minDistance(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
        
        // return editDistance_mem(word1, word2, n, m, dp);
        return editDistance_tab(word1, word2, n, m, dp);
    }
    
    public static int editDistance_mem(String s1, String s2, int n, int m, int[][] dp) {
        // Covered all 3 base cases by below one line => n=0 & m=0, n=0 & m!=0, n!=0 & m=0
        if (n == 0 || m == 0)
            return dp[n][m] = (n == 0 ? m : n);
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        int count = 0;
        if (s1.charAt(n-1) == s2.charAt(m-1)) {
            count = editDistance_mem(s1, s2, n-1, m-1, dp);
        }
        else {
            int insert = editDistance_mem(s1, s2, n, m-1, dp);
            int delete = editDistance_mem(s1, s2, n-1, m, dp);
            int replace = editDistance_mem(s1, s2, n-1, m-1, dp);
            // Here added 1 for any one operation performed here (i, d, r)
            count = Math.min(Math.min(insert, delete), replace) + 1;
        }
        
        return dp[n][m] = count;
    }
    
    public static int editDistance_tab(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = (n == 0 ? m : n);
                    continue;
                }

                int count = 0;
                if (s1.charAt(n-1) == s2.charAt(m-1)) {
                    count = dp[n-1][m-1];
                }
                else {
                    int insert = dp[n][m-1];
                    int delete = dp[n-1][m];
                    int replace = dp[n-1][m-1];
                    // Here added 1 for any one operation performed here (i, d, r)
                    count = Math.min(Math.min(insert, delete), replace) + 1;
                }

                dp[n][m] = count;
            }
        }
        
        return dp[N][M];
    }

    // ===========================================
}
