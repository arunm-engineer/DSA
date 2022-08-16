import java.util.ArrayList;
import java.util.Stack;

public class l007_TraversalSet {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode() {}

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /****************************************************************************************************/

    // TC 3n(on-average), 4n(at max) ; SC O(1)
    public static ArrayList<Integer> morrisInorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) { // Print area
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMost(left, curr);
                if (rightMost.right == null) { // Thread create area
                    rightMost.right = curr; // Thread create
                    curr = curr.left;
                } else { // Thread break area
                    rightMost.right = null; // Thread break
                    ans.add(curr.val);
                    curr = curr.right;
                }
            }
        }

        return ans;
    }

    public static TreeNode getRightMost(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr) {
            node = node.right;
        }

        return node;
    }

    /****************************************************************************************************/

    public static ArrayList<Integer> morrisPreorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMost_00(left, curr);
                if (rightMost.right == null) {
                    rightMost.right = curr;
                    ans.add(curr.val);
                    curr = curr.left;
                } else {
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }

        return ans;
    }

    public static TreeNode getRightMost_00(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr) {
            node = node.right;
        }

        return node;
    }

    /****************************************************************************************************/

    // LC 145
    public static ArrayList<Integer> morrisPostorderTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();

        TreeNode curr = root;
        while (curr != null) {
            TreeNode right = curr.right;
            if (right == null) {
                ans.add(0, curr.val);
                curr = curr.left;
            } else {
                TreeNode leftMost = getLeftMost(right, curr);
                if (leftMost.left == null) { // thread form
                    leftMost.left = curr;
                    ans.add(0, curr.val);
                    curr = curr.right;
                } else { // thread break
                    leftMost.left = null;
                    curr = curr.left;
                }
            }
        }

        return ans;
    }

    public static TreeNode getLeftMost(TreeNode node, TreeNode curr) {
        while (node.left != null && node.left != curr) {
            node = node.left;
        }

        return node;
    }

    /****************************************************************************************************/

    // LC 98
    // Validate BST => Using Inorder Traversal
    // Approach 1 => Recursive Inorder
    public static boolean validateBST_rec(TreeNode root, TreeNode[] prev) {
        if (root == null)
            return true;

        boolean left = validateBST_rec(root.left, prev);
        if (left == false)
            return false;

        if (prev[0] != null && prev[0].val >= root.val)
            return false;
        prev[0] = root;

        boolean right = validateBST_rec(root.right, prev);
        if (right == false)
            return false;

        return true;
    }

    // Approach 2 => Iterative Stack Inorder
    public static boolean validateBST_itr(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode[] prev = new TreeNode[1];
        boolean res = true;

        addAllLeft(root, stack);
        while (stack.size() > 0) {
            TreeNode curr = stack.pop();
            if (prev[0] != null && prev[0].val >= curr.val) {
                res = false;
                break;
            }
            prev[0] = curr;

            addAllLeft(curr.right, stack);
        }

        stack.clear();
        return res;
    }

    public static void addAllLeft(TreeNode node, Stack<TreeNode> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    // Approach 3 => Class Pair {min, max, isBST}
    public static class BSTPair {
        long min = (long) 1e13; // If in int range, then (int) 1e8
        long max = -(long) 1e13;
        boolean isBst = true;

        // boolean isBal = true;
        // int height = -1;
        // int largestBSTSize = 0;
        // TreeNode largestBSTNode = null;
        // int totalNoOfBST = 0;

        public BSTPair() {}

        public BSTPair(int min, int max, boolean isBst) {
            this.min = min;
            this.max = max;
            this.isBst = isBst;
        }
    }

    public static BSTPair validateBST(TreeNode root) {
        if (root == null) 
            return new BSTPair();

        BSTPair left = validateBST(root.left);
        BSTPair right = validateBST(root.right);

        BSTPair pair = new BSTPair();
        pair.isBst = false;
        if (left.isBst && right.isBst && left.max < root.val && root.val < right.min) {
            pair.isBst = true;
            pair.min = Math.min(root.val, left.min);
            pair.max = Math.max(root.val, right.max);
        }

        return pair;
    }
    
    // Approach4 => Morris Inorder
    public static boolean validateBST_MorrisInorder(TreeNode root) {
        TreeNode[] prev = new TreeNode[1];
        boolean res = true;

        TreeNode curr = root;
        while(curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                if (prev[0] != null && prev[0].val >= curr.val) {
                    res = false;
                    break;
                }
                prev[0] = curr;
                curr = curr.right;
            }
            else {
                TreeNode rightMost = getRightMost_01(left, curr);
                if (rightMost.right == null) { // Thread create
                    rightMost.right = curr;
                    curr = curr.left;
                }
                else { // Thread break
                    rightMost.right = null;
                    if (prev[0] != null && prev[0].val >= curr.val) {
                        res = false;
                        break;
                    }
                    prev[0] = curr;
                    curr = curr.right;
                }
            }
        }

        return res;
    }

    public static TreeNode getRightMost_01(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr) {
            node = node.right;
        }

        return node;
    }

    /****************************************************************************************************/

    // https://www.geeksforgeeks.org/kth-largest-element-in-bst-when-modification-to-bst-is-not-allowed/
    // Reverse Inorder Traversal (Morris Inorder reverse thread connected for node's left child to parent)
    public int kthLargest(TreeNode root, int k) {
        TreeNode kthLargest = KthLargest_MorrisInorderReverse(root, k);
        return kthLargest.val;
    }

    public static TreeNode KthLargest_MorrisInorderReverse(TreeNode root, int k) {
        TreeNode kthLargest = null;

        TreeNode curr = root;
        while (curr != null) {
            TreeNode right = curr.right;
            if (right == null) {
                if (--k == 0) {
                    kthLargest = curr;
                    break;
                }
                curr = curr.left;
            }
            else {
                TreeNode leftMost = getLeftMost(right, curr);
                if (leftMost.left == null) { // thread create
                    leftMost.left = curr;
                    curr = curr.right;
                }
                else { // thread break
                    leftMost.left = null;

                    if (--k == 0) {
                        kthLargest = curr;
                        break;
                    }
                    curr = curr.left;
                }
            }
        }
        
        return kthLargest;
    }

    public static TreeNode getLeftMost(TreeNode node, TreeNode curr) {
        while (node.left != null && node.left != curr) {
            node = node.left;
        }

        return node;
    }

    /****************************************************************************************************/

    // LC 230
    // kThSmallest => Using same Morris Inorder traversal
    public int kthSmallest(TreeNode root, int k) {
        TreeNode kthSmallest = KthSmallest_MorrisInorder(root, k);
        return kthSmallest.val;
    }
    
    public static TreeNode KthSmallest_MorrisInorder(TreeNode root, int k) {
        TreeNode kthSmallest = null;

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) { // Print area
                if (--k == 0) {
                    kthSmallest = curr;
                    break;
                }
                curr = curr.right;
            } else {
                TreeNode rightMost = getRightMost_02(left, curr);
                if (rightMost.right == null) { // Thread create area
                    rightMost.right = curr; // Thread create
                    curr = curr.left;
                } else { // Thread break area
                    rightMost.right = null; // Thread break
                    if (--k == 0) {
                        kthSmallest = curr;
                        break;
                    }
                    curr = curr.right;
                }
            }
        }

        return kthSmallest;
    }

    public static TreeNode getRightMost_02(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr) {
            node = node.right;
        }

        return node;
    }

    /****************************************************************************************************/

    // LC 173 => Using Morris Inorder Traversal SC O(1)
    class BSTIterator {
    
        TreeNode prev = null; // stores the data to be printed
        TreeNode curr = null; // stores future value
        
        public BSTIterator(TreeNode root) {
            this.curr = root;
        }
        
        public int next() {
            while (this.curr != null) {
                TreeNode left = this.curr.left;
                if (left == null) {
                    prev = this.curr;
                    this.curr = this.curr.right;
                    break;
                }
                else {
                    TreeNode rightMost = getRightMost(left, this.curr);
                    if (rightMost.right == null) { // thread create
                        rightMost.right = this.curr;
                        this.curr = this.curr.left;
                    }
                    else {
                        rightMost.right = null;
                        prev = this.curr;
                        this.curr = this.curr.right;
                        break;
                    }
                }
            }
            
            return prev.val;
        }
        
        private static TreeNode getRightMost(TreeNode node, TreeNode curr) {
            while (node.right != null && node.right != curr) {
                node = node.right;
            }
    
            return node;
        }
        
        public boolean hasNext() {
            return curr != null;
        }
    }

    /****************************************************************************************************/

    // This DLL will be sorted => Since BST inorder is sorted
    public static TreeNode BST_To_DLL_MorrisInorder(TreeNode root) {
        TreeNode dummy = new TreeNode();
        TreeNode prev = dummy; // prev, curr logic to link

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                prev.right = curr;
                curr.left = prev;
                prev = curr;
                curr = curr.right;
            }
            else {
                TreeNode rightMost = getRightMost_03(left, curr);
                if (rightMost.right == null) { // thread create
                    rightMost.right = curr;
                    curr = curr.left;
                }
                else { // thread break
                    rightMost.right = null;
                    prev.right = curr;
                    curr.left = prev;
                    prev = curr;
                    curr = curr.right;
                }
            }
        }

        TreeNode head = dummy.right; 
        dummy.right = null; // Must break this link from head, since DLL we can reach back to dummy node, so "break link"
        return head;
    }

    public static TreeNode getRightMost_03(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr) {
            node = node.right;
        }

        return node;
    }

    /****************************************************************************************************/

    public static TreeNode BST_To_CDLL_MorrisInorder(TreeNode root) {
        // The same code as above in BST to DLL, only change in bottom 2 lines
        TreeNode dummy = new TreeNode();
        TreeNode prev = dummy; // prev, curr logic to link

        TreeNode curr = root;
        while (curr != null) {
            TreeNode left = curr.left;
            if (left == null) {
                prev.right = curr;
                curr.left = prev;
                prev = curr;
                curr = curr.right;
            }
            else {
                TreeNode rightMost = getRightMost_04(left, curr);
                if (rightMost.right == null) { // thread create
                    rightMost.right = curr;
                    curr = curr.left;
                }
                else { // thread break
                    rightMost.right = null;
                    prev.right = curr;
                    curr.left = prev;
                    prev = curr;
                    curr = curr.right;
                }
            }
        }

        TreeNode head = dummy.right; 
        dummy.right = null; // Must break this link from head, since DLL we can reach back to dummy node, so "break link"

        head.left = prev; // Only change in these 2 lines
        prev.right = head;
        return head;
    }

    public static TreeNode getRightMost_04(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr) {
            node = node.right;
        }

        return node;
    }

    /****************************************************************************************************/
}
