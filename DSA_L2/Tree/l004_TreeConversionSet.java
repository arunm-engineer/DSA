import java.util.Stack;

public class l004_TreeConversionSet {

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
    
    // https://www.geeksforgeeks.org/convert-given-binary-tree-doubly-linked-list-set-3/
    // Interms of DLL, left : prev ; right : next

    // Approach 1 : With static variable
    static Node prev = null;
    public Node bToDLL(Node root) {
        Node dummy = new Node(-1);
        prev = dummy;
    
        BT_To_DLL(root);

        Node head = dummy.right;
        head.left = dummy.right = null; // Just to nullify useless memory
        return head;
    }

    public static void BT_To_DLL(Node root) {
        if (root == null) 
            return;

        BT_To_DLL(root.left);

        Node curr = root;
        prev.right = curr;
        curr.left = prev;

        prev = curr; // Update prev pointer to move along

        BT_To_DLL(root.right);
    }

    // Without static "prev" variable using Dummy node's left as prev. (Approach 1)
    public Node bToDLL_00(Node root) {
        Node listDummy = new Node(-1);
        Node dummy = new Node(-1); // Dummy's left is acts as the "prev" pointer
        dummy.left = listDummy;
    
        BT_To_DLL(root, dummy);

        Node head = listDummy.right;
        head.left = listDummy.right = null; // Just to nullify useless memory
        return head;
    }

    public static void BT_To_DLL(Node root, Node dummy) {
        if (root == null) 
            return;

        BT_To_DLL(root.left, dummy);

        Node curr = root;
        Node prev = dummy.left;
        prev.right = curr;
        curr.left = prev;

        dummy.left = curr; // Update prev pointer to move along ; Make change in dummy's left not in prev variable to make actual change

        BT_To_DLL(root.right, dummy);
    }

    // Without static "prev" variable. Using Iteravtive Inorder traversal. (Approach 2)
    public static Node bToDLL_01(Node root) {
        Stack<Node> st = new Stack<>();
        Node dummy = new Node(-1);
        Node prev = dummy;

        addAllLeft(root, st);
        while (st.size() > 0) {
            Node curr = st.pop();
            prev.right = curr;
            curr.left = prev;

            prev = curr;

            addAllLeft(curr.right, st);
        }

        Node head = dummy.right;
        head.left = dummy.right = null; // Just to nullify useless memory
        return head;
    }

    public static void addAllLeft(Node node, Stack<Node> st) {
        while (node != null) {
            st.push(node);
            node = node.left;
        }
    }

    /****************************************************************************************************/

    // https://www.geeksforgeeks.org/convert-a-binary-tree-to-a-circular-doubly-link-list/
    // Based on same previous problem completely. Just changed head and tail pointers to be circular DLL
    public static Node bToCDLL(Node root) {
        Stack<Node> st = new Stack<>();
        Node dummy = new Node(-1);
        Node prev = dummy;

        addAllLeft_00(root, st);
        while (st.size() > 0) {
            Node curr = st.pop();
            prev.right = curr;
            curr.left = prev;

            prev = curr;

            addAllLeft_00(curr.right, st);
        }

        Node head = dummy.right;
        Node tail = prev;

        // Only change to make DLL to CDLL (circular)
        head.left = tail;
        tail.right = head;
        
        dummy.right = null; // Just to nullify useless memory
        return head;
    }

    public static void addAllLeft_00(Node node, Stack<Node> st) {
        while (node != null) {
            st.push(node);
            node = node.left;
        }
    }

    /****************************************************************************************************/

    // Convert BT to BST
    public static Node mergeDLL(Node h1, Node h2) {
        if (h1 == null || h2 == null)
            return h1 != null ? h1 : h2;

        Node dummy = new Node(-1);
        Node prev = dummy;
        Node c1 = h1, c2 = h2;

        while (c1 != null && c2 != null) {
            if (c1.data <= c2.data) {
                prev.right = c1;
                c1.left = prev;
                
                c1 = c1.right;
            }
            else {
                prev.right = c2;
                c2.left = prev;
                
                c2 = c2.right;
            }

            prev = prev.right;
        }

        if (c1 != null) {
            prev.right = c1;
            c1.left = prev;
        }
        if (c2 != null) {
            prev.right = c2;
            c2.left = prev;
        }

        Node head = dummy.right;
        head.left = dummy.right = null; // Have to nullify (detach pointers), else since in DLL we can go prev and dummy will form part of DLL
        return head;
    }

    public static Node getMidNode(Node head) {
        if (head == null || head.right == null) 
            return head;

        Node slow = head, fast = head;
        while (fast.right != null && fast.right.right != null) {
            slow = slow.right;
            fast = fast.right.right;
        }

        return slow;
    }

    public static Node sortDLL(Node head) {
        if (head == null || head.right == null)
            return head;

        Node midNode = getMidNode(head);
        Node leftDLLHead = head, rightDLLHead = midNode.right;
        midNode.right = rightDLLHead.left = null; // Detach pointers to sort next set of LeftDLL & rightDLL

        leftDLLHead = sortDLL(leftDLLHead);
        rightDLLHead = sortDLL(rightDLLHead);
        return mergeDLL(leftDLLHead, rightDLLHead);
    }

    public static void addAllLeft_01(Node node, Stack<Node> st) {
        while (node != null) {
            st.push(node);
            node = node.left;
        }
    }

    public static Node BT_TO_DLL(Node root) {
        Stack<Node> st = new Stack<>();
        Node dummy = new Node(-1);
        Node prev = dummy;

        addAllLeft_01(root, st);
        while (st.size() > 0) {
            Node curr = st.pop();
            prev.right = curr;
            curr.left = prev;

            prev = curr;

            addAllLeft_01(curr.right, st);
        }

        Node head = dummy.right;
        head.left = dummy.right = null; // Detach pointers
        return head;
    }

    public static Node sortedDLL_To_BST(Node head) {
        if (head == null || head.right == null) 
            return head;

        Node root = getMidNode(head);
        Node leftDLLHead = head, rightDLLHead = root.right;

        root.left.right = root.right.left = null; // Nullify both ends from root's left & right
        root.left = root.right = null;

        root.left = sortedDLL_To_BST(leftDLLHead);
        root.right = sortedDLL_To_BST(rightDLLHead);

        return root;
    }

    // Question solution starts from here
    public static Node BT_To_BST(Node root) {
        // 1. BT to DLL
        Node head = BT_TO_DLL(root);
        // 2. Sort DLL
        head = sortDLL(head);
        // 3. Sorted DLL to BST
        return sortedDLL_To_BST(head);
    }

    /****************************************************************************************************/
}
