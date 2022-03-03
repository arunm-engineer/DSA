import java.util.*;

public class l002_StringSet {

    // LC 516
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // return lpss_mem(s, 0, n-1, dp);
        return lpss_tab(s, 0, n - 1, dp);
    }

    public static int lpss_mem(String s, int i, int j, int[][] dp) {
        if (i >= j)
            return dp[i][j] = i == j ? 1 : 0;

        if (dp[i][j] != 0)
            return dp[i][j];

        int count = 0;
        if (s.charAt(i) == s.charAt(j))
            count = 2 + lpss_mem(s, i + 1, j - 1, dp);
        else
            count = Math.max(lpss_mem(s, i + 1, j, dp), lpss_mem(s, i, j - 1, dp));

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
                    count = 2 + dp[i + 1][j - 1];
                else
                    count = Math.max(dp[i + 1][j], dp[i][j - 1]);

                dp[i][j] = count;
            }
        }

        return dp[I][J];
    }

    // ===========================================

    // LC 1143
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n + 1][m + 1];
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
        if (s1.charAt(n - 1) == s2.charAt(m - 1))
            count = 1 + lcss_mem(s1, s2, n - 1, m - 1, dp);
        else
            count = Math.max(lcss_mem(s1, s2, n - 1, m, dp), lcss_mem(s1, s2, n, m - 1, dp));

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
                if (s1.charAt(n - 1) == s2.charAt(m - 1))
                    count = 1 + dp[n - 1][m - 1];
                else
                    count = Math.max(dp[n - 1][m], dp[n][m - 1]);

                dp[n][m] = count;
            }
        }

        return dp[N][M];
    }

    // ===========================================

    // LC 115
    public int numDistinct(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] dp = new int[n + 1][m + 1];
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
        if (s1.charAt(n - 1) == s2.charAt(m - 1))
            // Why (n-1, m) call, bcoz we may again find the occurence of s2 in s1
            count = distinctSubsq_mem(s1, s2, n - 1, m - 1, dp) + distinctSubsq_mem(s1, s2, n - 1, m, dp);
        else
            count = distinctSubsq_mem(s1, s2, n - 1, m, dp);

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
                if (s1.charAt(n - 1) == s2.charAt(m - 1))
                    count = dp[n - 1][m - 1] + dp[n - 1][m];
                else
                    count = dp[n - 1][m];

                dp[n][m] = count;
            }
        }

        return dp[N][M];
    }

    // ===========================================

    // LC 72
    public int minDistance(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        // return editDistance_mem(word1, word2, n, m, dp);
        return editDistance_tab(word1, word2, n, m, dp);
    }

    public static int editDistance_mem(String s1, String s2, int n, int m, int[][] dp) {
        // Covered all 3 base cases by below one line => n=0 & m=0, n=0 & m!=0, n!=0 &
        // m=0
        if (n == 0 || m == 0)
            return dp[n][m] = (n == 0 ? m : n);

        if (dp[n][m] != -1)
            return dp[n][m];

        int count = 0;
        if (s1.charAt(n - 1) == s2.charAt(m - 1)) {
            count = editDistance_mem(s1, s2, n - 1, m - 1, dp);
        } else {
            int insert = editDistance_mem(s1, s2, n, m - 1, dp);
            int delete = editDistance_mem(s1, s2, n - 1, m, dp);
            int replace = editDistance_mem(s1, s2, n - 1, m - 1, dp);
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
                if (s1.charAt(n - 1) == s2.charAt(m - 1)) {
                    count = dp[n - 1][m - 1];
                } else {
                    int insert = dp[n][m - 1];
                    int delete = dp[n - 1][m];
                    int replace = dp[n - 1][m - 1];
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
        // i, d, r
        int[] operationsCost = { 1, 4, 7 };

        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        return editDistance_mem_01(word1, word2, n, m, dp, operationsCost);
        // return editDistance_tab(word1, word2, n, m, dp);
    }

    public static int editDistance_mem_01(String s1, String s2, int n, int m, int[][] dp, int[] cost) {
        // Covered all 3 base cases by below one line => n=0 & m=0, n=0 & m!=0, n!=0 &
        // m=0
        if (n == 0 && m == 0)
            return dp[n][m] = 0;
        else if (n == 0)
            return dp[n][m] = m * cost[0]; // m insert's
        else if (m == 0)
            return dp[n][m] = n * cost[1]; // n delete's

        if (dp[n][m] != -1)
            return dp[n][m];

        int count = 0;
        if (s1.charAt(n - 1) == s2.charAt(m - 1)) {
            count = editDistance_mem_01(s1, s2, n - 1, m - 1, dp, cost);
        } else {
            // Here added cost for any one operation performed here (i, d, r)
            int insert = editDistance_mem_01(s1, s2, n, m - 1, dp, cost) + cost[0]; // insert cost
            int delete = editDistance_mem_01(s1, s2, n - 1, m, dp, cost) + cost[1]; // delete cost
            int replace = editDistance_mem_01(s1, s2, n - 1, m - 1, dp, cost) + cost[2]; // replace cost
            count = Math.min(Math.min(insert, delete), replace);
        }

        return dp[n][m] = count;
    }

    public static int minDistance_02(String word1, String word2) {
        // a, b, c, d, ......, z
        int[][] operationsCost = { { 1, 2, 7, 3 }, // i
                { 2, 2, 3, 1 }, // d
                { 6, 2, 3, 5 } // r
        };

        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        return editDistance_mem_02(word1, word2, n, m, dp, operationsCost);
        // return editDistance_tab(word1, word2, n, m, dp);
    }

    public static int editDistance_mem_02(String s1, String s2, int n, int m, int[][] dp, int[][] cost) {
        // Covered all 3 base cases by below one line => n=0 & m=0, n=0 & m!=0, n!=0 &
        // m=0
        if (n == 0 && m == 0)
            return dp[n][m] = 0;
        else if (n == 0) {
            int baseCost = 0;
            for (int j = m - 1; j >= 0; j--) {
                char ch = s2.charAt(j);
                baseCost += cost[0][ch - 'a'];
            }

            return dp[n][m] = baseCost; // m insert's
        } else if (m == 0) {
            int baseCost = 0;
            for (int i = n - 1; i >= 0; i--) {
                char ch = s1.charAt(i);
                baseCost += cost[1][ch - 'a'];
            }

            return dp[n][m] = baseCost; // n delete's
        }

        if (dp[n][m] != -1)
            return dp[n][m];

        char ch1 = s1.charAt(n - 1);
        char ch2 = s2.charAt(m - 1);

        int count = 0;
        if (ch1 == ch2) {
            count = editDistance_mem_02(s1, s2, n - 1, m - 1, dp, cost);
        } else {
            int insert = editDistance_mem_02(s1, s2, n, m - 1, dp, cost) + cost[0][ch2 - 'a']; // insert cost
            int delete = editDistance_mem_02(s1, s2, n - 1, m, dp, cost) + cost[1][ch1 - 'a']; // delete cost
            int replace = editDistance_mem_02(s1, s2, n - 1, m - 1, dp, cost) + cost[2][ch1 - 'a']; // replace cost
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
        int[][] dp = new int[n + 1][m + 1];
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
            else if (m == 1 && s2.charAt(m - 1) == '*') // ("", "*")
                return dp[n][m] = 1;
            else // ("", "ac") ; ("", "*ad") ; ("", "?a") ; ("adb", "")
                return dp[n][m] = 0;
        }

        if (dp[n][m] != -1)
            return dp[n][m];

        char ch1 = s1.charAt(n - 1);
        char ch2 = s2.charAt(m - 1);

        boolean ans = false;
        if (ch1 == ch2 || ch2 == '?') {
            ans = isMatch_mem(s1, s2, n - 1, m - 1, dp) == 1;
        } else if (ch2 == '*') {
            // call1 => * => so matches a char, but * can stil match further more chars,
            // (n-1, m)
            // call2 => * => this time it will match an empty char ''
            ans = ans || isMatch_mem(s1, s2, n - 1, m, dp) == 1;
            ans = ans || isMatch_mem(s1, s2, n, m - 1, dp) == 1;
        } else { // Both chars are different
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
                    } else if (m == 1 && s2.charAt(m - 1) == '*') { // ("", "*")
                        dp[n][m] = 1;
                        continue;
                    } else { // ("", "ac") ; ("", "*ad") ; ("", "?a") ; ("adb", "")
                        dp[n][m] = 0;
                        continue;
                    }
                }

                char ch1 = s1.charAt(n - 1);
                char ch2 = s2.charAt(m - 1);

                boolean ans = false;
                if (ch1 == ch2 || ch2 == '?') {
                    ans = dp[n - 1][m - 1] == 1;
                } else if (ch2 == '*') {
                    // call1 => * => so matches a char, but * can stil match further more chars,
                    // (n-1, m)
                    // call2 => * => this time it will match an empty char ''
                    ans = ans || dp[n - 1][m] == 1;
                    ans = ans || dp[n][m - 1] == 1;
                } else { // Both chars are different
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
        while (i < str.length()) {
            while (i < str.length() && sb.charAt(sb.length() - 1) == '*' && str.charAt(i) == '*')
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
        int[][] dp = new int[n + 1][m + 1];
        for (int[] a : dp)
            Arrays.fill(a, -1);

        // int ans = isMatch_mem_01(s, p, n, m, dp); // Here Starting from back is
        // beneficial to understand
        int ans = isMatch_tab_01(s, p, n, m, dp);

        return ans == 1; // 1 => true ; 0 => false
    }

    public static int isMatch_mem_01(String s1, String s2, int n, int m, int[][] dp) {
        if (n == 0 || m == 0) {
            if (n == 0 && m == 0)
                return dp[n][m] = 1;
            else if (m >= 2 && s2.charAt(m - 1) == '*') { // ("", "c*") ; ("", "c*a*")
                int skipChar = isMatch_mem_01(s1, s2, n, m - 2, dp);
                return dp[n][m] = skipChar;
            } else // ("", "c*ba*") ; ("abc", "") ; ("", "c*a.")
                return dp[n][m] = 0;
        }

        if (dp[n][m] != -1)
            return dp[n][m];

        char ch1 = s1.charAt(n - 1);
        char ch2 = s2.charAt(m - 1);

        boolean ans = false;
        if (ch1 == ch2 || ch2 == '.') { // When matches
            ans = isMatch_mem_01(s1, s2, n - 1, m - 1, dp) == 1;
        } else if (ch2 == '*') {
            char prevChar = s2.charAt(m - 2);
            int takeChar = isMatch_mem_01(s1, s2, n - 1, m, dp); // one or more occurence
            int skipChar = isMatch_mem_01(s1, s2, n, m - 2, dp); // zero occurence

            if (prevChar == ch1 || prevChar == '.') { // When matches, * has 2 cases => 0 or more occurences
                ans = ans || takeChar == 1; // '*' accepts one or more of prev char
                ans = ans || skipChar == 1; // '*' accepts zero of prev char
            } else
                ans = skipChar == 1; // Not match, only case skip => '*' accepts zero of prev char
        } else { // Both chars are different (ch1 != ch2)
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
                    } else if (m >= 2 && s2.charAt(m - 1) == '*') {
                        dp[n][m] = dp[n][m - 2];
                        continue;
                    } else {
                        dp[n][m] = 0;
                        continue;
                    }
                }

                char ch1 = s1.charAt(n - 1);
                char ch2 = s2.charAt(m - 1);

                boolean ans = false;
                if (ch1 == ch2 || ch2 == '.') {
                    ans = dp[n - 1][m - 1] == 1;
                } else if (ch2 == '*') {
                    char prevChar = s2.charAt(m - 2);
                    int takeChar = dp[n - 1][m];
                    int skipChar = dp[n][m - 2];

                    if (prevChar == ch1 || prevChar == '.') {
                        ans = ans || takeChar == 1;
                        ans = ans || skipChar == 1;
                    } else
                        ans = skipChar == 1;
                } else { // Both chars are not equal
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
        while (i < str.length()) {
            while (i < str.length() && sb.charAt(sb.length() - 1) == '*' && str.charAt(i) == '*')
                i++;

            if (i < str.length())
                sb.append(str.charAt(i));

            i++;
        }

        return sb.toString();
    }

    // ===========================================

    // LC 1035 => Same Longest Common Subsequence (LCS), just that here we have
    // arrays instead of strings
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];
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
        if (nums1[n - 1] == nums2[m - 1]) {
            count = 1 + uncrossed_mem(nums1, nums2, n - 1, m - 1, dp);
        } else {
            int ans1 = uncrossed_mem(nums1, nums2, n - 1, m, dp);
            int ans2 = uncrossed_mem(nums1, nums2, n, m - 1, dp);
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
                if (nums1[n - 1] == nums2[m - 1]) {
                    count = 1 + dp[n - 1][m - 1];
                } else {
                    int ans1 = dp[n - 1][m];
                    int ans2 = dp[n][m - 1];
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
        int[][] dp = new int[n + 1][m + 1];
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

        int ans1 = nums1[n - 1] * nums2[m - 1]; // [20, -30, -40], [20, 30, 40]
        int ans2 = maxProduct_mem(nums1, nums2, n - 1, m - 1, dp) + ans1; // Yes [-20, 10, 20], [-10, 30, 4]
        int ans3 = maxProduct_mem(nums1, nums2, n, m - 1, dp); // No [20, 1, 2], [-2, 3, 6]
        int ans4 = maxProduct_mem(nums1, nums2, n - 1, m, dp); // No [-2, 3, 6], [20, 1, 2]

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

                int ans1 = nums1[n - 1] * nums2[m - 1];
                int ans2 = dp[n - 1][m - 1] + ans1;
                int ans3 = dp[n][m - 1];
                int ans4 = dp[n - 1][m];

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

    // LC 5 => [LPS] Longest Palindromic "Susbtring"
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        return lps_tab(s, dp);
    }

    public static String lps_tab(String str, boolean[][] dp) {

        int totalPalSubstr = 0; // Total number of LPS
        int longestPalSusbtrLen = 0; // Length of LPS
        String longestPalSubstr = ""; // Return the LPS itself

        // Gap strategy
        int n = dp.length, m = dp[0].length;
        for (int gap = 0; gap < m; gap++) {
            for (int i = 0, j = gap; j < m; i++, j++) {
                if (gap == 0)
                    dp[i][j] = true;
                else if (gap == 1 && str.charAt(i) == str.charAt(j))
                    dp[i][j] = true;
                else
                    dp[i][j] = str.charAt(i) == str.charAt(j) && dp[i + 1][j - 1]; // Check in between substr also (i+1,
                                                                                   // j-1) for valid palindrome

                if (dp[i][j] == true) {
                    totalPalSubstr++;
                    int len = (j - i) + 1;
                    if (len > longestPalSusbtrLen) {
                        longestPalSusbtrLen = len;
                        longestPalSubstr = str.substring(i, j + 1);
                    }
                }
            }
        }

        return longestPalSubstr;
    }

    // ===========================================

    // LCS => Longest Commong "Substring"
    // https://www.geeksforgeeks.org/longest-common-substring-dp-29/
    public static int lcs_tab(String s1, String s2, int N, int M, int[][] dp) {
        int maxLen = 0;
        int ei = -1; // ending index

        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                int count = 0;
                if (s1.charAt(n - 1) == s2.charAt(m - 1))
                    count = 1 + dp[n - 1][m - 1];
                else // (Si != Sj) ; Can skip this else, since by default value is 0
                    count = 0;

                dp[n][m] = count;
                if (dp[n][m] > maxLen) {
                    maxLen = dp[n][m];
                    ei = n - 1; // This "n" represents for s1
                }
            }
        }

        int si = ei - maxLen + 1;
        String lcsubstr = s1.substring(si, ei + 1); // To print Longest Common "Substring"
        System.out.println(lcsubstr);

        return maxLen;
    }

    // ===========================================

    // LC 718 => Same LCS but instead of Strings we are given arrays
    public int findLength(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];

        return lcs_tab(nums1, nums2, n, m, dp);
    }

    public static int lcs_tab(int[] nums1, int[] nums2, int N, int M, int[][] dp) {
        int maxLen = 0;
        int ei = -1; // ending index

        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                int count = 0;
                if (nums1[n - 1] == nums2[m - 1])
                    count = 1 + dp[n - 1][m - 1];
                else // (Si != Sj) ; Can skip this else, since by default value is 0
                    count = 0;

                dp[n][m] = count;

                maxLen = Math.max(dp[n][m], maxLen);
            }
        }

        return maxLen;
    }

    // ===========================================

    // LC 583 => Used ditto copy/paste of already solved questions concept
    public int minDistance_03(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int[] arr : dp)
            Arrays.fill(arr, -1);

        // Solution 1:
        // int lcssLen = lcss_tab_01(word1, word2, n, m, dp);
        // Why 2 times bcoz we match and delete lcss from the totalString and remaining
        // is the ans
        // int ans = (n+m) - (2*lcssLen); // Add 2 strings len => TotalLen - 2*lcssLen

        // Solution 2:
        // Can also try this edit Distance Approach, since both lcss and editDistance
        // are kind of interlinked concepts
        int ans = editDistance_tab_01(word1, word2, n, m, dp);
        return ans;
    }

    public static int editDistance_tab_01(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = (n == 0 ? m : n);
                    continue;
                }

                int count = 0;
                if (s1.charAt(n - 1) == s2.charAt(m - 1)) {
                    count = dp[n - 1][m - 1];
                } else {
                    int insert = dp[n][m - 1];
                    int delete = dp[n - 1][m];
                    // int replace = dp[n-1][m-1]; // Just don't need "replace" option here
                    // Here added 1 for any one operation performed here (i, d)
                    count = Math.min(insert, delete) + 1;
                }

                dp[n][m] = count;
            }
        }

        return dp[N][M];
    }

    public static int lcss_tab_01(String s1, String s2, int N, int M, int[][] dp) {
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m <= M; m++) {
                if (n == 0 || m == 0) {
                    dp[n][m] = 0;
                    continue;
                }

                int count = 0;
                if (s1.charAt(n - 1) == s2.charAt(m - 1))
                    count = 1 + dp[n - 1][m - 1];
                else
                    count = Math.max(dp[n - 1][m], dp[n][m - 1]);

                dp[n][m] = count;
            }
        }

        return dp[N][M];
    }

    // ===========================================

    // LC 132
    // Testcase => "fafaaabaageeg"
    public int minCut(String s) {
        int n = s.length();
        boolean[][] palDP = new boolean[n][n];

        // LPS logic => First pre-process and find all palindromic substrings
        int m = palDP[0].length;
        for (int gap = 0; gap < m; gap++) {
            for (int i = 0, j = gap; j < m; i++, j++) {
                if (gap == 0)
                    palDP[i][j] = true;
                else if (gap == 1 && s.charAt(i) == s.charAt(j))
                    palDP[i][j] = true;
                else
                    palDP[i][j] = s.charAt(i) == s.charAt(j) && palDP[i + 1][j - 1];
            }
        }

        // Now to find the minCut on the string
        int[] dp = new int[n]; // This dp is to store minCuts from every points of pal cut
        Arrays.fill(dp, -1);

        // int minCutAns = palPartition_mem(s, 0, n-1, dp, palDP);
        int minCutAns = palPartition_tab(s, 0, n - 1, dp, palDP);
        return minCutAns;
    }

    public static int palPartition_mem(String str, int si, int ei, int[] dp, boolean[][] palDP) {
        if (palDP[si][ei]) // Base case is pal string (even for 1 length string is pal)
            return dp[si] = 0;

        if (dp[si] != -1)
            return dp[si];

        int minCutAns = (int) 1e9;
        for (int cut = si; cut <= ei; cut++) {
            if (palDP[si][cut])
                minCutAns = Math.min(minCutAns, palPartition_mem(str, cut + 1, ei, dp, palDP) + 1);
        }

        return dp[si] = minCutAns;
    }

    public static int palPartition_tab(String str, int SI, int EI, int[] dp, boolean[][] palDP) {
        for (int si = EI; si >= SI; si--) {
            if (palDP[si][EI]) {
                dp[si] = 0;
                continue;
            }

            int minCutAns = (int) 1e9;
            for (int cut = si; cut <= EI; cut++) {
                if (palDP[si][cut])
                    minCutAns = Math.min(minCutAns, dp[cut + 1] + 1);

                dp[si] = minCutAns;
            }
        }

        return dp[SI];
    }

    // ===========================================

    // https://www.geeksforgeeks.org/number-subsequences-form-ai-bj-ck/
    // Count Subsequences of pattern a^i, b^j, c^k
    public int countSubsq(String s) {
        int mod = (int) 1e9 + 7;
        int n = s.length();
        long empty = 1, aCount = 0, bCount = 0, cCount = 0;

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (ch == 'a')
                aCount = ((aCount) + (empty + aCount)) % mod; // No + Yes

            if (ch == 'b')
                bCount = ((bCount) + (aCount + bCount)) % mod; // No + Yes

            if (ch == 'c')
                cCount = ((cCount) + (bCount + cCount)) % mod; // No + Yes
        }

        return (int) cCount;
    }

    // Follow Up question to above question =>
    // https://www.geeksforgeeks.org/number-subsequences-form-ai-bj-ck/
    // Testcase (a-f chars)=> a^i, b^j, c^k, d^l, e^m, f^n
    public static int countSubsq_01(String s) {
        int mod = (int) 1e9 + 7;
        int n = s.length();
        long empty = 1;
        long[] arr = new long[6];

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (ch == 'a')
                arr[ch - 'a'] = arr[ch - 'a'] + (empty + arr[ch - 'a']) % mod; // No + Yes
            else
                arr[ch - 'a'] = arr[ch - 'a'] + ((arr[ch - 'a' - 1]) + arr[ch - 'a']) % mod; // No + Yes
        }

        return (int) arr[arr.length - 1] % mod;
    }

    // ===========================================

    // LC 1278
    public int palindromePartition(String s, int k) {
        int n = s.length();
        int[][] palDP = new int[n][n]; // This DP stores minSteps to convert substr to a palindrome

        // LPS logic => Min steps to convert substr to a palindrome
        int m = palDP[0].length;
        for (int gap = 0; gap < m; gap++) {
            for (int i = 0, j = gap; j < m; i++, j++) {
                if (gap == 0)
                    palDP[i][j] = 0;
                else if (gap == 1) {
                    if (s.charAt(i) == s.charAt(j))
                        palDP[i][j] = 0;
                    else
                        palDP[i][j] = 1;
                } else {
                    if (s.charAt(i) == s.charAt(j))
                        palDP[i][j] = palDP[i + 1][j - 1];
                    else
                        palDP[i][j] = 1 + palDP[i + 1][j - 1];
                }
            }
        }

        int[][] dp = new int[n][k + 1]; // This dp is to store minCuts from every points of pal cut
        for (int[] a : dp)
            Arrays.fill(a, -1);

        // int minSteps = palPartition_03_mem(s, 0, n-1, k, dp, palDP);
        int minSteps = palPartition_03_tab(s, 0, n - 1, k, dp, palDP);
        return minSteps;
    }

    public static int palPartition_03_mem(String s, int si, int ei, int k, int[][] dp, int[][] palDP) {
        if (k == 1)
            return dp[si][k] = palDP[si][ei];

        if (dp[si][k] != -1)
            return dp[si][k];

        int minSteps = (int) 1e9;
        for (int cut = si; cut <= ei; cut++) {
            if (cut + 1 <= ei) { // To save from IndexOutOfBounds
                int currSteps = palDP[si][cut] + palPartition_03_mem(s, cut + 1, ei, k - 1, dp, palDP);
                minSteps = Math.min(minSteps, currSteps);
            }
        }

        return dp[si][k] = minSteps;
    }

    public static int palPartition_03_tab(String s, int SI, int EI, int K, int[][] dp, int[][] palDP) {
        for (int si = EI; si >= SI; si--) {
            for (int k = K; k > 0; k--) {
                if (k == 1) {
                    dp[si][k] = palDP[si][EI];
                    continue;
                }

                int minSteps = (int) 1e9;
                for (int cut = si; cut <= EI; cut++) {
                    if (cut + 1 <= EI) { // To save from IndexOutOfBounds
                        int currSteps = palDP[si][cut] + dp[cut + 1][k - 1]; // steps(si,cut) + steps(cut+1,ei)
                        minSteps = Math.min(minSteps, currSteps);
                    }
                }

                dp[si][k] = minSteps;
            }
        }

        return dp[SI][K];
    }

    // ===========================================

    // LC 139
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(); // To make retrieval faster
        int maxLen = 0;
        for (String str : wordDict) {
            set.add(str);
            maxLen = Math.max(maxLen, str.length());
        }

        int n = s.length();
        boolean[] dp = new boolean[n + 1]; // DP stores, True => We formed a valid word ending from dictionary
        dp[0] = true; // For empty char => ""

        for (int i = 0; i < n; i++) {
            if (dp[i] == false) // Not a valid word ending from dictionary
                continue;

            // True => valid word ending found, explore next set of possible words (i+1) to
            // form a sentence
            for (int l = 1; l <= maxLen && i + l <= n; l++) {
                String word = s.substring(i, i + l);
                if (set.contains(word)) // Valid dictionary word found
                    dp[i + l] = true;
            }
        }

        return dp[n];
    }

    // LC 139 => Correct Approach, but TLE
    public boolean wordBreak_00(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(); // To make retrieval speed
        for (String str : wordDict)
            set.add(str);

        int n = s.length();
        boolean[][] existDP = new boolean[n][n];

        // LPS logic => First pre-process and find all palindromic substrings
        int m = existDP[0].length;
        for (int gap = 0; gap < m; gap++) {
            for (int i = 0, j = gap; j < m; i++, j++) {
                existDP[i][j] = set.contains(s.substring(i, j + 1));
            }
        }

        boolean[] dp = new boolean[n];
        return wordBreak_00(s, 0, n - 1, dp, existDP);
    }

    public static boolean wordBreak_00(String str, int si, int ei, boolean[] dp, boolean[][] existDP) {
        if (existDP[si][ei])
            return dp[si] = true;

        if (dp[si])
            return dp[si];

        boolean res = false;
        for (int cut = si; cut <= ei; cut++) {
            if (existDP[si][cut])
                res = res || wordBreak_00(str, cut + 1, ei, dp, existDP);
        }

        return dp[si] = res;
    }

    // ===========================================

    // =============================================Back Engineering Concept [Used to print from DP ans]==============================================

    // To print LPSS(Longest Palindromic Subsequence) using the DP of LPSS
    // Note: Print LPSS using Recursion on the Way-Up => Can have a visited arr of
    // string's length to track and form LPSS string
    // Mark true for your possible ans char, But this approach consumes space so use
    // below method (Recursion Way-Down)

    // si = 0, ei = dp[0].length-1
    public static String lpss_BackEng(String s, int si, int ei, int[][] dp) {
        if (si >= ei)
            return (si == ei) ? "" + s.charAt(si) : "";

        if (s.charAt(si) == s.charAt(ei)) {
            String recAns = lpss_BackEng(s, si + 1, ei - 1, dp);
            return s.charAt(si) + recAns + s.charAt(si);
        } else {
            if (dp[si + 1][ei] > dp[si][ei - 1]) {
                return lpss_BackEng(s, si + 1, ei, dp);
            } else {
                return lpss_BackEng(s, si, ei - 1, dp);
            }
        }
    }

    // ===========================================

    // LC 140 => Approach 1
    // In this function "wordBreak_001" we have performed the same Word Break-1 (LC
    // 130) and apply Back Engg on the problem to find the sentences
    public List<String> wordBreak_001(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(); // To make retrieval faster
        int maxLen = 0;
        for (String str : wordDict) {
            set.add(str);
            maxLen = Math.max(maxLen, str.length());
        }

        int n = s.length();
        boolean[] dp = new boolean[n + 1]; // DP stores, True => We formed a valid word ending from dictionary
        dp[0] = true; // For empty char => ""

        for (int i = 0; i < n; i++) {
            if (dp[i] == true) {
                // True => valid word ending found, explore next set of possible words (i+1) to form a sentence
                for (int l = 1; l <= maxLen && i + l <= n; l++) {
                    String word = s.substring(i, i + l);
                    if (set.contains(word)) // Valid dictionary word found
                        dp[i + l] = true;
                }
            }
        }

        List<String> ans = new ArrayList<>();
        if (dp[n] == true) { // If last index of dp which is last char of string is not true, then don't call because no sentence could be formed
            wordBreak_BackEngg_001(s, 0, maxLen, dp, set, ans, "");
        }

        return ans;
    }

    public static void wordBreak_BackEngg_001(String s, int idx, int maxLen, boolean[] dp, HashSet<String> set, List<String> ans, String sentence) {
        if (idx == s.length()) {
            ans.add(sentence.substring(0, sentence.length()-1)); // Because we want to exclude the empty space " " of the sentence
            return;
        }

        int n = s.length();
        for (int l = 1; l <= maxLen && idx+l <= n; l++) {
            if (dp[idx+l] == true) {
                String str = s.substring(idx, idx+l);
                if (set.contains(str)) 
                    wordBreak_BackEngg_001(s, idx+l, maxLen, dp, set, ans, sentence + str + " ");
            }
        }
    }

    // LC 140 => Approach 2
    public List<String> wordBreak_002(String s, List<String> wordDict) {
        HashSet<String> set = new HashSet<>(); // To make retrieval speed
        for (String str : wordDict)
            set.add(str);

        int n = s.length();
        boolean[][] existDP = new boolean[n][n]; // DP for each substring word if exists in Dictionary, True => substr
                                                 // or word exists in dictionary, else F

        int m = existDP[0].length;
        for (int gap = 0; gap < m; gap++) {
            for (int i = 0, j = gap; j < m; i++, j++) {
                existDP[i][j] = set.contains(s.substring(i, j + 1));
            }
        }

        return wordBreak_BackEng_002(s, 0, existDP); // We use the row num & col num, (r, c) to indicate to indicate the
                                                     // range of word which forms in a sentence
    }

    public static List<String> wordBreak_BackEng_002(String s, int sr, boolean[][] existDP) {
        int n = existDP.length, m = existDP[0].length;
        List<String> ans = new ArrayList<>();

        if (sr == n) {
            ans.add("");
            return ans;
        }

        for (int sc = sr; sc < m; sc++) {
            if (existDP[sr][sc] == true) {
                // Why sc+1, because next word will start only after sc+1
                List<String> recAns = wordBreak_BackEng_002(s, sc + 1, existDP);

                if (recAns != null) {
                    for (String sentence : recAns) {
                        if (sc + 1 == n || sc + 1 == m) // Means the last word, so cannot add space " "
                            ans.add(s.substring(sr, sc + 1) + sentence);
                        else
                            ans.add(s.substring(sr, sc + 1) + " " + sentence);
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        // System.out.println(minDistance_01("a", "b"));
        // System.out.println(minDistance_02("a", "b"));

        // String s1 = "abcdxyzfr", s2 = "xyzabcd";
        // int n = s1.length(), m = s2.length();
        // int[][] dp = new int[n+1][m+1];
        // System.out.println(lcs_tab(s1, s2, n, m, dp));

        // System.out.println(countSubsq_01("abcdeff"));
    }
}
