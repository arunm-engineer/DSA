// LC 146
// Using Arrays instead of HashMap, to make optimization searching in O(1). HashMap take O(lambda) time.
// But using Arrays will require predefined Space complexity, whereas HashMap was Space wise better optimized
class LRUCacheOptimized {

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

    private Node[] map; // To search in O(1), Better than HashMap

    private Node head = null;
    private Node tail = null;
    private int capacity = 0;
    private int size = 0;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new Node[(int) 1e4+1]; // pre-defined space depends on question constraints
        this.size = 0;
        this.head = null;
        this.tail = null;
    }
    
    public int get(int key) { // Make node as recent and return the value (i.e. curr state of node)
        if (map[key] == null)
            return -1;

        Node node = map[key];
        makeRecent(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map[key] != null) { // If already present, update value and make it recent
            Node node = map[key];
            node.value = value;
            makeRecent(node);
        }
        else { // Check size and then add new
            if (this.size == this.capacity) { // If size is full
                int rKey = this.head.key;
                map[rKey] = null;
                removeNode(this.head);
            }

            Node node = new Node(key, value);
            addLast(node);
            map[key] = node;
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