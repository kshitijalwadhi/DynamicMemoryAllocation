// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {
        super();
    }

    public A2DynamicMem(int size) {
        super(size);
    }

    public A2DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    // In A2, you need to test your implementation using BSTrees and AVLTrees.
    // No changes should be required in the A1DynamicMem functions.
    // They should work seamlessly with the newly supplied implementation of BSTrees
    // and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test
    // using BSTrees and AVLTrees.
    // Your BST (and AVL tree) implementations should obey the property that keys in
    // the left subtree <= root.key < keys in the right subtree. How is this total
    // order between blocks defined? It shouldn't be a problem when using
    // key=address since those are unique (this is an important invariant for the
    // entire assignment123 module). When using key=size, use address to break ties
    // i.e. if there are multiple blocks of the same size, order them by address.
    // Now think outside the scope of the allocation problem and think of handling
    // tiebreaking in blocks, in case key is neither of the two.

    // Algorithm:
    // 1. Create a new BST/AVT Tree indexed by address. Use AVL/BST depending on the
    // type.
    // 2. Traverse all the free blocks and add them to the tree indexed by address
    // Note that the free blocks tree will be indexed by size, therefore a new tree
    // indexed by address needs to be created
    // 3. Find the first block in the new tree (indexed by address) and then find
    // the next block
    // 4. If the two blocks are contiguous, then
    // 4.1 Merge them into a single block
    // 4.2 Remove the free blocks from the free list and the new dictionary
    // 4.3 Add the merged block into the free list and the new dictionary
    // 5. Continue traversing the new dictionary
    // 6. Once the traversal is complete, delete the new dictionary

    public void Defragment() {
        Dictionary temp;
        if (allocBlk.getClass().getName() == "BSTree") {
            temp = new BSTree();
        } else {
            temp = new AVLTree();
        }
        for (Dictionary d = freeBlk.getFirst(); d != null; d = d.getNext()) {
            temp.Insert(d.address, d.size, d.address);
        }
        Dictionary cur = temp.getFirst();
        while (cur.getNext() != null) {
            if ((cur.address + cur.size) == cur.getNext().address) {
                Dictionary dict1, dict2;
                if (allocBlk.getClass().getName() == "BSTree") {
                    dict1 = new BSTree(cur.address, cur.size, cur.size);
                    dict2 = new BSTree(cur.getNext().address, cur.getNext().size, cur.getNext().size);
                } else {
                    dict1 = new AVLTree(cur.address, cur.size, cur.size);
                    dict2 = new AVLTree(cur.getNext().address, cur.getNext().size, cur.getNext().size);
                }
                freeBlk.Delete(dict1);
                freeBlk.Delete(dict2);
                temp.Delete(cur);
                temp.Delete(cur.getNext());
                freeBlk.Insert(cur.address, cur.size + cur.getNext().size, cur.size + cur.getNext().size);
                temp.Insert(cur.address, cur.size + cur.getNext().size, cur.address);
            }
            cur = cur.getNext();
        }
        temp = null;
        return;
    }

    private void status() {
        System.out.println("Free Block:");
        for (Dictionary d = freeBlk.getFirst(); d != null; d = d.getNext()) {
            System.out.println("Address: " + d.address + ", Size: " + d.size);
        }
        System.out.println("Allocated Block:");
        for (Dictionary d = allocBlk.getFirst(); d != null; d = d.getNext()) {
            System.out.println("Address: " + d.address + ", Size: " + d.size);
        }
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        A2DynamicMem mem = new A2DynamicMem(100, 2);
        mem.Allocate(30);
        mem.status();
        mem.Allocate(30);
        mem.status();
        mem.Free(30);
        mem.status();
        // mem.Allocate(30);
        // mem.status();
        mem.Defragment();
        mem.status();
    }
}