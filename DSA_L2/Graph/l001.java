import java.util.ArrayList;
import java.util.LinkedList;

// Note: If vertexes are not interms of indexes like 0, 1, 2,.... then we will use HashMap to store it as vertexes
public class l001 {
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

    // ---------------------------------------------------------------------------------------------------------

    // TC O(E) Worst case if all edges connected in the same single vertice
    // returns the idx of edge u->v from the list at uth vertice
    public static int findEdge(ArrayList<Edge>[] graph, int u, int v) {
        int idx = -1;
        for (int i = 0; i < graph[u].size(); i++) {
            Edge e = graph[u].get(i);
            if (e.v == v) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    // ---------------------------------------------------------------------------------------------------------

    // TC O(E)
    // Worst case -> all edges are connected in the same singe vertice
    public static void removeEdge(ArrayList<Edge>[] graph, int u, int v) {
        int idx = findEdge(graph, u, v); // for worst case O(E)
        if (idx != -1) graph[u].remove(idx); // shifting after removal O(E)

        idx = findEdge(graph, v, u); // for worst case O(1)
        if (idx != -1) graph[v].remove(idx); // O(1)
        // Why O(1) -> uth vertice will contain all edges connected to v
        // for vth there will exist only one edge v->u in the vth vertice list => so findEdge and removal for v->u edge will be O(1) operation
    }

    // ---------------------------------------------------------------------------------------------------------

    // hasPath
    // TC O(E) -> where E is the total no of edges in that particular component (we don't travel other components or vertices)
    // why not unmarking -> we don't need to travel all paths, if src to dest doesn't exist from one path in a connected graph then it will not exist in other path as well
    public static boolean dfs_findPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] visited) {
        if (src == dest)
            return true;

        visited[src] = true;

        boolean res = false;
        for (Edge e : graph[src]) {
            if (!visited[e.v])
                res = res || dfs_findPath(graph, e.v, dest, visited);
        }

        return res;
    }

    // ---------------------------------------------------------------------------------------------------------

    // TC O(V!) -> why will know later
    public static int printAllPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] visited, String psf, int wsf) {
        if (src == dest)  {
            System.out.println(psf + dest + " @ " + wsf);
            return 1;
        }

        visited[src] = true;

        int count = 0;
        for (Edge e : graph[src]) {
            if (!visited[e.v]) 
                count += printAllPath(graph, e.v, dest, visited, psf + src, wsf + e.w);
        }

        visited[src] = false;

        return count;
    }

    // ---------------------------------------------------------------------------------------------------------

    public static void dfs_GCC(ArrayList<Edge>[] graph, int src, boolean[] visited) {
        visited[src] = true;
        for (Edge e : graph[src]) {
            if (!visited[e.v])
                dfs_GCC(graph, e.v, visited);
        }
    }

    // Generally, TC O(E+V) -> E is total no of edges in all components of graph; V is the total no of loose vertices
    // TC O(E) if no loose vertices
    // TC O(V) if only loose vertices; We just substitute 0 accordingly seeing which exists in the O(E+V) commplexity
    public static void getConnectedComponents(ArrayList<Edge>[] graph, int N) {
        boolean[] visited = new boolean[N];

        int components = 0;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                components++;
                dfs_GCC(graph, i, visited);;
            }
        }

        System.out.println(components);
    }

    // ---------------------------------------------------------------------------------------------------------

    // TC O(E) - here E stands for all edges, even those edges which caused cycle, since we add first all nbr edges & only mark them once we encounter it
    // SC O(E)
    // So TC is slightly greater than below method of BFS
    public static void bfs_forCycle(ArrayList<Edge>[] graph, int src, boolean[] visited) {
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(src);

        int level = 0;
        boolean isCycle = false;
        while (!q.isEmpty()) {
            int size = q.size();
            System.out.println("Minimum no of edges: " + level + " -> ");
            while (size-- > 0) {
                int rvtx = q.removeFirst();

                if (visited[rvtx]) {
                    isCycle = true;
                    continue;
                }

                System.out.print(rvtx + ", ");
                visited[rvtx] = true;

                for (Edge e : graph[rvtx]) {
                    if (!visited[e.v])
                        q.addLast(e.v);
                }
            }

            System.out.println();
            level++;
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    // TC O(E) ~ O(V-1) ~ O(V)
    // SC O(V)
    // Here we mark when we add nbr edges itself, so we'll never add edges of all marked nbrs again because of someother path as we would in above BFS method
    // Edges added in queue will be those which does not form a cycle, since we never add it as we've already marked & then added further edges
    //  If give N number of vertices V, then num of edges need to be connected to not form a cycle is V-1, if even a single more edge get's connected after V-1 then it would for a cycle
    // NOTE: For cycle detection above BFS method is preferred
    public static void bfs_notForCycle(ArrayList<Edge>[] graph, int src, boolean[] visited) {
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(src);
        visited[src] = true;

        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            System.out.println("Minimum no of edges: " + level + " -> ");
            while (size-- > 0) {
                int rvtx = q.removeFirst();

                System.out.print(rvtx + ", ");
                for (Edge e : graph[rvtx]) {
                    if (!visited[e.v]) {
                        q.addLast(e.v);
                        visited[e.v] = true;
                    }
                }
            }

            System.out.println();
            level++;
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    public static void constructGraph() {
        int N = 7;
        ArrayList<Edge>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<Edge>();

        addEdge(graph, 0, 1, 10);
        addEdge(graph, 0, 3, 10);
        addEdge(graph, 1, 2, 10);
        addEdge(graph, 2, 3, 40);
        addEdge(graph, 3, 4, 2);
        addEdge(graph, 4, 5, 2);
        addEdge(graph, 4, 6, 8);
        addEdge(graph, 5, 6, 3);

        // display(graph);

        // removeEdge(graph, 3, 4);

        boolean[] visited = new boolean[N];
        // boolean res = dfs_findPath(graph, 0, 6, visited);
        // System.out.println(res);

        // int count = printAllPath(graph, 0, 6, visited, "", 0);
        // System.out.println(count);

        // getConnectedComponents(graph, N);
    }

    public static void main(String[] args) {
        constructGraph();
    }
}
