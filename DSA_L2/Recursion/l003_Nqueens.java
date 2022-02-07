public class l003_Nqueens {

    public static boolean isSafeToPlaceQueen(boolean[][] box, int sr, int sc) {
        // Direction to check for nquees "combinations"
        // int[][] dir = { { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } }; 
        // Direction to check for  nquees "permutations"
        int[][] dir = { { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 } }; 

        int n = box.length, m = box[0].length;
        for (int d = 0; d < dir.length; d++) {
            for (int rad = 1; rad < n; rad++) {
                int r = sr + rad * dir[d][0];
                int c = sc + rad * dir[d][1];

                if (r >= 0 && c >= 0 && r < n && c < m) {
                    if (box[r][c]) // True means another queen already placed which kill, so not safe to place
                        return false;
                } else // No in range of boxes so break
                    break;
            }
        }

        return true;
    }

    // Same ditto of queenComb_1D, just that using conversion logic of 1D to 2D
    public static int nqueen_combinations(boolean[][] box, int cb, int cq, int tb, int tq, String ans) {
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
            if (isSafeToPlaceQueen(box, r, c)) {
                box[r][c] = true;
                count += nqueen_combinations(box, b + 1, cq + 1, tb, tq, ans + "(" + r + ", " + c + ")" + " ");
                box[r][c] = false;
            }
        }

        return count;
    }

    public static int nqueen_permutations(boolean[][] box, int cb, int cq, int tb, int tq, String ans) {
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
            // Since permutation we may encount current position as well so check it also
            if (box[r][c] == false && isSafeToPlaceQueen(box, r, c)) {
                box[r][c] = true;
                count += nqueen_permutations(box, b + 1, cq + 1, tb, tq, ans + "(" + r + ", " + c + ")" + " ");
                box[r][c] = false;
            }
        }

        return count;
    }

    public static void nqueens() {
        int tb = 4, tq = 4, cb = 0, cq = 0;
        String ans = "";
        boolean[][] box = new boolean[tb][tb];

        // System.out.println(nqueen_combinations(box, cb, cq, tb, tq, ans));
        System.out.println(nqueen_permutations(box, cb, cq, tb, tq, ans));
    }

    public static void main(String[] args) {
        nqueens();
    }
}
