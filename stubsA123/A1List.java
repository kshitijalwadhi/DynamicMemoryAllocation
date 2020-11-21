// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    // make it private after testing.

    private A1List next; // Next Node
    private A1List prev; // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List() {
        super(-1, -1, -1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1, -1, -1); // Intiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    // pending doubt of corner case
    public A1List Insert(int address, int size, int key) {

        // current node is tail.
        if (this.next == null)
            return null;

        A1List temp = new A1List(address, size, key);
        temp.next = this.next;
        temp.next.prev = temp;
        temp.prev = this;
        this.next = temp;
        return temp;
    }

    // checked
    private boolean match(Dictionary d, A1List node) {
        if (d.key == node.key && d.address == node.address && d.size == node.size)
            return true;
        return false;
    }

    // checked
    public boolean Delete(Dictionary d) {
        if (d == null)
            return false;

        boolean flag = false;

        A1List cur = this.getFirst();

        // no element in list case
        if (cur == null)
            return false;

        // for all other cases
        while (cur.next != null) {
            if (match(d, cur)) {
                cur.prev.next = cur.next;
                cur.next.prev = cur.prev;
                flag = true;
                break;
            }
            cur = cur.next;
        }
        return flag;
    }

    // checked
    private A1List helperFind(A1List node, int k, boolean exact) {
        // reached sentinel
        if (node.next == null)
            return null;
        // if matched
        else if (exact ? node.key == k : node.key >= k)
            return node;
        // recursed to next node
        return helperFind(node.next, k, exact);
    }

    // checked
    public A1List Find(int k, boolean exact) {
        A1List cur = this.getFirst();
        if (cur == null)
            return null;
        return helperFind(cur, k, exact);
    }

    // checked
    public A1List getFirst() {

        A1List cur = this;

        // empty list case
        if ((cur.next == null && cur.prev.prev == null) || (cur.prev == null && cur.next.next == null))
            return null;

        // cur on header
        if (cur.prev == null) {
            cur = cur.next;
        }
        // cur on trailer or in between
        else {
            while (cur.prev.prev != null) {
                cur = cur.prev;
            }
        }
        return cur;
    }

    // checked
    public A1List getNext() {

        A1List cur = this;

        // if cur on trailer
        if (cur.next == null)
            return null;
        // all other cases
        if (cur.next.next != null) {
            return cur.next;
        }
        // empty list
        return null;
    }

    // checked
    private boolean checkSentinel(A1List node) {
        if ((node.prev == null || node.next == null) && (node.key == -1 && node.address == -1 && node.size == -1))
            return true;
        return false;
    }

    private boolean checkCycle(A1List node, int drn) {

        if (checkSentinel(node))
            return false;

        A1List slow, fast;
        slow = node;
        fast = node;

        while (true) {
            // drn=1 for right, 0 for left
            if (drn == 1) {
                slow = slow.next;
                if (!checkSentinel(fast.next))
                    fast = fast.next.next;
                else
                    return false;
            } else {
                slow = slow.prev;
                if (!checkSentinel(fast.prev))
                    fast = fast.prev.prev;
                else
                    return false;
            }
            if (fast == null)
                return true;

            if (checkSentinel(slow) || checkSentinel(fast))
                return false;

            if (slow == fast)
                return true;
        }
    }

    public boolean sanity() {

        A1List cur = this;

        // check if loop

        if (checkCycle(cur, 1) || checkCycle(cur, 0))
            return false;

        // empty list case
        if ((cur.next == null && cur.prev.prev == null) || (cur.prev == null && cur.next.next == null)) {
            return true;
        }

        cur = cur.getFirst();

        // check if head is (-1,-1,-1)
        if (!checkSentinel(cur.prev))
            return false;

        // check if cur.next.prev == cur
        while (cur.next != null) {
            if (cur.next.prev != cur)
                return false;
            cur = cur.next;
        }

        // check if trailer is (-1,-1,-1)
        if (!checkSentinel(cur))
            return false;

        // check if cur.prev.next == cur
        while (cur.prev != null) {
            if (cur.prev.next != cur)
                return false;
            cur = cur.prev;
        }

        return true;
    }

}
