import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.BaseStream;

public class dsuQuestions {

    // LC 695
    private int[] parent_00, size;

    public int maxAreaOfIsland(int[][] grid) {
        // Here only 2-directions enough (top, down) since actually at the end,
        // the edges of that component will be merged to that global leader of that
        // component
        int[][] dir = { { 0, 1 }, { 1, 0 } };
        int n = grid.length, m = grid[0].length;
        parent_00 = new int[n * m];
        size = new int[n * m];

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

    /****************************************************************************************************/

    // LC 1061
    private int[] parent_01;

    private String smallestEquivalentString(String s1, String s2, String baseStr) {
        parent_01 = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent_01.length; i++)
            parent_01[i] = i; // initiallly leader will be self

        for (int i = 0; i < s1.length(); i++) {
            int u = s1.charAt(i) - 'a', v = s2.charAt(i) - 'a';
            int parent1 = findParent_01(u);
            int parent2 = findParent_01(v);

            // since cycle doesn't matter so don't need to use "if", also p1 == p2 condition
            // is also handled (since the same parent will be set again which will not be an
            // issue - just clean code)
            parent_01[parent1] = Math.min(parent1, parent2);
            parent_01[parent2] = Math.min(parent1, parent2);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            int ch = (char) (findParent_01(baseStr.charAt(i) - 'a') + 'a');
            sb.append(ch);
        }

        return sb.toString();
    }

    private int findParent_01(int u) {
        return parent_01[u] == u ? u : (parent_01[u] = findParent_01(parent_01[u]));
    }

    /****************************************************************************************************/

    // LC 990
    private int[] parent_02;

    public boolean equationsPossible(String[] equations) {
        parent_02 = new int[26]; // since only 26 lowercase chars exist
        for (int i = 0; i < parent_02.length; i++)
            parent_02[i] = i; // initiallly leader will be self

        for (String s : equations) {
            int u = s.charAt(0) - 'a', v = s.charAt(3) - 'a';
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent_02(u);
            int parent2 = findParent_02(v);

            if (ch == '=' && parent1 != parent2) { // different set but equality condition, so union it
                parent_02[parent1] = parent2;
            }
        }

        for (String s : equations) {
            int u = s.charAt(0) - 'a', v = s.charAt(3) - 'a';
            char ch = s.charAt(1); // for ch we only need either '=' or '!' to evaluate condition
            int parent1 = findParent_02(u);
            int parent2 = findParent_02(v);

            // if parents same means belongs to same set, but question says "not equal - !"
            // condition
            if (ch == '!' && parent1 == parent2) {
                return false; // so condition failed, invalid
            }
        }

        return true;
    }

    private int findParent_02(int u) {
        return parent_02[u] == u ? u : (parent_02[u] = findParent_02(parent_02[u]));
    }

    /****************************************************************************************************/

    // LC 839
    private int[] parent_03;

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int groups = n;
        parent_03 = new int[n];

        for (int i = 0; i < n; i++)
            parent_03[i] = i;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (areSimilarStrings(strs[i], strs[j])) {
                    int u = i, v = j;
                    int parent1 = findParent_03(u);
                    int parent2 = findParent_03(v);

                    // union (merge)
                    if (parent1 != parent2) {
                        parent_03[parent1] = parent2;
                        groups--;
                    }
                }
            }
        }

        return groups;
    }

    private boolean areSimilarStrings(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++count > 2) // means will take more than swaps to convert
                return false;
        }

        return true;
    }

    private int findParent_03(int u) {
        return parent_03[u] == u ? u : (parent_03[u] = findParent_03(parent_03[u]));
    }

    /****************************************************************************************************/

    private int[] parent_04;

    public List<Integer> numIslands2(int n, int m, int[][] positions) {
        int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        parent_04 = new int[n * m];
        Arrays.fill(parent_04, -1); // -1 says that initially there's no land, if != -1 indicates there's a land present

        List<Integer> ans = new ArrayList<>();
        int islandCount = 0;

        for (int[] p : positions) {
            int sr = p[0], sc = p[1];
            int idx = sr * m + sc;

            if (parent_04[idx] == -1) { // -1 means no existing land, so we can place our land here now
                islandCount++; // just incr count assuming that you've placed a new land
                parent_04[idx] = idx; // parent as self

                int p1 = findParent_04(idx);
                for (int d = 0; d < dir.length; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];
                    int IDX = r * m + c;

                    // if nbr == -1 then we need to merge lands & decr count
                    if (r >= 0 && r < n && c >= 0 && c < m && parent_04[IDX] != -1) { 
                        int p2 = findParent_04(IDX);
                        if (p1 != p2) { // if both parents arenot equal ( != ) only then we can merge
                            parent_04[p2] = p1; // in these cases, since p2 is nbrs parent, set parent for nbrs (p2) as (p1)
                            islandCount--; // we can only decr count if & only if the islands which are gonna be merged are diff comps i.e. (p1 != p2)
                        }
                    }
                }
            }

            ans.add(islandCount);
        }

        return ans;
    }

    private int findParent_04(int u) {
        return parent_04[u] == u ? u : (parent_04[u] = findParent_04(parent_04[u]));
    }

}
