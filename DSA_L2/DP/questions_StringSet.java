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
        
        // return distinctSubsq_mem(s, t, n, m, dp);
        return distinctSubsq_tab(s, t, n, m, dp);
    }
    
    public static int distinctSubsq_mem(String s1, String s2, int n, int m, int[][] dp) {
        if (m == 0)
            return dp[n][m] = 1;
        
        if (n < m)
            return dp[n][m] = 0;
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        int count = 0;
        if (s1.charAt(n-1) == s2.charAt(m-1)) 
            // Why (n-1, m) call, bcoz we may again find the occurence of s2 in s1
            count = distinctSubsq_mem(s1, s2, n-1, m-1, dp) + distinctSubsq_mem(s1, s2, n-1, m, dp);
        else 
            count = distinctSubsq_mem(s1, s2, n-1, m, dp);
        
        return dp[n][m] = count;
    }
    
    public static int distinctSubsq_tab(String s1, String s2, int N, int M, int[][] dp) {
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

    // Two Follow up for above LC 72 question
    // Edit distance modified questions => Refer notes for clarity of questions
    public static int minDistance_01(String word1, String word2) {
        //                       i, d, r
        int[] operationsCost = { 1, 4, 7 };

        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
        
        return editDistance_mem_01(word1, word2, n, m, dp, operationsCost);
        // return editDistance_tab(word1, word2, n, m, dp);
    }

    public static int editDistance_mem_01(String s1, String s2, int n, int m, int[][] dp, int[] cost) {
        // Covered all 3 base cases by below one line => n=0 & m=0, n=0 & m!=0, n!=0 & m=0
        if (n == 0 && m == 0)
            return dp[n][m] = 0;
        else if (n == 0)
            return dp[n][m] = m * cost[0]; // m insert's
        else if (m == 0)
            return dp[n][m] = n * cost[1]; // n delete's
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        int count = 0;
        if (s1.charAt(n-1) == s2.charAt(m-1)) {
            count = editDistance_mem_01(s1, s2, n-1, m-1, dp, cost);
        }
        else {
            // Here added cost for any one operation performed here (i, d, r)
            int insert = editDistance_mem_01(s1, s2, n, m-1, dp, cost) + cost[0]; // insert cost
            int delete = editDistance_mem_01(s1, s2, n-1, m, dp, cost) + cost[1]; // delete cost
            int replace = editDistance_mem_01(s1, s2, n-1, m-1, dp, cost) + cost[2]; // replace cost
            count = Math.min(Math.min(insert, delete), replace);
        }
        
        return dp[n][m] = count;
    }

    public static int minDistance_02(String word1, String word2) {
        //                           a, b, c, d, ......, z
        int[][] operationsCost = {  {1, 2, 7, 3}, // i
                                    {2, 2, 3, 1}, // d
                                    {6, 2, 3, 5}  // r
                                };

        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
        
        return editDistance_mem_02(word1, word2, n, m, dp, operationsCost);
        // return editDistance_tab(word1, word2, n, m, dp);
    }

    public static int editDistance_mem_02(String s1, String s2, int n, int m, int[][] dp, int[][] cost) {
        // Covered all 3 base cases by below one line => n=0 & m=0, n=0 & m!=0, n!=0 & m=0
        if (n == 0 && m == 0)
            return dp[n][m] = 0;
        else if (n == 0) {
            int baseCost = 0;
            for (int j = m-1; j >= 0; j--) {
                char ch = s2.charAt(j);
                baseCost += cost[0][ch-'a'];
            }

            return dp[n][m] = baseCost; // m insert's
        }
        else if (m == 0) {
            int baseCost = 0;
            for (int i = n-1; i >= 0; i--) {
                char ch = s1.charAt(i);
                baseCost += cost[1][ch-'a'];
            }
                
            return dp[n][m] = baseCost; // n delete's
        }
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        char ch1 = s1.charAt(n-1);
        char ch2 = s2.charAt(m-1);

        int count = 0;
        if (ch1 == ch2) {
            count = editDistance_mem_02(s1, s2, n-1, m-1, dp, cost);
        }
        else {
            int insert = editDistance_mem_02(s1, s2, n, m-1, dp, cost) + cost[0][ch2-'a']; // insert cost
            int delete = editDistance_mem_02(s1, s2, n-1, m, dp, cost) + cost[1][ch1-'a']; // delete cost
            int replace = editDistance_mem_02(s1, s2, n-1, m-1, dp, cost) + cost[2][ch1-'a']; // replace cost
            // Here added cost for any one operation performed here (i, d, r)
            count = Math.min(Math.min(insert, delete), replace);
        }
        
        return dp[n][m] = count;
    }

    // ===========================================

    // LC 44
    public boolean isMatch(String s, String p) {
        p = removeDuplicateStars(p);
        
        int n = s.length(), m = p.length();
        // Since both true & false are part of answer using int dp so seperate meanings
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
        
        int ans = isMatch_mem(s, p, n, m, dp);
        // int ans = isMatch_tab(s, p, n, m, dp);
        
        return ans == 1; // 1 => true ; 0 => false
    }
    
    public static int isMatch_mem(String s1, String s2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            if (n == 0 && m == 0)
                return dp[n][m] = 1;
            else if (m == 1 && s2.charAt(m-1) == '*') // ("", "*")
                return dp[n][m] = 1;
            else // ("", "ac") ; ("", "*ad") ; ("", "?a") ; ("adb", "")
                return dp[n][m] = 0;
        }
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        char ch1 = s1.charAt(n-1);
        char ch2 = s2.charAt(m-1);
        
        boolean ans = false;
        if (ch1 == ch2 || ch2 == '?') {
            ans = isMatch_mem(s1, s2, n-1, m-1, dp) == 1;
        }
        else if (ch2 == '*') {
            // call1 => * => so matches a char, but * can stil match further more chars, (n-1, m)
            // call2 => * => this time it will match an empty char ''
            ans = ans || isMatch_mem(s1, s2, n-1, m, dp) == 1; 
            ans = ans || isMatch_mem(s1, s2, n, m-1, dp) == 1;
        }
        else { // Both chars are different
            ans = false;
        }
        
        return dp[n][m] = ans ? 1 : 0;
    }
    
    public static int isMatch_tab(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    if (n == 0 && m == 0) {
                        dp[n][m] = 1;                        
                        continue;
                    }
                    else if (m == 1 && s2.charAt(m-1) == '*') { // ("", "*")
                        dp[n][m] = 1;
                        continue;
                    }
                    else { // ("", "ac") ; ("", "*ad") ; ("", "?a") ; ("adb", "")
                        dp[n][m] = 0;                        
                        continue;
                    } 
                }

                char ch1 = s1.charAt(n-1);
                char ch2 = s2.charAt(m-1);

                boolean ans = false;
                if (ch1 == ch2 || ch2 == '?') {
                    ans = dp[n-1][m-1] == 1;
                }
                else if (ch2 == '*') {
                    // call1 => * => so matches a char, but * can stil match further more chars, (n-1, m)
                    // call2 => * => this time it will match an empty char ''
                    ans = ans || dp[n-1][m] == 1; 
                    ans = ans || dp[n][m-1] == 1;
                }
                else { // Both chars are different
                    ans = false;
                }

                dp[n][m] = ans ? 1 : 0;
            }
        }
        
        return dp[N][M];
    }
    
    public static String removeDuplicateStars(String str) {
        StringBuilder sb = new StringBuilder();

        if (str.length() > 0)
            sb.append(str.charAt(0));
        
        int i = 1;
        while(i < str.length()) {
            while(i < str.length() && sb.charAt(sb.length()-1) == '*' && str.charAt(i) == '*')
                i++;
            
            if (i < str.length())
                sb.append(str.charAt(i));
            
            i++;
        }
        
        return sb.toString();
    }

    // ===========================================

    // LC 10 => Refer same approach of above ques LC 44
    public boolean isMatch_01(String s, String p) {
        p = removeDuplicateStars_01(p);
        
        int n = s.length(), m = p.length();
        // Since both true & false are part of answer using int dp so seperate meanings
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
        
        // int ans = isMatch_mem_01(s, p, n, m, dp); // Here Starting from back is beneficial to understand
        int ans = isMatch_tab_01(s, p, n, m, dp);
        
        return ans == 1; // 1 => true ; 0 => false
    }
    
    public static int isMatch_mem_01(String s1, String s2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            if (n == 0 && m == 0) 
                return dp[n][m] = 1;
            else if (m >= 2 && s2.charAt(m-1) == '*') { // ("", "c*") ; ("", "c*a*")
                int skipChar = isMatch_mem_01(s1, s2, n, m-2, dp);
                return dp[n][m] = skipChar;
            }
            else // ("", "c*ba*") ; ("abc", "") ; ("", "c*a.")
                return dp[n][m] = 0;
        }
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        char ch1 = s1.charAt(n-1);
        char ch2 = s2.charAt(m-1);
        
        boolean ans = false;
        if (ch1 == ch2 || ch2 == '.') { // When matches
            ans = isMatch_mem_01(s1, s2, n-1, m-1, dp) == 1;
        }
        else if (ch2 == '*') {
            char prevChar = s2.charAt(m-2);
            int takeChar = isMatch_mem_01(s1, s2, n-1, m, dp); // one or more occurence
            int skipChar = isMatch_mem_01(s1, s2, n, m-2, dp); // zero occurence
            
            if (prevChar == ch1 || prevChar == '.') { //When matches, * has 2 cases => 0 or more occurences
                ans = ans || takeChar == 1; // '*' accepts one or more of prev char
                ans = ans || skipChar == 1; // '*' accepts zero of prev char
            }    
            else 
                ans = skipChar == 1; // Not match, only case skip => '*' accepts zero of prev char
        }
        else { // Both chars are different (ch1 != ch2)
            ans = false;
        }
        
        return dp[n][m] = ans ? 1 : 0;
    }
    
    public static int isMatch_tab_01(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    if (n == 0 && m == 0) {
                        dp[n][m] = 1;                        
                        continue;
                    }
                    else if (m >= 2 && s2.charAt(m-1) == '*') { 
                        dp[n][m] = dp[n][m-2];
                        continue;
                    }
                    else { 
                        dp[n][m] = 0;                        
                        continue;
                    } 
                }

                char ch1 = s1.charAt(n-1);
                char ch2 = s2.charAt(m-1);

                boolean ans = false;
                if (ch1 == ch2 || ch2 == '.') {
                    ans = dp[n-1][m-1] == 1;
                }
                else if (ch2 == '*') {
                    char prevChar = s2.charAt(m-2);
                    int takeChar = dp[n-1][m];
                    int skipChar = dp[n][m-2];
                    
                    if (prevChar == ch1 || prevChar == '.') {
                        ans = ans || takeChar == 1;
                        ans = ans || skipChar == 1;
                    }
                    else
                        ans = skipChar == 1;
                }
                else { // Both chars are different
                    ans = false;
                }

                dp[n][m] = ans ? 1 : 0;
            }
        }
        
        return dp[N][M];
    }
    
    public static String removeDuplicateStars_01(String str) {
        StringBuilder sb = new StringBuilder();

        if (str.length() > 0)
            sb.append(str.charAt(0));
        
        int i = 1;
        while(i < str.length()) {
            while(i < str.length() && sb.charAt(sb.length()-1) == '*' && str.charAt(i) == '*')
                i++;
            
            if (i < str.length())
                sb.append(str.charAt(i));
            
            i++;
        }
        
        return sb.toString();
    }

    // ===========================================

    // LC 1035 => Same Longest Common Subsequence (LCS), just that here we have arrays instead of strings
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -1);
        
        // return uncrossed_mem(nums1, nums2, n, m, dp);
        return uncrossed_tab(nums1, nums2, n, m, dp);
    }
    
    public static int uncrossed_mem(int[] nums1, int[] nums2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) 
            return dp[n][m] = 0;
        
        if (dp[n][m] != -1)
            return dp[n][m];
        
        int count = 0;
        if (nums1[n-1] == nums2[m-1]) {
            count = 1 + uncrossed_mem(nums1, nums2, n-1, m-1, dp);
        }
        else {
            int ans1 = uncrossed_mem(nums1, nums2, n-1, m, dp);
            int ans2 = uncrossed_mem(nums1, nums2, n, m-1, dp);
            count += Math.max(ans1, ans2);
        }
        
        return dp[n][m] = count;
    }
    
    public static int uncrossed_tab(int[] nums1, int[] nums2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                int count = 0;
                if (nums1[n-1] == nums2[m-1]) {
                    count = 1 + dp[n-1][m-1];
                }
                else {
                    int ans1 = dp[n-1][m];
                    int ans2 = dp[n][m-1];
                    count += Math.max(ans1, ans2);
                }

                dp[n][m] = count;
            }
        }
        
        return dp[N][M];
    }

    // ===========================================

    // LC 1458
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n+1][m+1];
        for (int[] a : dp)
            Arrays.fill(a, -(int) 1e9); // Both -ve & +ve numbers are part of answer so, -(int) 1e9
        
        // return maxProduct_mem(nums1, nums2, n, m, dp);
        return maxProduct_tab(nums1, nums2, n, m, dp);
    }
    
    public static int maxProduct_mem(int[] nums1, int[] nums2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0)
            return dp[n][m] = -(int) 1e9;
        
        if (dp[n][m] != -(int) 1e9)
            return dp[n][m];
        
        int ans1 = nums1[n-1] * nums2[m-1]; // [20, -30, -40], [20, 30, 40]
        int ans2 = maxProduct_mem(nums1, nums2, n-1, m-1, dp) + ans1; // Yes [-20, 10, 20], [-10, 30, 4]
        int ans3 = maxProduct_mem(nums1, nums2, n, m-1, dp); // No [20, 1, 2], [-2, 3, 6]
        int ans4 = maxProduct_mem(nums1, nums2, n-1, m, dp); // No [-2, 3, 6], [20, 1, 2]
        
        int ans = getMaximum(ans1, ans2, ans3, ans4);
        
        return dp[n][m] = ans;
    }
    
    public static int maxProduct_tab(int[] nums1, int[] nums2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = -(int) 1e9;                    
                    continue;
                }
                
                int ans1 = nums1[n-1] * nums2[m-1];
                int ans2 = dp[n-1][m-1] + ans1;
                int ans3 = dp[n][m-1];
                int ans4 = dp[n-1][m];

                int ans = getMaximum(ans1, ans2, ans3, ans4);
                
                dp[n][m] = ans;
            }
        }
        
        return dp[N][M];
    }
    
    public static int getMaximum(int... arr) {
        int max = arr[0];
        for (int elem : arr)
            max = Math.max(max, elem);
        
        return max;
    }

    // ===========================================

    public static void main(String[] args) {
        System.out.println(minDistance_01("a", "b"));
        // System.out.println(minDistance_02("a", "b"));
    }
}
