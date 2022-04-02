import java.util.LinkedList;

public class l005_ConstructionSet {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /****************************************************************************************************/

    public static TreeNode constructFromInorder(int[] inorder, int si, int ei) {
        if (si > ei)
            return null;

        int mid = (si + ei) / 2;
        int val = inorder[mid];
        TreeNode root = new TreeNode(val);

        root.left = constructFromInorder(inorder, si, mid-1);
        root.right = constructFromInorder(inorder, mid+1, ei);

        return root;
    }

    public static TreeNode bstFromInorder(int[] inorder) {
        int si = 0, ei = inorder.length-1;
        return constructFromInorder(inorder, si, ei);
    }

    /****************************************************************************************************/

    // We use "ranges" for each root, so that we can check if in range and then construct root and return those roots to form tree
    // To avoid static use dummy node with idx, and pass in recursion making changes in that node
    static int preIdx;
    public static TreeNode constructBSTFromPreorder(int[] preorder, int leftRange, int rightRange) {
        if (preIdx == preorder.length || preorder[preIdx] < leftRange || preorder[preIdx] > rightRange) // not in range
        return null;
        
        TreeNode root = new TreeNode(preorder[preIdx++]);
        root.left = constructBSTFromPreorder(preorder, leftRange, root.val);
        root.right = constructBSTFromPreorder(preorder, root.val, rightRange);
        
        return root;
    }

    public static TreeNode bstFromPreorder(int[] preorder) {
        preIdx = 0;
        int leftRange = -(int) 1e9, rightRange = (int) 1e9;
        return constructBSTFromPreorder(preorder, leftRange, rightRange);
    }
    
    /****************************************************************************************************/

    // Just changed the direction of flow as compared to preorder. Rest no change in concept
    // To avoid static use dummy node with idx, and pass in recursion making changes in that node
    static int postIdx;
    public static TreeNode constructBSTFromPostorder(int[] postorder, int leftRange, int rightRange) {
        if (postIdx < 0 || postorder[postIdx] < leftRange || postorder[postIdx] > rightRange) // not in range
            return null;

        TreeNode root = new TreeNode(postorder[postIdx--]);
        root.right = constructBSTFromPostorder(postorder, root.val, rightRange);
        root.left = constructBSTFromPostorder(postorder, leftRange, root.val);

        return root;
    }

    public static TreeNode bstFromPostorder(int[] postorder) {
        postIdx = postorder.length-1;
        int leftRange = -(int) 1e9, rightRange = (int) 1e9;
        return constructBSTFromPostorder(postorder, leftRange, rightRange);
    }

    /****************************************************************************************************/

    public static class BSTLvlPair {
        int leftRange;
        int rightRange;
        TreeNode parent;

        public BSTLvlPair(int leftRange, int rightRange, TreeNode parent) {
            this.leftRange = leftRange;
            this.rightRange = rightRange;
            this.parent = parent;
        }
    }

    public static TreeNode constructBSTFromLevelOrder(int[] levelorder) {
        if (levelorder.length == 0)
            return null;

        LinkedList<BSTLvlPair> que = new LinkedList<>();
        TreeNode root = new TreeNode(levelorder[0]);
        que.add(new BSTLvlPair(-(int) 1e9, root.val, root));
        que.add(new BSTLvlPair(root.val, (int) 1e9, root));
        
        int idx = 1; // points to elems in levelorder elem
        while (idx < levelorder.length) {
            BSTLvlPair rpair = que.removeFirst();
            int leftRange = rpair.leftRange, rightRange = rpair.rightRange;
            TreeNode parent = rpair.parent;

            if (levelorder[idx] < leftRange || levelorder[idx] > rightRange) // not in range
                continue;

            TreeNode node = new TreeNode(levelorder[idx++]);
            if (node.val < parent.val) parent.left = node;
            else parent.right = node;

            que.addLast(new BSTLvlPair(leftRange, node.val, node));
            que.addLast(new BSTLvlPair(node.val, rightRange, node));
        }

        return root;
    }

    /****************************************************************************************************/
    
    // All unique elems only in tree
    public static TreeNode constructBTFrom_Pre_In(int[] preorder, int[] inorder) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        return constructBTFrom_Pre_In(preorder, inorder, isi, iei, psi, pei);
    }

    public static TreeNode constructBTFrom_Pre_In(int[] preorder, int[] inorder, int isi, int iei, int psi, int pei) {
        if (psi > pei || isi > iei)
            return null;

        TreeNode root = new TreeNode(preorder[psi]);
        
        int idx = findRoot_00(root, inorder, isi, iei);
        int t_elem = idx - isi; // Total elems in "left" substree from inorder

        root.left = constructBTFrom_Pre_In(preorder, inorder, isi, idx-1, psi+1, psi+t_elem); // Left call pre & in-ranges calc
        root.right = constructBTFrom_Pre_In(preorder, inorder, idx+1, iei, psi+t_elem+1, pei); // Right call pre & in-ranges calc

        return root;
    }

    public static int findRoot_00(TreeNode root, int[] inorder, int isi, int iei) {
        int idx = -1;
        for (int i = isi; i <= iei; i++) {
            if (root.val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    /****************************************************************************************************/

    public static TreeNode constructBTFrom_Post_In(int[] postorder, int[] inorder) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        return constructBTFrom_Post_In(postorder, inorder, isi, iei, psi, pei);
    }

    public static TreeNode constructBTFrom_Post_In(int[] postorder, int[] inorder, int isi, int iei, int psi, int pei) {
        if (psi > pei || isi > iei)
            return null;

        TreeNode root = new TreeNode(postorder[pei]);
        
        int idx = findRoot_01(root, inorder, isi, iei);
        int t_elem = idx - isi; // Total elems in "right" substree from inorder

        root.left = constructBTFrom_Post_In(postorder, inorder, isi, idx-1, psi, psi+t_elem-1); // Left call pre & in-ranges calc
        root.right = constructBTFrom_Post_In(postorder, inorder, idx+1, iei, psi+t_elem, pei-1); // Right call pre & in-ranges calc

        return root;
    }

    public static int findRoot_01(TreeNode root, int[] inorder, int isi, int iei) {
        int idx = -1;
        for (int i = isi; i <= iei; i++) {
            if (root.val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    /****************************************************************************************************/
}
