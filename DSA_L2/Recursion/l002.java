public class l002 {

    // nCr method
    public static int infinitePermutation(int[] coins, int tar, String asf) {
        if (tar == 0) {
            System.out.println(asf);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < coins.length; i++) {
            if (tar - coins[i] >= 0)
                count += infinitePermutation(coins, tar - coins[i], asf + coins[i]);
            // coins[i] is not much important, but "i" (index) is, bcoz here you will find
            // the arrangement made is actually based of the indexes
        }

        return count;
    }

    public static int infiniteCombination(int[] coins, int tar, int idx, String asf) {
        if (tar == 0) {
            System.out.println(asf);
            return 1;
        }

        int count = 0;
        for (int i = idx; i < coins.length; i++) {
            if (tar - coins[i] >= 0)
                count += infiniteCombination(coins, tar - coins[i], i, asf + coins[i]);

            // count += infiniteCombination(coins, tar-coins[i], i, asf+i);
            // idx from i since infinite supply
        }

        return count;
    }

    public static int singleCombination(int[] coins, int tar, int idx, String asf) {
        if (tar == 0) {
            System.out.println(asf);
            return 1;
        }

        int count = 0;
        for (int i = idx; i < coins.length; i++) {
            if (tar - coins[i] >= 0)
                count += singleCombination(coins, tar - coins[i], i + 1, asf + coins[i]);
            // idx from i since infinite supply
        }

        return count;
    }

    public static int singlePermutation(int[] coins, int tar, String asf) {
        if (tar == 0) {
            System.out.println(asf);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] != -1 && tar - coins[i] >= 0) {
                int coin = coins[i];
                coins[i] = -1;
                count += singlePermutation(coins, tar - coin, asf + coin);
                coins[i] = coin;
            }
        }

        return count;
    }

    /************************************************/

    // Subsequence method
    public static int singleCombination_01(int[] coins, int tar, int idx, String asf) {
        if (tar == 0 || idx >= coins.length) {
            if (tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;

        if (tar - coins[idx] >= 0)
            count += singleCombination_01(coins, tar - coins[idx], idx + 1, asf + coins[idx] + " ");
        count += singleCombination_01(coins, tar, idx + 1, asf);

        return count;
    }

    public static int infiniteCombination_01(int[] coins, int tar, int idx, String asf) {
        if (tar == 0 || idx >= coins.length) {
            if (tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;

        if (tar - coins[idx] >= 0)
            count += infiniteCombination_01(coins, tar - coins[idx], idx, asf + coins[idx] + " ");
        count += infiniteCombination_01(coins, tar, idx + 1, asf);

        return count;
    }

    public static int infinitePermutation_01(int[] coins, int tar, int idx, String asf) {
        if (tar == 0 || idx >= coins.length) {
            if (tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;

        if (tar - coins[idx] >= 0)
            count += infinitePermutation_01(coins, tar - coins[idx], 0, asf + coins[idx] + " ");
        count += infinitePermutation_01(coins, tar, idx + 1, asf);

        return count;
    }

    public static int singlePermutation_01(int[] coins, int tar, int idx, String asf) {
        if (tar == 0 || idx >= coins.length) {
            if (tar == 0) {
                System.out.println(asf);
                return 1;
            }
            return 0;
        }

        int count = 0;

        if (coins[idx] != -1 && tar - coins[idx] >= 0) {
            int coin = coins[idx];
            coins[idx] = -1;
            count += singlePermutation_01(coins, tar - coin, 0, asf + coin + " ");
            coins[idx] = coin;
        }
        count += singlePermutation_01(coins, tar, idx + 1, asf);

        return count;
    }

    /************************************************************/
    // Queen Set 1D

    public static int queenCombination_1D_ncr(int cb, int cq, int tb, int tq, String ans) {
        if (cq == tq) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int b = cb; b < tb; b++) {
            count += queenCombination_1D_ncr(b + 1, cq + 1, tb, tq, ans + "b" + b + "q" + cq + " ");
        }
        return count;
    }

    public static int queenPermutation_1D_ncr(int cb, int cq, int tb, int tq, String ans, boolean[] visited) {
        if (cq == tq) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;

        for (int b = 0; b < tb; b++) {
            if (visited[b] == false) {
                visited[b] = true;
                count += queenPermutation_1D_ncr(b + 1, cq + 1, tb, tq, ans + "b" + b + "q" + cq + " ", visited);
                visited[b] = false;
            }
        }

        return count;
    }

    // Subsq method
    // tb => total boxes, tq => total queens
    public static int queenCombination_1D_subsq(int cb, int cq, int tb, int tq, String ans) {
        if (cb == tb || cq == tq) {
            if (cq == tq) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;

        count += queenCombination_1D_subsq(cb + 1, cq + 1, tb, tq, ans + "b" + cb + "q" + cq + " "); // Yes
        count += queenCombination_1D_subsq(cb + 1, cq, tb, tq, ans); // No

        return count;
    }

    public static int queenPermutation_1D_subsq(int cb, int cq, int tb, int tq, String ans, boolean[] visited) {
        if (cb == tb || cq == tq) {
            if (cq == tq) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;

        if (visited[cb] == false) {
            visited[cb] = true;
            count += queenPermutation_1D_subsq(0, cq + 1, tb, tq, ans + "b" + cb + "q" + cq + " ", visited); // Yes
            visited[cb] = false;
        }

        count += queenPermutation_1D_subsq(cb + 1, cq, tb, tq, ans, visited); // No

        return count;
    }

    /************************************************************/

    // Queen set 2D

    // ncr method

    // Same ditto of queenComb_1D, just that using conversion logic of 1D to 2D
    public static int queenCombination_2D_ncr(boolean[][] box, int cb, int cq, int tb, int tq, String ans) {
        if (cq == tq) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        int n = box.length, m = box[0].length;
        // Here your n*m will be equal to your "tb" & box acts as your visited but no
        // need of that in comb but used in perm
        for (int b = cb; b < n * m; b++) {
            int r = b / m;
            int c = b % m;
            count += queenCombination_2D_ncr(box, b + 1, cq + 1, tb, tq, ans + "(" + r + ", " + c + ")" + " ");
        }

        return count;
    }

    public static int queenPermutation_2D_ncr(boolean[][] box, int cb, int cq, int tb, int tq, String ans) {
        if (cq == tq) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        int n = box.length, m = box[0].length;
        // Here your n*m will be equal to your "tb" & box acts as your visited but no
        // need of that in comb but used in perm
        for (int b = 0; b < n * m; b++) {
            int r = b / m;
            int c = b % m;
            if (box[r][c] == false) {
                box[r][c] = true;
                count += queenPermutation_2D_ncr(box, b + 1, cq + 1, tb, tq, ans + "(" + r + ", " + c + ")" + " ");
                box[r][c] = false;
            }
        }

        return count;
    }

    // Subsq method

    public static int queenCombination_2D_subsq(boolean[][] box, int cb, int cq, int tb, int tq, String ans) {
        if (cb == tb || cq == tq) {
            if (cq == tq) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int n = box.length, m = box[0].length;
        int r = cb / m;
        int c = cb % m;

        int count = 0;

        count += queenCombination_2D_subsq(box, cb + 1, cq + 1, tb, tq, ans + "(" + r + ", " + c + ")" + " "); // Yes
        count += queenCombination_2D_subsq(box, cb + 1, cq, tb, tq, ans); // No

        return count;
    }

    public static int queenPermutation_2D_subsq(boolean[][] box, int cb, int cq, int tb, int tq, String ans) {
        if (cb == tb || cq == tq) {
            if (cq == tq) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int n = box.length, m = box[0].length;
        int r = cb / m;
        int c = cb % m;

        int count = 0;

        if (box[r][c] == false) {
            box[r][c] = true;
            count += queenPermutation_2D_subsq(box, 0, cq + 1, tb, tq, ans + "(" + r + ", " + c + ")" + " "); // Yes
            box[r][c] = false;
        }
        count += queenPermutation_2D_subsq(box, cb + 1, cq, tb, tq, ans); // No

        return count;
    }

    /************************************************************/

    public static void coinChange() {
        int[] coins = { 2, 3, 5, 7 };
        int tar = 7; // Use target=7 for easier dry-run over recursive diagram in subsq method
        String asf = "";

        // nCr method
        // System.out.println(infinitePermutation(coins, tar, asf));
        // System.out.println(infiniteCombination(coins, tar, 0, asf));
        // System.out.println(singleCombination(coins, tar, 0, asf));
        // System.out.println(singlePermutation(coins, tar, asf));

        // Subsequence method
        // System.out.println(singleCombination_01(coins, tar, 0, asf));
        // System.out.println(infiniteCombination_01(coins, tar, 0, asf));
        // System.out.println(infinitePermutation_01(coins, tar, 0, asf));
        // System.out.println(singlePermutation_01(coins, tar, 0, asf));
    }

    public static void queenSet_1D() {
        int tb = 3, tq = 2, cb = 0, cq = 0;
        String ans = "";
        boolean[] visited = new boolean[tb];

        // ncr method
        // System.out.println(queenCombination_1D_ncr(cb, cq, tb, tq, ans));
        // System.out.println(queenPermutation_1D_ncr(cb, cq, tb, tq, ans, visited));

        // Subsq method
        // System.out.println(queenCombination_1D_subsq(cb, cq, tb, tq, ans));
        // System.out.println(queenPermutation_1D_subsq(cb, cq, tb, tq, ans, visited));
    }

    public static void queenSet_2D() {
        int tb = 3, tq = 2, cb = 0, cq = 0;
        boolean[][] box = new boolean[tb][tb]; // Just for visited purpose
        String ans = "";

        // ncr method
        // System.out.println(queenCombination_2D_ncr(box, cb, cq, tb, tq, ans));
        System.out.println(queenPermutation_2D_ncr(box, cb, cq, tb, tq, ans));

        // Subsq method
        // System.out.println(queenCombination_2D_subsq(box, cb, cq, tb, tq, ans));
        System.out.println(queenPermutation_2D_subsq(box, cb, cq, tb, tq, ans));
    }

    public static void main(String[] args) {
        // coinChange();
        // queenSet_1D();
        queenSet_2D();
    }
}