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

    private BSTree deleteHelper(BSTree node, Dictionary e) {
        if (node == null)
            return null;
        if (e.key < node.key) {
            BSTree leftChild = deleteHelper(node.left, e);
            node.left = leftChild;
            leftChild.parent = node;
        } else if (e.key > node.key) {
            BSTree rightChild = deleteHelper(node.right, e);
            node.right = rightChild;
            rightChild.parent = node;
        } else {
            if (e.size == node.size && e.address == node.address) {
                if (node.left == null)
                    return node.right;
                else if (node.right == null)
                    return node.left;

                BSTree succ = node.getNext();
                node.address = succ.address;
                node.key = succ.key;
                node.size = succ.size;

                BSTree newRightChild = deleteHelper(node.right, succ);
                node.right = newRightChild;
                newRightChild.parent = node;
            } else
                return null;
        }
        return node;
    }

    public boolean Delete(Dictionary e) {
        BSTree cur = getSentinel(this);
        cur = cur.right;
        if (cur == null)
            return false;
        if (cur.Find(e.key, true) != null) {
            deleteHelper(cur, e);
            return true;
        }
        return false;
    }

    public BSTree Find(int key, boolean exact) {
        BSTree cur = getSentinel(this);
        cur = cur.right;
        if (cur == null)
            return null;
        BSTree ans = null;
        while (cur != null) {
            if (cur.key == key) {
                ans = cur;
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

    private void inOrder(BSTree node) {
        if (node == null)
            return;
        inOrder(node.left);
        System.out.print(node.key + " ");

        inOrder(node.right);
    }

    public static void main(String[] args) {
        BSTree temp = new BSTree();
        temp.Insert(5, 0, 5);
        temp.Insert(3, 0, 3);
        temp.Insert(6, 0, 6);
        temp.Insert(2, 0, 2);
        temp.Insert(4, 0, 4);
        temp.Insert(1, 0, 1);
        temp.Insert(7, 0, 7);
        temp.Insert(8, 0, 8);
        temp.inOrder(temp.right);
        System.out.println();
        BSTree d = new BSTree(1, 0, 1);
        temp.Delete(d);
        temp.inOrder(temp.right);
    }
}
