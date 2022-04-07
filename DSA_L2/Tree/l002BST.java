import java.util.ArrayList;
import java.util.Stack;

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

    public static int min(TreeNode root) {
        TreeNode curr = root;
        while (curr.left != null) // leftmost
            curr = curr.left;

        return curr.val;
    }

    public static int max(TreeNode root) {
        TreeNode curr = root;
        while (curr.right != null) // rightmost
            curr = curr.right;

        return curr.val;
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
        ArrayList<TreeNode> ans = new ArrayList<>();
        TreeNode curr = root;
        boolean flag = false;
        while (curr != null) {
            ans.add(curr);
            if (curr.val == data) {
                flag = true;
                break;
            } else if (curr.val < data)
                curr = curr.right;
            else
                curr = curr.left;
        }

        if (flag == false)
            ans.clear();

        return ans;
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

    // LC 173 => TC O(1) constant time on an average, SC O(logn) stack holds at max
    // height of the tree elements
    class BSTIterator {
        // Also can use LinkedList, ArrayDeque instead of Stack. Also ArrayDeque will be
        // little faster than LinkedList.
        Stack<TreeNode> stack; // With this at max we will only have height of the tree elements SC O(logn)

        public BSTIterator(TreeNode root) {
            stack = new Stack<TreeNode>();
            addAllLeft(root); // Initially fill, with root and all left elements of the root
        }

        public int next() {
            TreeNode node = stack.pop();
            addAllLeft(node.right);

            return node.val;
        }

        public boolean hasNext() {
            return stack.size() > 0;
        }

        public void addAllLeft(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }

    /****************************************************************************************************/

    // BST find method traversal
    // TC O(logn), SC O(1)
    public static void predSuccInBST(TreeNode root, int data) {
        TreeNode pred = null, succ = null, curr = root;

        while (curr != null) {
            if (curr.val == data) {
                TreeNode leftMost = getLeftMost(root.right);
                TreeNode rightMost = getRightMost(root.left);

                succ = leftMost != null ? leftMost : succ;
                pred = rightMost != null ? rightMost : pred;
            } else if (curr.val < data) { // Moving left => set potential succ
                succ = curr;
                curr = curr.left;
            } else { // Moving right => set potential pred
                pred = curr;
                curr = curr.right;
            }
        }
    }

    public static TreeNode getLeftMost(TreeNode root) {
        while (root.left != null)
            root = root.left;
        return root;
    }

    public static TreeNode getRightMost(TreeNode root) {
        while (root.right != null)
            root = root.right;
        return root;
    }

    /****************************************************************************************************/

}
