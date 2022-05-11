import java.util.ArrayList;

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

    public static void display(ArrayList<Edge>[] graph) {
        for (int u = 0; u < graph.length; u++) {
            System.out.print(u + " -> ");
            for (Edge e : graph[u]) 
                System.out.print("(" + e.v + ", " + e.w + ") ");
            System.out.println();
        }
    }

    public static void constructGraph() {
        int N = 7;
        ArrayList<Edge>[] graph = new ArrayList[7];
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

        display(graph);
    }

    public static void main(String[] args) {
        constructGraph();
    }
}
