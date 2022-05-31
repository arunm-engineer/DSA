import java.util.HashSet;

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

    // LC 463 TC O(E+V)
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

    // LC 130 TC O(E+V)
    public void solve(char[][] board) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int n = board.length, m = board[0].length;
        
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                // call only from boundary pts, this boundary pt. is pt. from where water will flow out
                // since that as starting pt. we can mark the entire place where water would flow out
                if((sr == 0 || sr == n-1 || sc == 0 || sc == m-1) && board[sr][sc] == 'O')
                    dfs_surroundedRegions(board, sr, sc, dir);
            }
        }
        
        for (int i = 0; i < n; i++)  {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '#') // ehy unmark, since water flowing area to be remained as it is
                    board[i][j] = 'O';
                else // water non-flowing areas to be, marked as buildings
                    board[i][j] = 'X';
            }
        }
    }
    
    private void dfs_surroundedRegions(char[][] grid, int sr, int sc, int[][] dir) {
        grid[sr][sc] = '#'; // marking all places where water would not last (water would flow out)
        
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] == 'O')
                dfs_surroundedRegions(grid, r, c, dir);
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    // https://www.hackerrank.com/challenges/journey-to-the-moon/problem

    // ---------------------------------------------------------------------------------------------------------

    // LC 694 (Premium)
    // LintCode 860
    public int numberofDistinctIslands(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        String[] dirS = {"T", "R", "D", "L"};
        HashSet<String> set = new HashSet<>();
        StringBuilder isLandPattern;

        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                if (grid[sr][sc] == 1) {
                    isLandPattern = new StringBuilder();
                    dfs_numOfDistinctIslands(grid, sr, sc, dir, dirS, isLandPattern);
                    set.add(isLandPattern.toString());
                }
                
            }
        }

        return set.size();
    }

    private void dfs_numOfDistinctIslands(int[][] grid, int sr, int sc, int[][] dir, String[] dirS, StringBuilder isLandPattern) {
        int n = grid.length, m = grid[0].length;

        grid[sr][sc] = 0;

        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                isLandPattern.append(dirS[d]);
                dfs_numOfDistinctIslands(grid, r, c, dir, dirS, isLandPattern);
                isLandPattern.append("B"); // backtracking
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 1905
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int n = grid2.length, m = grid2[0].length;
        int countOfSubIslands = 0;
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                if (grid2[sr][sc] == 1) {
                    boolean res = dfs_countSubIslands(grid1, grid2, sr, sc, dir);
                    if (res)
                        countOfSubIslands++;
                }
            }
        }
        
        return countOfSubIslands;
    }
    
    private boolean dfs_countSubIslands(int[][] grid1, int[][] grid2, int sr, int sc, int[][] dir) {
        int n = grid2.length, m = grid2[0].length;
        grid2[sr][sc] = 0; // mark
        
        boolean res = true;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && r < n && c >= 0 && c < m && grid2[r][c] == 1) 
                res = dfs_countSubIslands(grid1, grid2, r, c, dir) && res; 
                
            // why res afterwards rec call, bcoz if "false", we still want to mark that entire land as useless by "making rec calls",
            // so that the other parts of island on later point don't claim as subIsland, since it is not valid
        }
        
        return res && grid1[sr][sc] == 1; // checking myself also (for valid part of island)
    }

    // ---------------------------------------------------------------------------------------------------------

}