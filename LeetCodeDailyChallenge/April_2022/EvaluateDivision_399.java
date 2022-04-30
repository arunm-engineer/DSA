import java.util.*;

public class EvaluateDivision_399 {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        HashMap<String, HashMap<String, Double>> graph = constructGraph(equations, values);
        
        double[] ans = solve(graph, queries);
        return ans;
    }
    
    public static double[] solve(HashMap<String, HashMap<String, Double>> graph, List<List<String>> queries) {
        double[] ans = new double[queries.size()];
        
        for (int i = 0; i < queries.size(); i++) {
            HashSet<String> visited = new HashSet<>();
            List<String> query = queries.get(i);
            String src = query.get(0), dest = query.get(1);
            
            ans[i] = dfs(src, dest, graph, visited);
        }
        
        return ans;
    }
    
    public static double dfs(String src, String dest, HashMap<String, HashMap<String, Double>> graph, HashSet<String> visited) {
        if (!graph.containsKey(src))
            return -1.0;
        
        if (src.equals(dest))// If src & dest same,also here if src exists dest also exists since same node
            return 1.0;
        
        visited.add(src); // mark
        
        HashMap<String, Double> nbrs = graph.get(src);
        for (String nbr : nbrs.keySet()) {
            if (!visited.contains(nbr)) {
                double currVal = dfs(nbr, dest, graph, visited);
                
                if (currVal != -1) // path found
                    return currVal * nbrs.get(nbr);
            }
        }
        
        return -1.0;
    }
    
    
    
    public static HashMap<String, HashMap<String, Double>> constructGraph(List<List<String>> equations, double[] values) {
        // Graph built on HashMap
        HashMap<String, HashMap<String, Double>> graph = new HashMap<>(); 
        
        for (int i = 0; i < equations.size(); i++)  {
            List<String> equation = equations.get(i);
            String src = equation.get(0);
            String dest = equation.get(1);
            double wt = values[i];
            
            if (!graph.containsKey(src)) // Creating 1st edge (src -> dest)
                graph.put(src, new HashMap<>());
            graph.get(src).put(dest, wt);
            
            if (!graph.containsKey(dest)) // Creating 2nd edge (dest -> src)
                graph.put(dest, new HashMap<>());
            graph.get(dest).put(src, 1/wt); // a/b = wt; b/a = 1/wt (inverse)
        }
        
        return graph;
    }
}
