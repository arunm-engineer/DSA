import java.util.Arrays;

// Recursion Backtracking
public class l005RBTK {

    public static int mazePath(int sr, int sc, int er, int ec, String ans, int[][] dir, String[] dirS) {
        if (sr == er && sc == ec) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec) {
                count += mazePath(r, c, er, ec, ans + dirS[d], dir, dirS);
            }
        }

        return count;
    }

    // DFS Algo => Mark, Visit unvisited neighbour, Unmark
    // 0 -> empty cell, 1 -> blocked cell
    public static int floodFill(int sr, int sc, int er, int ec, String ans, int[][] board, int[][] dir, String[] dirS) {
        if (sr == er && sc == ec) {
            System.out.println(ans);
            return 1;
        }

        board[sr][sc] = 1;

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && board[r][c] == 0) {
                count += floodFill(r, c, er, ec, ans + dirS[d], board, dir, dirS);
            }
        }

        board[sr][sc] = 0;

        return count;
    }

    public static int floodFillWithJumps(int sr, int sc, int er, int ec, String ans, int[][] board, int[][] dir,
            String[] dirS) {
        if (sr == er && sc == ec) {
            System.out.println(ans);
            return 1;
        }

        board[sr][sc] = 1;

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            for (int rad = 1; rad <= Math.max(er, ec); rad++) {
                int r = sr + rad * dir[d][0];
                int c = sc + rad * dir[d][1];

                if (r >= 0 && c >= 0 && r <= er && c <= ec) {
                    if (board[r][c] == 0) {
                        count += floodFillWithJumps(r, c, er, ec, ans + dirS[d] + rad, board, dir, dirS);
                    }
                } else
                    break;
            }
        }

        board[sr][sc] = 0;

        return count;
    }

    public static int floodFillWithJumpsWithRadius(int sr, int sc, int er, int ec, String ans, int[][] board,
            int[][] dir,
            String[] dirS, int Radius) {
        if (sr == er && sc == ec) {
            System.out.println(ans);
            return 1;
        }

        board[sr][sc] = 1;

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            for (int rad = 1; rad <= Radius; rad++) {
                int r = sr + rad * dir[d][0];
                int c = sc + rad * dir[d][1];

                if (r >= 0 && c >= 0 && r <= er && c <= ec && board[r][c] != 1) {
                    count += floodFillWithJumpsWithRadius(r, c, er, ec, ans + dirS[d] + rad, board, dir, dirS, Radius);
                }
            }
        }

        board[sr][sc] = 0;

        return count;
    }

    // 1 => empty cell, 0 => blocked cell
    public static int ratInAMaze(int sr, int sc, int er, int ec, String ans, int[][] maze, int[][] path, int[][] dir,
            String[] dirS) {
        if (sr == er && sc == ec) {
            path[sr][sc] = 1;
            System.out.println(ans);
            return 1;
        }

        maze[sr][sc] = 0;
        path[sr][sc] = 1;

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && maze[r][c] == 1) {
                count += ratInAMaze(r, c, er, ec, ans + dirS[d], maze, path, dir, dirS);
                if (count > 0)
                    return count;
            }
        }

        path[sr][sc] = 0;
        maze[sr][sc] = 1;

        return count;
    }

    // 1 => blocked cell, 0 => empty cell
    public static int floodFill_longestPath(int sr, int sc, int er, int ec, int[][] board, int[][] dir) {
        if (sr == er & sc == ec)
            return 0; // Why zero, if src & dest are same then zero should be answer since no move

        board[sr][sc] = 1;

        int longestLen = -(int) 1e9;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && board[r][c] == 0) {
                int recAns = floodFill_longestPath(r, c, er, ec, board, dir);
                if (recAns != -(int) 1e9 && recAns + 1 > longestLen) {
                    longestLen = recAns + 1;
                }
            }
        }

        board[sr][sc] = 0;
        return longestLen;
        // Instead can also +1 ans while returning but "avoid it", because you may
        // receive a wrong ans from rec calls, and your +1 to ans would be wrong
    }

    // 1 => blocked cell, 0 => empty cell
    public static int floodFill_shortestPath(int sr, int sc, int er, int ec, int[][] board, int[][] dir) {
        if (sr == er & sc == ec)
            return 0; // Why zero, if src & dest are same then zero should be answer since no move

        board[sr][sc] = 1;

        int shortestLen = (int) 1e9;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= er && c <= ec && board[r][c] == 0) {
                int recAns = floodFill_shortestPath(r, c, er, ec, board, dir);
                if (recAns != (int) 1e9 && recAns + 1 < shortestLen) {
                    shortestLen = recAns + 1;
                }
            }
        }

        board[sr][sc] = 0;
        return shortestLen;
        // Instead can also +1 ans while returning but "avoid it", because you may
        // receive a wrong ans from rec calls, and your +1 to ans would be wrong
    }

    public static boolean knightTour(int sr, int sc, int[][] board, int move, int[] dirX, int[] dirY) {
        board[sr][sc] = move;
        int n = board.length, m = board[0].length;
        if (move == n*m-1) {  // Since we start our move from 0, our end cell will be n*m - 1
            return true;
        }

        boolean res = false;
        for (int d = 0;d < dirX.length;d++) {
            int r = sr + dirX[d];
            int c = sc + dirY[d];

            if (r >= 0 && c >= 0 && r <= n-1 && c <= m-1 && board[r][c] == -1) {
                res = knightTour(r, c, board, move+1, dirX, dirY);
                if (res)
                    return true;
            }
        }

        board[sr][sc] = -1;
        return res;
    }

    public static void knightTour() {
        int n = 8;
        int[][] board = new int[n][n];
        for (int i = 0;i < n;i++) {
            for (int j = 0;j < n;j++) {
                board[i][j] = -1; // Why fill -1, since 0 is part of the solution to mark
            }
        }

        int[] dirX = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] dirY = { 1, 2, 2, 1, -1, -2, -2, -1 };
        knightTour(0, 0, board, 0, dirX, dirY);
        System.out.println(Arrays.deepToString(board));
    }

    public static void main(String[] args) {

        // int[][] dir3 = { {1, 0}, {1, 1}, {0, 1} };
        // String[] dir3S = { "V", "D", "H" }; // 3-directions
        // System.out.println(mazePath(0, 0, 2, 2, "", dir3, dir3S));

        // int[][] dir4 = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } }; // 4-directions
        // String[] dir4S = { "t", "l", "d", "r" };
        // int[][] dir8 = { {-1, 0}, {0, -1}, {1, 0}, {0, 1}, {-1, -1}, {-1, 1}, {1, 1},
        // {1, -1} }; // 8-directions
        // String[] dir8S = { "t", "l", "d", "r", "N", "E", "S", "W" };
        // int n = 3, m = 3;
        // int[][] board = new int[n][m];
        // System.out.println(floodFill(0, 0, n-1, m-1, "", board, dir4, dir4S));
        // System.out.println(floodFillWithJumps(0, 0, n-1, m-1, "", board, dir4,
        // dir4S));

        // int Radius = 3;
        // System.out.println(floodFillWithJumpsWithRadius(0, 0, n - 1, m - 1, "",
        // board, dir, dirS, Radius));

        // int[][] dir2 = { { 1, 0 }, { 0, 1 } };
        // String[] dir2S = { "V", "H" }; // 2-directions
        // int[][] maze = { { 1, 0, 0, 0 },
        // { 1, 1, 0, 1 },
        // { 0, 1, 1, 0 },
        // { 1, 1, 1, 1 } };

        // int n = maze.length, m = maze[0].length;
        // int[][] path = new int[n][m];
        // System.out.println(ratInAMaze(0, 0, n-1, m-1, "", maze, path, dir2, dir2S));
        // System.out.println(Arrays.deepToString(path));

        // int[][] board = { { 0, 0, 0, 0 },
        // { 1, 0, 0, 1 },
        // { 0, 0, 1, 0 },
        // { 1, 0, 0, 0 } };

        // int n = board.length, m = board[0].length;
        // System.out.println(floodFill_longestPath(0, 0, n-1, m-1, board, dir4));
        // System.out.println(floodFill_shortestPath(0, 0, n-1, m-1, board, dir4));

        knightTour();
    }
}
