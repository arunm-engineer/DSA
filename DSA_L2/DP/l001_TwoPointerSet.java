public class l001_TwoPointerSet {

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

    public static int fib_rec(int n) {
        if (n <= 1)
            return n;

        int data = fib_rec(n - 1) + fib_rec(n - 2);
        return data;
    }

    public static int fib_mem(int n, int[] dp) {
        if (n <= 1)
            return dp[n] = n;

        if (dp[n] != 0)
            return dp[n];

        int data = fib_mem(n - 1, dp) + fib_mem(n - 2, dp);
        return dp[n] = data;
    }

    public static int fib_tab(int N, int[] dp) {
        for (int n = 0; n <= N; n++) {
            if (n <= 1) {
                dp[n] = n;
                continue;
            }

            int data = dp[n - 1] + dp[n - 2]; // fib_mem(n - 1, dp) + fib_mem(n - 2, dp);
            dp[n] = data;
        }

        return dp[N];
    }

    public static int fib_opt(int N) {
        int a = 0, b = 1;
        for (int n = 2; n <= N; n++) {
            int data = a + b;
            a = b;
            b = data;
        }

        return b;
    }

    public static void fibonacci() {
        int n = 5;
        int[] dp = new int[n + 1];

        // System.out.println(fib_rec(5));
        // System.out.println(fib_mem(5, dp));
        // System.out.println(fib_tab(5, dp));
        // display(dp);
        // System.out.println(fib_opt(n));
    }

    // ===================================================

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

    public static int mazePathInfiniteJump_mem(int sr, int sc, int er, int ec, int[][] dir, int[][] dp) {
        if (sr == er && sc == ec)
            return dp[sr][sc] = 1;

        if (dp[sr][sc] != 0)
            return dp[sr][sc];

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            for (int rad = 1; rad <= Math.max(er, ec); rad++) {
                int r = sr + rad * dir[d][0];
                int c = sc + rad * dir[d][1];
                if (r >= 0 && c >= 0 && r <= er && c <= ec) {
                    count += mazePathInfiniteJump_mem(r, c, er, ec, dir, dp);
                }
            }
        }

        return dp[sr][sc] = count;
    }

    public static int mazePathInfiniteJump_tab(int SR, int SC, int ER, int EC, int[][] dir, int[][] dp) {

        for (int sr = ER; sr >= SR; sr--) {
            for (int sc = EC; sc >= SC; sc--) {
                if (sr == ER && sc == EC) {
                    dp[sr][sc] = 1;
                    continue;
                }

                int count = 0;
                for (int d = 0; d < dir.length; d++) {
                    for (int rad = 1; rad <= Math.max(ER, EC); rad++) {
                        int r = sr + rad * dir[d][0];
                        int c = sc + rad * dir[d][1];
                        if (r >= 0 && c >= 0 && r <= ER && c <= EC)
                            count += dp[r][c];
                    }
                }
                dp[sr][sc] = count;
            }
        }

        return dp[SR][SC];
    }

    public static void mazePath() {
        int sr = 0, sc = 0, er = 3, ec = 3;
        int[][] dir3 = { { 0, 1 }, { 1, 1 }, { 1, 0 } };
        int[][] dp = new int[er + 1][ec + 1];

        // System.out.println(mazePath1Jump_mem(sr, sc, er, ec, dir3, dp));
        // System.out.println(mazePath1Jump_tab(sr, sc, er, ec, dir3, dp));

        // System.out.println(mazePathInfiniteJump_mem(sr, sc, er, ec, dir3, dp));
        // System.out.println(mazePathInfiniteJump_tab(sr, sc, er, ec, dir3, dp));
        // display2D(dp);
    }

    // ===================================================

    public static int boardPath_mem(int n, int[] dp) {
        if (n == 0)
            return dp[n] = 1;

        if (dp[n] != 0)
            return dp[n];

        int count = 0;
        for (int dice = 1; dice <= 6; dice++) {
            if (n - dice >= 0)
                count += boardPath_mem(n - dice, dp);
        }

        return dp[n] = count;
    }

    public static int boardPath_tab(int N, int[] dp) {
        for (int n = 0; n <= N; n++) {
            if (n == 0) {
                dp[n] = 1;
                continue;
            }

            int count = 0;
            for (int dice = 1; dice <= 6; dice++) {
                if (n - dice >= 0)
                    count += dp[n - dice];
            }

            dp[n] = count;
        }

        return dp[N];
    }

    public static int boardPathJumps_mem(int n, int[] dp, int[] jumps) {
        if (n == 0)
            return dp[n] = 1;

        if (dp[n] != 0)
            return dp[n];

        int count = 0;
        for (int jump : jumps) {
            if (n - jump >= 0)
                count += boardPathJumps_mem(n - jump, dp, jumps);
        }

        return dp[n] = count;
    }

    public static int boardPathJumps_tab(int N, int[] dp, int[] jumps) {
        for (int n = 0; n <= N; n++) {
            if (n == 0) {
                dp[n] = 1;
                continue;
            }

            int count = 0;
            for (int jump : jumps) {
                if (n - jump >= 0)
                    count += dp[n - jump];
            }

            dp[n] = count;
        }

        return dp[N];
    }

    public static void boardPath() {
        int n = 12;
        int[] dp = new int[n + 1]; // n+1 bcoz we want n to be part of ans.
        int[] jumps = { 2, 5, 1, 3 };

        // System.out.println(boardPath_mem(n, dp));
        // System.out.println(boardPath_tab(n, dp));
        // System.out.println(boardPathJumps_mem(n, dp, jumps));
        System.out.println(boardPathJumps_tab(n, dp, jumps));
        // display(dp);
    }

    public static void main(String[] args) {
        // fibonacci();
        // mazePath();
        boardPath();
    }
}
