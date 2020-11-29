import java.util.LinkedList;
import java.util.Queue;

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

    private int getHeight(AVLTree node) {
        if (node == null)
            return -1;
        return node.height;
    }

    private int max(int a, int b) {
        if (a >= b)
            return a;
        return b;
    }

    private void updateHeight(AVLTree node) {
        int left_height = getHeight(node.left);
        int right_height = getHeight(node.right);

        node.height = 1 + max(left_height, right_height);
    }

    private int balanceFactor(AVLTree node) {
        int left_height = getHeight(node.left);
        int right_height = getHeight(node.right);

        return right_height - left_height;
    }

    private AVLTree leftLeft(AVLTree node) {
        return rightRotate(node);
    }

    private AVLTree leftRight(AVLTree node) {
        node.left = leftRotate(node.left);
        return leftLeft(node);
    }

    private AVLTree rightRight(AVLTree node) {
        return leftRotate(node);
    }

    private AVLTree rightLeft(AVLTree node) {
        node.right = rightRotate(node.right);
        return rightRight(node);
    }

    private AVLTree rightRotate(AVLTree node) {
        AVLTree leftChild = node.left;
        leftChild.parent = node.parent;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
            node.left = leftChild.right;
        } else {
            node.left = null;
        }
        leftChild.right = node;
        node.parent = leftChild;

        updateHeight(node);
        updateHeight(leftChild);
        return leftChild;
    }

    private AVLTree leftRotate(AVLTree node) {
        AVLTree rightChild = node.right;
        rightChild.parent = node.parent;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
            node.right = rightChild.left;
        } else {
            node.right = null;
        }
        rightChild.left = node;
        node.parent = rightChild;

        updateHeight(node);
        updateHeight(rightChild);
        return rightChild;
    }

    private AVLTree balance(AVLTree node) {

        if (balanceFactor(node) == -2) {
            if (balanceFactor(node.left) <= 0)
                return leftLeft(node);
            else
                return leftRight(node);
        } else if (balanceFactor(node) == 2) {
            if (balanceFactor(node.right) >= 0)
                return rightRight(node);
            else
                return rightLeft(node);
        }
        return node;
    }

    private AVLTree insertHelper(AVLTree node, int address, int size, int key) {
        if (node == null) {
            node = new AVLTree(address, size, key);
            return node;
        }
        if (key != node.key ? key < node.key : address < node.address) {
            AVLTree leftChild = insertHelper(node.left, address, size, key);
            node.left = leftChild;
            leftChild.parent = node;
        } else if (key != node.key ? key > node.key : address > node.address) {
            AVLTree rightChild = insertHelper(node.right, address, size, key);
            node.right = rightChild;
            rightChild.parent = node;
        }

        updateHeight(node);

        return balance(node);
    }

    public AVLTree Insert(int address, int size, int key) {

        AVLTree sentinel = getSentinel(this);
        if (sentinel.right == null) {
            AVLTree newNode = new AVLTree(address, size, key);
            sentinel.right = newNode;
            newNode.parent = sentinel;
            return newNode;
        }

        AVLTree cur = sentinel.right;
        AVLTree temp = insertHelper(cur, address, size, key);
        sentinel.right = temp;

        return temp;
    }

    public boolean Delete(Dictionary e) {
        AVLTree cur = getSentinel(this);
        cur = cur.right;
        if (cur == null)
            return false;
        AVLTree prev = cur.parent;
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
            AVLTree cur2 = cur.right;
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
                prev = cur.parent;
            }
        }
        deleteHelper(prev);
        return true;
    }

    private void deleteHelper(AVLTree node) {
        AVLTree cur = node;
        if (cur == null)
            return;
        while (cur.parent != null) {
            updateHeight(cur);
            if (balanceFactor(cur) > -2 && balanceFactor(cur) < 2) {
                if (cur.parent.left == cur) {
                    cur.parent.left = balance(cur);
                } else {
                    cur.parent.right = balance(cur);
                }
            }
            cur = cur.parent;
        }
        if (cur == null || cur.parent == null)
            return;
    }

    public AVLTree Find(int k, boolean exact) {
        AVLTree cur = getSentinel(this);
        cur = cur.right;
        // only sentinel
        if (cur == null)
            return null;
        AVLTree ans = null;
        while (cur != null) {
            if (cur.key == k) {
                ans = cur;
                while (ans != null && ans.getPrev() != null && ans.getPrev().key == k)
                    ans = ans.getPrev();
                break;
            } else if (cur.key > k) {
                ans = cur;
                cur = cur.left;
            } else
                cur = cur.right;
        }
        if (ans == null)
            return null;
        if (exact)
            return ans.key == k ? ans : null;
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
        return true;
    }

    private void printLevelOrder() {
        Queue<AVLTree> queue = new LinkedList<AVLTree>();
        queue.add(this);
        while (!queue.isEmpty()) {
            AVLTree tempNode = queue.poll();
            System.out.print(tempNode.key + ":" + tempNode.height + " ");
            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }
            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
        }
    }

    public static void main(String[] args) {
        AVLTree temp = new AVLTree();
        // temp.Insert(5, 0, 5);
        // temp.Insert(3, 0, 3);
        // temp.Insert(7, 0, 7);
        // temp.Insert(2, 0, 2);
        // temp.Insert(4, 0, 4);
        // temp.Insert(1, 0, 1);
        // temp.Insert(6, 0, 6);
        // temp.Insert(8, 0, 8);
        // AVLTree d = new AVLTree(1, 0, 1);
        // temp.Delete(d);
        temp.Insert(0, 10, 0);
        temp.Insert(10, 10, 10);
        temp.right.printLevelOrder();
        System.out.println();
        temp.Insert(20, 10, 20);

        temp.right.printLevelOrder();
        // int count = 0;
        // for (AVLTree d = temp.getFirst(); d != null; d = d.getNext())
        // count++;
        // System.out.println(count);
    }
}
