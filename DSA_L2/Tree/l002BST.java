import java.util.ArrayList;

public class l002BST {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static int size(TreeNode root) {
        return root == null ? 0 : size(root.left) + size(root.right) + 1;
    }

    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right)) + 1;
    }

    public static TreeNode max(TreeNode root) {
        TreeNode curr = root;
        while(curr != null) // rightmost
            curr = curr.right;

        return curr;
    }

    public static TreeNode min(TreeNode root) {
        TreeNode curr = root;
        while(curr != null) // rightmost
            curr = curr.left;

        return curr;
    }

    public static boolean find(TreeNode root, int data) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.val == data)
                return true;
            else if (curr.val < data)
                curr = curr.right;
            else 
                curr = curr.left;
        }

        return false;
    }

    public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, int data) {
        ArrayList<TreeNode> list = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            list.add(curr);
            if (curr.val == data) 
                return list;
            else if (curr.val < data) 
                curr = curr.right;
            else 
                curr = curr.left;
        }

        list.clear();
        return list;
    }

    /****************************************************************************************************/

    // LC 235
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while (curr != null) {
            if (p.val > curr.val && q.val > curr.val)
            curr = curr.right;
            else if (p.val < curr.val && q.val < curr.val)
            curr = curr.left;
            else 
            return curr;
        }
        
        return null;
    }

    /****************************************************************************************************/
}
