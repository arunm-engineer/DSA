import java.util.stream.BaseStream;

public class dsuQuestions {

    // LC 695
    private int[] parent_00, size;

    public int maxAreaOfIsland(int[][] grid) {
        // Here only 2-directions enough (top, down) since actually at the end,
        // the edges of that component will be merged to that global leader of that component
        int[][] dir = {{0, 1}, {1, 0}};
        int n = grid.length, m = grid[0].length;
        parent_00 = new int[n*m];
        size = new int[n*m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    int idx = i * m + j;
                    parent_00[idx] = idx;
                    size[idx] = 1;
                }
            }
        }

        int maxArea = 0;
        for (int sr = 0; sr < n; sr++) {
            for (int sc = 0; sc < m; sc++) {
                if (grid[sr][sc] == 1) {
                    int u = sr * m + sc, p1 = findParent_00(u);

                    for (int d = 0; d < dir.length; d++) {
                        int r = sr + dir[d][0];
                        int c = sc + dir[d][1];

                        if (r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 1) {
                            int v = r * m + c, p2 = findParent_00(v);
                            if (p1 != p2) { 
                                // not considering size factor (by comparing both sets size)
                                // since still the complexity might only fluctuate from O(4) to O(6 or 7)
                                parent_00[p2] = p1; 
                                size[p1] += size[p2];
                            }
                        }
                    }

                    maxArea = Math.max(maxArea, size[p1]);
                }
            }
        }

        return maxArea;
    }

    private int findParent_00(int u) {
        return parent_00[u] == u ? u : (parent_00[u] = findParent_00(parent_00[u]));
    }

    // ==================================================================

    // LC 1061
    private int[] parent_01;

    private String smallestEquivalentString(String s1, String s2, String baseStr) {
        parent_01 = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent_01.length; i++) 
            parent_01[i] = i; // initiallly leader will be self

        for (int i = 0; i < s1.length(); i++) {
            int u = s1.charAt(i)-'a', v = s2.charAt(i)-'a';
            int parent1 = findParent_01(u);
            int parent2 = findParent_01(v);

            // since cycle doesn't matter so don't need to use "if", also == condition is also handled
            parent_01[parent1] = Math.min(parent1, parent2);
            parent_01[parent2] = Math.min(parent1, parent2);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            int ch = (char)(findParent_01(baseStr.charAt(i)-'a') + 'a');
            sb.append(ch);
        }

        return sb.toString();
    }

    private int findParent_01(int u) {
        return parent_01[u] == u ? u : (parent_01[u] = findParent_01(parent_01[u]));
    }

    // ==================================================================

    // LC 990
    private int[] parent_02;

    public boolean equationsPossible(String[] equations) {
        parent_02 = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent_02.length; i++) 
            parent_02[i] = i; // initiallly leader will be self

        for (String s : equations) {
            int u = s.charAt(0)-'a', v = s.charAt(3)-'a'; 
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent_02(u);
            int parent2 = findParent_02(v);

            if (ch == '=' && parent1 != parent2) { // different set but equality condition, so union it
                parent_02[parent1] = parent2;
            }
        }

        for (String s : equations) {
            int u = s.charAt(0)-'a', v = s.charAt(3)-'a'; 
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent_02(u);
            int parent2 = findParent_02(v);

            // if parents same means belongs to same set, but question says "not equal - !" condition
            if (ch == '!' && parent1 == parent2) { 
                return false; // so condition failed, invalid
            }
        }

        return true;
    }

    private int findParent_02(int u) {
        return parent_02[u] == u ? u : (parent_02[u] = findParent_02(parent_02[u]));
    }

    // ==================================================================

}
