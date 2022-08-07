import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dsuQuestions {

    // LC 695
    private int[] parent, size;

    public int maxAreaOfIsland(int[][] grid) {
        // Here only 2-directions enough (top, down) since actually at the end,
        // the edges of that component will be merged to that global leader of that
        // component
        int[][] dir = { { 0, 1 }, { 1, 0 } };
        int n = grid.length, m = grid[0].length;
        parent = new int[n * m];
        size = new int[n * m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    int idx = i * m + j;
                    parent[idx] = idx;
                    size[idx] = 1;
                }
            }
        }

        int maxArea = 0;
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                if (grid[sr][sc] == 1) {
                    int u = sr * m + sc, p1 = findParent(u);

                    for (int d = 0; d < dir.length; d++) {
                        int r = sr + dir[d][0];
                        int c = sc + dir[d][1];

                        if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                            int v = r * m + c, p2 = findParent(v);
                            if (p1 != p2) {
                                // not considering size factor (by comparing both sets size)
                                // since still the complexity might only fluctuate from O(4) to O(6 or 7)
                                parent[p2] = p1;
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

    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 1061
    private int[] parent;

    private String smallestEquivalentString(String s1, String s2, String baseStr) {
        parent = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent.length; i++)
            parent[i] = i; // initiallly leader will be self

        for (int i = 0; i < s1.length(); i++) {
            int u = s1.charAt(i) - 'a', v = s2.charAt(i) - 'a';
            int parent1 = findParent(u);
            int parent2 = findParent(v);

            // since cycle doesn't matter so don't need to use "if", also p1 == p2 condition
            // is also handled (since the same parent will be set again which will not be an
            // issue - just clean code)
            parent[parent1] = Math.min(parent1, parent2);
            parent[parent2] = Math.min(parent1, parent2);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            int ch = (char) (findParent(baseStr.charAt(i) - 'a') + 'a');
            sb.append(ch);
        }

        return sb.toString();
    }

    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 990
    private int[] parent;

    public boolean equationsPossible(String[] equations) {
        parent = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent.length; i++)
            parent[i] = i; // initiallly leader will be self

        for (String s : equations) {
            int u = s.charAt(0) - 'a', v = s.charAt(3) - 'a';
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent(u);
            int parent2 = findParent(v);

            if (ch == '=' && parent1 != parent2) { // different set but equality condition, so union it
                parent[parent1] = parent2;
            }
        }

        for (String s : equations) {
            int u = s.charAt(0) - 'a', v = s.charAt(3) - 'a';
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent(u);
            int parent2 = findParent(v);

            // if parents same means belongs to same set, but question says "not equal - !"
            // condition
            if (ch == '!' && parent1 == parent2) {
                return false; // so condition failed, invalid
            }
        }

        return true;
    }

    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 839
    private int[] parent;

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int groups = n;
        parent = new int[n];

        for (int i = 0; i < n; i++)
            parent[i] = i;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (areSimilarStrings(strs[i], strs[j])) {
                    int u = i, v = j;
                    int parent1 = findParent(u);
                    int parent2 = findParent(v);

                    // union (merge)
                    if (parent1 != parent2) {
                        parent[parent1] = parent2;
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

    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    private int[] parent;

    public List<Integer> numIslands2(int n, int m, int[][] positions) {
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        parent = new int[n * m];
        Arrays.fill(parent, -1); // -1 says that initially there's no land, if != -1 indicates there's a land present

        List<Integer> ans = new ArrayList<>();
        int islandCount = 0;

        for (int[] p : positions) {
            int sr = p[0], sc = p[1];
            int idx = sr * m + sc;

            if (parent[idx] == -1) { // -1 means no existing land, so we can place our land here now
                islandCount++; // just incr count assuming that you've placed a new land
                parent[idx] = idx; // parent as self

                int p1 = findParent(idx);
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    int IDX = r * m + c;

                    // if nbr == -1 then we need to merge lands & decr count
                    if (r >= 0 && r < n && c >= 0 && c < m && parent[IDX] != -1) { 
                        int p2 = findParent(IDX);
                        if (p1 != p2) { // if both parents arenot equal ( != ) only then we can merge
                            parent[p2] = p1; // in these cases, since p2 is nbrs parent, set parent for nbrs (p2) as (p1)
                            islandCount--; // we can only decr count if & only if the islands which are gonna be merged are diff comps i.e. (p1 != p2)
                        }
                    }
                }
            }

            ans.add(islandCount);
        }

        return ans;
    }

    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 684
    private int[] parent;
    
    public int[] findRedundantConnection(int[][] edges) {
        // Why using DSU?
        // if extra edge means it will connect with some vertice within
        // And ultimately this will lead to cycle formation
        
        int N = edges.length; // this holds true as per the question
        parent = new int[N+1];
        
        for (int i = 1; i <= N; i++)
            parent[i] = i;
        
        int[] extraEdge = null; // stores that extra edge 
        
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            
            int p1 = findParent(u);
            int p2 = findParent(v);
            
            
            if (p1 != p2) 
                parent[p1] = p2; // union, not worrying about size factor
            else 
                extraEdge = e; // cycle, means found that extra edge
        }
        
        return extraEdge;
    }
    
    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 685
    // case 1 : 2 indegrees alone (2 parents for a node)
    // case 2 : only a cycle exists (no 2 indegrees)
    // case 3 : both 2 indegrees and a cycle exists
    int[] parent;
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
        parent = new int[N+1];
        for (int i = 0; i < N; i++) parent[i] = i;
        
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
        int p1 = findParent(u), p2 = findParent(v);
        if (p1 != p2) {
            parent[p2] = p1;
            return false; // no cycle
        }
        else { // cycle
            return true;
        }
    }
    
    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 959
    // Using DSU
    private int[] parent;
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int m = n + 1;
        
        parent = new int[m * m]; // n & m same as per constraints given
        
        for (int i = 0; i < m * m; i++) {
            parent[i] = i;
            
            int r = i / m, c = i % m;
            if (r == 0 || r == m-1 || c == 0 || c == m-1)
                parent[i] = 0; // connect by making a global parent for the boundary regions
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
                    p1 = findParent(u);
                    p2 = findParent(v);
                }
                else if (ch == '\\') { // this format will come as one single char bcoz of escape sequence
                    // For (x, y) -> (x, y) (x+1, y+1)
                    int u = i * m + j;
                    int v = (i + 1) * m + j + 1;
                    p1 = findParent(u);
                    p2 = findParent(v);
                }
                else // means empty space -> ' '
                    continue;
                
                if (p1 != p2) 
                    parent[p1]= p2;
                else // a cycle indicates two boundaries have touched which will partition a new region
                    regions++;
            }            
        }
        
        return regions;
    }
    
    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

    // LC 924
    private int[] parent;
    private int[] size; // represents population count of country
    // initial arr contains infected persons
    public int minMalwareSpread(int[][] graph, int[] initial) {
        int N = graph.length;
        parent = new int[N];
        size = new int[N];
        
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        
        for (int i = 0; i < N; i++) {
            int p1 = findParent(i);
            for (int j = 0; j < N; j++) {
                if (i != j) { // self pointing, although DSU will handle this case by default
                    if (graph[i][j] == 1) {
                        int p2 = findParent(j);
                        if (p1 != p2) {
                            parent[p2] = p1;
                            size[p1] += size[p2];
                        }
                    }
                }
            }
        }
        
        // Now find & store no of infected persons in ecah country
        int[] count = new int[N]; // stores infected persons count in each country
        for (int ip : initial) { 
            // find country & leader of this infected & incr count
            int par = findParent(ip); 
            count[par]++;
        }

        Arrays.sort(initial); // smallest value (infected person) if multiple ans
        
        // Now find country in which if infected removed spread stops, also maxPopulated country is best to save always
        int maxPopulated = 0;
        int infected = initial[0]; // since atleast 1 infected always exists
        for (int ip : initial) {
            int par = findParent(ip);
            // Only if infected count is 1, since if > 1 then other infected's would've spread
            if (count[par] == 1 && size[par] > maxPopulated) {
                maxPopulated = size[par];
                infected = ip;
            }
        }
        
        return infected;
    }
    
    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    /****************************************************************************************************/

}
