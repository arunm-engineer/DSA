import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class bfs_Questions {
    
    // LC 994
    public int orangesRotting(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        LinkedList<Integer> q = new LinkedList<>();
        int freshOrangeCount = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    freshOrangeCount++;
                }
                else if (grid[i][j] == 2) { // added all rotten oranges first, which will take time=0
                    int IDX = i * m + j; // encode to 1D arr idx
                    q.addLast(IDX);
                    grid[i][j] = 2; // mark visited, here '2' is just an entity to mark
                }
            }
        }
        
        if (freshOrangeCount == 0)
            return 0;
        
        int time = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int rottenOrangeIDX = q.removeFirst();
                int sr = rottenOrangeIDX / m, sc = rottenOrangeIDX % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                        if (--freshOrangeCount == 0)
                            return time + 1;
                        
                        grid[r][c] = 2; // mark visited, fresh orange -> rotted now
                        int IDX = r * m + c;
                        q.addLast(IDX); // added fresh orange which wil rotted now
                    }
                }
            }
            time++;
        }
        
        return -1; // means more freshOranges are left, which couldn't be reached
    }

    /****************************************************************************************************/

    // LC 1091
    public int shortestPathBinaryMatrix(int[][] grid) {
        int[][] dir = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        
        int n = grid.length, m = grid[0].length;
        if (grid[0][0] == 1 || grid[n-1][m-1] == 1)
            return -1;
        
        int shortestPath = 1;
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(0); // adding encoded index in q (2D to 1D)
        grid[0][0] = 1; // mark first position visited
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int idx = q.removeFirst();                
                int sr = idx / m, sc = idx % m;
                
                if (sr == n-1 && sc == m-1)
                    return shortestPath;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        q.addLast(r * m + c); // add encoded index (2D to 1D)
                    }
                }
            }
            shortestPath++;
        }
        
        return -1;
    }

    /****************************************************************************************************/

    // LC 542
    public int[][] updateMatrix(int[][] mat) {
        // return bfs_01(mat);
        return bfs_02(mat);
    }
    
    // TC O(n*m) ; SC O (2(n*m)) -> Queue Space + visited arr space
    private int[][] bfs_01(int[][] grid) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int n = grid.length, m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        LinkedList<Integer> q = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) { // add all zeroes, to propagate from those 0s to 1s
                    visited[i][j] = true;
                    q.addLast(i * m + j);
                }
            }
        }
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int idx = q.removeFirst();
                int sr = idx / m, sc = idx % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    // propagating only towards 1s, since 0s are already marked true
                    if (r >= 0 && r < n && c >= 0 && c < m && visited[r][c] == false) {
                        visited[r][c] = true;
                        int parentLevel = grid[sr][sc];
                        grid[r][c] = parentLevel+1; // updating curr by +1 level
                        q.addLast(r * m + c);
                    }
                }
            }
        }
        
        return grid;
    }

    // TC O(n*m) ; SC O(n*m) -> Queue space only
    private int[][] bfs_02(int[][] grid) {
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        int n = grid.length, m = grid[0].length;
        LinkedList<Integer> q = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) { // add all zeroes, to propagate from those 0s to 1s
                    grid[i][j] = 0; // marking is important, marking 0 now though already it is 0
                    q.addLast(i * m + j);
                }
            }
        }
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int idx = q.removeFirst();
                int sr = idx / m, sc = idx % m;
                
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    
                    // propagating only towards 1s, since 0s are already marked true
                    if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] > 0) {
                        int parentLevel = grid[sr][sc];
                        // why deducting level in -ve, since also used as for visited marking
                        grid[r][c] = parentLevel-1; // updating curr by -1 level, later convert to +ve
                        q.addLast(r * m + c);
                    }
                }
            }
        }
        
        // converting to +ve
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = -1 * grid[i][j];
            }
        }
        
        return grid;
    }

    /****************************************************************************************************/

    // LC 785
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        
        int[] visited = new int[n];
        Arrays.fill(visited, -1);
        
        boolean res = true;
        for (int i = 0; i < n; i++) {
            if (visited[i] == -1) 
                res = res && bfs_isBiPartite(graph, i, visited);                
        }
        
        return res;
    }
    
    private boolean bfs_isBiPartite(int[][] graph, int src, int[] visited) {
        int color = 0; // colors -> 0, 1
        boolean isCycle = false, isBiPartite = true;
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(src);
        visited[src] = color;
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int rIdx = q.removeFirst();
                
                if (visited[rIdx] != -1) { // visited
                    isCycle = true;
                    if (visited[rIdx] != color) { // conflict
                        isBiPartite = false;
                        break;
                    }
                }
                
                visited[rIdx] = color;
                
                for (int v : graph[rIdx]) {
                    if (visited[v] == -1)
                        q.addLast(v);
                }
            }
            color = (color+1) % 2; // switching colors @ each elvel
            if (!isBiPartite)
                break;
        }
        
        if (isCycle) {
            if (isBiPartite) 
                System.out.println("Graph is BiPartite since the cycle is even length");
            else 
                System.out.println("Graph is not a BiPartite since the cycle is odd length");
        }
        else {
            System.out.println("Graph is BiPartite since no cycle");
        }
        
        return isBiPartite;
    }

    /****************************************************************************************************/

    // LC 886
    public boolean possibleBipartition(int n, int[][] dislikes) {
        ArrayList<Integer>[] graph = constructGraph(dislikes, n);
        
        int[] visited = new int[graph.length];
        Arrays.fill(visited, -1);
        
        // Why using BiPartite logic, since bipartite checks for independent sets of separation of nodes
        boolean res = true;
        for (int i = 1; i < visited.length; i++) {
            if (visited[i] == -1) {
                res = res && bfs_isBiPartite(graph, i, visited);
            }
        }
        
        return res;
    }
    
    private boolean bfs_isBiPartite(ArrayList<Integer>[] graph, int src, int[] visited) {
        int color = 0; // colors -> 0, 1
        boolean isCycle = false, isBiPartite = true;
        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(src);
        visited[src] = color;
        
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int rIdx = q.removeFirst();
                
                if (visited[rIdx] != -1) { // visited
                    isCycle = true;
                    if (visited[rIdx] != color) { // conflict
                        isBiPartite = false;
                        break;
                    }
                }
                
                visited[rIdx] = color;
                
                for (int v : graph[rIdx]) {
                    if (visited[v] == -1)
                        q.addLast(v);
                }
            }
            color = (color+1) % 2; // switching colors @ each elvel
            if (!isBiPartite)
                break;
        }
        
        if (isCycle) {
            if (isBiPartite) 
                System.out.println("Graph is BiPartite since the cycle is even length");
            else 
                System.out.println("Graph is not a BiPartite since the cycle is odd length");
        }
        else {
            System.out.println("Graph is BiPartite since no cycle");
        }
        
        return isBiPartite;
    }
    
    private void addEdge(ArrayList<Integer>[] graph, int u, int v) {
        graph[u].add(v);
        graph[v].add(u);
    }
    
    private ArrayList<Integer>[] constructGraph(int[][] dislikes, int n) {
        ArrayList<Integer>[] graph = new ArrayList[n+1]; // why n+1, since n is 1-indexed
        
        for (int i = 0; i < graph.length; i++) 
            graph[i] = new ArrayList<Integer>();
        
        for (int i = 0; i < dislikes.length; i++) 
            addEdge(graph, dislikes[i][0], dislikes[i][1]);
        
        return graph;
    }

    /****************************************************************************************************/

    // LC 286
    // LintCode 663
    // Concept and technique similar to LC 542
    public void wallsAndGates(int[][] rooms) {
        int n = rooms.length, m = rooms[0].length;
        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        LinkedList<Integer> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (rooms[i][j] == 0) 
                    q.addLast(i * m + j);
            }
        }

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int remIdx = q.removeFirst();
                int sr = remIdx / m, sc = remIdx % m;

                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && r < n && c >= 0 && c < m && rooms[r][c] == Integer.MAX_VALUE) {
                        q.add(r * m + c);
                        int parentDistance = rooms[sr][sc];
                        rooms[r][c] = parentDistance + 1;
                    }
                }
            }
        }
    }

    /****************************************************************************************************/

    // LC 815
    // BFS
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) // edge case
            return 0;
        
        int n = routes.length;
        
        // Map <BustStand, Buses> (Here buses indicates buses which travel from that BusStand)
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        
        // Form relation for all buses travelling from each busStands
        for (int bus = 0; bus < routes.length; bus++) {
            for (int busStand : routes[bus]) {
                map.putIfAbsent(busStand, new ArrayList<>());
                map.get(busStand).add(bus);
            }
        }

        boolean[] busVisited = new boolean[n]; // track buses used
        HashSet<Integer> busStandVisited = new HashSet<>(); // track busStands, buses used have travelled
        
        LinkedList<Integer> q = new LinkedList<>();
        q.add(source);
        busStandVisited.add(source);
        
        int busInterchangesDoneSoFar = 0;
        
        while (!q.isEmpty()) {
            int size = q.size();
            
            while (size-- > 0) {
                int busStand = q.removeFirst();
                for (int bus : map.get(busStand)) {
                    
                    if (busVisited[bus]) // if bus already visited, means upcoming stands also visited
                        continue;
                    
                    busVisited[bus] = true; // mark bus as used
                    
                    // travel, mark, find all further bus stands of bus's routes for (src == dest)
                    for (int upcomingBusStand : routes[bus]) {
                        if (!busStandVisited.contains(upcomingBusStand)) {
                            q.addLast(upcomingBusStand);
                            busStandVisited.add(upcomingBusStand);
                            
                            if (upcomingBusStand == target)
                                return busInterchangesDoneSoFar+1;
                        }
                    }
                }
            }
            
            busInterchangesDoneSoFar++;
        }
        
        return -1;
    }

    /****************************************************************************************************/

    // LC 1376
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        ArrayList<Edge>[] graph = constructGraph(n, manager, informTime);
        
        int totalMinutes = -(int) 1e9;
        boolean[] visited = new boolean[n];
        LinkedList<Pair> q = new LinkedList<>();
        
        q.add(new Pair(headID, informTime[headID]));
        visited[headID] = true;
        while (!q.isEmpty()) {
            Pair p = q.removeFirst();
            
            totalMinutes = Math.max(totalMinutes, p.wsf);

            for(Edge e : graph[p.vtx]) {
                if (!visited[e.v]) {
                    visited[e.v] = true;
                    q.addLast(new Pair(e.v, p.wsf + e.w));
                }
            }
        }
        
        return totalMinutes;
    }
    
    private class Pair {
        int vtx; // nbr
        int wsf; // minutes
        
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
    
    private ArrayList<Edge>[] constructGraph(int N, int[] nodes, int[] wts) {
        ArrayList<Edge>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();
        
        for (int i = 0; i < N; i++) {
            int u = i, v = nodes[i], w = wts[i];
            if (v == -1)
                continue;
            
            graph[u].add(new Edge(v, w));
            graph[v].add(new Edge(u, w));
        }
        
        return graph;
    }

    /****************************************************************************************************/
    
}
