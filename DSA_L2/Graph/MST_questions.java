import java.util.ArrayList;
import java.util.Collections;

public class MST_questions {
    
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

}
