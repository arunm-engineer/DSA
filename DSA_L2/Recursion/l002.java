public class l002 {

    // nCr method
    public static int infinitePermutation(int[] coins, int tar, String asf) {
        if (tar == 0) {
            System.out.println(asf);
            return 1;
        }

        int count = 0;
        for (int i = 0;i < coins.length;i++) {
            if (tar-coins[i] >= 0)
                count += infinitePermutation(coins, tar - coins[i], asf+coins[i]);
                // coins[i] is not much important, but "i" (index) is, bcoz here you will find the arrangement made is actually based of the indexes
        }

        return count;
    }

    public static int infiniteCombination(int[] coins, int tar, int idx, String asf) {
        if (tar == 0) {
            System.out.println(asf);
            return 1;
        }

        int count = 0;
        for (int i = idx;i < coins.length;i++) {
            if (tar-coins[i] >= 0)
                count += infiniteCombination(coins, tar-coins[i], i, asf+coins[i]);

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
        for (int i = idx;i < coins.length;i++) {
            if (tar-coins[i] >= 0)
                count += singleCombination(coins, tar-coins[i], i+1, asf+coins[i]);
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
        for (int i = 0;i < coins.length;i++) {
            if (coins[i] != -1 && tar-coins[i] >= 0) {
                int coin = coins[i];
                coins[i] = -1;
                count += singlePermutation(coins, tar-coin, asf+coin);
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

        if (tar-coins[idx] >= 0)
            count += singleCombination_01(coins, tar-coins[idx], idx+1, asf+coins[idx]+" ");
        count += singleCombination_01(coins, tar, idx+1, asf);

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

        if (tar-coins[idx] >= 0)
            count += infiniteCombination_01(coins, tar-coins[idx], idx, asf+coins[idx]+" ");
        count += infiniteCombination_01(coins, tar, idx+1, asf);

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

        if (tar-coins[idx] >= 0)
            count += infinitePermutation_01(coins, tar-coins[idx], 0, asf+coins[idx]+" ");
        count += infinitePermutation_01(coins, tar, idx+1, asf);

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

        if (coins[idx] != -1 && tar-coins[idx] >= 0) {
            int coin = coins[idx];
            coins[idx] = -1;
            count += singlePermutation_01(coins, tar-coin, 0, asf+coin+" ");
            coins[idx] = coin;
        }
        count += singlePermutation_01(coins, tar, idx+1, asf);

        return count;
    }

    /************************************************************/ 

    // Queen Set
    // tb => total boxes, tq => total queens
    public static int queenCombination_1D(int cb, int cq, int tb, int tq, String ans) {
        if (cb == tb || cq == tq) {
            if (cq == tq) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        
        int count = 0;

        count += queenCombination_1D(cb+1, cq+1, tb, tq, ans+"b"+cb+"q"+cq+" "); // Yes
        count += queenCombination_1D(cb+1, cq, tb, tq, ans); // No

        return count;
    }

    public static int queenPermutation_1D(int cb, int cq, int tb, int tq, String ans) {
        if (cb == tb || cq == tq) {
            if (cq == tq) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        
        int count = 0;

        count += queenPermutation_1D(cb+1, cq+1, tb, tq, ans+"b"+cb+"q"+cq+" "); // Yes
        count += queenPermutation_1D(cb+1, cq, tb, tq, ans); // No

        return count;
    }

    public static void coinChange() {
        int[] coins = {2, 3, 5, 7};
        int tar = 7;  // Use target=7 for easier dry-run over recursive diagram in subsq method
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

    public static void queenSet() {
        int tb = 4, tq = 3, cb = 0, cq = 0;
        String ans = "";
        System.out.println(queenCombination_1D(cb, cq, tb, tq, ans));
    }

    public static void main(String[] args) {
        coinChange();   
        queenSet();
    }
}