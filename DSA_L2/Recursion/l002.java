public class l002 {

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
                count += infiniteCombination(coins, tar-coins[i], i+1, asf+coins[i]);
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

    public static void main(String[] args) {
        int[] coins = {2, 3, 5, 7};
        int tar = 10;
        String asf = "";
        // System.out.println(infinitePermutation(coins, tar, asf));
        // System.out.println(infiniteCombination(coins, tar, 0, asf));
        System.out.println(singlePermutation(coins, tar, asf));
    }
}