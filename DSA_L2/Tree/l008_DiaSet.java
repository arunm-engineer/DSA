public class l008_DiaSet {
    
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode() {}

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right))+1;
    }

    // Approach1 => TC O(n^2) ; Get l_pot_dia ; r_pot_dia ; (lht,rht) to calc next pot_dia and return it
    // Height interms of edges
    public static int diameter(TreeNode root) {
        if (root == null)
            return 0;

        int ldia = diameter(root.left); // L subtree potential dia
        int rdia = diameter(root.right); // R subtree potential dia

        // To calculate self dia
        int lht = height(root.left);
        int rht = height(root.right);
        int dia = lht + rht + 2;

        return Math.max(dia, Math.max(ldia, rdia)); // return potential dia from L ST dia, R ST dia, self dia
    }

    // Approach 2 => Can use either class based having {dia, ht} (or) like below array which stores both
    // TC O(n)
    // {diameter, height} => array storage
    public static int[] diameter_01(TreeNode root) {
        if (root == null) {
            return new int[]{0, -1};
        }

        int[] lpair = diameter_01(root.left);
        int[] rpair = diameter_01(root.right);

        int ldia = lpair[0], rdia = rpair[0];
        int lht = lpair[1], rht = rpair[1];
        int dia = lht + rht + 2;

        int pot_dia = Math.max(dia, Math.max(ldia, rdia)); // Potential dia
        int ht = Math.max(lht, rht) + 1;

        return new int[]{pot_dia, ht};
    }

    // Approach 3 => TC O(n)
    // Now we just want ht from L.ST & R.ST, since potential dia from L.ST & R.ST will already be stored in the static kind reference
    public static int diameter_02(TreeNode root, int[] pot_dia) {
        if (root == null)
            return -1;

        int lht = diameter_02(root.left, pot_dia);
        int rht = diameter_02(root.right, pot_dia);
        
        int dia = lht + rht + 2;
        pot_dia[0] = Math.max(pot_dia[0], dia); // Calc next potential dia

        return Math.max(lht, rht) + 1;
    }

    /****************************************************************************************************/

}
