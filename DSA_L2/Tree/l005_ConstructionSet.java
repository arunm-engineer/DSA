
public class l005_ConstructionSet {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public Treeroot() {
        }

        public Treeroot(int val) {
            this.val = val;
        }
    }

    /****************************************************************************************************/

    public static TreeNode constructFromInorder(int[] inorder, int si, int ei) {
        if (si > ei)
            return null;

        int mid = (si + ei) / 2;
        int val = inorder[mid];
        TreeNode root = new Treeroot(val);

        root.left = constructFromInorder(inorder, si, mid-1);
        root.right = constructFromInorder(inorder, mid+1, ei);

        return root;
    }

    public static TreeNode bstFromInorder(int[] inorder) {
        int si = 0, ei = inorder.length-1;
        return constructFromInOrder(inorder, si, ei);

    }

    /****************************************************************************************************/

    // We use "ranges" for each root, so that we can check if in range and then construct root and return those roots to form tree
    // To avoid static use dummy node with idx, and pass in recursion making changes in that node
    static int prePointer;
    public static TreeNode constructBSTFromPreorder(int[] preorder, int leftRange, int rightRange) {
        if (prePointer == preorder.length || preorder[prePointer] < leftRange || preorder[prePointer] > rightRange) // check for val not in ranges
        return null;
        
        TreeNode root = new TreeNode(preorder[prePointer++]);
        root.left = constructBSTFromPreorder(preorder, leftRange, root.val);
        root.right = constructBSTFromPreorder(preorder, root.val, rightRange);
        
        return root;
    }

    public static TreeNode bstFromPreorder(int[] preorder) {
        prePointer = 0;
        int leftRange = -(int) 1e9, rightRange = (int) 1e9;
        return constructBSTFromPreorder(preorder, leftRange, rightRange);
    }
    
    /****************************************************************************************************/

    // Just changed the direction of flow as compared to preorder. Rest no change in concept
    // To avoid static use dummy node with idx, and pass in recursion making changes in that node
    static int postPointer;
    public static TreeNode constructBSTFromPostorder(int[] postorder, int leftRange, int rightRange) {
        if (postPointer < 0 || postorder[postPointer] < leftRange || postorder[postPointer] > rightRange)
            return null;

        TreeNode root = new TreeNode(postorder[postPointer--]);
        root.right = constructBSTFromPostorder(postorder, root.val, rightRange);
        root.left = constructBSTFromPostorder(postorder, leftRange, root.val);

        return root;
    }

    public static TreeNode bstFromPostorder(int[] postorder) {
        postPointer = postorder.length-1;
        int leftRange = -(int) 1e9, rightRange = (int) 1e9;
        return constructBSTFromPostorder(postorder, leftRange, rightRange);
    }

    /****************************************************************************************************/

}
