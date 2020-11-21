// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {

    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return;
    }

    // In A1, you need to implement the Allocate and Free functions for the class
    // A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only
    // (A1List.java).

    public int Allocate(int blockSize) {
        if (blockSize <= 0)
            return -1;

        Dictionary temp = freeBlk.Find(blockSize, false);
        if (temp == null)
            return -1;
        if (temp.size == blockSize) {
            freeBlk.Delete(temp);
            allocBlk.Insert(temp.address, temp.size, temp.address);
            return temp.address;
        } else {
            freeBlk.Delete(temp);
            allocBlk.Insert(temp.address, blockSize, temp.address);
            freeBlk.Insert(temp.address + blockSize, temp.size - blockSize, temp.size - blockSize);
            return temp.address;
        }
    }

    public int Free(int startAddr) {
        if (startAddr < 0)
            return -1;
        Dictionary temp = allocBlk.Find(startAddr, true);
        if (temp == null)
            return -1;
        allocBlk.Delete(temp);
        freeBlk.Insert(temp.address, temp.size, temp.size);
        return 0;
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
    }

    public static void main(String[] args) {
        A1DynamicMem mem = new A1DynamicMem(100, 1);
        mem.Allocate(30);
        mem.status();
        mem.Allocate(30);
        mem.status();
        mem.Free(30);
        mem.status();
        mem.Allocate(30);
        mem.status();
    }
}