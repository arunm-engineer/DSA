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
    
}
