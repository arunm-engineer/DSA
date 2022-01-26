public class l001 {

    static public class Pair {
        int len;
        String path;
        int count;

        public Pair(int len, String path, int count) {
            this.len = len;
            this.path = path;
            this.count = count;
        }

        public String toString() {
            return "len - " + this.len + "\npath - " + this.path + "\n count - " + this.count;
        }
    }

    // 1 => blocked cell, 0 => empty cell
    public static Pair floodfill_longestPath(int sr, int sc, int er, int ec, int[][] mat, int[][] dir,String[] dirS) {
        if (sr == er && sc == ec) {
            return new Pair(0, "", 1);
        }

        mat[sr][sc] = 1;

        Pair myAns = new Pair(-(int)1e9, "", 0);
        for (int d = 0;d < dir.length;d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && mat[r][c] != 1) {
                Pair p = floodfill_longestPath(r, c, er, ec, mat, dir, dirS);
                if (p.len != -(int)1e9 && p.len+1 > myAns.len) {
                    myAns.len = p.len+1;
                    myAns.path = dirS[d]+p.path;
                }

                myAns.count += p.count;
            }
        }

        mat[sr][sc] = 0;
        return myAns;
    }

    public static void floodfill_longestPath() {
        int[][] mat = {{0,0,1,0},
                        {1,0,0,0},
                        {0,0,0,0},
                        {1,0,1,0}};
        int n = mat.length, m= mat[0].length;
        int[][] dir = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
        String[] dirS = {"t", "l", "d", "r"};
        Pair ansP = floodfill_longestPath(0, 0, n-1, m-1, mat, dir, dirS);
        System.out.println(ansP);
    }

    public static Pair floodfill_shortestPath(int sr, int sc, int er, int ec, int[][] mat, int[][] dir,String[] dirS) {
        if (sr == er && sc == ec) {
            return new Pair(0, "", 1);
        }

        mat[sr][sc] = 1;

        Pair myAns = new Pair((int)1e9, "", 0);
        for (int d = 0;d < dir.length;d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && mat[r][c] != 1) {
                Pair p = floodfill_shortestPath(r, c, er, ec, mat, dir, dirS);
                if (p.len != (int)1e9 && p.len+1 < myAns.len) {
                    myAns.len = p.len+1;
                    myAns.path = dirS[d]+p.path;
                }

                myAns.count += p.count;
            }
        }

        mat[sr][sc] = 0;
        return myAns;
    }

    public static void floodfill_shortestPath() {
        int[][] mat = {{0,0,1,0},
                        {1,0,0,0},
                        {0,0,1,0},
                        {1,0,0,0}};
        int n = mat.length, m= mat[0].length;
        int[][] dir = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
        String[] dirS = {"t", "l", "d", "r"};
        Pair ansP = floodfill_shortestPath(0, 0, n-1, m-1, mat, dir, dirS);
        System.out.println(ansP);
    }

    /*****************************************************************/ 

    public static int goldmine2(int sr, int sc, int er, int ec, int[][] mat, int[][] dir) {
        int curGold = mat[sr][sc];
        mat[sr][sc] = 0;

        int maxGold = curGold;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && mat[r][c] != 0) {
                int recAns = goldmine2(r, c, er, ec, mat, dir);
                maxGold = Math.max(maxGold, recAns + curGold);
            }
        }

        mat[sr][sc] = curGold;
        return maxGold;
    }

    public static void goldmine2() {
        int[][] dir4 = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
        int[][] mat = { { 0, 1, 4, 2, 8, 2 },
                { 4, 3, 6, 5, 0, 4 },
                { 1, 2, 4, 1, 4, 6 },
                { 2, 0, 7, 3, 2, 2 },
                { 3, 1, 5, 9, 2, 4 },
                { 2, 7, 0, 8, 5, 1 } };

        int n = mat.length, m = mat[0].length;

        int ans = 0;
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                if (mat[sr][sc] != 0) {
                    int recAns = goldmine2(sr, sc, n - 1, m - 1, mat, dir4);
                    ans = Math.max(ans, recAns);
                }
            }
        }

        System.out.println(ans);
    }

    /*****************************************************************/ 

    public static void palindromicPermutations(String str, StringBuilder ans) {
        if (str.length() == 0) {
            boolean isPal = isPalindrome(ans.toString());
            if (isPal)
                System.out.println(ans);
        }

        char prev = '$';
        for (int i = 0;i < str.length();i++) {
            char cur = str.charAt(i);
            if (prev != cur) {
                String ros = str.substring(0, i) + str.substring(i+1);

                ans.append(cur);
                palindromicPermutations(ros, ans);
                ans.deleteCharAt(ans.length()-1);
            }
            prev = cur;
        }
    }

    public static boolean isPalindrome(String str) {
        int i = 0, j = str.length()-1;
        while (i < j) {
            if (str.charAt(i) != str.charAt(j))
                return false;
            i++;
            j--;
        }

        return true;
    }

    public static void palindromicPermutations() {
        String str = "abbda";
        int[] freq = new int[26];
        
        for (int i = 0;i < str.length();i++) {
            freq[str.charAt(i)-'a']++;
        }

        String sortedStr = "";// To remove duplicacy
        for (int i = 0;i < freq.length;i++) {
            for (int j = 1;j <= freq[i];j++) {
                sortedStr += (char)('a'+i);
            }
        }

        palindromicPermutations(sortedStr, new StringBuilder());
    }

    public static void main(String[] args) {
        // floodfill_longestPath();
        // floodfill_shortestPath();
        
        // LC 1219
        // goldmine2();

        // palindromicPermutations();

    }

}
