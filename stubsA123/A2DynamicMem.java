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

    public int Allocate(int blockSize) {
        if (blockSize <= 0)
            return -1;

        Dictionary temp = freeBlk.Find(blockSize, false);
        if (temp == null)
            return -1;
        int addr = temp.address;
        int sz = temp.size;
        if (temp.size == blockSize) {
            freeBlk.Delete(temp);
            allocBlk.Insert(addr, sz, addr);
            return addr;
        } else {
            freeBlk.Delete(temp);
            allocBlk.Insert(addr, blockSize, addr);
            freeBlk.Insert(addr + blockSize, sz - blockSize, sz - blockSize);
            return addr;
        }
    }

    public int Free(int startAddr) {
        if (startAddr < 0)
            return -1;
        Dictionary temp = allocBlk.Find(startAddr, true);
        if (temp == null)
            return -1;
        int addr = temp.address;
        int sz = temp.size;
        allocBlk.Delete(temp);
        freeBlk.Insert(addr, sz, sz);
        return 0;
    }

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
        if (cur == null)
            return;
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
                freeBlk.Insert(cur.address, cur.size + cur.getNext().size, cur.size + cur.getNext().size);
                cur.size = cur.size + cur.getNext().size;
                temp.Delete(cur.getNext());
            } else {
                cur = cur.getNext();
            }
        }
        temp = null;
        return;
    }
}