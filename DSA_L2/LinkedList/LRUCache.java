import java.util.HashMap;

// LC 146
// Overall Complexity is O(1). But to be honest, HashMap takes O(lambda) time.
// So use Arrays to have better search complexity which is pure O(1). But Arrays needs pre-defined space,
// whereas HashMap would take less space compared to Arrays. [Refer LRUCacheOptimized]
class LRUCache {

    private class Node {
        int key, value;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    private HashMap<Integer, Node> map = new HashMap<>(); // To search in O(1)

    private Node head = null;
    private Node tail = null;
    private int capacity = 0;
    private int size = 0;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public int get(int key) { // Make node as recent and return the value (i.e. curr state of node)
        if (!map.containsKey(key))
            return -1;

        Node node = map.get(key);
        makeRecent(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) { // If already present, update value and make it recent
            Node node = map.get(key);
            node.value = value;
            makeRecent(node);
        }
        else { // Check size and then add new
            if (this.size == this.capacity) { // If size is full
                int rKey = this.head.key;
                map.remove(rKey);
                removeNode(this.head);
            }

            Node node = new Node(key, value);
            addLast(node);
            map.put(key, node);
        }
    }

    private void makeRecent(Node node) {
        removeNode(node);
        addLast(node);
    }

    private void addLast(Node node) {
        if (this.size == 0) {
            this.head = this.tail = node;
        }
        else {
            this.tail.next = node;
            node.prev = this.tail;
            this.tail = node;
        }

        this.size++;
    }

    private void removeNode(Node node) { // We do not trigger this method when the list is empty
        if (this.size == 1) {
            this.head = this.tail = null;
        }
        else {
            Node prev = node.prev;
            Node forw = node.next;

            if (prev == null) {
                this.head = node.next;
                forw.prev = null;
            }
            else if (forw == null) {
                this.tail = prev;
                prev.next = null;
            }
            else {
                prev.next = forw;
                forw.prev = prev;
            }

            node.prev = node.next = null;
        }

        this.size--;
    }
}