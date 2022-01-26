import java.util.ArrayList;

public class l001 {

    public static class Edge {
        int v;
        int w;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        public String toString() {
            return "(" + this.v + "," + this.w + ")";
        }
    }

    public static void addEdge(int u, int v, int w) {
        graph[u].add(new Edge(v, w));
        graph[v].add(new Edge(u, w));
    }

    public static void display() {
        for (int u = 0; u < N; u++) {
            System.out.print(u + " -> ");

            for (Edge e : graph[u])
                System.out.print(e + " ");

            System.out.println();
        }
    }

    public static int find(int u, int v) {
        for (int i = 0;i < graph[u].size();i++) {
            Edge e = graph[u].get(i);
            if (e.v == v) return i;
        }

        return -1;
    }

    public static void removeEdge(int u, int v) {
        int index1 = find(u, v);
        int index2 = find(v, u);
        if (index1 == -1 || index2 == -1) return;

        graph[u].remove(index1);
        graph[v].remove(index2);
    }

    public static void removeVertex(int u) {
        while (graph[u].size() != 0) {
            int n = graph[u].size();
            Edge e = graph[u].get(n - 1);
            removeEdge(u, e.v);
        }
    }

    public static boolean hasPath(int src, int dest, boolean[] visited) {
        if (src == dest) return true;
        visited[src] = true;

        for (Edge e : graph[src]) {
            if (visited[e.v] == false) {
                boolean res = hasPath(e.v, dest, visited);
                if (res) return res;
            }
        }
        
        return false;
    }

    public static void printAllPaths(int src, int dest, boolean[] visited, String path) {
        if (src == dest) {
            System.out.println(path+src);
            return;
        }

        visited[src] = true;
        
        for (Edge e : graph[src]) {
            if (visited[e.v] == false) {
                printAllPaths(e.v, dest, visited, path+src);
            }
        }

        visited[src] = false;
    }

    public static int N = 7;
    public static ArrayList<Edge>[] graph = new ArrayList[N];

    public static void main(String[] args) {
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<Edge>();

        addEdge(0, 1, 10);
        addEdge(0, 3, 10);
        addEdge(1, 2, 10);
        addEdge(2, 3, 10);
        addEdge(3, 4, 10);
        addEdge(4, 5, 10);
        addEdge(4, 6, 10);
        addEdge(5, 6, 10);

        // display();
        // System.out.println(find(3, 4));
        // removeEdge(0, 3);
        // removeVertex(0);
        // display();

        boolean[] visited = new boolean[N];
        // boolean res = hasPath(0, 6, visited);
        printAllPaths(0, 6, visited, "");
        // System.out.println(res);
    }
}
