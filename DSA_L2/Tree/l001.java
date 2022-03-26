import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class l001 {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode () {}

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

    public static int max(TreeNode root) {
        return root == null ? -(int) 1e9 : Math.max(Math.max(max(root.left), max(root.right)), root.val);
    }

    public static boolean find(TreeNode root, int data) {
        if (root == null)
            return false;
        if (root.val == data)
            return true;

        return find(root.left, data) || find(root.right, data);
    }

    /****************************************************************************************************/

    // ArrayList as return type
    public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, int data) {
        ArrayList<TreeNode> list = new ArrayList<>();

        if (root == null)
            return list;
        if (root.val == data) {
            list.add(root);
            return list;
        }

        ArrayList<TreeNode> l = nodeToRootPath(root.left, data);
        if (l.size() > 0) {
            l.add(root);
            return l;
        }
        ArrayList<TreeNode> r = nodeToRootPath(root.right, data);
        if (r.size() > 0) {
            r.add(root);
            return r;
        }

        return list;
    }

    // ArrayList as argument type
    public static boolean nodeToRootPath(TreeNode root, int data, ArrayList<TreeNode> list) {
        if (root == null)
            return false;

        if (root.val == data) {
            list.add(root);
            return true;
        }

        boolean res = nodeToRootPath(root.left, data, list) || nodeToRootPath(root.right, data, list);
        if (res)
            list.add(root);

        return res;
    }

    /****************************************************************************************************/

    // Use bucket logic
    public static void rootToAllLeafPath(TreeNode root, ArrayList<ArrayList<Integer>> ans,
            ArrayList<Integer> smallAns) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            ArrayList<Integer> base = new ArrayList<>(smallAns);
            base.add(root.val);
            ans.add(base);
            return;
        }

        smallAns.add(root.val);

        rootToAllLeafPath(root.left, ans, smallAns);
        rootToAllLeafPath(root.right, ans, smallAns);

        smallAns.remove(smallAns.size() - 1);
    }

    public static ArrayList<ArrayList<Integer>> rootToAllLeafPath(TreeNode root) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> smallAns = new ArrayList<>();

        rootToAllLeafPath(root, ans, smallAns);
        return ans;
    }

    /****************************************************************************************************/

    public static void singleChildParentInBinaryTree(TreeNode root, ArrayList<Integer> ans) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            return;
        if (root.left == null || root.right == null)
            ans.add(root.val);

        singleChildParentInBinaryTree(root.left, ans);
        singleChildParentInBinaryTree(root.right, ans);
    }

    public static ArrayList<Integer> singleChildParentInBinaryTree(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        singleChildParentInBinaryTree(root, ans);
        return ans;
    }

    /****************************************************************************************************/

    public static int countAllSingleChildParentInBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 0;

        int count = 0;
        count += countAllSingleChildParentInBinaryTree(root.left);
        count += countAllSingleChildParentInBinaryTree(root.right);

        if (root.left == null || root.right == null)
            count += 1;
        return count;
    }

    /****************************************************************************************************/

    // K Distance Away Nodes
    // Easy Solution - 1 SC Worst Case O(N) => Bcoz your tree can be a skew tree in
    // worst case thereby O(n) space
    public ArrayList<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        ArrayList<TreeNode> path = new ArrayList<>();
        nodeToRootPath(root, path, target);

        ArrayList<Integer> ans = getKLevelsDownNodes(root, k, path);
        return ans;
    }

    public static ArrayList<Integer> getKLevelsDownNodes(TreeNode root, int k, ArrayList<TreeNode> path) {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            if (k - i < 0)
                break;

            TreeNode blocker = (i == 0) ? null : path.get(i - 1);
            getkLevelsDownNodesHelper(path.get(i), k - i, ans, blocker);
        }

        return ans;
    }

    public static void getkLevelsDownNodesHelper(TreeNode root, int k, ArrayList<Integer> ans, TreeNode blocker) {
        if (root == null || root == blocker || k < 0)
            return;

        if (k == 0) {
            ans.add(root.val);
            return;
        }

        getkLevelsDownNodesHelper(root.left, k - 1, ans, blocker);
        getkLevelsDownNodesHelper(root.right, k - 1, ans, blocker);
    }

    public static boolean nodeToRootPath(TreeNode root, ArrayList<TreeNode> path, TreeNode target) {
        if (root == null)
            return false;
        if (root.val == target.val) {
            path.add(root);
            return true;
        }

        boolean res = nodeToRootPath(root.left, path, target) || nodeToRootPath(root.right, path, target);

        if (res)
            path.add(root);

        return res;
    }

    // K Distance Away Nodes
    // Space Optimized O(1) [exclusive recursion space i.e., no extra space]
    // Worst case complexity : TC: O(2n) => n to find target + n for KLevelDownNodes
    // upto root , SC: O(1) exclude recursive space
    public List<Integer> distanceK_01(TreeNode root, TreeNode target, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        distanceKHelper(root, k, target, ans);
        return ans;
    }

    public static int distanceKHelper(TreeNode root, int k, TreeNode target, ArrayList<Integer> ans) {
        if (root == null)
            return -1;

        if (root == target) {
            getkLevelsDownNodesHelper(root, k, ans, null);
            return 1;
        }

        int l = distanceKHelper(root.left, k, target, ans);
        if (l != -1) {
            getkLevelsDownNodesHelper(root, k - l, ans, root.left);
            return l + 1;
        }
        int r = distanceKHelper(root.right, k, target, ans);
        if (r != -1) {
            getkLevelsDownNodesHelper(root, k - r, ans, root.right);
            return r + 1;
        }

        return -1;
    }

    // Alternative return logic of optimized
    // public static int distanceKHelper(TreeNode root, int k, TreeNode target,
    // ArrayList<Integer> ans) {
    // if (root == null) return -1;

    // if (root == target) {
    // getkLevelsDownNodesHelper(root, k, ans, null);
    // return k-1;
    // }

    // int l = distanceKHelper(root.left, k, target, ans);
    // if (l >= 0) {
    // getkLevelsDownNodesHelper(root, l, ans, root.left);
    // return l-1;
    // }
    // int r = distanceKHelper(root.right, k, target, ans);
    // if (r >= 0) {
    // getkLevelsDownNodesHelper(root, r, ans, root.right);
    // return r-1;
    // }

    // return -1;
    // }

    /****************************************************************************************************/

    public static ArrayList<ArrayList<Integer>> burningTree(TreeNode root, int target) {
        ArrayList<TreeNode> path = new ArrayList<>();
        nodeToRootPath(root, target, path);

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            TreeNode blocker = i == 0 ? null : path.get(i - 1);
            burningTreeHelper(path.get(i), i, ans, blocker);
        }
        return ans;
    }

    public static void burningTreeHelper(TreeNode root, int time, ArrayList<ArrayList<Integer>> ans, TreeNode blocker) {
        if (root == null || root == blocker)
            return;

        if (ans.size() == time)
            ans.add(new ArrayList<Integer>());

        ans.get(time).add(root.val);

        burningTreeHelper(root.left, time + 1, ans, blocker);
        burningTreeHelper(root.right, time + 1, ans, blocker);
    }

    // Space Optimized
    public static ArrayList<ArrayList<Integer>> burningTree_01(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        burningTree_01_Helper(root, target, ans);
        return ans;
    }

    public static int burningTree_01_Helper(TreeNode root, int target, ArrayList<ArrayList<Integer>> ans) {
        if (root == null)
            return -1;

        if (root.val == target) {
            kDown(root, 0, null, ans);
            return 1;
        }

        int l = burningTree_01_Helper(root.left, target, ans);
        if (l != -1) {
            kDown(root, l, root.left, ans);
            return l + 1;
        }

        int r = burningTree_01_Helper(root.right, target, ans);
        if (r != -1) {
            kDown(root, r, root.right, ans);
            return r + 1;
        }

        return -1;
    }

    public static void kDown(TreeNode root, int time, TreeNode blocker, ArrayList<ArrayList<Integer>> ans) {
        if (root == null || root == blocker)
            return;

        if (time == ans.size())
            ans.add(new ArrayList<Integer>());

        ans.get(time).add(root.val);

        kDown(root.left, time + 1, blocker, ans);
        kDown(root.right, time + 1, blocker, ans);
    }

    // Burning Tree Variation => Fire with water
    public static ArrayList<ArrayList<Integer>> burningTree_02(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        burningTree_01_Helper(root, target, ans);
        return ans;
    }

    // -1 => fire node not found, -2 => fire can't be reached, time t > 0 => time
    // for the node to burn
    public static int burningTree_02_Helper(TreeNode root, int target, ArrayList<ArrayList<Integer>> ans,
            HashSet<Integer> water) {
        if (root == null)
            return -1;
        if (root.val == target) {
            if (!water.contains(root.val)) {
                kDown(root, 0, null, ans);
                return 1;
            } else
                return -2; // If the root is the both fire & water node
        }

        int l = burningTree_02_Helper(root.left, target, ans, water);
        if (l > 0) {
            if (!water.contains(root.val)) {
                kDown_02(root, l, root.left, ans, water);
                return l + 1;
            }
            return -2;
        }
        if (l == -2)
            return -2; // Jump straight to main root without traversing rest of tree, since fire can't
                       // be reached here

        int r = burningTree_02_Helper(root.right, target, ans, water);
        if (r > 0) {
            if (!water.contains(root.val)) {
                kDown_02(root, r, root.right, ans, water);
                return r + 1;
            }
            return -2;
        }
        if (r == -2)
            return -2;

        return -1;
    }

    public static void kDown_02(TreeNode root, int time, TreeNode blocker, ArrayList<ArrayList<Integer>> ans, HashSet<Integer> water) {
        if (root == null || root == blocker || water.contains(root.val))
            return;

        if (time == ans.size())
            ans.add(new ArrayList<Integer>());

        ans.get(time).add(root.val);

        kDown_02(root.left, time + 1, blocker, ans, water);
        kDown_02(root.right, time + 1, blocker, ans, water);
    }

    // Burning Tree Variation => Count no. of unburnt nodes, use same logic of
    // burning tree & count burnt nodes first using return count from kDown function
    // Then find total nodes in tree
    // Then unburnt nodes = total nodes - burnt nodes

    /****************************************************************************************************/

    // LC 236
    // Approach 1 (Okay Approach)
    // This approach might not run if either of (p,q) nodes doesn't exist in Tree
    public TreeNode lowestCommonAncestor_00(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        
        if (root == p || root == q) 
            return root;
        
        TreeNode left = lowestCommonAncestor_00(root.left, p, q);
        TreeNode right = lowestCommonAncestor_00(root.right, p, q);
        
        if (left != null && right != null)
            return root;
        else if (left != null)
            return left;
        else if (right != null)
            return right;
        
        return null;
    }

    // Approach 2 (Correct Approach for all cases)
    // This approach works even if either of (p,q) node is not present in the Tree
    public TreeNode lowestCommonAncestor_01(TreeNode root, TreeNode p, TreeNode q) {
        lowestCommonAncestor_helper(root, p, q);
        return LCA;
    }
    
    TreeNode LCA = null; // GlobalVariable
    public boolean lowestCommonAncestor_helper(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) 
            return false;
        
        boolean self = false;
        if (root == p || root == q)
            self = true;
        
        boolean left = lowestCommonAncestor_helper(root.left, p, q);
        boolean right = lowestCommonAncestor_helper(root.right, p, q);
        
        if ((left && right) || (self && left) || (self && right))
            LCA = root;
        
        return left || right || self;
    }

    // Approach 3 (Correct Approach for all cases)
    // If don't want global variable use Dummy node or pair class. Below approach is Dummy node
    public TreeNode lowestCommonAncestor_02(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode LCA = new TreeNode(); // Dummy node to escape LCA as  a global variable
        lowestCommonAncestor_helper(root, p, q, LCA);
        return LCA.left;
    }
    
    public boolean lowestCommonAncestor_helper(TreeNode root, TreeNode p, TreeNode q, TreeNode LCA) {
        if (root == null) 
            return false;
        
        boolean self = false;
        if (root == p || root == q)
            self = true;
        
        boolean left = lowestCommonAncestor_helper(root.left, p, q, LCA);
        boolean right = lowestCommonAncestor_helper(root.right, p, q, LCA);
        
        if ((left && right) || (self && left) || (self && right))
            LCA.left = root;
        
        return left || right || self;
    }

    /****************************************************************************************************/
}