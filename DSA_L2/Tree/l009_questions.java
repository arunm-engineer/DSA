import java.util.LinkedList;

public class l009_questions {

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

    // LC 297
    // Approach 1 => Serialize & deserialize using Preorder
    // Same Time & space complexity
    public class Codec1 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        public static void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("# "); // To represent null
                return;
            }

            sb.append(root.val + " ");

            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] arr = data.split(" ");
            int[] idx = new int[1];
            return deserialize(arr, idx);
        }

        public static TreeNode deserialize(String[] arr, int[] idx) {
            if (idx[0] >= arr.length || arr[idx[0]].equals("#")) {
                idx[0]++;
                return null;
            }
            
            int i = idx[0]++;
            int val = Integer.parseInt(arr[i]);
            TreeNode root = new TreeNode(val);

            root.left = deserialize(arr, idx);
            root.right = deserialize(arr, idx);

            return root;
        }
    }

    // Approach 2 => Serialize & deserialize using Levelorder
    // Same Time & space complexity
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) // edge case
                return "";

            StringBuilder sb = new StringBuilder();
            LinkedList<TreeNode> que = new LinkedList<>();
            que.add(root);

            while (!que.isEmpty()) {
                TreeNode rnode = que.removeFirst();

                if (rnode == null) {
                    sb.append("# ");
                    continue;
                }
                else {
                    sb.append(rnode.val + " ");
                }

                que.addLast(rnode.left);
                que.addLast(rnode.right);
            }

            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data.length() == 0) 
                return null;

            String[] arr = data.split(" ");
            LinkedList<TreeNode> que = new LinkedList<>();
            TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
            que.addLast(root);
            int i = 1, n = arr.length;

            while (i < n) {
                TreeNode rnode = que.removeFirst();

                if (!arr[i].equals("#")) {
                    int val = Integer.parseInt(arr[i]);
                    rnode.left = new TreeNode(val);
                    que.addLast(rnode.left);
                }
                i++; // If arr[i] == null or arr[i] != null => for either case I have to keep incrementing

                if (!arr[i].equals("#")) {
                    int val = Integer.parseInt(arr[i]);
                    rnode.right = new TreeNode(val);
                    que.addLast(rnode.right);
                }
                i++;
            }

            return root;
        }
    }

    /****************************************************************************************************/

    // LC 510 [LC premium subscription question, so for Question description refer image given in the folder structure]

    // This class structure given in question
    public static class Node {
        int val = 0;
        Node left = null;
        Node right = null;
        Node parent = null;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }
    }

    public Node inorderSuccessor2(Node root) {
        Node right = root.right;
        // If right node exists then succ = leftmost of right node
        if (root.right != null) { 
            while (right.left != null) {
                right = right.left;
            }

            return right;
        }

        // If right node doesn't exist, move towards parent to find succ
        while (node.parent != null && node.parent.left != node) { // "loop runs" means they would have been predecessors
            node = node.parent;
        }

        return node.parent; // Succ => (node.parent.left = node)
    }

    /****************************************************************************************************/

    // LC 1382
    public TreeNode balanceBST(TreeNode root) {
        // Traverse in post order, and balance at each ST node, this post order will ensure an entire subtree will be balanced one-by-one
        if (root == null)
            return null;
        
        root.left = balanceBST(root.left);
        root.right = balanceBST(root.right);
        
        return getRotation(root);
    }

    public static TreeNode getRotation(TreeNode root) {
        // Note : changes already built ree so B.F can be out of range so, "<=", "=>"

        if (getBalance(root) >= 2) { // ll, lr => left child will surely exist
            if (getBalance(root.left) >= 1) { // ll, right rotation
                return getRightRotation(root);                
            }
            else { // lr
                root.left = getLeftRotation(root.left);
                return getRightRotation(root);
            }
        }
        else if (getBalance(root) <= -2) { // rr, rl => right child will surely exist
            if (getBalance(root.right) <= -1) { // rr, left rotation
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

        // Above we didn't need to rotate again since we were maintaining balance factor along with construction
        // Edge case => More rotation possible in ST since levels of node are shifted
        // Perform rotation again to balance on level shifting nodes
        B.left = getRotation(A);
        return getRotation(B);
    }

    // TC O(1)
    public static TreeNode getRightRotation(TreeNode A) {
        TreeNode B = A.left;
        TreeNode BKaRight = B.right;

        B.right = A;
        A.left = BKaRight;

        // Above we didn't need to rotate again since we were maintaining balance factor along with construction
        // Edge case => More rotation possible in ST since levels of node are shifted
        // Perform rotation again to balance on level shifting nodes
        B.right = getRotation(A);
        return getRotation(B);
    }
    
    public static int getHeight(TreeNode node) {
        return node == null ? -1 : Math.max(getHeight(node.left), getHeight(node.right))+1;
    }

    public static int getBalance(TreeNode node) {
        int lh = getHeight(node.left);
        int rh = getHeight(node.right);

        return lh - rh;
    }

    /****************************************************************************************************/

    // LC 662
    public class WidthPair {
        TreeNode node;
        int wd;
        
        public WidthPair() {}
        
        public WidthPair(TreeNode node, int wd) {
            this.node = node;
            this.wd = wd; // Represents levelorder index
        }
    }
    
    public int widthOfBinaryTree(TreeNode root) {
        LinkedList<WidthPair> que = new LinkedList<>();
        que.add(new WidthPair(root, 0));
        
        int maxWd = -(int) 1e9;
        while (!que.isEmpty()) {
            int size = que.size();
            WidthPair rpair = que.getFirst();
            
            int lWd = rpair.wd; // leftmost node's width of that level
            int rWd = rpair.wd; // rightmost node's width of that level
            while (size-- > 0) {
                rpair = que.removeFirst();
                rWd = rpair.wd;
                
                if (rpair.node.left != null) 
                    que.addLast(new WidthPair(rpair.node.left, rWd*2+1));
                if (rpair.node.right != null)
                    que.addLast(new WidthPair(rpair.node.right, rWd*2+2));
            }
            
            int currLvlWd = rWd - lWd + 1; // This currWd also represents number of node for that level
            maxWd = Math.max(maxWd, currLvlWd);
        }
        
        return maxWd;
    }

    /****************************************************************************************************/

}
