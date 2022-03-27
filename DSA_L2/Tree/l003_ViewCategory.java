import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class l003_ViewCategory {

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

    public static class Node {
        int val = 0;
        Node left = null;
        Node right = null;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }
    }

    public static void levelOrder_BFS(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int level = 0;
        while (!que.isEmpty()) {
            int size = que.size();
            System.out.println(level);

            ArrayList<Integer> smallAns = new ArrayList<>();
            while (size-- > 0) {
                TreeNode rNode = que.removeFirst();
                smallAns.add(rNode.val);

                if (rNode.left != null)
                    que.add(rNode.left);
                if (rNode.right != null)
                    que.add(rNode.right);
            }

            ans.add(smallAns);
            level++;
        }

        for (ArrayList<Integer> list : ans) {
            System.out.println(list);
        }
    }

    /****************************************************************************************************/

    // No change same above level order code, just that your first value you remove
    // from queue (level-wise) is the left view element
    public static ArrayList<Integer> leftView(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rNode = que.removeFirst();

                if (rNode.left != null)
                    que.add(rNode.left);
                if (rNode.right != null)
                    que.add(rNode.right);
            }
        }

        return ans;
    }

    /****************************************************************************************************/

    // LC 199
    public List<Integer> rightSideView(TreeNode root) {
        return rightView(root);
    }

    // No change same above level order code, just that we add right child first
    // then followed by left child (just maintaining queue's functionality)
    public static ArrayList<Integer> rightView(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;

        LinkedList<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (!que.isEmpty()) {
            int size = que.size();

            ans.add(que.getFirst().val);
            while (size-- > 0) {
                TreeNode rNode = que.removeFirst();

                if (rNode.right != null)
                    que.add(rNode.right);
                if (rNode.left != null)
                    que.add(rNode.left);
            }
        }

        return ans;
    }

    /****************************************************************************************************/

    // https://classroom.pepcoding.com/myClassroom/the-placement-program-gtbit-nov-27-2020/advance-binary-tree/width-of-a-binary-tree/ojquestion
    // vLevel => vertical level
    public static void widthOrShadowOfTree(TreeNode root, int[] minMax, int vLevel) {
        if (root == null)
            return;

        minMax[0] = Math.min(minMax[0], vLevel); // Min since it's the left-most vertical level
        minMax[1] = Math.max(minMax[1], vLevel); // Max since it's the right-most vertical level

        widthOrShadowOfTree(root.left, minMax, vLevel - 1);
        widthOrShadowOfTree(root.right, minMax, vLevel + 1);
    }

    public static class VPair {
        TreeNode node;
        int vl; // vl indicates the vertical level of the node

        public VPair(TreeNode node, int vl) {
            this.node = node;
            this.vl = vl;
        }
    }

    // https://classroom.pepcoding.com/myClassroom/the-placement-program-gtbit-nov-27-2020/advance-binary-tree/vertical-order-traversal-of-a-binarytree/ojquestion
    public static ArrayList<ArrayList<Integer>> verticalOrderTraversal(TreeNode root) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>(); // Each of inner arraylist will have that vLevel's elems
        if (root == null)
            return ans;

        // First find with or shadow of the tree [i.e. vertical levels of the tree to
        // expand, so we can store elems in list accr to these vLevels]
        int[] minMax = new int[2];
        widthOrShadowOfTree(root, minMax, 0);
        int totalVLevels = minMax[1] - minMax[0] + 1; // [a,b] => b-a+1 formula to find total elems between a range by
                                                      // including those end elems also

        for (int vl = 0; vl < totalVLevels; vl++)
            ans.add(new ArrayList<>());

        LinkedList<VPair> que = new LinkedList<>();
        int startVLevel = Math.abs(minMax[0]); // why 0th => since we want to nullify the -ve value with actual vLevel
                                               // of root node
        que.addLast(new VPair(root, startVLevel));

        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) {
                VPair rpair = que.removeFirst();
                TreeNode node = rpair.node;
                int vl = rpair.vl;

                ans.get(vl).add(node.val);

                if (rpair.node.left != null)
                    que.addLast(new VPair(node.left, vl - 1));
                if (rpair.node.right != null)
                    que.addLast(new VPair(node.right, vl + 1));
            }
        }

        int count = 0;
        for (ArrayList<Integer> list : ans)
            System.out.println(count++ + " -> " + list);

        return ans;
    }

    /****************************************************************************************************/

    public static ArrayList<Integer> bottomView(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>(); // Each of inner arraylist will have that vLevel's elems
        if (root == null)
            return ans;

        // First find with or shadow of the tree [i.e. vertical levels of the tree to
        // expand, so we can store elems in list accr to these vLevels]
        int[] minMax = new int[2];
        widthOrShadowOfTree(root, minMax, 0);
        int totalVLevels = minMax[1] - minMax[0] + 1; // [a,b] => b-a+1 formula to find total elems between a range by
                                                      // including those end elems also

        for (int vl = 0; vl < totalVLevels; vl++)
            ans.add(null);

        LinkedList<VPair> que = new LinkedList<>();
        int startVLevel = Math.abs(minMax[0]); // why 0th => since we want to nullify the -ve value with actual vLevel
                                               // of root node
        que.addLast(new VPair(root, startVLevel));

        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) {
                VPair rpair = que.removeFirst();
                TreeNode node = rpair.node;
                int vl = rpair.vl;

                ans.set(vl, node.val); // Updating to every next elem in the vertical level

                if (rpair.node.left != null)
                    que.addLast(new VPair(node.left, vl - 1));
                if (rpair.node.right != null)
                    que.addLast(new VPair(node.right, vl + 1));
            }
        }

        return ans;
    }

    /****************************************************************************************************/

    public static ArrayList<Integer> topView(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>(); // Each of inner arraylist will have that vLevel's elems
        if (root == null)
            return ans;

        // First find with or shadow of the tree [i.e. vertical levels of the tree to
        // expand, so we can store elems in list accr to these vLevels]
        int[] minMax = new int[2];
        widthOrShadowOfTree(root, minMax, 0);
        int totalVLevels = minMax[1] - minMax[0] + 1; // [a,b] => b-a+1 formula to find total elems between a range by
                                                      // including those end elems also

        for (int vl = 0; vl < totalVLevels; vl++)
            ans.add(null);

        LinkedList<VPair> que = new LinkedList<>();
        int startVLevel = Math.abs(minMax[0]); // why 0th => since we want to nullify the -ve value with actual vLevel
                                               // of root node
        que.addLast(new VPair(root, startVLevel));

        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) {
                VPair rpair = que.removeFirst();
                TreeNode node = rpair.node;
                int vl = rpair.vl;

                if (ans.get(vl) == null) // Updating to elem in the vertical level only for first time
                    ans.set(vl, node.val);

                if (rpair.node.left != null)
                    que.addLast(new VPair(node.left, vl - 1));
                if (rpair.node.right != null)
                    que.addLast(new VPair(node.right, vl + 1));
            }
        }

        return ans;
    }

    /****************************************************************************************************/

    public static ArrayList<ArrayList<Integer>> diagonalOrderTraversal(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ArrayList<Integer> smallAns = new ArrayList<>();
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    smallAns.add(node.val); // Forming nodeent diagonla cluster ans to print
                    if (node.left != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.left);

                    node = node.right; // Traversing nodeent diagonal cluster
                }
            }

            ans.add(smallAns);
        }

        return ans;
    }

    // https://www.geeksforgeeks.org/diagonal-traversal-of-binary-tree/
    // Same diagonal order => But GFG doesn't wants all different diagonal levels to
    // print instead wants to have all diagonals in one list itself
    public static ArrayList<Integer> diagonalOrderTraversal_GFGtype(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    ans.add(node.val); // Forming nodeent diagonla cluster ans to print
                    if (node.left != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.left);

                    node = node.right; // Traversing nodeent diagonal cluster
                }
            }

        }

        return ans;
    }

    public static ArrayList<ArrayList<Integer>> diagonalOrderTraversal_AntiClockwise(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ArrayList<Integer> smallAns = new ArrayList<>();
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    smallAns.add(node.val); // Forming nodeent diagonla cluster ans to print
                    if (node.right != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.right);

                    node = node.left; // Traversing nodeent diagonal cluster
                }
            }

            ans.add(smallAns);
        }

        return ans;
    }

    /****************************************************************************************************/

    // Based on Diagonal Order Traversal
    public static ArrayList<Integer> diagonalView_FromBottomRight(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ans.add(null);
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    ans.set(ans.size() - 1, node.val);
                    if (node.left != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.left);

                    node = node.right; // Traversing nodeent diagonal cluster
                }
            }
        }

        return ans;
    }

    public static ArrayList<Integer> diagonalView_FromTopLeft(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ans.add(null);
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    if (ans.get(ans.size() - 1) == null)
                        ans.set(ans.size() - 1, node.val);
                    if (node.left != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.left);

                    node = node.right; // Traversing nodeent diagonal cluster
                }
            }
        }

        return ans;
    }

    public static ArrayList<Integer> diagonalViewAnticlockwise_FromTopRight(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ans.add(null);
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    if (ans.get(ans.size() - 1) == null)
                        ans.set(ans.size() - 1, node.val);
                    if (node.right != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.right);

                    node = node.left; // Traversing nodeent diagonal cluster
                }
            }
        }

        return ans;
    }

    public static ArrayList<Integer> diagonalViewAnticlockwise_FromBottomLeft(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            ans.add(null);
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    ans.set(ans.size() - 1, node.val);
                    if (node.right != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.right);

                    node = node.left; // Traversing nodeent diagonal cluster
                }
            }
        }

        return ans;
    }

    /****************************************************************************************************/

    /**
     * Questions and variations
     * BFS======================
     * 
     * Level Order Traversal
     * => Left View
     * => Right View
     * 
     * Vertical Order Traversal
     * => Top View
     * => Bottom View
     * 
     * Diagonal Order Traversal
     * \ \ \
     * \ \ \
     * \ \ \
     * \ \ \
     * => Diagonal View (Bottom Right)
     * => Diagonal View (Top Left)
     * 
     * Diagonal Order Traversal Anticlockwise
     * / / /
     * / / /
     * / / /
     * / / /
     * => Diagonal View (Bottom Left)
     * => Diagonal View (Top Right)
     * 
     */

    /****************************************************************************************************/

    public static ArrayList<Integer> verticalOrderSum(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>(); // Each of inner arraylist will have that vLevel's elems
        if (root == null)
            return ans;

        // First find with or shadow of the tree [i.e. vertical levels of the tree to
        // expand, so we can store elems in list accr to these vLevels]
        int[] minMax = new int[2];
        widthOrShadowOfTree(root, minMax, 0);
        int totalVLevels = minMax[1] - minMax[0] + 1; // [a,b] => b-a+1 formula to find total elems between a range by
                                                      // including those end elems also

        for (int vl = 0; vl < totalVLevels; vl++)
            ans.add(0);

        LinkedList<VPair> que = new LinkedList<>();
        int startVLevel = Math.abs(minMax[0]); // why 0th => since we want to nullify the -ve value with actual vLevel
                                               // of root node
        que.addLast(new VPair(root, startVLevel));

        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) {
                VPair rpair = que.removeFirst();
                TreeNode node = rpair.node;
                int vl = rpair.vl;

                ans.set(vl, ans.get(vl) + node.val); // updating vertical sum

                if (rpair.node.left != null)
                    que.addLast(new VPair(node.left, vl - 1));
                if (rpair.node.right != null)
                    que.addLast(new VPair(node.right, vl + 1));
            }
        }

        return ans;
    }

    public static ArrayList<Integer> diagonalOrderSum(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        que.add(root);

        ArrayList<Integer> ans = new ArrayList<>();
        while (!que.isEmpty()) {
            int size = que.size();

            int sum = 0;
            while (size-- > 0) { // Diagonal level which has diagonal cluster's for that particular level
                TreeNode node = que.removeFirst(); // Start node of a diagonal's cluster

                while (node != null) { // Work for nodeent diagonal
                    sum += node.val;
                    if (node.left != null) // Preparing upcoming diagonal cluster by adding in queue
                        que.addLast(node.left);

                    node = node.right; // Traversing nodeent diagonal cluster
                }
            }

            ans.add(sum);
        }

        return ans;
    }

    /****************************************************************************************************/
    // Shadowing Technique (over Tree recursive traversal
    // movement)=======================

    // Doubly Linked List
    public static class ListNode {
        int data = 0;
        ListNode prev = null;
        ListNode next = null;

        public ListNode(int data) {
            this.data = data;
        }
    }

    public static ArrayList<Integer> verticalOrderSum_Shadowing(TreeNode root) {
        ListNode node = new ListNode(0);
        verticalOrderSum_Shadow(root, node);

        ArrayList<Integer> ans = new ArrayList<>();
        getSumFromLL(node, ans);
        return ans;
    }

    public static void verticalOrderSum_Shadow(TreeNode root, ListNode node) {
        if (root == null)
            return;

        node.data += root.val; // First add data

        // Then traverse tree and shodow tree on Doubly LinkedList accr to root's left,
        // right existence
        if (root.left != null) {
            if (node.prev == null) {
                ListNode n = new ListNode(0);
                node.prev = n;
                n.next = node;
            }
            verticalOrderSum_Shadow(root.left, node.prev);
        }

        if (root.right != null) {
            if (node.next == null) {
                ListNode n = new ListNode(0);
                node.next = n;
                n.prev = node;
            }
            verticalOrderSum_Shadow(root.right, node.next);
        }
    }

    public static void getSumFromLL(ListNode node, ArrayList<Integer> ans) {
        while (node.prev != null) // Get to head of list
            node = node.prev;

        while (node != null) { // Traverse LL and add to ans List
            ans.add(node.data);
            node = node.next;
        }
    }

    /****************************************************************************************************/

    // LC 987
    // Using 2 Priority Queue (PQ) to solve
    public static List<List<Integer>> verticalOrderTraversal_00(TreeNode root) {
        // Maintaining two queue, just to ensure one level of nodes is completely done and only then moving to next level (This maintains vertical order)
        PriorityQueue<VPair> que = new PriorityQueue<>((a, b) -> {
            if (a.vl != b.vl) 
                return a.vl - b.vl;
            else // vl same
                return a.node.val - b.node.val;
        });

        PriorityQueue<VPair> childQue = new PriorityQueue<>((a, b) -> {
            if (a.vl != b.vl)
                return a.vl - b.vl;
            else 
                return a.node.val - b.node.val;
        });
        
        int[] minMax = new int[2];
        widthOrShadowOfTree(root, minMax, 0);
        int totalVLevels = minMax[1] - minMax[0] + 1;
        
        List<List<Integer>> ans = new ArrayList<>();
        for (int vl = 0; vl < totalVLevels; vl++)
            ans.add(new ArrayList<>());
            
        int startVLevel = Math.abs(minMax[0]);
        que.add(new VPair(root, startVLevel));

        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) {
                VPair rpair = que.remove();
                TreeNode node = rpair.node;
                int vl = rpair.vl;

                ans.get(vl).add(node.val);

                if (rpair.node.left != null)
                    childQue.add(new VPair(node.left, vl - 1));
                if (rpair.node.right != null)
                    childQue.add(new VPair(node.right, vl + 1));
            }

            PriorityQueue<VPair> temp = que; // obviously "que" will be empty so no need to create new reference, just swap
            que = childQue;
            childQue = temp;
        }

        return ans;
    }

    public static class VHPair {
        TreeNode node;
        int vl; // vertical level
        int hl; // horizontal level

        public VHPair(TreeNode node, int vl, int hl) {
            this.node = node;
            this.vl = vl;
            this.hl = hl;
        }
    }

    // Using 1 Priority Queue (PQ) to solve
    public static List<List<Integer>> verticalOrderTraversal_01(TreeNode root) {
        PriorityQueue<VHPair> que = new PriorityQueue<>((a, b) -> {
            if(a.hl != b.hl) 
                return a.hl - b.hl;
            else if (a.vl != b.vl) // hl same, vl not same
                return a.vl - b.vl;
            else // hl same, vl same
                return a.node.val - b.node.val;
        });
        
        int[] minMax = new int[2];
        widthOrShadowOfTree(root, minMax, 0);
        int totalVLevels = minMax[1] - minMax[0] + 1;
        
        List<List<Integer>> ans = new ArrayList<>();
        for (int vl = 0; vl < totalVLevels; vl++)
            ans.add(new ArrayList<>());
            
        int startVLevel = Math.abs(minMax[0]);
        que.add(new VHPair(root, startVLevel, 0));

        while (!que.isEmpty()) {
            int size = que.size();

            while (size-- > 0) {
                VHPair rpair = que.remove();
                TreeNode node = rpair.node;
                int vl = rpair.vl;
                int hl = rpair.hl;

                ans.get(vl).add(node.val);

                if (rpair.node.left != null)
                    que.add(new VHPair(node.left, vl - 1, hl+1));
                if (rpair.node.right != null)
                    que.add(new VHPair(node.right, vl + 1, hl+1));
            }
        }

        return ans;
    }

    /****************************************************************************************************/

}
