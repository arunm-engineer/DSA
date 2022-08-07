import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Algo {

    public static class Edge {
        int v = 0;
        int w = 0;

        public Edge() {}

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
        // Bi-directional Graph
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }

    // TC O(2E) -> O(E) // why 2E, bcoz bi-directional graph. Total no. of edges will be 2E. Why complexity not O(V.E)? Not all vertices have E num of edges -> Refer notes
    public static void display(ArrayList<Edge>[] graph) {

        for (int u = 0; u < graph.length; u++) {
            System.out.print(u + " -> ");
            for (Edge e : graph[u]) 
                System.out.print("(" + e.v + ", " + e.w + ") ");
            System.out.println();
        }
    }

    /****************************************************************************************************/

    // Minimum Spanning Tree (MST) => Summation of all edges wt. which is minimum
    // Kruskal's Algorithm => DSU + Sort edges on basis of wt. in increasing order

    int[] parent, size;


    private int findParent(int u) {
        return parent[u] == u ? u : (parent[u] = findParent(parent[u]));
    }

    private void union(int p1, int p2) {
        if (size[p1] > size[p2]) {
            parent[p2] = p1;
            size[p1] += size[p2];
        }
        else {
            parent[p1] = p2;
            size[p2] += size[p1];
        }
    }

    // TC O(V + ElogV)
    private void unionFind(int[][] edges, ArrayList<Edge>[] graph, int N) {
        parent = new int[N];
        size = new int[N];

        // TC O(V)
        for (int i = 0; i < N; i++)
            parent[i] = i;

        // TC O(ElogV), why logV? since some books say it even though we applied Path  compression, so just for sake, also E, loop for total no. of edges
        // But we know original complexity with P.C applied is O(4) ~ O(1)
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];

            int p1 = findParent(u);
            int p2 = findParent(v);

            if (p1 != p2) { // means no cycle, else we can avoid that cycle edge
                union(p1, p2);
                addEdge(graph, u, v, w); // constructing Acyclic graph along the way
            }
        }
    }

    // TC O(ElogE)
    public void KruskalAlgo(int[][] edges, int N) {
        // TC O(ElogE) where E is total no. of edges
        Arrays.sort(edges, (a, b) -> {
            return a[2] - b[2]; // since we want edges sorted on basis of wt. in increasing order
        });

        ArrayList<Edge>[] graph = new ArrayList[N];

        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<Edge>();

        unionFind(edges, graph, N);
    }

    /**
     * Overall complexity for Kruskal Algo
     * When assuming path compression is not applied
     * Elog(E) + V + Elog(V) --> Equation 1
     * TC O(ElogE)
     * 
     * When assuming Path compression is not applied
     * Elog(E) + V + E(1)
     * TC O(ElogE)
     * 
     * Incase of dense graph, E = V^2, each vertex is connected to all other vertices by an edge, Subs in above eq.
     * [log a^b = bloga]
     * 2Elog(V) + V + Elog(V)
     * 3Elog(V)) + V
     * TC O(ElogV)
     * 
     * We've different TC stated according interms of V and interms E
     *  */ 

    /****************************************************************************************************/

    // Articulation Point and Bridges(Edges) TC O(V+E)
    // disc - discovery time of node, low - node with lowest discovery time accessible
    private int[] low, disc;
    private boolean[] articulation, visited;
    private int time, rootCalls; // rootCalls - indicates num of dfs calls made from root node for non-visited nodes
    // parent - can also be maintained in arr instead passing as a variable in dfs call

    private void dfs(ArrayList<Edge>[] graph, int src, int parent) {
        disc[src] = low[src] = time++;
        visited[src] = true;

        for (Edge e : graph[src]) {
            int nbr = e.v;
            if (!visited[nbr]) {
                if (parent == -1)
                    rootCalls++;

                dfs(graph, nbr, src);

                // Articulation Points
                if (low[nbr] >= disc[src]) // has no back edge to more lowest disc node, so will break to comp
                    articulation[src] = true;
                // Articulation Edges
                if (low[nbr] > disc[src]) // == case means has atleast one back edge to directly to itself in the comp, so can't break into multiple comps
                    System.out.println("Articulation edge : " + src + " -> " + nbr);

                low[src] = Math.min(low[src], low[nbr]);
            }
            else if (nbr != parent) { // visited node & not parent node
                low[src] = Math.min(low[src], disc[nbr]);
            }
        }
    }

    // 1. Why disc[nbr] for visited node case - we can't take low[nbr] since we can't directly reach or have edge to low[nbr] (lowest accessible of nbr, but accessible only upto that nbr node itself)
    // 2. why low[nbr] on backtrack - means that node can also reached by low[nbr] node (another new path)

    public void ArticulationPointAndBridges(ArrayList<Edge>[] graph) {
        int N = graph.length;
        low = disc = new int[N];
        visited = articulation = new boolean[N];
        time = 0;

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                dfs(graph, i, -1); // -1 indicates no parent - root node
            }
        }
    }

    /****************************************************************************************************/

    // Djikstra's Algorithm
    // Algo used to get Min Cost Path from given src vtx to any other vtx(dest)
    // Also this algo will construct a Acyclic Graph which will be a ST (but not necessarily a MST)
    // Same BFS based algo, just PQ instead of Queue as in BFS
    private class Pair {
        int vtx;
        int par; // parent
        int wt;
        int wsf; // weight so far

        public Pair() {}

        public Pair(int vtx, int par, int wt, int wsf) { // for Djikstra_01
            this.vtx = vtx;
            this.par = par;
            this.wt = wt;
            this.wsf = wsf;
        }

        public Pair(int vtx, int wsf) { // for Djikstra_02
            this.vtx = vtx;
            this.wsf = wsf;
        }
    }

    // Approach 1 - Simple code-wise easy Djikstra
    public void djikstra_01(ArrayList<Edge>[] graph, int src) {
        int N = graph.length;
        ArrayList<Edge>[] newGraph = new ArrayList[N]; // Acyclic min cost path graph constructed by Djikstra Algo from given src vtx to any vtx(dest)
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();

        boolean[] visited = new boolean[N];
        int[] dis = new int[N]; //distance arr -> stores min cost i.e. min dist to reach the vtx from a given src vtx
        int[] par = new int[N]; // parent arr -> stores parent of vtx, parent vtx - vtx which is part of the minCost path

        // Default values
        Arrays.fill(dis, (int) 1e9);
        Arrays.fill(par, -1);

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf; // min heap based on wsf (weight so far)
        });

        pq.add(new Pair(src, -1, 0, 0));
        while (!pq.isEmpty()) {
            Pair p = pq.remove();

            if (visited[p.vtx])
                continue;

            if (p.par != -1) // to construct acyclic minCost path graph
                addEdge(newGraph, p.par, p.vtx, p.wt);

            visited[p.vtx] = true;
            par[p.vtx] = p.par;
            dis[p.vtx] = p.wsf;

            for (Edge e : graph[p.vtx]) {
                if (!visited[e.v]) {
                    pq.add(new Pair(e.v, p.vtx, e.w, p.wsf + e.w));
                }
            }
        }
    }

    // Approach 2 - Better version of Djikstra
    // Without using visited - if (newWSF < oldWSF) then update since it's a minCost
    public void djikstra_02(ArrayList<Edge>[] graph, int src) {
        int N = graph.length;
        int[] dis = new int[N];
        int[] par = new int[N];
        Arrays.fill(dis, (int) 1e9);
        Arrays.fill(par, -1);

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            return a.wsf - b.wsf;
        });

        pq.add(new Pair(src, 0));
        dis[src] = 0;
        while (!pq.isEmpty()) {
            Pair p = pq.remove();
            if (p.wsf > dis[p.vtx]) // >= will not work since == will see same value and continue, which will not add current node's nbr edges
                continue;

            for (Edge e : graph[p.vtx]) {
                if (p.wsf + e.w < dis[e.v]) { // this will prevent adding useless high weighted edges wt in PQ
                    dis[e.v] = p.wsf + e.w;
                    par[e.v] = p.vtx;
                    pq.add(new Pair(e.v, p.wsf + e.w));
                }
            }
        }
    }

    /****************************************************************************************************/

    // Prims Algorithm
    // To find MST (Same BFS logic, PQ instead of Queue)
    // MST - MinCost of the summation of all the edges wt, Also constructs Acyclic graph
    // NOTE: Prims and Djikstra might give same answer in some cases, but not always, both are complete different logic
    // NOTE: Parent arr in Prims doesn't have any usecase

    private class PrimsPair {
        int vtx;
        int par; // parent - just to construct graph
        int wt;

        public PrimsPair() {}

        public PrimsPair(int vtx, int par, int wt) { 
            this.vtx = vtx;
            this.wt = wt;
        }
    }

    // Approach 1 - Simple code-wise easy Prims
    public void Prims_01(ArrayList<Edge>[] graph, int src) {
        int N = graph.length;
        ArrayList<Edge>[] newGraph = new ArrayList[N]; // Acyclic min cost path graph constructed by Djikstra Algo from given src vtx to any vtx(dest)
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();

        boolean[] visited = new boolean[N];
        int[] dis = new int[N]; //distance arr -> stores min cost i.e. min dist to reach the vtx from a given src vtx
        Arrays.fill(dis, (int) 1e9); // Default values

        PriorityQueue<PrimsPair> pq = new PriorityQueue<>((a, b) -> {
            return a.wt - b.wt; // min heap based on wt
        });

        pq.add(new PrimsPair(src, -1, 0));
        while (!pq.isEmpty()) {
            PrimsPair p = pq.remove();

            if (visited[p.vtx])
                continue;

            if (p.par != -1) // to construct acyclic minCost path graph
                addEdge(newGraph, p.par, p.vtx, p.wt);

            visited[p.vtx] = true;
            dis[p.vtx] = p.wt;

            for (Edge e : graph[p.vtx]) {
                if (!visited[e.v]) {
                    pq.add(new PrimsPair(e.v, p.vtx, e.w));
                }
            }
        }
    }

    // For Prims_02 have to maintain Visited arr, cant depend only on dis arr, since Djikstra check "wsf" factor whereas Prims checks "wt" factor
    public void Prims_02(ArrayList<Edge>[] graph, int src) {
        int N = graph.length;
        ArrayList<Edge>[] newGraph = new ArrayList[N];
        for (int i = 0; i < N; i++) graph[i] = new ArrayList<>();

        boolean[] visited = new boolean[N];
        int[] dis = new int[N];
        Arrays.fill(dis, (int) 1e9);

        PriorityQueue<PrimsPair> pq = new PriorityQueue<>((a, b) -> {
            return a.wt - b.wt;
        });

        pq.add(new PrimsPair(src, -1, 0));
        dis[src] = 0;
        while (!pq.isEmpty()) {
            PrimsPair p = pq.remove();

            if (visited[p.vtx])
                continue;

            if (p.par != -1)
                addEdge(newGraph, p.par, p.vtx, p.wt);

            visited[p.vtx] = true;

            for (Edge e : graph[p.vtx]) {
                if (!visited[e.v] && e.w < dis[e.v]) { // this will prevent adding useless high weighted edges wt in PQ
                    dis[e.v] = e.w;
                    pq.add(new PrimsPair(e.v, p.vtx, e.w));
                }
            }
        }
    }

    /****************************************************************************************************/

    // Bellman Ford Algorithm (Min cost path event with -ve edges)
    // If -ve cycle exists, detects and says -ve cycle exists; if no -ve cycle then returns correct answer
    // Works without constructing graph, only edges is enough
    // Each of the edges idx, i.e. ith index says => Atmost using i no. of edges, min wt. to reach the vtx is...
    // TC V(V+E) => (V.V + V.E) => O(V.E) Better complexity than Djikstra
    public static void bellmanFord(int[][] edges, int N, int src) {
        // N = no. of vertices, we require Vth edge to detect -ve cycle
        int[] prev = new int[N]; // u
        Arrays.fill(prev, (int) 1e9);
        prev[src] = 0; // relaxation of src vtx
        
        boolean negativeCycle = false;

        for (int i = 1; i <= N; i++) { // runs till Vth edge, since V-1 edges cant form a cycle, 1 extra edge, Vth forms the possibility for cycle to exist
            int[] curr = new int[N]; // v
            for (int j = 0; j < N; j++) 
                curr[j] = prev[j];

            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (prev[u] != (int) 1e9 && prev[u] + w < curr[v]) { // if infinity ignore, else less value "updation"
                    curr[v] = prev[u] + w;

                    if (i == N) // Vth edge, since updates so -ve cycle exists
                        negativeCycle = true;
                }
            }

            prev = curr;
        }

        System.out.println("Negative cycle : " + negativeCycle);
    }

    // Slight time optimized
    // If no update in a complete ith edge, then "no" update will occur for n-i edges as well, so return ans there itself
    public static void bellmanFord_TimeOptimized(int[][] edges, int N, int src) {
        int[] prev = new int[N]; // u
        Arrays.fill(prev, (int) 1e9);
        prev[src] = 0;
        
        boolean negativeCycle = false;

        for (int i = 1; i <= N; i++) {
            int[] curr = new int[N]; // v
            for (int j = 0; j < N; j++) 
                curr[j] = prev[j];

            boolean anyUpdate = false;
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (prev[u] != (int) 1e9 && prev[u] + w < curr[v]) {
                    curr[v] = prev[u] + w;
                    anyUpdate = true;

                    if (i == N)
                        negativeCycle = true;
                }
            }

            if (!anyUpdate) // means no update further as well
                break;

            prev = curr;
        }

        System.out.println("Negative cycle : " + negativeCycle);
    }

    // Slight space optimized we maintain we use same prev arr to maintain copied state, so we don't create "new" arr all the time for each ith edge
    public static void bellmanFord_SpaceOptimized(int[][] edges, int N, int src) {
        int[] prev = new int[N]; // u
        int[] curr = new int[N]; // v
        Arrays.fill(prev, (int) 1e9);
        Arrays.fill(curr, (int) 1e9);

        prev[src] = curr[src] = 0;
        
        boolean negativeCycle = false;

        for (int i = 1; i <= N; i++) {
            boolean anyUpdate = false;

            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (prev[u] != (int) 1e9 && prev[u] + w < curr[v]) {
                    curr[v] = prev[u] + w;
                    anyUpdate = true;

                    if (i == N)
                        negativeCycle = true;
                }
            }

            if (!anyUpdate) // means no update further as well
                break;

            for (int j = 0; j < N; j++) 
                prev[j] = curr[j];
        }

        System.out.println("Negative cycle : " + negativeCycle);
    }

    /****************************************************************************************************/

}
