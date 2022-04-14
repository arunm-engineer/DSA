import java.util.List;
import java.util.ArrayList;

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

    public static class Node {
        int data = 0;
        Node left = null;
        Node right = null;

        public Node() {}

        public Node(int data) {
            this.data = data;
        }
    }

    public static int height(TreeNode root) {
        return root == null ? -1 : Math.max(height(root.left), height(root.right))+1;
    }

    // Approach1 => TC O(n^2) ; Get l_pot_dia ; r_pot_dia ; (lht,rht) to calc next pot_dia and return it
    // Height interms of edges
    // [Genuine Concept based approach]
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
    // [Genuine Concept based approach]
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
    // ["Farzi approach", Since static kind idea is arrived only by conceptual approach]
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

    // LC 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        
        if (root.left == null && root.right == null) // Leaf node
            return (targetSum - root.val == 0) ? true : false;
        
        boolean res = false;
        res = res || hasPathSum(root.left, targetSum - root.val);
        res = res || hasPathSum(root.right, targetSum - root.val);
        
        return res;
    }

    /****************************************************************************************************/

    // LC 113
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        pathSum(root, ans, smallAns, targetSum);
        
        return ans;
    }
    
    public static void pathSum(TreeNode root, List<List<Integer>> ans, List<Integer> smallAns, int targetSum) {
        if (root == null) 
            return;
        
        
        if (root.left == null && root.right == null) { // Leaf node
            if (targetSum - root.val == 0) {
                smallAns.add(root.val);
                ans.add(new ArrayList<Integer>(smallAns)); // New copy
                smallAns.remove(smallAns.size()-1);
            }
        }

        smallAns.add(root.val);
        pathSum(root.left, ans, smallAns, targetSum - root.val);
        pathSum(root.right, ans, smallAns, targetSum - root.val);
        smallAns.remove(smallAns.size()-1);
    }

    /****************************************************************************************************/

    // https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/
    // Approach 1 => Pair Class based
    // This will not pass all Testcases in GFG bcoz GFG Testcases assumption is wrong. Conceptually the code is correct 
    public static int maxPathSum(Node root) {
        LLPair ans = maxPathSum_LL(root);
        int LTLMaxSum = ans.LTLMaxSum, NTLMaxSum = ans.NTLMaxSum;
        // return LTLMaxSum; // Conceptually actual ans => ans.LTLMaxSum
        // But just for this question submission, since for skew type tree they have test case, which is conceptually wrong, since there are no two leaf nodes in skew tree
        return LTLMaxSum != -(int) 1e9 ? LTLMaxSum : Math.max(LTLMaxSum, NTLMaxSum);
    }
    
    public static class LLPair { // Leaf to Leaf Pair
        int LTLMaxSum = -(int) 1e9; // Leaf To Leaf Max Sum
        int NTLMaxSum = -(int) 1e9; // Node To Leaf Max Sum
        
        public LLPair() {}
    }
    
    public static LLPair maxPathSum_LL(Node root) { // Leaf to Leaf
        if (root == null)
            return new LLPair();
            
        if (root.left == null && root.right == null) {
            LLPair base = new LLPair();
            base.NTLMaxSum = root.data; // Since -(int) 1e9 will lead to wrong comparison ahead
            return base;
        }
        
        LLPair lres = maxPathSum_LL(root.left);
        LLPair rres = maxPathSum_LL(root.right);
        
        LLPair res = new LLPair();
        // 1.
        res.LTLMaxSum = Math.max(lres.LTLMaxSum, rres.LTLMaxSum);
        if (root.left != null && root.right != null) { // Edge case handled, what if L or R ST doesn't exist, since calc will go wrong
            res.LTLMaxSum = Math.max(res.LTLMaxSum, lres.NTLMaxSum + root.data + rres.NTLMaxSum);
        }
        
        // 2.
        res.NTLMaxSum = Math.max(lres.NTLMaxSum, rres.NTLMaxSum) + root.data;
        
        return res;
    }

    // Approach 2 => Static kind based

    /****************************************************************************************************/

    // LC 124
    // Approach 1 => Pair Class based
    public int maxPathSum(TreeNode root) {
        NNPair ans = maxSumPath_NN(root);
        return ans.NTNMaxSum;
    }
    
    public static class NNPair { // Node to Node Pair
        int NTNMaxSum = -(int) 1e9; // Node to Node Max Sum
        int RTNMaxSum = 0; // Root to Node Max Sum

        public NNPair() {}
    }

    public static NNPair maxSumPath_NN(TreeNode root) { // Node to Node
        if (root == null) 
            return new NNPair();

        NNPair lres = maxSumPath_NN(root.left);
        NNPair rres = maxSumPath_NN(root.right);

        // This value will help for Node to Node Path sum including root NTNMaxSum
        int RTNMaxSum = Math.max(lres.RTNMaxSum, rres.RTNMaxSum) + root.val; // max sum path including root ( one-sided, either root+left_path or root+right_path)
        // 1. Possible scenarios for Node to Node max sum path
        int NTNMaxSum = getMax(lres.NTNMaxSum, rres.NTNMaxSum, lres.RTNMaxSum + root.val + rres.RTNMaxSum, root.val, RTNMaxSum);
        // 2. Possible scenarios for Root to Node max sum path, either root+left_path or root+right_path or root alone will start a new path
        RTNMaxSum = Math.max(RTNMaxSum, root.val);
        
        NNPair myPair = new NNPair();
        myPair.RTNMaxSum = RTNMaxSum;
        myPair.NTNMaxSum = NTNMaxSum;

        return myPair;
    }

    public static int getMax(int... arr) { // Accepts any number of arguments & internally converts into an array
        int max = arr[0];
        for (int elem : arr) 
            max = Math.max(max, elem);

        return max;
    }

    // Approach 2 => Static kind based
    // This static kind would have already stored potential NTNMaxSum for the left & right ST
    public static int maxSumPath_NN(TreeNode root, int[] NTNMaxSum) {
        if (root == null)
            return 0;

        int lres = maxSumPath_NN(root.left, NTNMaxSum); // RTN in left ST
        int rres = maxSumPath_NN(root.right, NTNMaxSum); // RTN in right ST

        int RTNMaxSum = Math.max(lres, rres) + root.val;
        // 1.
        NTNMaxSum[0] = getMax(NTNMaxSum[0], lres + root.val + rres, root.val, RTNMaxSum);
        // 2.
        RTNMaxSum = Math.max(RTNMaxSum, root.val);

        return RTNMaxSum;
    }


    /****************************************************************************************************/

    // LC 968
    public int minCameraCover(TreeNode root) {
        int[] countOfCameras = new int[1];
        int res = minCameraCover(root, countOfCameras);
        if (res == -1)
            countOfCameras[0]++;
        
        return countOfCameras[0];
        
    }
    
    // -1 : Camera required, 0 : already covered, 1 : I'm a camera
    // Above faith is child telling the parent
    public static int minCameraCover(TreeNode root, int[] countOfCameras) {
        if (root == null) 
            return 0;
        
        int lres = minCameraCover(root.left, countOfCameras);
        int rres = minCameraCover(root.right, countOfCameras);
        
        if (lres == -1 || rres == -1) {
            countOfCameras[0]++;
            return 1; // Im a camera            
        }
        
        if (lres == 1 || rres == 1)
            return 0; // Im already covered
        
        return -1; // camera required
    }

    /****************************************************************************************************/

    // LC 337
    // Class based approach
    public int rob(TreeNode root) {
        RobPair ans = rob_(root);
        return Math.max(ans.amountWithRob, ans.amountWithoutRob);
    }
    
    public static class RobPair {
        // maxamount for rob or not rob on a node
        int amountWithRob = 0;
        int amountWithoutRob = 0;
        
        public RobPair () {}
        public RobPair (int amountWithRob, int amountWithoutRob) {
            this.amountWithRob = amountWithRob;
            this.amountWithoutRob = amountWithoutRob;
        }
    }
    
    public static RobPair rob_(TreeNode root) {
        if (root == null)
            return new RobPair();
        
        RobPair lpair = rob_(root.left);
        RobPair rpair = rob_(root.right);
        
        // If rob on current node
        int amountWithRob = lpair.amountWithoutRob + root.val + rpair.amountWithoutRob;
        // If not rob on current node => then max of (rob, notRob) in children since we get max either from below ST
        int amountWithoutRob = Math.max(lpair.amountWithRob, lpair.amountWithoutRob) + Math.max(rpair.amountWithRob, rpair.amountWithoutRob);
        
        RobPair mypair = new RobPair(amountWithRob, amountWithoutRob);
        return mypair;
    }

    // Array based approach
    // array stores => {amountWithRob, amountWithoutRob}

    /****************************************************************************************************/

    public int longestZigZag(TreeNode root) {
        ZigZagPair ans = longestZigZag_(root);
        return ans.maxLen;
    }
    
    public static class ZigZagPair {
        int forward = -1; // right to left direction for a node i.e. left child
        int backward = -1; // left to right direction for a node i.e. right child
        int maxLen = -1; // Since Zigzag max len may or may not start through
        
        public ZigZagPair() {}
        public ZigZagPair(int forward, int backward, int maxLen) {
            this.forward = forward;
            this.backward = backward;
            this.maxLen = maxLen;
        }
    }

    public static ZigZagPair longestZigZag_(TreeNode root) {
        if (root == null)
            return new ZigZagPair();
        
        ZigZagPair lpair = longestZigZag_(root.left);
        ZigZagPair rpair = longestZigZag_(root.right);
        
        int forward = lpair.backward + 1;
        int backward = rpair.forward + 1;
        // Longest zigzag may or may not start from root (can be found in either ST alone)
        int maxLen = getMax_00(forward, backward, lpair.maxLen, rpair.maxLen); 
            
        ZigZagPair mypair = new ZigZagPair(forward, backward, maxLen);
        return mypair;
    }
    
    // Accepts any number of arguments & internally converts into an array
    public static int getMax_00(int... arr) { 
        int max = arr[0];
        for (int elem : arr) 
            max = Math.max(max, elem);

        return max;
    }
    
    /****************************************************************************************************/

}
