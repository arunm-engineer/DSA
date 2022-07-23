import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AlgoQuestions {
    
    // LC 1168
    // Using Kruskal MST Algo
    private int[] parent_00;
    public int minCostToSupplyWater(int N, int[] wells, int[][] pipes) {
        // Connect all cities with wells 0th node as a common reference point to apply MST
        ArrayList<int[]> allPipes = new ArrayList<>(); // edges
        
        for (int[] e : pipes)
            allPipes.add(e);
        
        for (int i = 0; i < wells.length; i++) // adding wells to cities edges
            allPipes.add(new int[]{0, i+1, wells[i]});

        parent_00 = new int[N+1];
        for (int i = 0; i <= N; i++) 
            parent_00[i] = i;

        // Sort on wt.
        Collections.sort(allPipes, (a, b) -> a[2] - b[2]);
        
        // DSU
        int ans = 0; // represents the mincost to distribute water (the MST ans)
        for (int[] e : allPipes) {
            int u = e[0], v = e[1], w = e[2];

            int p1 = findParent_00(u);
            int p2 = findParent_00(v);

            if (p1 != p2) {
                parent_00[p1] = p2;
                ans += w;
            }
        }

        return ans;
    }

    private int findParent_00(int u) {
        return parent_00[u] == u ? u : (parent_00[u] = findParent_00(parent_00[u]));
    }

    /****************************************************************************************************/

    // https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/practice-problems/algorithm/mr-president/
    // Kruskal MST Algo
    private int[] parent_01;
    private int mrPresident(int[][] edges, int N, int k) {
        Arrays.sort(edges, (a, b) -> {
            return a[2] - b[2];
        });

        parent_01 = new int[N+1];
        int totalComponents = N;

        for (int i = 0; i <= N; i++)
            parent_01[i] = i;

        ArrayList<Integer> roads = new ArrayList<>(); // contains weights of MST component (in increasing order since sorted)
        int cost = 0;
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            int p1 = findParent_01(u);
            int p2 = findParent_01(v);

            // union
            if (p1 != p2) {
                parent_01[p2] = p1;
                cost += w;
                totalComponents--; // reduce comps while merging
                roads.add(w);
            }
        }

        if (totalComponents > 1)
            return -1;

        int superRoadsConverted = 0;
        for (int i = roads.size()-1; i >= 0; i--) { // we need to reduce maintenance cost, so remove edge with greater weights, so traverse in reverse order (list with MST edge weights is in increasing order)
            if (cost <= k)
                break;
            cost = cost - roads.get(i) + 1; // +1 for super road conversion maintenance cost
            superRoadsConverted++;
        }

        return cost <= k ? superRoadsConverted : -1;
    }

    private int findParent_01(int u) {
        return parent_01[u] == u ? u : (parent_01[u] = findParent_01(parent_01[u]));
    }

    /****************************************************************************************************/

    // LC 1192
    // Articulation Edges
    private int[] low, disc;
    private boolean[] visited;
    private int time;
    
    public List<List<Integer>> criticalConnections(int N, List<List<Integer>> connections) {
        // construct graph
        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();

        for (List<Integer> e : connections) {
            int u = e.get(0), v = e.get(1);
            graph[u].add(v);
            graph[v].add(u);
        }

        // intialization
        low = new int[N];
        disc = new int[N];
        visited = new boolean[N];
        List<List<Integer>> ans = new ArrayList<>();
        time = 0;
        
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                dfs(graph, 0, -1, ans); // -1 -> since no parent for root node
            }
        }

        return ans;
    }
    
    private void dfs(ArrayList<Integer>[] graph, int src, int parent, List<List<Integer>> ans) {
        disc[src] = low[src] = time++;
        visited[src] = true;
        
        for (int nbr : graph[src]) {
            if (!visited[nbr]) {
                dfs(graph, nbr, src, ans);
                
                if (low[nbr] > disc[src]) {
                    List<Integer> artEdge = new ArrayList<>(Arrays.asList(src, nbr));
                    ans.add(artEdge);
                }
                
                low[src] = Math.min(low[src], low[nbr]);
            }
            else if (nbr != parent) {
                low[src] = Math.min(low[src], disc[nbr]);
            }
        }
    }

    /****************************************************************************************************/

}
