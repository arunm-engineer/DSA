public class l006_AVL {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        int height = 0; // ht interms of edges
        int bal = 0; // Balancing factor = lh - rh ; (-1 <= B.F <= 1)

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }
    }

    // TC O(1)
    public static TreeNode getRotation(TreeNode root) {
        updateHeightBalance(root); // First update your ht, bal

        if (root.bal == 2) { // ll, lr => left child will surely exist
            if (root.left.bal == 1) { // ll, right rotation
                return getRightRotation(root);                
            }
            else { // lr
                root.left = getLeftRotation(root.left);
                return getRightRotation(root);
            }
        }
        else if (root.bal == -2) { // rr, rl => right child will surely exist
            if (root.right.bal == -1) { // rr, left rotation
                return getLeftRotation(root);
            }
            else { // rl
                root.right = getRightRotation(root.right);
                return getLeftRotation(root);
            }
        }

        return root; // This return means the tree is already balanced
    }
    
    // TC O(1)
    public static TreeNode  getLeftRotation(TreeNode A) {
        TreeNode B = A.right;
        TreeNode BKaLeft = B.left;

        B.left = A;
        A.right = BKaLeft;

        // After manipulation, there will be some change in height & balance factor to be updated for some nodes & subtree
        updateHeightBalance(A);
        updateHeightBalance(B);

        return B;
    }

    // TC O(1)
    public static TreeNode getRightRotation(TreeNode A) {
        TreeNode B = A.left;
        TreeNode BKaRight = B.right;

        B.right = A;
        A.left = BKaRight;

        updateHeightBalance(A);
        updateHeightBalance(B);

        return B;
    }
    
    // TC O(1)
    public static void updateHeightBalance(TreeNode node) {
        int lh = node.left != null ? node.left.height : -1;
        int rh = node.right != null ? node.right.height : -1;

        node.height = Math.max(lh, rh) + 1;
        node.bal = lh - rh;
    }

    // LC 701
    // TC O(logn)
    public static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        
        if (val < root.val) 
            root.left = insertIntoBST(root.left, val);
        else 
            root.right = insertIntoBST(root.right, val);
        
            root = getRotation(root); // This will tak care of underlying to make it a balanced tree
        return root;
    }
    
    // LC 450
    // TC O(logn)
    public static TreeNode deleteNode(TreeNode root, int val) {
        if (root == null)
            return null;
        
        if (val == root.val) {
            if (root.left == null || root.right == null) { // 1-children
                return root.left != null ? root.left : root.right;
            }
            else { // 2-children
                // Since right's leftmost will be next potential max node
                TreeNode delNode = getMax(root.right);
                root.val = delNode.val;
                root.right = deleteNode(root.right, delNode.val);
                return root;
            }
        }
        else if (val < root.val) 
            root.left = deleteNode(root.left, val);
        else 
            root.right = deleteNode(root.right, val);
        
            root = getRotation(root); // This will tak care of underlying to make it a balanced tree
        return root;
    }

    // TC O(logn)
    public static TreeNode getMax(TreeNode root) {
        while (root.left != null)
            root = root.left;
        
        return root;
    }

    public static void display(TreeNode root) {
        if (root == null)
            return;

        // Printing in preorder
        StringBuilder sb = new StringBuilder();
        sb.append(root.left != null ? root.left.val : ".");
        sb.append(" <- " + root.val + " -> ");
        sb.append(root.right != null ? root.right.val : ".");
        System.out.println(sb);

        display(root.left);
        display(root.right);
    }

    public static void main(String[] args) {
        TreeNode root = null;
        for (int val = 1; val <= 10; val++) {
            root = insertIntoBST(root, val*10);
        }
        display(root);
    }
}
