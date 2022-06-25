import java.util.ArrayList;
import java.util.HashMap;

public class UnionFind {

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

    // =========================================================

    static int[] parent, size;

    public static int findParent(int u) {
        if (parent[u] == u)
            return u;
        
        return parent[u] = findParent(parent[u]); // Path Compression
        
        // one-liner
        // return parent[u] = parent[u] == u ? u : findParent(parent[u]);
    }

    public static void union(int parent1, int parent2) {
        if (size[parent1] < size[parent2]) {
            parent[parent1] = parent2; // Update parent
            size[parent2] += size[parent1]; // Update size
        }
        else {
            parent[parent2] = parent1;
            size[parent1] += size[parent2];
        }
    }
    
    // edges => [[u1, v1, w1], [u2, v2, w2],...]
    public static void unionFind(int[][] edges, int N) {
        ArrayList<Edge>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<Edge>();
        
        boolean isCycle = false; // Detect cycle

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            int parent1 = findParent(u); // parent => Global leader
            int parent2 = findParent(v);

            if (parent1 != parent2) { // means not same set i.e. no cycle
                union(parent1, parent2);
                addEdge(graph, u, v, w); // constructing Acyclic graph
            }
            else {
                isCycle = true;
            }
        }

        // indicate total Components exists in graph and size of each components
        HashMap<Integer, Integer> map = new HashMap<>(); // <componentLeader, componentSize>
        int totalComponents = 0;
        for (int i = 0; i < N; i++) {
            int leader = parent[i];
            if (i == leader) {
                totalComponents++;
                map.put(i, size[i]);
            }
        }
    }
}
