// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right; // Children.
    private BSTree parent; // Parent pointer.

    public BSTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root!
        // and left child will always be null.
    }

    public BSTree(int address, int size, int key) {
        super(address, size, key);
    }

    private BSTree insertHelper(BSTree node, int address, int size, int key) {
        if (node == null) {
            node = new BSTree(address, size, key);
            return node;
        }
        if (key != node.key ? key < node.key : address < node.address) {
            BSTree leftChild = insertHelper(node.left, address, size, key);
            node.left = leftChild;
            leftChild.parent = node;
        } else if (key != node.key ? key > node.key : address > node.address) {
            BSTree rightChild = insertHelper(node.right, address, size, key);
            node.right = rightChild;
            rightChild.parent = node;
        }
        return node;
    }

    private BSTree getSentinel(BSTree node) {
        // trivial
        if (node == null)
            return null;
        // for all other. (takes to sentinel)
        while (node.parent != null)
            node = node.parent;
        return node;
    }

    public BSTree Insert(int address, int size, int key) {
        BSTree sentinel = getSentinel(this);
        if (sentinel.right == null) {
            BSTree newNode = new BSTree(address, size, key);
            sentinel.right = newNode;
            newNode.parent = sentinel;
            return newNode;
        }
        BSTree cur = sentinel.right;
        BSTree temp = insertHelper(cur, address, size, key);
        return temp;
    }

    public boolean Delete(Dictionary e) {
        return false;
    }

    public BSTree Find(int key, boolean exact) {
        return null;
    }

    public BSTree getFirst() {
        // gets root
        BSTree cur = getSentinel(this);
        cur = cur.right;
        // empty BST
        if (cur == null)
            return null;
        // all other cases
        while (cur.left != null)
            cur = cur.left;
        return cur;
    }

    public BSTree getNext() {

        BSTree cur = this;
        if (cur.right != null) {
            cur = cur.right;
            while (cur.left != null)
                cur = cur.left;
            return cur;
        }
        BSTree p_node = cur.parent;
        while (p_node != null && cur == p_node.right) {
            cur = p_node;
            p_node = p_node.parent;
        }
        if (p_node == null)
            return p_node;
        return p_node;
    }

    public boolean sanity() {
        return false;
    }

    public static void main(String[] args) {
        BSTree temp = new BSTree();
        temp.Insert(5, 0, 0);
        temp.Insert(3, 0, 0);
        temp.Insert(6, 0, 0);
        temp.Insert(2, 0, 0);
        temp.Insert(4, 0, 0);
        temp.Insert(1, 0, 0);
        temp.Insert(7, 0, 0);
        temp.Insert(8, 0, 0);
        int test = temp.getFirst().getNext().address;
        System.out.println(test);
    }
}
