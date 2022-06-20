import java.util.ArrayList;
import java.util.LinkedList;

// Note: If vertexes are not interms of indexes like 0, 1, 2,.... then we will use HashMap to store it as vertexes
public class l002DirectedGraph {

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
        graph[u].add(new Edge(v, w));
    }

    // ---------------------------------------------------------------------------------------------------------

    public static void dfs_TopologicalOrder(ArrayList<Edge>[] graph) {
        int N = graph.length;
        boolean[] visited = new boolean[N];
        ArrayList<Integer> ans = new ArrayList<>(); // Using ArrayList in place of stack, so that I can traverse on it anytime to find the topological order

        for (int i = 0; i < N; i++) {
            if (!visited[i])
                dfs_topo(graph, i, visited, ans);
        }

    }

    public static void dfs_topo(ArrayList<Edge>[] graph, int src, boolean[] visited, ArrayList<Integer> ans) {
        visited[src] = true;
        for (Edge e : graph[src]) {
            if (!visited[e.v])
                dfs_topo(graph, e.v, visited, ans);
        }

        ans.add(src); // adding in post order, since topological is all about resolving dependency and then finding the order
    }

    // ---------------------------------------------------------------------------------------------------------

    // Kahn's Algorithm
    // Used for Topological Order and Cycle Detection in Directed graph using BFS
    // TC E + V + (E+V) => 2(E + V) => TC O(E+V)
    public static ArrayList<Integer> Kahns_Algo_BFS_TopologicalOrder(ArrayList<Edge>[] graph) {
        int N = graph.length;
        int[] indegree = new int[N];
        LinkedList<Integer> q = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>(); // stores the topological order

        // Calculating indegree => num of dependencies on each vertexes
        // TC O(E) => not O(2E) why? since this is directional graph, for bi-directional grraph we have O(2E) complexity
        for (int i = 0; i < N; i++) {
            for (Edge e : graph[i])
                indegree[e.v]++;
        }

        // find and add 0 dependency vertexes to queue
        // TC O(V)
        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0)
                q.addLast(i);
        }

        // BFS complexity => TC O(E+V)
        while (!q.isEmpty()) {
            int rvtx = q.removeFirst();

            ans.add(rvtx);

            for (Edge e : graph[rvtx]) {
                if (--indegree[e.v] == 0) {
                    q.addLast(e.v);
                }
            }
        }

        // if num of vertexes in graph = num of vertexes in ans (means no cycle) => only then return ans with topological order formed
        if (ans.size() != N)
            ans.clear();

        return ans;
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 207
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<Integer>[] graph = constructGraph_00(numCourses, prerequisites);
        ArrayList<Integer> ans = Kahns_Algo_BFS_TopologicalOrder_00(graph);
        
        return ans.size() != 0;
    }
    
    // Kahn's Algorithm
    private ArrayList<Integer> Kahns_Algo_BFS_TopologicalOrder_00(ArrayList<Integer>[] graph) {
        int N = graph.length;
        int[] indegree = new int[N];
        LinkedList<Integer> q = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int v : graph[i])
                indegree[v]++;
        }

        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0)
                q.addLast(i);
        }

        while (!q.isEmpty()) {
            int rvtx = q.removeFirst();

            ans.add(rvtx);

            for (int v : graph[rvtx]) {
                if (--indegree[v] == 0) {
                    q.addLast(v);
                }
            }
        }

        if (ans.size() != N)
            ans.clear();

        return ans;
    }
    
    private ArrayList<Integer>[] constructGraph_00(int N, int[][] prerequisites) {
        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();
        
        for (int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            
            addEdge_00(graph, u, v);
        }

        return graph;
    }
    
    private void addEdge_00(ArrayList<Integer>[] graph, int u, int v) {
        graph[u].add(v);
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 210
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList<Integer>[] graph = constructGraph_01(numCourses, prerequisites);
        int[] ans = Kahns_Algo_BFS_TopologicalOrder_01(graph);
        
        return ans;
    }
    
    // Kahn's Algorithm
    private int[] Kahns_Algo_BFS_TopologicalOrder_01(ArrayList<Integer>[] graph) {
        int N = graph.length;
        int[] indegree = new int[N];
        LinkedList<Integer> q = new LinkedList<>();
        
        
        int idx = N-1; // used to fill vertexes in ans arr in dependency order first (opp. to topo order)
        int[] ans = new int[N];

        for (int i = 0; i < N; i++) {
            for (int v : graph[i])
                indegree[v]++;
        }

        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0)
                q.addLast(i);
        }

        while (!q.isEmpty()) {
            int rvtx = q.removeFirst();

            ans[idx--] = rvtx;

            for (int v : graph[rvtx]) {
                if (--indegree[v] == 0) {
                    q.addLast(v);
                }
            }
        }

        if (idx != -1)
            return new int[0];

        return ans;
    }
    
    private ArrayList<Integer>[] constructGraph_01(int N, int[][] prerequisites) {
        ArrayList<Integer>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();
        
        for (int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            
            addEdge_01(graph, u, v);
        }

        return graph;
    }
    
    private void addEdge_01(ArrayList<Integer>[] graph, int u, int v) {
        graph[u].add(v);
    }

    // ---------------------------------------------------------------------------------------------------------

    // LC 329
    // BFS Kahn's Algo in 2D Array
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        LinkedList<Integer> q = new LinkedList<>();
        
        // Calc indegree
        int[][] indegree = new int[n][m];
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if (r >= 0 && r < n && c >= 0 && c < m && matrix[sr][sc] > matrix[r][c])
                        indegree[sr][sc]++; // calc indegree coming on me (sr,sc) via nbrs
                }
                // find indegree => (performing indegree here itself saving a useless loop later)
                if (indegree[sr][sc] == 0) // since
                    q.addLast(sr * m + sc);
            }
        }        
        
        // BFS
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            
            while (size-- > 0) {
                int rvtx = q.removeFirst();
                int sr = rvtx / m, sc = rvtx % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if (r >= 0 && r < n && c >= 0 && c < m && matrix[r][c] > matrix[sr][sc]) {
                        if (--indegree[r][c] == 0)
                            q.addLast(r * m + c);
                    }
                }
            }
            
            level++;
        }
        
        return level;
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
    }

    public static void main(String[] args) {
        constructGraph();
    }

}
