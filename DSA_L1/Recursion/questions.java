import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class questions {

    // LC 46
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();
        
        permutationsWithoutDuplicates(nums, 0, ans, list);
        return list;
    }
    
    public int permutationsWithoutDuplicates(int[] nums, int k, List<Integer> ans, List<List<Integer>> list) {
        if (k == nums.length) {
            List<Integer> base = new ArrayList<>(ans);
            list.add(base);
            return 1;
        }
        
        int count = 0;
        for (int i = 0;i < nums.length;i++) {
            if (nums[i] == (int)1e9) continue;
            
            int val = nums[i];
            
            nums[i] = (int)1e9;
            ans.add(val);
            count += permutationsWithoutDuplicates(nums, k+1, ans, list);
            
            ans.remove(ans.size()-1);
            nums[i] = val;
        }
        
        return count;
    }

    /****************************************************************/

    // LC 47
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums); // To avoid duplication first sort, then by 2-pointer logic we can avoid duplicacy
        
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();
        permutationsWithDuplicates(nums, 0, ans, list);
        return list;
    }
    
    public int permutationsWithDuplicates(int[] nums, int k, List<Integer> ans, List<List<Integer>> list) {
        if (k == nums.length) {
            List<Integer> base = new ArrayList<>(ans);
            list.add(base);
            return 1;
        }
        
        int count = 0;
        
        int prev = (int)1e9;
        for (int i = 0;i < nums.length;i++) {
            int cur = nums[i];
            // To check for cur is not marked & p != c to avoid duplicacy of permutation calls
            if (cur != (int)1e9 && prev != cur) {
                nums[i] = (int)1e9;
                ans.add(cur);

                count += permutationsWithDuplicates(nums, k+1, ans, list);

                ans.remove(ans.size()-1);
                nums[i] = cur;   
            }
            
            prev = cur;
        }
        
        return count;
    }

    /****************************************************************/

    // https://www.geeksforgeeks.org/rat-in-a-maze-backtracking-2/
    public static int floodFill(int sr, int sc, int er, int ec, String ans,
    int[][] maze, int[][] dir, String[] dirS, ArrayList<String> list) {
        
        if (sr == er && sc == ec) {
            list.add(ans);
            return 1;
        }
        
        maze[sr][sc] = 0;
        
        int count = 0;
        for (int d = 0;d < dir.length;d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && c >= 0 && r <= er && c <= ec && maze[r][c] == 1) {
                count += floodFill(r, c, er, ec, ans + dirS[d], maze, dir, dirS, list);
            }
        }
        
        maze[sr][sc] = 1;
        return count;
    }
    
    // If you want to find "only one path" & return immediately
    public static boolean floodFill_02(int sr, int sc, int er, int ec, String ans,
    int[][] maze, int[][] dir, String[] dirS, ArrayList<String> list) {
        
        if (sr == er && sc == ec) {
            return true;
        }
        
        maze[sr][sc] = 0;
        
        boolean res = false;
        for (int d = 0;d < dir.length;d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && c >= 0 && r <= er && c <= ec && maze[r][c] == 1) {
                res = res || floodFill_02(r, c, er, ec, ans + dirS[d], maze, dir, dirS, list);
            }
        }
        
        maze[sr][sc] = 1;
        return res;
    }
    
    public static ArrayList<String> findPath(int[][] m, int n) {
        // Your code here
        
        ArrayList<String> list = new ArrayList<>();
        
        if (n == 0 || m[0][0] == 0 || m[n-1][n-1] == 0) return list;
        
        int[][] dir = { {-1,0}, {1,0}, {0,-1}, {0,1} };
        String[] dirS = { "U", "D", "L", "R" };
        floodFill(0, 0, n-1, n-1, "", m, dir, dirS, list);
        // floodFill_02(0, 0, n-1, n-1, "", m, dir, dirS, list);
        
        Collections.sort(list);
        return list;
    }

    /****************************************************************/

    // https://www.geeksforgeeks.org/count-number-ways-reach-destination-maze/
    static int mod = (int)1e9+7;
    public static int floodFill(int sr, int sc, int er, int ec,
        int[][] maze, int[][] dir) {
        
        if (sr == er && sc == ec) {
            return 1;
        }
        
        maze[sr][sc] = 1;
        
        int count = 0;
        for (int d = 0;d < dir.length;d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            
            if (r >= 0 && c >= 0 && r <= er && c <= ec && maze[r][c] == 0) {
                count = (count%mod + floodFill(r, c, er, ec, maze, dir)%mod)%mod;
            }
        }
        
        maze[sr][sc] = 0;
        return count;
    }
    
    public int FindWays(int n, int m, int[][] blocked_cells)
    {
        // Code here
        if (n == 0 || m == 0) return 0;
        // 1 => blocked cell, 0 => empty cell
        int[][] maze = new int[n][m];
        for (int[] cells : blocked_cells) {
            int r = cells[0] - 1;
            int c = cells[1] - 1;
            
            maze[r][c] = 1;
        }
        
        if (maze[0][0] == 1 || maze[n-1][m-1] == 1) return 0;
        
        int[][] dir = { {1,0}, {0,1} };
        int count = floodFill(0, 0, n-1, m-1, maze, dir);
        return count;
    }

    /****************************************************************/

    // https://www.geeksforgeeks.org/rat-in-a-maze-with-multiple-steps-jump-allowed/
    // https://www.geeksforgeeks.org/rat-in-a-maze-problem-when-movement-in-all-possible-directions-is-allowed/

    /****************************************************************/

}