import java.util.LinkedList;

public class bfs_Questions {
    
    // LC 994
    public int orangesRotting(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        LinkedList<Integer> q = new LinkedList<>();
        int freshOrangeCount = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    freshOrangeCount++;
                }
                else if (grid[i][j] == 2) { // added all rotten oranges first, which will take time=0
                    int IDX = i * m + j; // encode to 1D arr idx
                    q.addLast(IDX);
                    grid[i][j] = 2; // mark visited, here '2' is just an entity to mark
                }
            }
        }
        
        if (freshOrangeCount == 0)
            return 0;
        
        int time = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int rottenOrangeIDX = q.removeFirst();
                int sr = rottenOrangeIDX / m, sc = rottenOrangeIDX % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                        if (--freshOrangeCount == 0)
                            return time + 1;
                        
                        grid[r][c] = 2; // mark visited, fresh orange -> rotted now
                        int IDX = r * m + c;
                        q.addLast(IDX); // added fresh orange which wil rotted now
                    }
                }
            }
            time++;
        }
        
        return -1; // means more freshOranges are left, which couldn't be reached
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 1091
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dir = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        
        int n = grid.length, m = grid[0].length;
        if (grid[0][0] == 1 || grid[n-1][m-1] == 1)
            return -1;
        
        int shortestPath = 1;
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(0); // adding encoded index in q (2D to 1D)
        grid[0][0] = 1; // mark first position visited
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int idx = q.removeFirst();                
                int sr = idx / m, sc = idx % m;
                
                if (sr == n-1 && sc == m-1)
                    return shortestPath;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        q.addLast(r * m + c); // add encoded index (2D to 1D)
                    }
                }
            }
            shortestPath++;
        }
        
        return -1;
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 542
    public int[][] updateMatrix(int[][] mat) {
        // return bfs_01(mat);
        return bfs_02(mat);
    }
    
    // TC O(n*m) ; SC O(n*m) -> Queue space only
    private int[][] bfs_02(int[][] grid) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int n = grid.length, m = grid[0].length;
        LinkedList<Integer> q = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) { // add all zeroes, to propagate from those 0s to 1s
                    grid[i][j] = 0; // marking is important, marking 0 now though already it is 0
                    q.addLast(i * m + j);
                }
            }
        }
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int idx = q.removeFirst();
                int sr = idx / m, sc = idx % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    // propagating only towards 1s, since 0s are already marked true
                    if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] > 0) {
                        int parentLevel = grid[sr][sc];
                        // why deducting level in -ve, since also used as for visited marking
                        grid[r][c] = parentLevel-1; // updating curr by -1 level, later convert to +ve
                        q.addLast(r * m + c);
                    }
                }
            }
        }
        
        // converting to +ve
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = -1 * grid[i][j];
            }
        }
        
        return grid;
    }
    
    // TC O(n*m) ; SC O (2(n*m)) -> Queue Space + visited arr space
    private int[][] bfs_01(int[][] grid) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int n = grid.length, m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        LinkedList<Integer> q = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) { // add all zeroes, to propagate from those 0s to 1s
                    visited[i][j] = true;
                    q.addLast(i * m + j);
                }
            }
        }
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int idx = q.removeFirst();
                int sr = idx / m, sc = idx % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    // propagating only towards 1s, since 0s are already marked true
                    if (r >= 0 && r < n && c >= 0 && c < m && visited[r][c] == false) {
                        visited[r][c] = true;
                        int parentLevel = grid[sr][sc];
                        grid[r][c] = parentLevel+1; // updating curr by +1 level
                        q.addLast(r * m + c);
                    }
                }
            }
        }
        
        return grid;
    }

    // ---------------------------------------------------------------------------------------------------------
    
}
