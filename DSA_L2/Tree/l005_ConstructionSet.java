import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;

public class l005_ConstructionSet {

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
    // Refer notes for these formulas
    // TC O(n^2) => Building O(n) & searching idx O(n), since worst case skew tree
    public static TreeNode constructBTFrom_Pre_In(int[] preorder, int[] inorder) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        return constructBTFrom_Pre_In(preorder, inorder, isi, iei, psi, pei);
    }

    public static TreeNode constructBTFrom_Pre_In(int[] preorder, int[] inorder, int isi, int iei, int psi, int pei) {
        if (psi > pei || isi > iei)
            return null;

        TreeNode root = new TreeNode(preorder[psi]);
        
        int idx = findRoot_00(root.val, inorder, isi, iei);
        int t_elem = idx - isi; // Total elems in "left" substree from inorder

        root.left = constructBTFrom_Pre_In(preorder, inorder, isi, idx-1, psi+1, psi+t_elem); // Left call pre & in-ranges calc
        root.right = constructBTFrom_Pre_In(preorder, inorder, idx+1, iei, psi+t_elem+1, pei); // Right call pre & in-ranges calc

        return root;
    }

    public static int findRoot_00(int val, int[] inorder, int si, int ei) {
        int idx = -1;
        for (int i = si; i <= ei; i++) {
            if (val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    // Solution 2 => TC optimized solution O(contant) for search using Map<elem, idxOfElem>
    // Total TC O(n)
    // 1. Put all inorder elems in Map<elem, idxOfElem>
    // 2. Search idx in map (contant time)

    /****************************************************************************************************/

    // All unique elems only in tree
    // Refer notes for these formulas
    public static TreeNode constructBTFrom_Post_In(int[] postorder, int[] inorder) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        return constructBTFrom_Post_In(postorder, inorder, isi, iei, psi, pei);
    }

    public static TreeNode constructBTFrom_Post_In(int[] postorder, int[] inorder, int isi, int iei, int psi, int pei) {
        if (psi > pei || isi > iei)
            return null;

        TreeNode root = new TreeNode(postorder[pei]);
        
        int idx = findRoot_01(root.val, inorder, isi, iei);
        int t_elem = idx - isi; // Total elems in "right" substree from inorder

        root.left = constructBTFrom_Post_In(postorder, inorder, isi, idx-1, psi, psi+t_elem-1); // Left call pre & in-ranges calc
        root.right = constructBTFrom_Post_In(postorder, inorder, idx+1, iei, psi+t_elem, pei-1); // Right call pre & in-ranges calc

        return root;
    }

    public static int findRoot_01(int val, int[] inorder, int si, int ei) {
        int idx = -1;
        for (int i = si; i <= ei; i++) {
            if (val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    
    // Solution 2 => TC optimized solution O(contant) for search using Map<elem, idxOfElem>
    // Total TC O(n)
    // 1. Put all inorder elems in Map<elem, idxOfElem>
    // 2. Search idx in map (contant time)

    /****************************************************************************************************/

    // All unique elems only in tree
    // Refer notes for these formulas
    public static TreeNode constructBTFrom_Pre_Post(int[] preorder, int[] postorder) {
        int n = preorder.length;
        int prsi = 0, prei = n-1, posi = 0, poei = n-1;
        return constructBTFrom_Pre_Post(preorder, postorder, prsi, prei, posi, poei);
    }

    public static TreeNode constructBTFrom_Pre_Post(int[] preorder, int[] postorder, int prsi, int prei, int posi, int poei) {
        if (prsi > prei) // Out of Bounds
            return null;

        TreeNode root = new TreeNode(preorder[prsi]);
        if (prsi == prei) // if both st & end are same points. Here next elem is out of range so return from here itself
            return root;

        int idx = findRoot_02(preorder[prsi+1], postorder, posi, poei);
        int tel = idx - posi + 1; // Total elements in leftsubtree from postorder arr

        root.left = constructBTFrom_Pre_Post(preorder, postorder, prsi+1, prsi+tel, posi, idx);
        root.right = constructBTFrom_Pre_Post(preorder, postorder, prsi+tel+1, prei, idx+1, poei-1);

        return root;
    }

    public static int findRoot_02(int val, int[] inorder, int si, int ei) {
        int idx = -1;
        for (int i = si; i <= ei; i++) {
            if (val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    
    // Solution 2 => TC optimized solution O(contant) for search using Map<elem, idxOfElem>
    // Total TC O(n)
    // 1.Put all inorder elems in Map<elem, idxOfElem>
    // 2. Search idx in map (contant time)

    /****************************************************************************************************/

    // https://www.geeksforgeeks.org/construct-tree-inorder-level-order-traversals/
    public static Node constructBTFrom_In_Level(int inorder[], int levelorder[]) {
        int isi = 0, iei = inorder.length-1, lsi = 0, lei = levelorder.length-1;
        return constructBTFrom_In_Level(inorder, levelorder, isi, iei, lsi, lei);
    }
    
    public static Node constructBTFrom_In_Level(int[] inorder, int[] levelorder, int isi, int iei, int lsi, int lei)  {
        if (isi > iei)
            return null;
        
        Node root = new Node(levelorder[lsi]);
        int idx = findRoot(inorder, levelorder[lsi], isi, iei); // root idx from inorder
        int L_tel = idx - isi, R_tel = iei - idx; // Total no. of elems
        
        int[][] nextLevelOrders = getNextLvlOrder(inorder, levelorder, isi, idx-1, lsi+1, lei, L_tel, R_tel);
        
        root.left = constructBTFrom_In_Level(inorder, nextLevelOrders[0], isi, idx-1, 0, L_tel-1);
        root.right = constructBTFrom_In_Level(inorder, nextLevelOrders[1], idx+1, iei, 0, R_tel-1);
        
        return root;
    }
    
    public static int[][] getNextLvlOrder(int[] inorder, int[] levelorder, int isi, int iei, int lsi, int lei, int L_tel, int R_tel) {
        HashSet<Integer> set = new HashSet<>(); // set contains L.ST inorder elems, helps to find next level order elems
        for (int i = isi; i <= iei; i++) {
            set.add(inorder[i]);
        }
        
        int[] L_ST_LevelOrder = new int[L_tel], R_ST_LevelOrder = new int[R_tel];
        int L_idx = 0, R_idx = 0;
        
        for (int i = lsi; i <= lei; i++) {
            if (set.contains(levelorder[i])) // L.ST elems
                L_ST_LevelOrder[L_idx++] = levelorder[i];
            else // R.ST elems
                R_ST_LevelOrder[R_idx++] = levelorder[i];
        }
        
        int[][] nextLevelOrders = new int[2][]; // Since next ST levelorder total elems count varies, kept [] empty
        nextLevelOrders[0] = L_ST_LevelOrder;
        nextLevelOrders[1] = R_ST_LevelOrder;
        return nextLevelOrders;
    }
    
    public static int findRoot(int[] inorder, int val, int si, int ei) {
        int idx = -1;
        for (int i = si; i <= ei; i++) {
            if (inorder[i] == val) {
                idx = i;
                break;
            }
        }
        
        return idx;
    }

    
    // Solution 2 => TC optimized solution O(contant) for search using Map<elem, idxOfElem>
    // 1.Put all inorder elems in Map<elem, idxOfElem>
    // 2. Search idx in map (contant time)

    /****************************************************************************************************/

    // Approach 1=====================================================================

    // Not always correct (Correct only if pre and in are correct so that tree can be formed, else go for solution 2)
    // https://www.geeksforgeeks.org/check-if-given-preorder-inorder-and-postorder-traversals-are-of-same-tree/
    // TC O(n^2) => Building O(n) & searching idx O(n), since worst case skew tree, check postorder O(n)
    public static boolean checkIf_pre_in_post_sameTree(int[] preorder, int[] inorder, int[] postorder) {
        TreeNode root = constructBTFrom_Pre_In_00(preorder, inorder);
        int[] idx = new int[1]; // static kind
        return checkPostOrder(root, postorder, idx);
    }

    public static TreeNode constructBTFrom_Pre_In_00(int[] preorder, int[] inorder) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        return constructBTFrom_Pre_In_00(preorder, inorder, isi, iei, psi, pei);
    }

    public static TreeNode constructBTFrom_Pre_In_00(int[] preorder, int[] inorder, int isi, int iei, int psi, int pei) {
        if (psi > pei || isi > iei)
            return null;

        TreeNode root = new TreeNode(preorder[psi]);
        
        int idx = findRoot_03(root.val, inorder, isi, iei);
        int t_elem = idx - isi; // Total elems in "left" substree from inorder

        root.left = constructBTFrom_Pre_In_00(preorder, inorder, isi, idx-1, psi+1, psi+t_elem); // Left call pre & in-ranges calc
        root.right = constructBTFrom_Pre_In_00(preorder, inorder, idx+1, iei, psi+t_elem+1, pei); // Right call pre & in-ranges calc

        return root;
    }

    public static int findRoot_03(int val, int[] inorder, int si, int ei) {
        int idx = -1;
        for (int i = si; i <= ei; i++) {
            if (val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    public static boolean checkPostOrder(TreeNode root, int[] postorder, int[] idx) {
        if (root == null)
            return true;

        boolean left = checkPostOrder(root.left, postorder, idx);
        if (left == false) return false;
        boolean right = checkPostOrder(root.right, postorder, idx);
        if (right == false) return false;
        
        // boolean res = true; // Alternative short code
        // res = !res || checkPostOrder(root.left, postorder, idx);
        // res = !res || checkPostOrder(root.right, postorder, idx);

        if (postorder[idx[0]++] != root.val)
            return false;

        return true;
    }

    // Approach 2===========================================================================

    // Not always tree can be built from in & pre => Their order can also be wrong so evaluate
    // TC optimized solution O(contant) for search using Map<elem, idxOfElem>

    public static boolean checkIf_pre_in_post_sameTree_00(int[] preorder, int[] inorder, int[] postorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        putInorderInMap(inorder, map); // For search in constant time while tree construction
        boolean[] isTreeCorrect = new boolean[1]; //static kind
        isTreeCorrect[0] = true;

        TreeNode root = constructBTFrom_Pre_In(preorder, inorder, isTreeCorrect, map);
        if (isTreeCorrect[0] == false) // Tree can't be formed
            return false;
        
        int[] idx = new int[1]; // static kind
        return checkPostOrder(root, postorder, idx);
    }

    public static void putInorderInMap(int[] inorder, HashMap<Integer, Integer> map) {
        for (int i = 0; i < inorder.length; i++)
            map.put(inorder[i], i);
    }

    public static TreeNode constructBTFrom_Pre_In(int[] preorder, int[] inorder, boolean[] isTreeCorrect, HashMap<Integer, Integer> map) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        return constructBTFrom_Pre_In(preorder, inorder, isi, iei, psi, pei, isTreeCorrect, map);
    }

    public static TreeNode constructBTFrom_Pre_In(int[] preorder, int[] inorder, int isi, int iei, int psi, int pei, boolean[] isTreeCorrect, HashMap<Integer, Integer> map) {
        if (psi > pei || isi > iei)
            return null;

        TreeNode root = new TreeNode(preorder[psi]);
        
        int idx = map.get(root.val); // Instead of findRoot using map (constant time)
        if (!(isi <= idx && idx <= iei)) { // root elem not in valid subtree range, so tree can't be formed, so stop building further
            isTreeCorrect[0] = false;
            return root;
        }

        int t_elem = idx - isi; // Total elems in "left" substree from inorder

        root.left = constructBTFrom_Pre_In(preorder, inorder, isi, idx-1, psi+1, psi+t_elem, isTreeCorrect, map); // Left call pre & in-ranges calc
        if (isTreeCorrect[0] == false) // In left subtree itself detected, so return right away;
            return root;
        root.right = constructBTFrom_Pre_In(preorder, inorder, idx+1, iei, psi+t_elem+1, pei, isTreeCorrect, map); // Right call pre & in-ranges calc

        return root;
    }

    public static boolean checkPostOrder_00(TreeNode root, int[] postorder, int[] idx) {
        if (root == null)
            return true;

        boolean left = checkPostOrder(root.left, postorder, idx);
        if (left == false) return false;
        boolean right = checkPostOrder(root.right, postorder, idx);
        if (right == false) return false;
        
        // boolean res = true; // Alternative short code
        // res = !res || checkPostOrder(root.left, postorder, idx);
        // res = !res || checkPostOrder(root.right, postorder, idx);

        if (postorder[idx[0]++] != root.val)
            return false;

        return true;
    }

    // Approach 3======================================================================

    //  Best approach [No need to construct tree, just imagine construction like structure]
    public static boolean checkIf_pre_in_post_sameTree_01(int[] preorder, int[] inorder, int[] postorder) {
        boolean[] isTreeCorrect = new boolean[1]; //static kind
        isTreeCorrect[0] = true;
        int[] postIdx = new int[1]; // static kind to traverse postorder arr

        constructBTFrom_Pre_In(preorder, inorder, postorder, isTreeCorrect, postIdx);
        if (isTreeCorrect[0] == false) // Tree can't be formed
            return false;

        return isTreeCorrect[0];
    }

    public static void constructBTFrom_Pre_In(int[] preorder, int[] inorder, int[] postorder, boolean[] isTreeCorrect, int[] postIdx) {
        int n = inorder.length;
        int psi = 0, pei = n-1, isi = 0, iei = n-1;
        constructBTFrom_Pre_In(preorder, inorder, postorder, isi, iei, psi, pei, isTreeCorrect, postIdx);
    }

    public static void constructBTFrom_Pre_In(int[] preorder, int[] inorder, int[] postorder, int isi, int iei, int psi, int pei, boolean[] isTreeCorrect, int[] postIdx) {
        if (psi > pei || isi > iei)
            return;

        int rootVal = preorder[psi];
        int idx = findRoot_04(rootVal, inorder, isi, iei); // Store indexes of inorder in Map for constant time
        if (idx == -1 || !(isi <= idx && idx <= iei)) { // root elem not in valid subtree range, so tree can't be formed, so stop building further
            isTreeCorrect[0] = false;
            return;
        }

        int t_elem = idx - isi; // Total elems in "left" substree from inorder

        constructBTFrom_Pre_In(preorder, inorder, postorder, isi, idx-1, psi+1, psi+t_elem, isTreeCorrect, postIdx); // Left call pre & in-ranges calc
        if (isTreeCorrect[0] == false) // In left subtree itself detected, so return right away;
            return;
        constructBTFrom_Pre_In(preorder, inorder, postorder, idx+1, iei, psi+t_elem+1, pei, isTreeCorrect, postIdx); // Right call pre & in-ranges calc

        // Check postorder
        if (postorder[postIdx[0]++] != rootVal) 
            isTreeCorrect[0] = false;
    }

    public static int findRoot_04(int val, int[] inorder, int si, int ei) {
        int idx = -1;
        for (int i = si; i <= ei; i++) {
            if (val == inorder[i]) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    /****************************************************************************************************/

    public static void main(String[] args) {
        // Given
        // Wrong tree orders
        int inorder[] = {4, 2, 5, 1, 3};
        int preorder[] = {1, 5, 4, 2, 3};
        int postorder[] = {4, 1, 2, 3, 5};
        // Correct tree orders
        // int inorder[] = {4, 2, 5, 1, 3};
        // int preorder[] = {1, 2, 4, 5, 3};
        // int postorder[] = {4, 5, 2, 3, 1};
        boolean res1 = checkIf_pre_in_post_sameTree(preorder, inorder, postorder);
        boolean res2 = checkIf_pre_in_post_sameTree_00(preorder, inorder, postorder);
        boolean res3 = checkIf_pre_in_post_sameTree_01(preorder, inorder, postorder);
        System.out.println(res1 + " " + res2 + " " + res3);

    }
}
