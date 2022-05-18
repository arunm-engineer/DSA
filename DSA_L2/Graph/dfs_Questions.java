public class dfs_Questions {

    // LC 200
    // Interms of matrix TC O(n*m); Interms of Edges TC O(E+V)
    public int numIslands(char[][] grid) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int count = 0;
        for (int sr = 0; sr < grid.length; sr++) {
            for (int sc = 0; sc < grid[0].length; sc++) {
                if (grid[sr][sc] != '0' && grid[sr][sc] != '2') {
                    count++;
                    dfs_numOfIslands(grid, sr, sc, dir);                    
                }
            }
        }
        
        return count;
    }
    
    // '1' -> land, '0' -> water, '2' -> visited
    private void dfs_numOfIslands(char[][] grid, int sr, int sc, int[][] dir) {        
        grid[sr][sc] = '2';
        
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] != '0' && grid[r][c] != '2')
                dfs_numOfIslands(grid, r, c, dir);
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 695
    // Interms of matrix TC O(n*m); Interms of Edges TC O(E+V)
    public int maxAreaOfIsland(int[][] grid) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int maxArea = 0;
        for (int sr = 0; sr < grid.length; sr++) {
            for (int sc = 0; sc < grid[0].length; sc++) {
                if (grid[sr][sc] != 0 && grid[sr][sc] != 2) {
                    int area = dfs_maxAreaOfIsland(grid, sr, sc, dir);
                    maxArea = Math.max(area, maxArea);
                }
            }
        }
        
        return maxArea;
    }
    
    // '1' -> land, '0' -> water, '2' -> visited
    private int dfs_maxAreaOfIsland(int[][] grid, int sr, int sc, int[][] dir) {
        grid[sr][sc] = 2;
        
        int count = 1; // this 1 is for current cell including itself; count -> area (num of cells)
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] != 0 && grid[r][c] != 2)
                count += dfs_maxAreaOfIsland(grid, r, c, dir);
        }
        
        return count;
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 463 // TC O(E+V)
    public int islandPerimeter_00(int[][] grid) {
        int[][] dir = {{-1, 0}, {0,1}, {1, 0}, {0, -1}};
        
        int perimeter = 0;
        for (int sr = 0; sr < grid.length; sr++) {
            for (int sc = 0; sc < grid[0].length; sc++) {
                // since only one such land exists in the entire graph
                if (grid[sr][sc] != 0 && grid[sr][sc] != 2) { 
                    perimeter = dfs_islandPerimeter(grid, sr, sc, dir);
                    break;
                }
            }
        }
        
        return perimeter;
    }
    
    // Perimeter -> the edges or border line of the graph component(land)
    // So to calc perimeter count the border lines
    private int dfs_islandPerimeter(int[][] grid, int sr, int sc, int[][] dir) {
        grid[sr][sc] = 2;
        
        int perimeter = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            // calc perimeter for currCell -> out of bounds (or) water means border line i.e. add perimeter
            if (r == -1 || c == -1 || r == grid.length || c == grid[0].length || grid[r][c] == 0)
                perimeter += 1;
            
            if (r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] != 0 && grid[r][c] != 2)
                perimeter += dfs_islandPerimeter(grid, r, c, dir);
        }
        
        return perimeter;
    }

    public int islandPerimeter_01(int[][] grid) {
        int[][] dir = {{0, 1}, {1, 0}};
        int validCellsCount = 0;
        int neighbourCellsCount = 0; // Adjacent cells
        
        // direction -> right, down to check for adjacent cells, why not 4 directions? since other 2 direction will already be covered in cases
        for (int sr = 0; sr < grid.length; sr++) {
            for (int sc = 0; sc < grid[0].length; sc++) {
                if (grid[sr][sc] == 1) {
                    validCellsCount++;
                
                    for (int d = 0; d < dir.length; d++) {
                        int r = sr + dir[d][0];
                        int c = sc + dir[d][1];

                        if (r < grid.length && c < grid[0].length && grid[r][c] == 1) 
                            neighbourCellsCount++;
                    }    
                }
                
            }
        }
        
        // formula -> 4 sides, 2 boxes shares a edge
        int perimeter = (validCellsCount * 4) - (neighbourCellsCount * 2); 
        return perimeter;
    }

    // ---------------------------------------------------------------------------------------------------------

}
