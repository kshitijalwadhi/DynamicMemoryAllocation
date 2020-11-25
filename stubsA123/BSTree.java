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

    // checked
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

    // checked
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

    // checked
    private BSTree getSentinel(BSTree node) {
        // trivial
        if (node == null)
            return null;
        // for all other. (takes to sentinel)
        while (node.parent != null)
            node = node.parent;
        return node;
    }

    // checked
    public boolean Delete(Dictionary e) {
        BSTree cur = getSentinel(this);
        cur = cur.right;
        if (cur == null)
            return false;
        BSTree prev = cur.parent;
        while (cur != null) {
            if (e.key == cur.key && e.address == cur.address)
                break;
            if ((cur.key < e.key) || ((cur.key == e.key) && cur.address < e.address))
                cur = cur.right;
            else
                cur = cur.left;
        }
        if (cur == null)
            return false;
        prev = cur.parent;
        if (cur.left == null && cur.right == null) {
            if (prev.left == cur)
                prev.left = null;
            else
                prev.right = null;
        } else if (cur.right == null) {
            if (prev.right == cur) {
                prev.right = cur.left;
                cur.left.parent = prev;
            } else {
                prev.left = cur.left;
                cur.left.parent = prev;
            }
        } else if (cur.left == null) {
            if (prev.right == cur) {
                prev.right = cur.right;
                cur.right.parent = prev;
            } else {
                prev.left = cur.right;
                cur.right.parent = prev;
            }
        } else {
            prev = null;
            BSTree cur2 = cur.right;
            while (cur2.left != null) {
                prev = cur2;
                cur2 = cur2.left;
            }
            cur.key = cur2.key;
            cur.address = cur2.address;
            cur.size = cur2.size;
            if (prev != null) {
                prev.left = cur2.right;
                if (cur2.right != null)
                    cur2.right.parent = prev;
            } else {
                cur.right = cur2.right;
                if (cur2.right != null)
                    cur2.right.parent = cur;
            }
        }
        return true;
    }

    // checked
    public BSTree Find(int key, boolean exact) {
        BSTree cur = getSentinel(this);
        cur = cur.right;
        // only sentinel
        if (cur == null)
            return null;
        BSTree ans = null;
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

    // checked
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

    // checked
    public BSTree getNext() {

        BSTree cur = this;
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
        BSTree p_node = cur.parent;
        while (p_node != null && cur == p_node.right) {
            cur = p_node;
            p_node = p_node.parent;
        }
        if (p_node == null)
            return p_node;
        return p_node;
    }

    private BSTree getPrev() {

        BSTree cur = this;
        // if sentinel
        if (cur.parent == null)
            return null;
        if (cur.left != null) {
            cur = cur.left;
            while (cur.right != null)
                cur = cur.right;
            return cur;
        }
        BSTree p_node = cur.parent;
        while (p_node != null && cur == p_node.left) {
            cur = p_node;
            p_node = p_node.parent;
        }
        if (p_node == null)
            return p_node;
        return p_node;
    }

    private boolean checkSentinel(BSTree node) {
        if (node.parent == null && (node.key == -1 && node.address == -1 && node.size == -1))
            return true;
        return false;
    }

    // flag = 0 for key. 1 for address
    private BSTree getMin(BSTree node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // flag = 0 for key, 1 for address
    private BSTree getMax(BSTree node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    // this should return true for sane tree
    private boolean dfshelper(BSTree node) {

        if (node == null)
            return true;

        if (node.left != null) {
            if (node.left.parent != node)
                return false;
            BSTree max = getMax(node.left);
            if (max.key > node.key || (max.key == node.key && max.address >= node.address))
                return false;
            return true;
        }
        if (node.right != null) {
            if (node.right.parent != node)
                return false;
            BSTree min = getMin(node.right);
            if (min.key < node.key || (min.key == node.key && min.address <= node.address))
                return false;
            return true;
        }
        return dfshelper(node.left) && dfshelper(node.right);
    }

    private boolean checkCycle(BSTree node) {
        if (node == null)
            return false;
        BSTree slow = node;
        BSTree fast = node;
        while (true) {
            slow = slow.parent;
            if (slow == null)
                break;
            if (fast.parent.parent != null)
                fast = fast.parent.parent;
            else
                break;
            if (fast == null || slow == null)
                break;
            if (fast.parent == null || slow.parent == null)
                break;
            if (slow == fast)
                return true;
        }
        return false;
    }

    public boolean sanity() {

        // traverse towards root. Check if cycle via parent pointer
        BSTree cur = this;
        if (cur.parent == null && (cur.key != -1 || cur.address != -1 || cur.size != -1))
            return false;
        if (checkCycle(cur))
            return false;
        // go towards parent
        while (cur.parent != null) {
            cur = cur.parent;
        }
        // check if sentinel holds.
        if (!checkSentinel(cur))
            return false;

        // perform dfs to check if node.left.parent == node || node.right.parent == node
        // && BST property holds or not. (for both key and address)
        cur = cur.right;
        if (!dfshelper(cur))
            return false;
        return true;
    }
}
