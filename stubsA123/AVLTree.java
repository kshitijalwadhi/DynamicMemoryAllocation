// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {

    private AVLTree left, right; // Children.
    private AVLTree parent; // Parent pointer.
    private int height; // The height of the subtree

    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root!
        // and left child will always be null.

    }

    public AVLTree(int address, int size, int key) {
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions.
    // Some of the functions may be directly inherited from the BSTree class and
    // nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    // checked
    private AVLTree getSentinel(AVLTree node) {
        // trivial
        if (node == null)
            return null;
        // for all other. (takes to sentinel)
        while (node.parent != null)
            node = node.parent;
        return node;
    }

    public AVLTree Insert(int address, int size, int key) {
        return null;
    }

    public boolean Delete(Dictionary e) {
        return false;
    }

    public AVLTree Find(int k, boolean exact) {
        AVLTree cur = getSentinel(this);
        cur = cur.right;
        // only sentinel
        if (cur == null)
            return null;
        AVLTree ans = null;
        while (cur != null) {
            if (cur.key == key) {
                ans = cur;
                while (ans != null && ans.getPrev() != null && ans.getPrev().key == key)
                    ans = ans.getPrev();
                break;
            } else if (cur.key > key) {
                ans = cur;
                cur = cur.left;
            } else
                cur = cur.right;
        }
        if (ans == null)
            return null;
        if (exact)
            return ans.key == key ? ans : null;
        return ans;
    }

    public AVLTree getFirst() {
        // gets root
        AVLTree cur = getSentinel(this);
        cur = cur.right;
        // empty BST
        if (cur == null)
            return null;
        // all other cases
        while (cur.left != null)
            cur = cur.left;
        return cur;
    }

    public AVLTree getNext() {
        AVLTree cur = this;
        // if sentinel
        if (cur.parent == null)
            return null;
        // if right subtree present. go right then keep going left
        if (cur.right != null) {
            cur = cur.right;
            while (cur.left != null)
                cur = cur.left;
            return cur;
        }
        // if no right subtree.
        AVLTree p_node = cur.parent;
        while (p_node != null && cur == p_node.right) {
            cur = p_node;
            p_node = p_node.parent;
        }
        if (p_node == null)
            return p_node;
        return p_node;
    }

    private AVLTree getPrev() {

        AVLTree cur = this;
        // if sentinel
        if (cur.parent == null)
            return null;
        if (cur.left != null) {
            cur = cur.left;
            while (cur.right != null)
                cur = cur.right;
            return cur;
        }
        AVLTree p_node = cur.parent;
        while (p_node != null && cur == p_node.left) {
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
}
