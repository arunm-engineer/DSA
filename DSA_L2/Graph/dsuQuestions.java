import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dsuQuestions {

    // LC 695
    private int[] parent_00, size;

    public int maxAreaOfIsland(int[][] grid) {
        // Here only 2-directions enough (top, down) since actually at the end,
        // the edges of that component will be merged to that global leader of that
        // component
        int[][] dir = { { 0, 1 }, { 1, 0 } };
        int n = grid.length, m = grid[0].length;
        parent_00 = new int[n * m];
        size = new int[n * m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    int idx = i * m + j;
                    parent_00[idx] = idx;
                    size[idx] = 1;
                }
            }
        }

        int maxArea = 0;
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                if (grid[sr][sc] == 1) {
                    int u = sr * m + sc, p1 = findParent_00(u);

                    for (int d = 0; d < dir.length; d++) {
                        int r = sr + dir[d][0];
                        int c = sc + dir[d][1];

                        if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                            int v = r * m + c, p2 = findParent_00(v);
                            if (p1 != p2) {
                                // not considering size factor (by comparing both sets size)
                                // since still the complexity might only fluctuate from O(4) to O(6 or 7)
                                parent_00[p2] = p1;
                                size[p1] += size[p2];
                            }
                        }
                    }

                    maxArea = Math.max(maxArea, size[p1]);
                }
            }
        }

        return maxArea;
    }

    private int findParent_00(int u) {
        return parent_00[u] == u ? u : (parent_00[u] = findParent_00(parent_00[u]));
    }

    /****************************************************************************************************/

    // LC 1061
    private int[] parent_01;

    private String smallestEquivalentString(String s1, String s2, String baseStr) {
        parent_01 = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent_01.length; i++)
            parent_01[i] = i; // initiallly leader will be self

        for (int i = 0; i < s1.length(); i++) {
            int u = s1.charAt(i) - 'a', v = s2.charAt(i) - 'a';
            int parent1 = findParent_01(u);
            int parent2 = findParent_01(v);

            // since cycle doesn't matter so don't need to use "if", also p1 == p2 condition
            // is also handled (since the same parent will be set again which will not be an
            // issue - just clean code)
            parent_01[parent1] = Math.min(parent1, parent2);
            parent_01[parent2] = Math.min(parent1, parent2);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            int ch = (char) (findParent_01(baseStr.charAt(i) - 'a') + 'a');
            sb.append(ch);
        }

        return sb.toString();
    }

    private int findParent_01(int u) {
        return parent_01[u] == u ? u : (parent_01[u] = findParent_01(parent_01[u]));
    }

    /****************************************************************************************************/

    // LC 990
    private int[] parent_02;

    public boolean equationsPossible(String[] equations) {
        parent_02 = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent_02.length; i++)
            parent_02[i] = i; // initiallly leader will be self

        for (String s : equations) {
            int u = s.charAt(0) - 'a', v = s.charAt(3) - 'a';
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent_02(u);
            int parent2 = findParent_02(v);

            if (ch == '=' && parent1 != parent2) { // different set but equality condition, so union it
                parent_02[parent1] = parent2;
            }
        }

        for (String s : equations) {
            int u = s.charAt(0) - 'a', v = s.charAt(3) - 'a';
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent_02(u);
            int parent2 = findParent_02(v);

            // if parents same means belongs to same set, but question says "not equal - !"
            // condition
            if (ch == '!' && parent1 == parent2) {
                return false; // so condition failed, invalid
            }
        }

        return true;
    }

    private int findParent_02(int u) {
        return parent_02[u] == u ? u : (parent_02[u] = findParent_02(parent_02[u]));
    }

    /****************************************************************************************************/

    // LC 839
    private int[] parent_03;

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int groups = n;
        parent_03 = new int[n];

        for (int i = 0; i < n; i++)
            parent_03[i] = i;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (areSimilarStrings(strs[i], strs[j])) {
                    int u = i, v = j;
                    int parent1 = findParent_03(u);
                    int parent2 = findParent_03(v);

                    // union (merge)
                    if (parent1 != parent2) {
                        parent_03[parent1] = parent2;
                        groups--;
                    }
                }
            }
        }

        return groups;
    }

    private boolean areSimilarStrings(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++count > 2) // means will take more than swaps to convert
                return false;
        }

        return true;
    }

    private int findParent_03(int u) {
        return parent_03[u] == u ? u : (parent_03[u] = findParent_03(parent_03[u]));
    }

    /****************************************************************************************************/

    private int[] parent_04;

    public List<Integer> numIslands2(int n, int m, int[][] positions) {
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        parent_04 = new int[n * m];
        Arrays.fill(parent_04, -1); // -1 says that initially there's no land, if != -1 indicates there's a land present

        List<Integer> ans = new ArrayList<>();
        int islandCount = 0;

        for (int[] p : positions) {
            int sr = p[0], sc = p[1];
            int idx = sr * m + sc;

            if (parent_04[idx] == -1) { // -1 means no existing land, so we can place our land here now
                islandCount++; // just incr count assuming that you've placed a new land
                parent_04[idx] = idx; // parent as self

                int p1 = findParent_04(idx);
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    int IDX = r * m + c;

                    // if nbr == -1 then we need to merge lands & decr count
                    if (r >= 0 && r < n && c >= 0 && c < m && parent_04[IDX] != -1) { 
                        int p2 = findParent_04(IDX);
                        if (p1 != p2) { // if both parents arenot equal ( != ) only then we can merge
                            parent_04[p2] = p1; // in these cases, since p2 is nbrs parent, set parent for nbrs (p2) as (p1)
                            islandCount--; // we can only decr count if & only if the islands which are gonna be merged are diff comps i.e. (p1 != p2)
                        }
                    }
                }
            }

            ans.add(islandCount);
        }

        return ans;
    }

    private int findParent_04(int u) {
        return parent_04[u] == u ? u : (parent_04[u] = findParent_04(parent_04[u]));
    }

    /****************************************************************************************************/

    // LC 684
    private int[] parent_05;
    
    public int[] findRedundantConnection(int[][] edges) {
        // Why using DSU?
        // if extra edge means it will connect with some vertice within
        // And ultimately this will lead to cycle formation
        
        int N = edges.length; // this holds true as per the question
        parent_05 = new int[N+1];
        
        for (int i = 1; i <= N; i++)
            parent_05[i] = i;
        
        int[] extraEdge = null; // stores that extra edge 
        
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            
            int p1 = findParent_05(u);
            int p2 = findParent_05(v);
            
            
            if (p1 != p2) 
                parent_05[p1] = p2; // union, not worrying about size factor
            else 
                extraEdge = e; // cycle, means found that extra edge
        }
        
        return extraEdge;
    }
    
    private int findParent_05(int u) {
        return parent_05[u] == u ? u : (parent_05[u] = findParent_05(parent_05[u]));
    }

    /****************************************************************************************************/

    // LC 685
    // case 1 : 2 indegrees alone (2 parents for a node)
    // case 2 : only a cycle exists (no 2 indegrees)
    // case 3 : both 2 indegrees and a cycle exists
    int[] parent_06;
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int N = edges.length;
        
        // first find case 1 : 2 indegrees
        int[] indegrees = new int[N+1];
        Arrays.fill(indegrees, -1);
        int edge1 = -1, edge2 = -1; // stores index of edge from edges arr
        for (int i = 0; i < N; i++) {
            int u = edges[i][0], v = edges[i][1];
            if (indegrees[v] == -1) { 
                indegrees[v] = i;
            }
            else { // 2 indegrees found
                edge1 = indegrees[v];
                edge2 = i;
                break;
            }
        }
        
        
        // Still we need to find if at all cycle exists, then our answeer changes
        parent_06 = new int[N+1];
        for (int i = 0; i < N; i++) parent_06[i] = i;
        
        for (int i = 0; i < N; i++) {
            if (i == edge2) // ignoring edge2 since it came later, which might have caused indegrees prblm
                continue;
            
            int u = edges[i][0], v = edges[i][1];
            boolean isCycle = union(u, v);
            if (isCycle) {
                if (edge1 == -1) { // case 2, means no 2 indegree, so no edge ignored, so only a cycle
                    return edges[i];
                } 
                else { // case 3, still found cycle, means ignored worng edge, so other edge to be removed
                    return edges[edge1];
                }
            }
        }
        
        return edges[edge2]; // case 1, also case 3 since we ignored correct edge in DSU so no cycle
    }
    
    private boolean union(int u, int v) {
        int p1 = findParent_06(u), p2 = findParent_06(v);
        if (p1 != p2) {
            parent_06[p2] = p1;
            return false; // no cycle
        }
        else { // cycle
            return true;
        }
    }
    
    private int findParent_06(int u) {
        return parent_06[u] == u ? u : (parent_06[u] = findParent_06(parent_06[u]));
    }

    /****************************************************************************************************/

    // LC 959
    // Using DSU
    private int[] parent_07;
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int m = n + 1;
        
        parent_07 = new int[m * m]; // n & m same as per constraints given
        
        for (int i = 0; i < m * m; i++) {
            parent_07[i] = i;
            
            int r = i / m, c = i % m;
            if (r == 0 || r == m-1 || c == 0 || c == m-1)
                parent_07[i] = 0; // connect by making a global parent for the boundary regions
        }
        
        int regions = 1; // initially after joining all boundaries we will have 1 region by default
        for (int i = 0; i < grid.length; i++) {
            String s = grid[i];
            for (int j = 0; j < s.length(); j++) {
                char ch = s.charAt(j);
                int p1 = -(int) 1e9, p2 = -(int) 1e9;
                
                if (ch == '/') {
                    // For (x, y) -> (x, y+1) (x+1, y)
                    int u = i * m + j + 1;
                    int v = (i+1) * m + j;
                    p1 = findParent_07(u);
                    p2 = findParent_07(v);
                }
                else if (ch == '\\') { // this format will come as one single char bcoz of escape sequence
                    // For (x, y) -> (x, y) (x+1, y+1)
                    int u = i * m + j;
                    int v = (i + 1) * m + j + 1;
                    p1 = findParent_07(u);
                    p2 = findParent_07(v);
                }
                else // means empty space -> ' '
                    continue;
                
                if (p1 != p2) 
                    parent_07[p1]= p2;
                else // a cycle indicates two boundaries have touched which will partition a new region
                    regions++;
            }            
        }
        
        return regions;
    }
    
    private int findParent_07(int u) {
        return parent_07[u] == u ? u : (parent_07[u] = findParent_07(parent_07[u]));
    }

    /****************************************************************************************************/

}
