import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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

    // LC 743
    // Djikstra Approach 1
    public int networkDelayTime(int[][] times, int n, int k) {
        ArrayList<Edge>[] graph = constructGraph(times, n); // Instead of Edge class we can use int[] array to represent edge (OA test - int[], Interview - Edge class)
        
        // Djikstra's Algo
        boolean[] visited = new boolean[n+1];
        int[] dis = new int[n+1];
        Arrays.fill(dis, (int) 1e9);
        
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> { // Instead of Pair class we can use int[] array to represent edge (OA test - int[], Interview - Pair class)
            return a.wsf - b.wsf;
        });
        
        pq.add(new Pair(k, 0));
        while (!pq.isEmpty()) {
            Pair p = pq.remove();
            
            if (visited[p.vtx])
                continue;
            
            visited[p.vtx] = true;
            dis[p.vtx] = p.wsf;
            
            for (Edge e : graph[p.vtx]) {
                if (!visited[e.v]) {
                    pq.add(new Pair(e.v, p.wsf + e.w));
                }
            }
        }
        
        int minTime = -(int) 1e9;
        for (int i = 1; i < dis.length; i++) // since nodes vtx starts from 1
            minTime = Math.max(dis[i], minTime);
        
        return minTime == (int) 1e9 ? -1 : minTime;
    }

    // Djikstra Approach 2
    public int networkDelayTime_(int[][] times, int n, int k) {
        ArrayList<Edge>[] graph = constructGraph(times, n);
        
        // Djikstra's Algo
        int[] dis = new int[n+1];
        Arrays.fill(dis, (int) 1e9);
        
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf;
        });
        
        pq.add(new Pair(k, 0));
        dis[k] = 0;
        while (!pq.isEmpty()) {
            Pair p = pq.remove();
            
            if (p.wsf > dis[p.vtx])
                continue;
            
            for (Edge e : graph[p.vtx]) {
                if (p.wsf + e.w < dis[e.v]) {
                    dis[e.v] = p.wsf + e.w;
                    pq.add(new Pair(e.v, p.wsf + e.w));
                }
            }
        }
        
        int minTime = -(int) 1e9;
        for (int i = 1; i < dis.length; i++) // since nodes vtx starts from 1
            minTime = Math.max(dis[i], minTime);
        
        return minTime == (int) 1e9 ? -1 : minTime;
    }
    
    private class Pair {
        int vtx;
        int wsf;
        
        public Pair() {}
        
        public Pair(int vtx, int wsf) {
            this.vtx = vtx;
            this.wsf = wsf;
        }
    }
    
    private class Edge {
        int v;
        int w;
        
        public Edge() {}
        
        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
    
    private ArrayList<Edge>[] constructGraph(int[][] times, int n) {
        ArrayList<Edge>[] graph = new ArrayList[n+1];
        
        for (int i = 0; i < graph.length; i++)
            graph[i] = new ArrayList<Edge>();
        
        for (int[] e : times) 
            addEdge(graph, e[0], e[1], e[2]);
        
        return graph;
    }
    
    private void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        graph[u].add(new Edge(v, w));
    }

    /****************************************************************************************************/

    // LC 787
    // Aprroach 1 - Djikstra like, not exactly (But based on 2 factors - wsf & k stops)
    // Correct approach but we'll get TLE (Just for relating with Djikstra solved this, conceptual based)
    // TC O(ElogV)
    public int findCheapestPrice_00(int n, int[][] flights, int src, int dst, int k) {
        ArrayList<Edge>[] graph = constructGraph(flights, n); // can use int[] instead of Edge in OA
        
        // can't use dis arr concept since we've 2 factors here wsf and k stops
        // so wsf can be big also even if stops are within k
        // Note: for k=1 -> edges=2, k=2 -> edges=3 (i.e. edges = k+1, k resembles nodes which takes at max k+1 edges to reach from src to dest with k stops in-between)

        int allowedEdges = k+1;
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf;
        });

        pq.add(new Pair(src, 0, 0));
        while (!pq.isEmpty()) {
            Pair p = pq.remove();
            
            if (p.edges > allowedEdges) 
                continue;
            
            if (p.vtx == dst) // at this point edges are within allowedEdges & we reached dest with min. wsf
                return p.wsf;
            
            for (Edge e : graph[p.vtx]) {
                pq.add(new Pair(e.v, p.wsf + e.w, p.edges + 1));
            }
        }
        
        return -1; // means we cant reach dest within k stops, if at all ans existed it would be caught above
    }
    
    private class Pair {
        int vtx;
        int wsf;
        int edges;
        
        public Pair() {}
        
        public Pair(int vtx, int wsf, int edges) {
            this.vtx = vtx;
            this.wsf = wsf;
            this.edges = edges;
        }
    }
    
    private class Edge {
        int v;
        int w;
        
        public Edge() {}
        
        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
    
    private ArrayList<Edge>[] constructGraph(int[][] edges, int N) {
        ArrayList<Edge>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<Edge>();
        
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            addEdge(graph, u, v, w);
        }
        
        return graph;
    }
    
    private void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        graph[u].add(new Edge(v, w));
    }

    // Bellman Ford Algo 
    // (Simple version) Accepted
    // Since only +ve edges, no negative cycle exists
    public int findCheapestPrice_01(int n, int[][] flights, int src, int dst, int k) {
        int[] prev = new int[n]; // k+1 size is enough 
        Arrays.fill(prev, (int) 1e9);
        prev[src] = 0;
        
        int totalEdges = k+1; 
        for (int i = 1; i <= totalEdges; i++) { // ith idx says atmost using i edges, min. wt. to reach vtxs
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) 
                curr[j] = prev[j];

            for (int[] e : flights) {
                int u = e[0], v = e[1], w = e[2];
                if (prev[u] != (int) 1e9 && prev[u] + w < curr[v]) 
                    curr[v] = prev[u] + w;
            }

            prev = curr;
        }
        
        return prev[dst] != (int) 1e9 ? prev[dst] : -1;
    }

    // Bellman Ford Algo 
    // (Time and space optimized version) Accepted
    // Since only +ve edges, no negative cycle exists
    public int findCheapestPrice_02(int n, int[][] flights, int src, int dst, int k) {
        // k+1 size is enough
        int[] prev = new int[n]; // u 
        int[] curr = new int[n]; // v
        Arrays.fill(prev, (int) 1e9);
        Arrays.fill(curr, (int) 1e9);
        prev[src] = curr[src] = 0;

        int totalEdges = k+1; 
        for (int i = 1; i <= totalEdges; i++) { // ith idx says atmost using i edges, min. wt. to reach vtxs
            boolean anyUpdate = false;

            for (int[] e : flights) {
                int u = e[0], v = e[1], w = e[2];
                if (prev[u] != (int) 1e9 && prev[u] + w < curr[v]) {
                    curr[v] = prev[u] + w;
                    anyUpdate = true;
                }
            }

            if (!anyUpdate) // means no update further as well
                break;

            for (int j = 0; j < n; j++) 
                prev[j] = curr[j];
        }
        
        return prev[dst] != (int) 1e9 ? prev[dst] : -1;
    }

    /****************************************************************************************************/

    // LC 1334
    // Floyd Warshall Algo
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // relaxation of vtxs & conversion to matrix form graph
        int[][] mat = new int[n][n]; // finally will store mincost from any vtx to any vtx

        for (int[] a : mat) // if no edges will be infinite wt. by default
            Arrays.fill(a, (int) 1e9);

        for (int[] e : edges) { // plot edges
            int u = e[0], v = e[1], w = e[2];
            mat[u][v] = w;
            mat[v][u] = w;
        }

        for(int i = 0; i < n; i++)
            mat[i][i] = 0; // relaxation

        for (int k = 0; k < n; k++) { // intermediate vtx
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    mat[i][j] = Math.min(mat[i][j], mat[i][k] + mat[k][j]); // infinite case check will be handled
                }
            }
        }
        
        // finding smallest nbrs within threshold distance
        int city = -1;
        int cityCount = (int) 1e9;
        for (int i = 0; i < n; i++) {
            int currVtxCityCount = 0;
            for (int j = 0; j < n; j++) {
                if (mat[i][j] <= distanceThreshold)
                    currVtxCityCount++;
            }
            
            if (currVtxCityCount < cityCount) {
                cityCount = currVtxCityCount;
                city = i;
            }
            else if (currVtxCityCount == cityCount) {
                city = Math.max(i, city);
            }
        }
        
        return city;
    }

    /****************************************************************************************************/

    // LC 154 [follow up problem of LC 153]
    // Worst case TC O(n) (bcoz of duplicates), Avg case TC O(logn)
    public int findMin(int[] nums) {
        int n = nums.length;
        int lo = 0, hi = n-1;
        
        while (lo < hi) {
            int mid = (hi-lo)/2 + lo;
            
            if (nums[hi] > nums[mid]) {
                hi = mid;
            }
            else if (nums[hi] < nums[mid]) {
                lo = mid + 1;
            }
            else { // duplicates, equality case, nums[hi] == nums[mid]
                hi--;
            }
        }
        
        return nums[lo];
    }

    /****************************************************************************************************/

}
