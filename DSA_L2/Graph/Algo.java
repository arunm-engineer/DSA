import java.util.ArrayList;
import java.util.Arrays;

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
    // Kruskal's Algo => DSU + Sort edges on basis of wt. in increasing order

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

    // Articulation Point and Bridges(Edges)
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

}
